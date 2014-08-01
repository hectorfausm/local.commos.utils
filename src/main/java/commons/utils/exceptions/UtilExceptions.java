package commons.utils.exceptions;

public enum UtilExceptions {
	//Excepciones del manejador de la base de datos
	NO_DRIVER_FOUND("DB001","No se ha encontrado el driver indicado"),
	CONECT_EXCEPTION("DB002","Ha surgido un problema al conectarse en a la base de datos"),
	CLOSE_DB_EXCEPTION("DB003","Ha surgido un problema al cerrar la base de datos"),
	CREATE_RESULT_SATATEMENT("DB004","Ha surgido un problema al crear el  objeto recolector"
			+ " de resultados"),
	QUERY_UPDATE_ERROR("DB005","Excepci贸n producida al ejecutar la query de modificaci贸n"),
	EXECUTE_QUERY("DB006","Excepci贸n producida al ejecutar la query de recuperaci贸n de datos"),
	PREPARE_FUNCTION_ERROR("DB007","Error al preparar la llamada de la funci髇"),
	
	
	//Excepciones del manejador del fichero de propiedades
	NO_FILE_PROPERTIES("FP001","EL fichero de propiedades no existe"),
	ON_CHARGE_FILE_PROPERTIES("FP002","Excepci贸n al cargar el fichero de propiedades"),
	NO_PROPERTIE_FOUND("FP003","La propiedad que intenta buscar no existe"),
	CLOSE_PROPERTIES_FILE("FP004","Exepci贸n producida al cerrar el fichero de propiedades"),
	
	//Excepciones del Enmuerado de tipos de bases de datos
	NO_TYPE_FOUND("DT001","No se ha encontrado el tipo representado por la cadena");
	
	
	private String errorCode,errorMessage;

	private UtilExceptions(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
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
