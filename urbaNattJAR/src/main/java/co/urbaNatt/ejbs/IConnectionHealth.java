/**
 * IConnectionHealth.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 17/06/2016
 */
public interface IConnectionHealth {

	/**
	 * <b>Descripcion: </b>Obtiene la conexion a la base de datos del Back
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnectionBack() throws Exception;
}
