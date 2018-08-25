/**
 * CypherUtils.java
 */
package co.urbaNatt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 15/06/2016
 */
public class CypherUtils {
	/**
	 * 
	 * <b>Descripcion: </b> Metodo encargado de generar el md5 hash
	 * @param message El mensaje a procesar.
	 * @return El hash de la cadena
	 * @throws NoSuchAlgorithmException Cuando el algoritmo no existe.
	 */
	public static String getMD5(String message) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(message.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(CypherUtils.getMD5("password"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
