package commons.utils.exceptions;

/**
 * Enumerado que contiene las excepciones producidas por el framework de utilidades
 * */
public enum UtilExceptions {
	
	//Excepciones del manejador de la base de datos
	NO_DRIVER_FOUND("DB001","No se ha encontrado el driver indicado"),
	CONECT_EXCEPTION("DB002","Ha surgido un problema al conectarse en a la base de datos"),
	CLOSE_DB_EXCEPTION("DB003","Ha surgido un problema al cerrar la base de datos"),
	CREATE_RESULT_SATATEMENT("DB004","Ha surgido un problema al crear el  objeto recolector"
			+ " de resultados"),
	QUERY_UPDATE_ERROR("DB005","Excepci贸n producida al ejecutar la query de modificaci髇"),
	EXECUTE_QUERY("DB006","Excepci贸n producida al ejecutar la query de recuperaci贸n de datos"),
	PREPARE_PROCEDURE_ERROR("DB007","Error al preparar la llamada de la funci髇"),
	INTRODUCE_PARAMETERS_ERROR("DB008","Error al introducir par醡etros en la funci髇"),
	CALL_PROCEDURE_ERROR("DB009","Error producido al llamar al procedimiento almacenado"),
	OBTAINING_OUTPUT_PROCEDURE_ERROR("DB010", "Error obtenido al obtener la salida del procedimiento almacenado"),
	
	//Excepciones del manejador del fichero de propiedades
	NO_FILE_PROPERTIES("FP001","EL fichero de propiedades no existe"),
	ON_CHARGE_FILE_PROPERTIES("FP002","Excepci贸n al cargar el fichero de propiedades"),
	NO_PROPERTIE_FOUND("FP003","La propiedad que intenta buscar no existe"),
	CLOSE_PROPERTIES_FILE("FP004","Exepci贸n producida al cerrar el fichero de propiedades"),
	
	
	//Excepciones del Enmuerado de tipos de bases de datos
	NO_TYPE_FOUND("DT001","No se ha encontrado el tipo representado por la cadena");
	
	//Propiedades
	private String errorCode,errorMessage;

	/**
	 * Constructor
	 * @param errorCode C骴igo del error
	 * @param errorMessage Error del mensaje
	 * */
	private UtilExceptions(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	//Getters
	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	
	@Override
	public String toString() {
		return "Exception: "+errorCode+"-> "+errorMessage;
	}
}
