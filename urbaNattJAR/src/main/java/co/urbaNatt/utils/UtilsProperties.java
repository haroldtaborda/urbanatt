/**
 * PropertiesUtils.java
 */
package co.urbaNatt.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <b>Descripcion: </b>Maneja las propiedades del sistema
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">hreysand</a>
 * @date Jul 8, 2016
 */
public abstract class UtilsProperties {
	
	/**
	 * <b>Descripcion: </b>Obtiene el nombre del archivo que debe procesar
	 * @return Nombre del archivo
	 */
	public abstract String getFileName(); 
	
	/**
     * variable que contiene las propiedades a administratr
     */
    private Properties prop;
    
    /**
     * Constructor de la clase
     */
    public UtilsProperties() {
        prop = new Properties();
    }
    
    /**
     * <b>Descripcion: </b>Carga las propiedades de un archivo especifico
     * @param file
     * @throws IOException
     */
    public void loadProperties() throws IOException {
        ClassLoader classLoader;
        InputStream is = null;
        try {
            classLoader = this.getClass().getClassLoader();
            is = classLoader.getResourceAsStream(getFileName());
            prop.load(is);
        } catch (IOException ioe) {
            throw new FileNotFoundException(
                    "No se encontro el archivo de propiedades: " + getFileName());
        } catch (Exception e) {
            throw new FileNotFoundException(
                    "Ocurrio un error con el archivo de propiedades: " + getFileName());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * <b>Descripcion: </b>Obtiene una propiedades especifica
     * @param key Llave de la propiedad
     * @return Valor de la propiedad
     */
    public String getProperty(String key) {
        String property = null;
        property = prop.getProperty(key);
        if (property != null) {
            property = property.trim();
        } else {
            property = "";
        }
        return property;
    }
	
}
