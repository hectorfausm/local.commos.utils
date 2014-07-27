package commons.utils.exceptions;

public enum UtilExceptions {
	NO_DRIVER_FOUND("DB001","No se ha encontrado el driver indicado"),
	CONECT_EXCEPTION("DB002","Ha surgido un problema al conectarse en a la base de datos"),
	CLOSE_DB_EXCEPTION("DB003","Ha surgido un problema al cerrar la base de datos"),
	CREATE_RESULT_SATATEMENT("DB004","Ha surgido un problema al crear el  objeto recolector"
			+ " de resultados"),
	QUERY_UPDATE_ERROR("DB005","Excepción producida al ejecutar la query de modificación"),
	EXECUTE_QUERY("DB006","Excepción producida al ejecutar la query de recuperación de datos"),
			
	NO_FILE_PROPERTIES("FP001","EL fichero de propiedades no existe"),
	ON_CHARGE_FILE_PROPERTIES("FP002","Excepción al cargar el fichero de propiedades"),
	NO_PROPERTIE_FOUND("FP003","La propiedad que intenta buscar no existe"),
	CLOSE_PROPERTIES_FILE("FP004","Exepción producida al cerrar el fichero de propiedades"),
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
