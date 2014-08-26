package commons.utils.mapeo.factory;

/**
 * Clase que define las propiedades de un objeto de destino
 * */
public class DestinyObject{
	
	/** Identtificador del origen de datos */
	private String idOrigin;
	
	/** Identificador del destino */
	private String id;
	
	/** Clase del destino */
	private Class<?> clase;
	
	/** Valor del destino */
	private Object valor;

	/** 
	 * Constructor
	 * @param idOrigin Identificador del origen de datos
	 * @param id Identificador
	 * @param clase Clase del destino
	 * */
	public DestinyObject(String idOrigin, String id, Class<?> clase){
		this.id = id;
		this.clase = clase;
		this.idOrigin = idOrigin;
	}
	
	/**
	 * Constructor por defecto
	 * */
	public DestinyObject() {}

	//accedentes
	public void setValor(Object valor) {
		this.valor = valor;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Class<?> getClase() {
		return clase;
	}
	public String getId() {
		return id;
	}
	public String getIdOrigin() {
		return idOrigin;
	}
	public Object getValor() {
		return valor;
	}
	public void setIdOrigin(String idOrigin) {
		this.idOrigin = idOrigin;
	}
}
