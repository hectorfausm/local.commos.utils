package commons.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import commons.utils.exceptions.UtilExceptions;
import commons.utils.exceptions.UtilsException;

public class PropertiesHandler implements commons.utils.Logs {
	private static String FILE_PROPERTIES;
	private static Properties PROPS;
	private static InputStream INPUT = null;

	public static void init(String fileProperties) {
		if (PROPS == null) {
			PROPS = new Properties();
			PropertiesHandler.FILE_PROPERTIES = fileProperties;
		}
	}

	public static String loadPropertie(String key, String defaultValue)
			throws UtilsException {
		startPropertiesFile();
		String res = PROPS.getProperty(key, defaultValue);
		if (res == null) {
			UtilExceptions exs = UtilExceptions.NO_PROPERTIE_FOUND;
			PROPHANDLER_LOG.error(exs.toString());
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage());
		}
		closePropertiesFile();
		return res;
	}

	private static void closePropertiesFile() throws UtilsException {
		try {
			INPUT.close();
		} catch (IOException e) {

			UtilExceptions exs = UtilExceptions.CLOSE_PROPERTIES_FILE;
			PROPHANDLER_LOG.error(exs.toString());
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage());
		}
	}

	public static String loadPropertie(String key) throws UtilsException {
		return loadPropertie(key, null);
	}

	private static void startPropertiesFile() throws UtilsException {
		PROPS.clear();

		INPUT = PropertiesHandler.class.getClassLoader().getResourceAsStream(
				FILE_PROPERTIES);

		if (INPUT == null) {
			UtilExceptions exs = UtilExceptions.NO_FILE_PROPERTIES;
			PROPHANDLER_LOG.error(exs.toString());
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage());
		}

		// load a properties file
		try {
			PROPS.load(INPUT);
		} catch (IOException e) {
			UtilExceptions exs = UtilExceptions.ON_CHARGE_FILE_PROPERTIES;
			PROPHANDLER_LOG.error(exs.toString());
			throw new UtilsException(exs.getErrorCode(), exs.getErrorMessage(),
					e);
		}
	}

	public String getFileProperties() {
		return FILE_PROPERTIES;
	}
}
