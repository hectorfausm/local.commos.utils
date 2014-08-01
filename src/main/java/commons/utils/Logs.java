package commons.utils;

import org.apache.log4j.Logger;

import commons.utils.db.DBTypes;
import commons.utils.properties.PropertiesHandler;

/**
 * Interfaz que contiene los LOGS de la utilidades
 * */
public interface Logs{
	//LOG del manejador de bases de datos
	public static Logger DBHANDLER_LOG = Logger.getLogger(PropertiesHandler.class);
	
	//LOG del manejador de ficheros de propiedades
	public static Logger PROPHANDLER_LOG = Logger.getLogger(PropertiesHandler.class);
	
	//LOG del enumerado que determina los tipos de bases de datos
	public static Logger DBTYPE_LOG = Logger.getLogger(DBTypes.class);
	
	//LOG de las funciones de comparación
	public static Logger COMPARATORS_LOG = Logger.getLogger(Comparators.class);
}
