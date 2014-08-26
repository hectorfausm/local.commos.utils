package commons.utils.mapeo.factory;

/**
 * Clase que contiene las propiedades de un objeto de origen
 * */
public class OriginObject{
	private String id;
	private Class<?> clase;
	private Object value;
	
	/**
	 * Constructor
	 * @param id Identificador del origen
	 * @param clase Clase del origen
	 * @param value Valor del origen
	 * */
	public OriginObject(String id, Class<?> clase, Object value) {
		super();
		this.id = id;
		this.clase = clase;
		this.value = value;
	}
	
	/**
	 * Constructor por defecto
	 * */
	public OriginObject() {}

	//Accedentes
	public String getId() {
		return id;
	}
	public Object getValue() {
		return value;
	}
	public Class<?> getClase() {
		return clase;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//Método útil para visualizar las propiedades del origen en el momento de
	//la implementación del mapeo
	@Override
	public String toString() {
		return "{ID: "+id+", clase: "+clase+", value: "+value+"}";
	}
}
