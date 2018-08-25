/**
 * ResponseResultDTO.java
 */
package co.urbaNatt.DTO;

import java.io.Serializable;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 22/06/2016
 */
public class ResponseResultDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1035821124939058420L;
	private Integer responseCode =0;
	private boolean result = true;
	private Error dtls = null;
	 

	/**
	 * Constructor de la clase
	 */
	public ResponseResultDTO() {
	}


	/**
	 * <b>Descripcion: </b> Retorna el valor de responseCode
	 * @return El valor de responseCode
	 */
	public Integer getResponseCode() {
		return responseCode;
	}


	/**
	 * <b>Descripcion: </b>Actualiza el valor de responseCode
	 * @param responseCode el responseCode a actualizar
	 */
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}


	/**
	 * <b>Descripcion: </b> Retorna el valor de result
	 * @return El valor de result
	 */
	public boolean isResult() {
		return result;
	}


	/**
	 * <b>Descripcion: </b>Actualiza el valor de result
	 * @param result el result a actualizar
	 */
	public void setResult(boolean result) {
		this.result = result;
	}


	/**
	 * <b>Descripcion: </b> Retorna el valor de dtls
	 * @return El valor de dtls
	 */
	public Error getDtls() {
		return dtls;
	}


	/**
	 * <b>Descripcion: </b>Actualiza el valor de dtls
	 * @param dtls el dtls a actualizar
	 */
	public void setDtls(Error dtls) {
		this.dtls = dtls;
	}

}
