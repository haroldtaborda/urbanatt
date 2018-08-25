/**
 * InvalidSessionException.java
 */
package co.urbaNatt.exceptions;

import co.urbaNatt.DTO.ResultSecurityDTO;

/**
 * <b>Descripcion: </b>Define una excepcion especial para cuando no esta autenticado
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 14/06/2016
 */
public class InvalidSessionException extends Exception{

	/**
	 * Serial de la clase
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Define el result con el error 
	 */
	private ResultSecurityDTO result;

	/**
	 * Constructor de la clase
	 * @param message
	 * @param cause
	 */
	public InvalidSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public InvalidSessionException(String message) {
		super(message);
	}

	/**
	 * Constructor de la clase
	 * @param cause
	 */
	public InvalidSessionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor de la clase
	 * @param result ResultSecurityDTO
	 */
	public InvalidSessionException(ResultSecurityDTO result) {
		super();
		this.result = result;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de result
	 * @return El valor de result
	 */
	public ResultSecurityDTO getResult() {
		return result;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de result
	 * @param result el result a actualizar
	 */
	public void setResult(ResultSecurityDTO result) {
		this.result = result;
	}
}
