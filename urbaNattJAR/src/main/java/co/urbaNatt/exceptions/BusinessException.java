/**
 * BusinessExceptions.java
 */
package co.urbaNatt.exceptions;

import javax.ejb.ApplicationException;

import co.urbaNatt.DTO.ResultSecurityDTO;
import co.urbaNatt.constans.EnumWebServicesErrors;

/**
 * <b>Descripcion: </b>Define un error de negocio
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 14/06/2016
 */
@ApplicationException(rollback=true, inherited=false)
public class BusinessException extends Exception{

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
	 */
	public BusinessException(ResultSecurityDTO result) {
		super();
		this.result = result;
	}
	public BusinessException(EnumWebServicesErrors error){
		super();
		result = new ResultSecurityDTO();
		result.result = false;
		result.error = error.getError();
	}
	
	/**
	 * Constructor de la clase
	 */
	public BusinessException(ResultSecurityDTO result, Throwable cause) {
		super(cause);
		this.result = result;
	}

	/**
	 * Constructor de la clase
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * Constructor de la clase
	 * @param cause
	 */
	public BusinessException(Throwable cause) {
		super(cause);
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
