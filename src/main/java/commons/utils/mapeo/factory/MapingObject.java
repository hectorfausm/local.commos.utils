package commons.utils.mapeo.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import commons.utils.ListUtils;

/**
 * Clase que contiene toda la definción de los mapeos
 * */
public class MapingObject{
	
	/** Origenes de los datos */
	private List<OriginObject> origenes;
	
	/** Destinso de los datos */
	
	private List<DestinyObject> destinos;
	
	/** Relaciones entre orígenes y destinos */
	private List<OriginDestinyObject> origenesDestinos;
	
	/**
	 * Constructor, crea la listas
	 * */
	public MapingObject() {
		this.origenes = new ArrayList<OriginObject>();
		this.destinos = new ArrayList<DestinyObject>();
		this.origenesDestinos = new ArrayList<OriginDestinyObject>();
	}
	
	/**
	 * Obtiene un Objeto de Origen de datos a partir de su identificador
	 * @param id Identificador del origen a buscar
	 * @return Devulve un {@link OriginObject} si es encontrado, en caso contrario, devulve null
	 * */
	public OriginObject getOriginObjectFromId(String id){
		Collections.sort(origenes, new Orderer.OrderOriginObjects());
		OriginObject o = new OriginObject();
		o.setId(id);
		int index = Collections.binarySearch(origenes, o, new Orderer.OrderOriginObjects());
		if(index>=0){
			return origenes.get(index);
		}
		return null;
	}
	
	/**
	 * Obtiene un Objeto de Destino de datos a partir de su identificador
	 * @param id Identificador del destino a buscar
	 * @return Devulve un {@link DestinyObject} si es encontrado, en caso contrario, devulve null
	 * */
	public DestinyObject getDestinyObjectFromId(String id){
		Collections.sort(destinos, new Orderer.OrderDestinyObjects());
		DestinyObject o = new DestinyObject();
		o.setId(id);
		int index = Collections.binarySearch(destinos, o, new Orderer.OrderDestinyObjects());
		if(index>=0){
			return destinos.get(index);
		}
		return null;
	}
	
	/**
	 * Devulve los objetos de Destino con los que esta relaciondo un determinado identificador 
	 * de objeto de origen
	 * @param idOrigin Identificador de Origen
	 * @return Devulve una lista con los Objeto de Destino relacionados
	 * */
	public List<DestinyObject> getDestinyObjectsFromOriginId(String idOrigin){
		List<DestinyObject> res = new ArrayList<DestinyObject>();
		for (DestinyObject destino : destinos) {
			if(idOrigin.compareTo(destino.getIdOrigin())==0)
				res.add(destino);
		}
		return res;
	}
	
	/**
	 * Obtiene los los objeto de Origen relacionados con un determinado identificador
	 * de un objeto de destino
	 * @param destinyId Identificador del objeto de desrtino
	 * @return Devuelve una lista de {@link OriginObject} relacionados con el identificador de destino
	 * */
	public List<OriginObject> getOriginObjectsFromDestinyId(String destinyId){
		Collections.sort(origenesDestinos, new Orderer.OrderOriginDestinyObjectsByDestiny());
		Collections.sort(origenes, new Orderer.OrderOriginObjects());

		List<OriginObject> res = new ArrayList<OriginObject>();
		boolean find = false;
		int size = origenesDestinos.size();
		
		for (int i = 0; i< size;i++){
			String destinyIdAux = origenesDestinos.get(i).getDestiny();
			if(destinyIdAux.compareTo(destinyId)==0){
				find = true;
				OriginObject o = getOriginObjectFromId(origenesDestinos.get(i).getOrigin());
				if(o!=null){
					res.add(o);
				}
			}else{
				if(find){
					return res;
				}
					
			}
		}
		return res;
	}

	/**
	 * Añade un origen a la lista de orígenes
	 * @param id Identificador del origen
	 * @param clase Clase del origen de datos
	 * @param object Valor del origen de datos
	 * */
	protected void addOrigin(String id, Class<?> clase, Object object){
		origenes.add(new OriginObject(id, clase, object));
	}
	protected void addDestiny(String idOrigin, String id, Class<?> clase){
		destinos.add(new DestinyObject(idOrigin,id, clase));
	}

	protected void generarTablaIntermedia() {
		origenesDestinos = new ArrayList<OriginDestinyObject>();
		Collections.sort(destinos, new Orderer.OrderDestinyObjects());
		List<DestinyObject> listaIterable = ListUtils.cloneArrayList(destinos);
		for (DestinyObject destinyObject : listaIterable) {
			origenesDestinos.add(
				new OriginDestinyObject(
					destinyObject.getIdOrigin(),
					destinyObject.getId()
				)
			);
			if(duplicateDestiny(destinyObject.getId())){
				destinos.remove(destinyObject);
			}
		}
	}

	protected void addingAMappnigObject(MapingObject obtainMapParamValues) {
		destinos.addAll(obtainMapParamValues.getDestinos());
		for (DestinyObject destino : destinos) {
			destino.setIdOrigin(null);
			if(duplicateDestiny(destino.getId())){
				destinos.remove(destino);
			}
		}
		origenes.addAll(obtainMapParamValues.getOrigenes());
		origenesDestinos.addAll(obtainMapParamValues.getOrigenesDestinos());
	}
	
	private boolean duplicateDestiny(String id) {
		int cont = 0;
		for (DestinyObject destiny : destinos) {
			if(destiny.getId().compareTo(id)==0)
				cont++;
		}
		return cont>1;
	}
	
	//accedentes
	public List<DestinyObject> getDestinos() {
		return destinos;
	}
	public List<OriginObject> getOrigenes() {
		return origenes;
	}
	public List<OriginDestinyObject> getOrigenesDestinos() {
		return origenesDestinos;
	}
	
	@Override
	public String toString() {
		
		return "Origenes: "+origenes+"\n"+
			   "Destionos: "+destinos+"\n";
	}
}
