package commons.utils.db;
import commons.utils.Logs;
import commons.utils.exceptions.UtilExceptions;
import commons.utils.exceptions.UtilsException;

public enum DBTypes implements Logs{
	
	MYSQL("com.mysql.jdbc.Driver","jdbc:mysql:","//","/");
	
	private String driverClass,idetifyString, firstSeparator, separator;

	private DBTypes(String driverClass, String identifyStrig, 
			String firstSeparator, String separator) {
		this.driverClass = driverClass;
		this.idetifyString = identifyStrig;
		this.firstSeparator = firstSeparator;
		this.separator = separator;
	}
	
	public String getDriverClass() {
		return driverClass;
	}
	
	public String getIdetifyString() {
		return idetifyString;
	}
	
	public String getFirstSeparator() {
		return firstSeparator;
	}
	
	public String getSeparator() {
		return separator;
	}
	
	public static DBTypes getDBTypes(String value) throws UtilsException{
		DBTypes res;
		try{
			 res = DBTypes.valueOf(value);
		}catch(IllegalArgumentException e){
			UtilExceptions ex = UtilExceptions.NO_TYPE_FOUND;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(),e);
		}
		return res;
	}
}
