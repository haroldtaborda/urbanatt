
package co.urbaNatt.ejbs;

import java.sql.Connection;


public interface IConnectionHealth {

	
	public Connection getConnectionBack() throws Exception;
}
