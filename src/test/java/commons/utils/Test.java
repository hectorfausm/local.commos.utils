package commons.utils;

import commons.constants.DBConstants;
import commons.utils.db.DBHandler;
import commons.utils.db.DBHandler.DBHandlerSelectElemet;
import commons.utils.db.DBTypes;
import commons.utils.exceptions.UtilsException;
import commons.utils.properties.PropertiesHandler;

public class Test {
	public static void main(String[] args) {
		PropertiesHandler.init("config.properties");
		DBHandler db;
		try {
			db = new DBHandler(
					DBTypes.getDBTypes(PropertiesHandler.loadPropertie(DBConstants.PROPERTIES.DB_TYPE)),
					PropertiesHandler.loadPropertie(DBConstants.PROPERTIES.DB_DIR),
					PropertiesHandler.loadPropertie(DBConstants.PROPERTIES.DB_NAME),
					PropertiesHandler.loadPropertie(DBConstants.PROPERTIES.DB_USER_NAME),
					PropertiesHandler.loadPropertie(DBConstants.PROPERTIES.DB_PASS)
				);
			db.conect();
			DBHandlerSelectElemet dbse = db.select("select * from Book", 2, 1);
			System.out.println(dbse.getResultSet());
			System.out.println(dbse.getTotalResults());
			db.close();
		} catch (UtilsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
