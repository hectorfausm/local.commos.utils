package commons.utils;

import org.apache.log4j.Logger;

import commons.utils.db.DBTypes;
import commons.utils.properties.PropertiesHandler;

public interface Logs{
	public static Logger DBHANDLER_LOG = Logger.getLogger(PropertiesHandler.class);
	public static Logger PROPHANDLER_LOG = Logger.getLogger(PropertiesHandler.class);
	public static Logger DBTYPE_LOG = Logger.getLogger(DBTypes.class);
	public static Logger COMPARATORS_LOG = Logger.getLogger(Comparators.class);
}
