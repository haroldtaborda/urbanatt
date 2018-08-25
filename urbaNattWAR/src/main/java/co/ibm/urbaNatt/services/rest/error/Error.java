/**
 * Error.java
 */
package co.ibm.urbaNatt.services.rest.error;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 15/06/2016
 */
public class Error {
	public Integer errorCode;
	public String errorType;
	public String errorDescription;

	/**
	 * Constructor de la clase
	 */
	public Error() {
	}
	
	/**
	 * Constructor de la clase
	 * @param errorCode
	 * @param errorType
	 * @param errorDescription
	 */
	public Error(Integer errorCode, String errorType, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorType = errorType;
		this.errorDescription = errorDescription;
	}
	/**
	 * <b>Descripcion: </b> Retorna el valor de errorCode
	 * @return El valor de errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de errorCode
	 * @param errorCode el errorCode a actualizar
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * <b>Descripcion: </b> Retorna el valor de errorType
	 * @return El valor de errorType
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de errorType
	 * @param errorType el errorType a actualizar
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * <b>Descripcion: </b> Retorna el valor de errorDescription
	 * @return El valor de errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de errorDescription
	 * @param errorDescription el errorDescription a actualizar
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
