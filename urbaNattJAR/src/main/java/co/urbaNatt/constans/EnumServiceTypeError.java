/**
 * EnumServiceTypeError.java
 */
package co.urbaNatt.constans;

/**
 * <b>Descripcion: </b>Define los posibles tipos de errores en los servicios web expuestos
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">hreysand</a>
 * @date 3/12/2014
 */
public enum EnumServiceTypeError {
	/**
	 * Errores de estructura (Nulos, longitudes, tipos de datos)
	 */
	STRUCTURE(1, "STRUCTURE"), 
	/**
	 * Errores de negocio (Validaciones, reglas de negocio)
	 */
	BUSINESS(2, "BUSINESS"), 
	/**
	 * Errores técnicos (Problemas de conexión, errores de insert, otros )
	 */
	TECHNICAL(3, "TECHNICAL");
	
	/**
	 * Define el código del tipo de error
	 */
	private Integer codigoError;
	
	
	/**
	 * Define el nombre del tipo de error
	 */
	private String tipoError;
	
	/**
	 * Constructor de la clase
	 * @param codigoError Codigo del tipo de error
	 * @param tipoError Nombre del tipo de error
	 */
	private EnumServiceTypeError(Integer codigoError, String tipoError){
		this.codigoError = codigoError;
		this.tipoError = tipoError;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de codigoError
	 * @return El valor de codigoError
	 */
	public Integer getCodigoError() {
		return codigoError;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de codigoError
	 * @param codigoError el codigoError a actualizar
	 */
	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de tipoError
	 * @return El valor de tipoError
	 */
	public String getTipoError() {
		return tipoError;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de tipoError
	 * @param tipoError el tipoError a actualizar
	 */
	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}
	
}
