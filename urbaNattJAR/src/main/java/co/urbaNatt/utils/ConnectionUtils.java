/**
 * ConnectionUtils.java
 */
package co.urbaNatt.utils;

import java.sql.Connection;

import co.urbaNatt.ejbs.ConnectionHealthServer;
import co.urbaNatt.ejbs.ConnectionHealthTest;
import co.urbaNatt.ejbs.IConnectionHealth;

/**
 * <b>Descripcion: </b>Obtiene la conexión a la base de datos
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">Hugo Rey</a>
 * @date 1/02/2016
 */
public class ConnectionUtils {

	/**
	 * Instancia de la clase
	 */
	private static ConnectionUtils instance = null;
	private IConnectionHealth delegado;

	/**
	 * Constructor de la clase
	 */
	private ConnectionUtils() {
		String test = System.getProperty("test");
		if (test == null) {
			delegado = new ConnectionHealthServer();
		} else if (test.equals("true")) {
			delegado = new ConnectionHealthTest();
		}
	}

	/**
	 * <b>Descripcion: </b>Devuelve la instancia de la clase
	 * @return ConnectionUtils
	 */
	public static ConnectionUtils getInstance() {
		if (instance == null) {
			instance = new ConnectionUtils();
		}
		return instance;
	}
	/**
	 * <b>Descripcion: </b>Obtiene la conexion a la base de datos del Back
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnectionBack() throws Exception {

		return delegado.getConnectionBack();
	}

}
