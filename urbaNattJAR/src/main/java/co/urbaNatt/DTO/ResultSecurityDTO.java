/**
 * ResultSecurityDTO.java
 */
package co.urbaNatt.DTO;

import co.urbaNatt.constans.Error;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 15/06/2016
 */
public class ResultSecurityDTO {
	/**
	 * EL resultado
	 */
	public boolean result = false;
	/**
	 * El error de la validación
	 */
	public Error error;
	/**
	 * Constructor de la clase
	 */
	public ResultSecurityDTO() {
		// TODO Auto-generated constructor stub
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
	 * <b>Descripcion: </b> Retorna el valor de error
	 * @return El valor de error
	 */
	public Error getError() {
		return error;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de error
	 * @param error el error a actualizar
	 */
	public void setError(Error error) {
		this.error = error;
	}
	
}
