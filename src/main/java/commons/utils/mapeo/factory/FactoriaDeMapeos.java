package commons.utils.mapeo.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import commons.utils.mapeo.anotations.Maping;
import commons.utils.mapeo.anotations.MapingAttr;
/**
 * Clase que permite la creación de Factorías de mapeos
 * */
public abstract class FactoriaDeMapeos {
	
	/**
	 * Método de transformación de la clase
	 * @param objectToMap Objeto a mapear
	 * @param classWithMap Clase que contiene la definción del mapeado
	 * @param invested Determina que es el mapeo es invero o no
	 * @return Devulve un {@link Object} transformado según los designios de la implementación
	 * */
	public Object transformObject(Object objectToMap, Class<?> classWithMap, boolean invested) throws IllegalArgumentException,
		IllegalAccessException, NoSuchMethodException, SecurityException, InstantiationException,
		InvocationTargetException, NoSuchFieldException{
		
		//Se comprueba que la clase que contiene los mapeos, realmente contiene mapeos
		if(!haveMapingAnnotations(classWithMap)){
			return null;
		}
		
		//Obtiene los valores de origen y el mapeo del destino
		MapingObject objetoDeMapeo = obtainMapParamValues(objectToMap, classWithMap, invested);
		
		//Obtiene los valores de destino		
		List<DestinyObject> destinos = objetoDeMapeo.getDestinos();
		for (DestinyObject destinyObject : destinos) {
			List<OriginObject> origenesPorDestinos = objetoDeMapeo.getOriginObjectsFromDestinyId(destinyObject.getId());
			Object value = resolverMapeo(
				objectToMap.getClass(),
				origenesPorDestinos,
				destinyObject.getId()
			);
			destinyObject.setValor(value);
		}
		
		//Instancia el objeto
		Class<?> destinyClass;
		if(invested){
			destinyClass = classWithMap;
		}else{
			destinyClass = classWithMap.getAnnotation(Maping.class).destinyClass();
		}
		Object o = destinyClass.newInstance();
		
		//Carga los valores en el objeto
		cargarValores(o,objetoDeMapeo);
		
		return o;
	}

	/**
	 * Método que ha de implementarse y en el que se define cuál será el resultado de la transformación
	 * de cada uno de los parámetros de destino
	 * @param originClass Clase del objeto origen de los datos
	 * @param origenesPorDestinos Lista que contiene {@link OriginObject}, con la información necesario para
	 * realizar el mapeo
	 * @param idDestiny Método setter que identifica el objeto de destino
	 * @return La función debe devolver un objeto que representará el valor destino para ese identificador de destno
	 * */
	protected abstract Object resolverMapeo(Class<?> originClass, List<OriginObject> origenesPorDestinos, String idDestiny);

	/**
	 * Método que devuelve una Lista representando el mapeo entre parámetros y sus valores.
	 * Cada una de las filas está compuesta por dos objetos, el primero de ellos representará el mapeo
	 * de los nombres de los valores, y el segundo, representará el valor.
	 * @param objectFrom Objeto del origen de datos
	 * @param classWithMap clase que contiene el mapeo
	 * @param Determina si el mapeo e sinvertido o no
	 * */
	private static MapingObject obtainMapParamValues(Object objectFrom, Class<?> classWithMap, boolean invested) 
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, 
			IllegalArgumentException {
		MapingObject res = new MapingObject();
		Field[] fieldsWithMap = classWithMap.getDeclaredFields();
		
		for (Field field : fieldsWithMap) {
			if (haveMapingAnnotations(field)) {
				
				//Se mapea de una u otra forma en caso de estar invertido o no el mapeo
				mapearOrigen(field,res,invested,objectFrom);
				
				//Se mapea el destino sin valores
				mapearDestino(field,res,invested);
				
			}
		}
		//Se genera la tabla de unión entre las dos listas del objeto
		res.generarTablaIntermedia();
		
		// Si tiene herencia se llama recursivamente a la función y se cargan
		// las listas en el resultado
		if (haveInheritance(classWithMap)) {
			Annotation a = classWithMap.getAnnotation(Maping.class);
			res.addingAMappnigObject(obtainMapParamValues(objectFrom,
					((Maping) a).inheritanceClass(), invested));
		}
		return res;
	}
	
	/**
	 * Determina si existe o no herencia en las anotaciones de una clase
	 * @param classWithMap Clase que contiene la definición del mapeo
	 * */
	private static boolean haveInheritance(Class<?> classWithMap) {
		return 	classWithMap.getAnnotation(Maping.class)!=null && 
				!classWithMap.getAnnotation(Maping.class).inheritanceClass().equals(Object.class);
	}
	
