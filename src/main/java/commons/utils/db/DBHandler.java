package commons.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import commons.utils.Logs;
import commons.utils.exceptions.UtilExceptions;
import commons.utils.exceptions.UtilsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler implements Logs{
	
	private DBTypes dbType;
	private String dir,dbName,user,pass;
	private Connection conn;
	
	public DBHandler(DBTypes dbType, String dir, String dbName) throws UtilsException{
		this(dbType, dir, dbName, "","");
	}
	
	public DBHandler(DBTypes dbType, String dir, String dbName,
			String user) throws UtilsException{
		this(dbType,dir,dbName,user,"");
	}
	
	public DBHandler(DBTypes dbType, String dir, String dbName,
			String user, String pass) throws UtilsException {
		this.dbType = dbType;
		this.dir = dir;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;
		
		try {
			Class.forName(this.dbType.getDriverClass());
		} catch (ClassNotFoundException e) {
			UtilExceptions ex = UtilExceptions.NO_DRIVER_FOUND;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}
	
	public void conect() throws UtilsException{
		try {
			this.conn = DriverManager.getConnection(
					 this.dbType.getIdetifyString()
					 +dbType.getFirstSeparator()
					 +dir
					 +dbType.getSeparator()
					 +dbName,user,pass);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CONECT_EXCEPTION;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}
	
	public void close() throws UtilsException{
		try {
			conn.close();
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CLOSE_DB_EXCEPTION;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}
	
	public void update(String query) throws UtilsException {
		Statement st = obtainStatement();

		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.QUERY_UPDATE_ERROR;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}
	
	private Statement obtainStatement() throws UtilsException {
		Statement st;
		try {
			st = (Statement) conn.createStatement();
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CREATE_RESULT_SATATEMENT;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
		return st;
	}

	public DBHandlerSelectElemet select(String query, int offset, int rowCount) throws UtilsException{
		Statement st = obtainStatement();
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
		long sizeCount = 0;
		try {
			ResultSet rs = st.executeQuery(query+" LIMIT "+offset+","+rowCount);
			ResultSetMetaData metas = rs.getMetaData();
			int sizeCols = metas.getColumnCount();
			
			while(rs.next()){
				Map<String,Object> aux = new HashMap<String, Object>();
				for (int i = 1; i <= sizeCols; i++) {
					aux.put(metas.getColumnName(i), rs.getObject(i));
				}
				res.add(aux);
			}
			ResultSet rsCount = st.executeQuery("SELECT COUNT(*) FROM ("+query+") AS count");
			rsCount.next();
			sizeCount = rsCount.getLong(1);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.EXECUTE_QUERY;
			DBHANDLER_LOG.error(ex);
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
		
		return new DBHandlerSelectElemet(res,sizeCount);
	}
	
	public class DBHandlerSelectElemet{
		private List<Map<String,Object>> resultSet;
		private long totalResults;
		public DBHandlerSelectElemet(List<Map<String, Object>> resultSet,
				long totalResults) {
			this.resultSet = resultSet;
			this.totalResults = totalResults;
		}
		
		public List<Map<String, Object>> getResultSet() {
			return resultSet;
		}
		
		public long getTotalResults() {
			return totalResults;
		}
	}
}
