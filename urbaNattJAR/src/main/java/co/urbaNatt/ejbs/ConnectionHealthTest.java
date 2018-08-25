/**
 * ConnectionHealthTest.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 17/06/2016
 */
public class ConnectionHealthTest implements IConnectionHealth {
	/**
	 * Archivo de propiedades
	 */
	private ResourceBundle resourceBundle = null;

	/**
	 * Datasource
	 */
	private BasicDataSource datasource2;

	/**
	 * Constructor de la clase
	 */
	public ConnectionHealthTest() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * <b>Descripcion: </b>Inicializa las propiedades de conexión
	 * @throws Exception
	 */
	public BasicDataSource setupDatasource2() throws Exception {

		String user = getStaticProperty("back.db.username");
		String password = getStaticProperty("back.db.password");
		String host = getStaticProperty("back.db.servername");
		String port = getStaticProperty("back.db.serverport");
		String name = getStaticProperty("back.db.name");
		String url = "jdbc:db2://" + host + ":" + port + "/" + name;
		datasource2 = new BasicDataSource();
		datasource2.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
		datasource2.setUsername(user);
		datasource2.setPassword(password);
		datasource2.setUrl(url);

		datasource2.setInitialSize(5);
		return datasource2;

	}

	/**
	 * <b>Descripcion: </b>Obtiene una propiedad del archivo de bootstrap
	 * @param key Llave
	 * @return valor asociado a la llave
	 */
	public String getStaticProperty(String key) {

		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle("Bootstrap");
		}

		String value = resourceBundle.getString(key);

		if (value == null) {
			return null;
		}
		String trimString = value.trim();
		if (trimString.length() == 0) {
			return null;
		}
		return trimString;
	}

	

	public Connection getConnection2() throws Exception {
		if (datasource2 == null) {
			datasource2 = setupDatasource2();
		}

		Connection connection = datasource2.getConnection();

		return connection;
	}

	/**
	 * <b>Descripcion: </b>
	 * @return
	 * @throws Exception
	 * @see co.ibm.healthcare.utils.connection.IConnectionHealth#getConnectionBack()
	 */
	public Connection getConnectionBack() throws Exception {
		return getConnection2();
	}
	public static void main(String[] args) {
		try{
		ConnectionHealthTest cht = new ConnectionHealthTest();
		System.out.println(cht.getConnection2());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
