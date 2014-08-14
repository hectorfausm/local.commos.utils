package commons.utils.exceptions;

/**
 * Excepci�n producida por el framework de utilidades
 * */
public class UtilsException extends Exception{
	private static final long serialVersionUID = 1L;
	private String codeError;
	
	/**
	 * Constructor
	 * */
	public UtilsException(String codeError) {
		this(codeError, null, null);
	}
	
	public UtilsException(String codeError, String message){
		this(codeError, message, null);
	}
	
	/**
	 * Constructor
	 * @param codeError C�digo del error
	 * @param message Mensaje del error
	 * @param cause Causa de la excepci�n
	 * */
	public UtilsException(String codeError, String message, Throwable cause){
		super(message, cause);
		this.codeError = codeError;
	}
	
	//getter
	public String getCodeError() {
		return codeError;
	}
}