	/**
	 * Clase que genera el mapeo del destino
	 * @param f {@link Field} de destino
	 * @param res {@link MapingObject} con la definición del mapeo
	 * */
	private static void mapearDestino(Field f, MapingObject res,
			boolean invested) {
		
		MapingAttr anotacion = f.getAnnotation(MapingAttr.class);
		
		String[] setDestinos = anotacion.setDestinys();
		String[] getDestinos = anotacion.getDestinys();
		String getOrigen = anotacion.getOrigin();
		String setOrigen = anotacion.setOrigin();
		
		Class<?>[] clases = anotacion.destinyClases();
		//TODO si la longitud de destinos y clases no es la misma, lanza error
		int size = clases.length;
		
		//Si el mapeo es invertido
		if(invested){
			for (int i = 0; i < size; i++) {
				res.addDestiny(
					getDestinos[i],
					setOrigen,
					f.getType()
				);
			}
			
		//Si no
		}else{
			for (int i = 0; i < size; i++) {
				res.addDestiny(
					getOrigen,
					setDestinos[i],
					clases[i]
				);
			}
		}
	}

	/**
	 * Clase que genera el mapo de origen y que obtiene los valores del objeto de origen
	 * @param f {@link Field} de origen
	 * @param res {@link MapingObject} con el mapeo
	 * @param invested Determina si el mapeo es invertido o no
	 * @param object Objeto con los valores de origen
	 * */
	private static void mapearOrigen(Field f, MapingObject res,
			boolean invested, Object object) throws NoSuchMethodException,
			InvocationTargetException, IllegalAccessException, IllegalArgumentException{
		
		MapingAttr anotacion = f.getAnnotation(MapingAttr.class);
		
		String[] getDestinos = anotacion.getDestinys();
		String getOrigen = anotacion.getOrigin();
		
		//Si el mapeo es invertido
		if(invested){
			Class<?>[] clases = anotacion.destinyClases();
			//TODO si la longitud de destinos y clases no es la misma, lanza error
			int size = clases.length;
			for (int i = 0; i < size; i++) {
				Object value = obtainValue(object,getDestinos[i]);
				res.addOrigin(
					getDestinos[i],
					clases[i],
					value
				);
			}
		//Si no
		}else{
			Object value = obtainValue(object,getOrigen);
			res.addOrigin(
				getOrigen,
				f.getClass(),
				value
			);
		}
	}

	/**
	 * Método que obtiene el valor de un {@link Field} a partir de su identificador
	 * @param object Objeto con los valores de origen de datos
	 * @param idField Identificador del field
	 * @return Devuelve el valor del campo
	 * */
	private static Object obtainValue(Object object, String idField) throws 
			NoSuchMethodException, InvocationTargetException,
			IllegalAccessException, IllegalArgumentException {
		
		Method m = object.getClass().getMethod(idField);
		Object value = m.invoke(object);
		return value;
	}
	
	/**
	 * Determina si un objeto tiene o no anotaciones de tipo Mapeo
	 * */
	private static boolean haveMapingAnnotations(Object object){
		Annotation[] originAnotations;
		boolean isMapeo = false;
		boolean isMapeoAtr = false;
		
		//Para mAnotaciones establecidas en los objetos Class
		if(object instanceof Class<?>){
			originAnotations = ((Class<?>)object).getAnnotations();
			isMapeo = true;
		
		//Para anotaciones establecuidas en los Field
		}else if(object instanceof Field){
			originAnotations = ((Field)object).getDeclaredAnnotations();
			isMapeoAtr = true;
		
		//Para anotaciones establecidas en cualquier otro Objeto
		}else{
			originAnotations = object.getClass().getAnnotations();
			isMapeo = true;
		}
		//Si anotaciones de tipo Maping
		if(isMapeo){
			return  originAnotations!=null 		&&
			    originAnotations.length>0 		&&	
			    originAnotations[0].annotationType().equals(Maping.class);
		}
		//Si hay anotaciones de tipo MapingAttr
		else if(isMapeoAtr){
			return  originAnotations!=null 	&&
		    originAnotations.length>0 		&&	
		    originAnotations[0].annotationType().equals(MapingAttr.class);
		}
		//Si no
		return false;
	}
	/**
	 * Método que carga los valores en el objeto que será devulve al usuario
	 * @param o Objeto que será devulevto al usuario
	 * @param objectoDeMapeo Objeto que contiene la construcción lógica de los mapeos
	 * */
	private void cargarValores(Object o, MapingObject objectoDeMapeo) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException{
		
		List<Field> fields = new ArrayList<Field>();
		getAllFields(fields, o.getClass());
		
		for (DestinyObject destinyObject : objectoDeMapeo.getDestinos()) {
			Method m = o.getClass().getMethod(destinyObject.getId(),destinyObject.getClase());
			m.invoke(o, destinyObject.getValor());
		}
	}

	/**
	 * Método que obtiene de forma recursiva todos los campos de una clase, incluidos sus 
	 * campos heredados
	 * @param fields Lista donde serán cargados los campos de la clase
	 * @param type Tipo de la clase
	 * */
	private static void getAllFields(List<Field> fields, Class<?> type) {
		for (Field field : type.getDeclaredFields()) {
			fields.add(field);
		}

		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}
	}
}
