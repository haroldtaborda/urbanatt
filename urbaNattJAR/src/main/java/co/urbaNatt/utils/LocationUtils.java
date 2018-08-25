/**
 * LocationUtils.java
 */
package co.urbaNatt.utils;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 29/06/2016
 */
public class LocationUtils {
	/**
	 * Unica instancia de la clase
	 */
	private static final LocationUtils instance = new LocationUtils();
	/**
	 * Constructor de la clase
	 */
	private LocationUtils() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * <b>Descripcion: </b> Metodo que implementa el patron solitario
	 * @return La unica instancia de la clase
	 */
	public static LocationUtils getInstance(){
		return instance;
	}
}
