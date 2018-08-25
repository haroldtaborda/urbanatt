/**
 * TechnicalException.java
 */
package co.urbaNatt.exceptions;

import javax.ejb.ApplicationException;

import co.urbaNatt.DTO.ResultSecurityDTO;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 14/06/2016
 */
@ApplicationException(rollback=true, inherited=false)
public class TechnicalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Define el result con el error 
	 */
	private ResultSecurityDTO result;

	/**
	 * Constructor de la clase
	 */
	public TechnicalException() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param arg0
	 * @param arg1
	 */
	public TechnicalException(ResultSecurityDTO result) {
		super();
		this.result = result;
	}
	
	/**
	 * Constructor de la clase
	 * @param arg0
	 * @param arg1
	 */
	public TechnicalException(ResultSecurityDTO result, Throwable cause) {
		super(cause);
		this.result = result;
	}

	/**
	 * Constructor de la clase
	 * @param message
	 * @param cause
	 */
	public TechnicalException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor de la clase
	 * @param arg0
	 */
	public TechnicalException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor de la clase
	 * @param arg0
	 */
	public TechnicalException(Throwable arg0) {
		super(arg0);
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
