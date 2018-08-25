/**
 * BusinessRollBackExceptions.java
 */
package co.urbaNatt.exceptions;

import javax.ejb.ApplicationException;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 14/06/2016
 */
@ApplicationException(rollback=true, inherited=false)
public class BusinessRollBackExceptions extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase
	 */
	public BusinessRollBackExceptions() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor de la clase
	 * @param message
	 * @param cause
	 */
	public BusinessRollBackExceptions(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor de la clase
	 * @param message
	 */
	public BusinessRollBackExceptions(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor de la clase
	 * @param cause
	 */
	public BusinessRollBackExceptions(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
