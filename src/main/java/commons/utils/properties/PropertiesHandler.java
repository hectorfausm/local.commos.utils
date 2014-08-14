package commons.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import commons.utils.exceptions.UtilExceptions;
import commons.utils.exceptions.UtilsException;

/**
 * Clase que facilita el manejo con fihcheros de propiedade
 * */
public class PropertiesHandler {
	
	/** Dirección estática del fichero de propiedades*/
	private static String FILE_PROPERTIES;
	
	/** Clase {@link Properties} estática*/
	private static Properties PROPS;
	
	/** Stream establecido con el fichero de propiedades y el propgrama*/
	private static InputStream INPUT = null;

	//+++++++++++++++++  METODOS PUBLICOS  ++++++++++++++++++++++++++++++++++++++++++++++++++//
	/**
	 * Método que inicializa el manejador, si este y aestá inicializado, no hace nada.
	 * @param fileProperties Fichero de propiedades
	 * */
	public static void init(String fileProperties) {
		if (PROPS == null) {
			PROPS = new Properties();
			FILE_PROPERTIES = fileProperties;
		}
	}

	/**
	 * Método que devulve el valor de una propiedad
	 * @param key Clave identificada de la cual se quiere obtener el valor
	 * @param defaultValue Valor por defecto de la propiedad
	 * @return Devulve el valor obtenido
	 * */
	public synchronized static String loadPropertie(String key, String defaultValue)
			throws UtilsException {
		//inicia el stream con el fichero
		startPropertiesFile();
		
		//Obtiene el valor de la propiedad
		String res = PROPS.getProperty(key, defaultValue);
		if (res == null) {
			UtilExceptions exs = UtilExceptions.NO_PROPERTIE_FOUND;
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage());
		}
		
		//Cierra el sream con el fichero de propiedades
		closePropertiesFile();
		return res;
	}

	/**
	 * Método que devulve el valor de una propiedad
	 * @param key Clave identificada de la cual se quiere obtener el valor
	 * @return Devulve el valor obtenido
	 * */
	public static String loadPropertie(String key) throws UtilsException {
		return loadPropertie(key, null);
	}
	
	//---------------  METODOS PUBLICOS  --------------------------------------------------//
	
	//+++++++++++++++++  METODOS PRIVADOS  ++++++++++++++++++++++++++++++++++++++++++++++++//
	
	/**
	 * Método que cierra el fichero de propiedades
	 * */
	private static void closePropertiesFile() throws UtilsException {
		try {
			INPUT.close();
		} catch (IOException e) {
			UtilExceptions exs = UtilExceptions.CLOSE_PROPERTIES_FILE;
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage());
		}
	}

	/**
	 * Método que inicializa la conexión con el fichero de propiedades
	 * */
	private static void startPropertiesFile() throws UtilsException {
		//Antes de nada se vacía la instancia de Properties
		PROPS.clear();
		
		//Se intenta la conexión
		INPUT = PropertiesHandler.class.getClassLoader().getResourceAsStream(
				FILE_PROPERTIES);

		//Si no se consigue la conexión se lanza una excepción
		if (INPUT == null) {
			UtilExceptions exs = UtilExceptions.NO_FILE_PROPERTIES;
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage());
		}

		//load a properties file
		try {
			PROPS.load(INPUT);
		} catch (IOException e) {
			UtilExceptions exs = UtilExceptions.ON_CHARGE_FILE_PROPERTIES;
			throw new UtilsException(
				exs.getErrorCode(),
				exs.getErrorMessage(),
				e
			);
		}
	}
	
	//---------------  METODOS PRIVADOS  --------------------------------------------------//
}
