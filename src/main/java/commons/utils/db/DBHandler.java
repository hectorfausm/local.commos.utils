package commons.utils.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import commons.utils.exceptions.UtilExceptions;
import commons.utils.exceptions.UtilsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase para manejadora de Bases de datos
 * */
public class DBHandler {

	private DBTypes dbType;
	private String dir, dbName, user, pass;
	private Connection conn;

	/**
	 * Constructor
	 * 
	 * @param dbType
	 *            Tipo de la base de datos (MYSQL | SQL)
	 * @param dir
	 *            Dirección de la base de datos
	 * @param dbName
	 *            Nombre de la base de datos
	 * @param user
	 *            Usuario de acceso a la base de datos
	 * @param pass
	 *            Password de acceso a la base de datos
	 * */
	public DBHandler(DBTypes dbType, String dir, String dbName, String user,
			String pass) throws UtilsException {
		this.dbType = dbType;
		this.dir = dir;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;

		try {
			Class.forName(this.dbType.getDriverClass());
		} catch (ClassNotFoundException e) {
			UtilExceptions ex = UtilExceptions.NO_DRIVER_FOUND;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}

	/**
	 * Constructor para bases de datos sin contraseña ni nombre de usuario
	 * 
	 * @param dbType
	 *            Tipo de la base de datos (MYSQL | SQL)
	 * @param dir
	 *            Dirección de la base de datos
	 * @param dbName
	 *            Nombre de la base de datos
	 * */
	public DBHandler(DBTypes dbType, String dir, String dbName)
			throws UtilsException {
		this(dbType, dir, dbName, "", "");
	}

	/**
	 * Constructor para bases de datos sin contraseña
	 * 
	 * @param dbType
	 *            Tipo de la base de datos (MYSQL | SQL)
	 * @param dir
	 *            Dirección de la base de datos
	 * @param dbName
	 *            Nombre de la base de datos
	 * @param user
	 *            Usuario de acceso a la base de datos
	 * */
	public DBHandler(DBTypes dbType, String dir, String dbName, String user)
			throws UtilsException {
		this(dbType, dir, dbName, user, "");
	}

	// ++++++++++++++++++++++++++++ API
	// +++++++++++++++++++++++++++++++++++++++++++++++//

	/**
	 * Métofo que permite actualización de filas en la base de datos a partir de
	 * una query.
	 * 
	 * @param query
	 *            Query para al actualización
	 * */
	public void update(String query) throws UtilsException {
		conect();
		Statement st = obtainStatement();

		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.QUERY_UPDATE_ERROR;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
		disconect();
	}

	/**
	 * Método que permite realizar un select a al base de datos.
	 * 
	 * @param query
	 *            Query de selección
	 * @param offset
	 *            Registro a partir del cual se devulven datos
	 * @param rowCount
	 *            Número de datos a devolver
	 * @return Devuelve un objeto de tipo {@link DBHandlerSelectElemet}
	 * */
	public DBHandlerSelectElemet select(String query, int offset, int rowCount)
			throws UtilsException {
		conect();
		Statement st = obtainStatement();
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		long sizeCount = 0;
		try {
			ResultSet rs = st.executeQuery(query + " LIMIT " + offset + ","
					+ rowCount);
			ResultSetMetaData metas = rs.getMetaData();
			int sizeCols = metas.getColumnCount();

			while (rs.next()) {
				Map<String, Object> aux = new HashMap<String, Object>();
				for (int i = 1; i <= sizeCols; i++) {
					aux.put(metas.getColumnName(i), rs.getObject(i));
				}
				res.add(aux);
			}
			ResultSet rsCount = st.executeQuery("SELECT COUNT(*) FROM ("
					+ query + ") AS count");
			rsCount.next();
			sizeCount = rsCount.getLong(1);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.EXECUTE_QUERY;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
		disconect();
		return new DBHandlerSelectElemet(res, sizeCount);
	}
	
	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @throws SiaException
	 * */
	public Map<String, Object> callInternProcedure(String nameFunction)
			throws UtilsException {
		return (Map<String, Object>)callInternProcedure(nameFunction, null, null, null, null);
	}

	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @param params
	 *            Mapa de objetos con los par�metros out del PL/SQL, siendo key
	 *            el nombre del parametro en el Procedure PL/SQL y value su
	 *            valor o tipo
	 * @throws SiaException
	 * */
	public Map<String, Object> callInternProcedure(String nameFunction,
			Map<String, Object> params)
			throws UtilsException {
		return (Map<String, Object>)callInternProcedure(nameFunction, params, null, null, null);
	}
	
	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @param params
	 *            Mapa de objetos con los par�metros out del PL/SQL, siendo key
	 *            el nombre del parametro en el Procedure PL/SQL y value su
	 *            valor o tipo
	 * @param outs
	 *            Mapa objetos de salida
	 * @throws SiaException
	 * */
	public Map<String, Object> callInternProcedure(String nameFunction,
			Map<String, Object> params, Map<String, Object> outs)
			throws UtilsException {
		return (Map<String, Object>)callInternProcedure(nameFunction, params, outs, null, null);
	}
	
	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @param params
	 *            Mapa de objetos con los par�metros out del PL/SQL, siendo key
	 *            el nombre del parametro en el Procedure PL/SQL y value su
	 *            valor o tipo
	 * @param outs
	 *            Mapa objetos de salida
	 * @param inouts
	 *            Mapa objetos de salida y intrada
	 * @throws SiaException
	 * */
	public Map<String, Object> callInternProcedure(String nameFunction,
			Map<String, Object> params, Map<String, Object> outs, Map<String,Object> inouts)
			throws UtilsException {
		return (Map<String, Object>)callInternProcedure(nameFunction, params, outs, inouts, null);
	}
	
	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @param returnType Tipo ({@link Types} constantes) del valor retornado
	 * @throws SiaException
	 * */
	public Object callInternFunction(String nameFunction, Integer returnType)
			throws UtilsException {
		return callInternProcedure(nameFunction, null, null, null, returnType);
	}
	
	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @param params
	 *            Mapa de objetos con los par�metros out del PL/SQL, siendo key
	 *            el nombre del parametro en el Procedure PL/SQL y value su
	 *            valor o tipo
	 * @param returnType Tipo ({@link Types} constantes) del valor retornado
	 * @throws SiaException
	 * */
	public Object callInternFunction(String nameFunction,
			Map<String, Object> params, Integer returnType)
			throws UtilsException {
		return callInternProcedure(nameFunction, params, null, null, returnType);
	}

	/**
	 * Clase que contiene los elementos de una query de consulta a la base de
	 * datos
	 * */
	public class DBHandlerSelectElemet {
		private List<Map<String, Object>> resultSet;
		private long totalResults;

		/**
		 * Constructor
		 * 
		 * @param resultSet
		 *            Lista de Mapas en donde cada uno de los los elementos de
		 *            la lista representa una fila de la consulta, siendo los
		 *            identificadores, los nombres de las columnas
		 * @param totalResults
		 *            Número total de resultados que aportó la consulta
		 * */
		public DBHandlerSelectElemet(List<Map<String, Object>> resultSet,
				long totalResults) {
			this.resultSet = resultSet;
			this.totalResults = totalResults;
		}

		/**
		 * Método getter para resultSet
		 * 
		 * @return Devulve el valor de la lista d emapas
		 * */
		public List<Map<String, Object>> getResultSet() {
			return resultSet;
		}

		/**
		 * Método getter para el número total de resultados
		 * 
		 * @return Devulve el número de total de resultados que proporcióno la
		 *         query de consulta
		 * */
		public long getTotalResults() {
			return totalResults;
		}
	}
	
	public void setDbType(DBTypes dbType) {
		this.dbType = dbType;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setUser(String user) {
		this.user = user;
	}

	// ------------------------------ API ------------------------------------//

	// ++++++++++++++++++++++++++++++ METODOS PRIVADOS  ++++++++++++++++++++++//

	/**
	 * Método que obtiene un {@link Statement} con la base de datos.
	 * 
	 * @return Devulve una instancia de {@link Statement} con la base de datos
	 * */
	private Statement obtainStatement() throws UtilsException {
		Statement st;
		try {
			st = (Statement) conn.createStatement();
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CREATE_RESULT_SATATEMENT;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
		return st;
	}

	/**
	 * Método que llama a una procedimiento interna de la base de datos
	 * 
	 * @param nameFunction
	 *            Mapa de objetos con los par�metros del PL/SQL, Siendo la clave
	 *            el nombre del par�metro en el Porcedimiento PL/SQL y value su
	 *            valor.
	 * @param params
	 *            Mapa de objetos con los par�metros out del PL/SQL, siendo key
	 *            el nombre del parametro en el Procedure PL/SQL y value su
	 *            valor o tipo
	 * @param outs
	 *            Mapa objetops de salida
	 * @param inouts
	 *            Mapa de objetos de entrada y salida
	 * @param returnType
	 *            Determina el tipo de return de una función de la base de datos, si se trata de
	 *            un procedimiento este parámetro debe ser null
	 * @throws UtilsException
	 * */
	private Object callInternProcedure(String nameFunction,	Map<String, Object> params,
			Map<String, Object> outs,Map<String, Object> inouts, Integer returnType)
			throws UtilsException {
		
		conect();
		Map<String, Object> res = new HashMap<String, Object>();

		// PREPARE PL/SQL FUNCTION CALL ("?" IS A PLACEHOLDER FOR RETURN
		// VALUES AND PARAMETER VALUES, NUMBERED FROM LEFT TO RIGHT)
		String prepareFunction = "{";

		if (returnType!=null)
			prepareFunction += "? = ";

		prepareFunction += "call " + nameFunction + "(";
		int sizeParams = (params == null) ? 0 : params.size();
		int sizeOuts = (outs == null) ? 0 : outs.size();
		int sizeInouts = (inouts == null) ? 0 : inouts.size();
		
		for (int i = 0; i < sizeParams; i++) {
			prepareFunction += "?";
			if (i + 1 < sizeParams) {
				prepareFunction += ",";
			}
		}

		if(sizeOuts>0 && sizeParams>0)
			prepareFunction+=",";
		
		for (int i = 0; i < sizeOuts; i++) {
			prepareFunction += "?";
			if (i + 1 < sizeOuts) {
				prepareFunction += ",";
			}
		}

		for (int i = 0; i < sizeInouts; i++) {
			prepareFunction += "?";
			if (i + 1 < sizeInouts) {
				prepareFunction += ",";
			}
		}

		prepareFunction += ")}";

		CallableStatement vStatement;
		try {
			vStatement = conn.prepareCall(prepareFunction);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.PREPARE_PROCEDURE_ERROR;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage()
					+ prepareFunction + " no es correcta", e);
		}

		// Introducir paramatros
		if (params != null) {
			for (String key : params.keySet()) {
				try {
					vStatement.setObject(key, params.get(key));
				} catch (SQLException e) {
					UtilExceptions ex = UtilExceptions.INTRODUCE_PARAMETERS_ERROR;
					throw new UtilsException(ex.getErrorCode(),
							ex.getErrorMessage() + " " + prepareFunction, e);
				}
			}

		}
		
		//Introducir outs
		if(returnType!=null){
			try {
				vStatement.registerOutParameter(1, returnType);
			} catch (SQLException e) {
				UtilExceptions ex = UtilExceptions.RETURN_VALUE;
				throw new UtilsException(ex.getErrorCode(),
						ex.getErrorMessage(), e);
			}
		}else{
			// Introducir outputs
			if (outs != null) {
				for (String key : outs.keySet()) {
					try {
						Object aux = (outs.get(key));

						if (aux instanceof Integer) {
							vStatement.registerOutParameter(key, Types.NUMERIC);
						} else {
							vStatement.registerOutParameter(key, Types.VARCHAR);
						}
					} catch (SQLException e) {
						UtilExceptions ex = UtilExceptions.INTRODUCE_PARAMETERS_ERROR;
						throw new UtilsException(ex.getErrorCode(),
								ex.getErrorMessage() + " " + prepareFunction
										+ " KEY: " + key, e);
					}
				}
			}
			// Introducir inouts
			if (inouts != null) {
				for (String key : inouts.keySet()) {
					try {
						Object aux = (inouts.get(key));
						vStatement.setObject(key, aux);
						if (aux instanceof Integer) {
							vStatement.registerOutParameter(key, Types.NUMERIC);
						} else {
							vStatement.registerOutParameter(key, Types.VARCHAR);
						}
					} catch (SQLException e) {
						UtilExceptions ex = UtilExceptions.INTRODUCE_PARAMETERS_ERROR;
						throw new UtilsException(ex.getErrorCode(),
								ex.getErrorMessage() + " " + prepareFunction
										+ " KEY: " + key, e);
					}
				}
			}
		}

		// LLamada al PL/SQL Procedure
		try {
			vStatement.execute();
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CALL_PROCEDURE_ERROR;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage()
					+ " " + prepareFunction, e);
		}
		
		// Construyendo la salida de datos
		if (returnType!=null) {
			try {
				return vStatement.getObject(1);
			} catch (SQLException e) {
				UtilExceptions ex = UtilExceptions.OBTAINING_RETURN_VALUE;
				throw new UtilsException(ex.getErrorCode(),
						ex.getErrorMessage() + " " + prepareFunction, e);
			}
		}else{
			if (outs != null) {
				for (String key : outs.keySet()) {
					try {
						res.put(key, vStatement.getObject(key));
					} catch (SQLException e) {
						UtilExceptions ex = UtilExceptions.OBTAINING_OUTPUT_PROCEDURE_ERROR;
						throw new UtilsException(ex.getErrorCode(),
								ex.getErrorMessage() + " " + prepareFunction, e);
					}
				}
			}
			if (inouts != null) {
				for (String key : inouts.keySet()) {
					try {
						res.put(key, vStatement.getObject(key));
					} catch (SQLException e) {
						UtilExceptions ex = UtilExceptions.OBTAINING_OUTPUT_PROCEDURE_ERROR;
						throw new UtilsException(ex.getErrorCode(),
								ex.getErrorMessage() + " " + prepareFunction, e);
					}
				}
			}
		}
		disconect();
		return res;
	}
	
	/**
	 * Método que habilita la conexión con la base de datos
	 * */
	private void conect() throws UtilsException {
		try {
			this.conn = DriverManager.getConnection(
					this.dbType.getIdetifyString() + dbType.getFirstSeparator()
							+ dir + dbType.getSeparator() + dbName, user, pass);
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CONECT_EXCEPTION;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}

	/**
	 * Método que cierra la conexión con la base de datos
	 * */
	private void disconect() throws UtilsException {
		try {
			conn.close();
		} catch (SQLException e) {
			UtilExceptions ex = UtilExceptions.CLOSE_DB_EXCEPTION;
			throw new UtilsException(ex.getErrorCode(), ex.getErrorMessage(), e);
		}
	}
	// ----------------------------- METODOS PRIVADOS  ------------------------------//
}
