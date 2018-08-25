/**
 * ConnectionHealthServer.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import co.urbaNatt.utils.JndiProperties;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 17/06/2016
 */
public class ConnectionHealthServer implements IConnectionHealth {

	/**
	 * Constructor de la clase
	 */
	public ConnectionHealthServer() {
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * <b>Descripcion: </b>Obtiene la conexion a la base de datos del Back
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnectionBack() throws Exception{

		Context ctx = new InitialContext();
		
		String jndi = JndiProperties.getInstance().getProperty("jndi.database.backend");
		
		if (jndi == null || jndi.isEmpty()){
			jndi = "jdbc/HealthCare";
		}
		
		DataSource ds = (DataSource) ctx.lookup(jndi);
		Connection conn = ds.getConnection();
		
		return conn;
		
	}

}
