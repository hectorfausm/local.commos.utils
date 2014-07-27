package commons.utils.exceptions;

public class UtilsException extends Exception{
	private static final long serialVersionUID = 1L;
	private String codeError;
	
	public UtilsException(String codeError) {
		this(codeError, null, null);
	}
	
	public UtilsException(String codeError, String message){
		this(codeError, message, null);
	}
	
	public UtilsException(String codeError, String message, Throwable cause){
		super(message, cause);
		this.codeError = codeError;
	}
	
	public String getCodeError() {
		return codeError;
	}
}
