/**
 * JndiProperties.java
 */
package co.urbaNatt.utils;

import java.io.IOException;

/**
 * <b>Descripcion: </b>
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">hreysand</a>
 * @date Jul 8, 2016
 */
public class JndiProperties extends UtilsProperties{

	/**
	 * Instancia de la clase
	 */
	private static JndiProperties instance;
	
	/**
	 * <b>Descripcion: </b>Obtiene la instancia de la clase
	 * @return BatchProperties
	 */
	public static JndiProperties getInstance(){
		if (instance == null){
			instance = new JndiProperties();
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase
	 */
	private JndiProperties() {
		try {
			loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * @see co.ibm.healthcare.utils.properties.UtilsProperties#getFileName()
	 */
	@Override
	public String getFileName() {
		return "jndi.properties";
	}
}
