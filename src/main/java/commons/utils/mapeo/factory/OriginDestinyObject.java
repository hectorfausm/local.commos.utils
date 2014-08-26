package commons.utils.mapeo.factory;

/**
 * Clase que trata la relación entre los OBjetos {@link OriginObject} y {@link DestinyObject}
 * */
public class OriginDestinyObject {
	
	private String origin;
	private String destiny;
	
	/**
	 * Constructor
	 * @param origin Identificador de origen
	 * @param destiny Identificador de destino
	 * */
	public OriginDestinyObject(String origin, String destiny) {
		this.origin = origin;
		this.destiny = destiny;
	}
	
	//Accedentes
	public String getDestiny() {
		return destiny;
	}
	public String getOrigin() {
		return origin;
	}
}
