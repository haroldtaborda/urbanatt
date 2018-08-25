/**
 * Identification.java
 */
package co.urbaNatt.DTO;

/**
 * <b>Descripcion: </b>Define el dto para el manejo de la identificacion
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">hreysand</a>
 * @date Jun 16, 2016
 */
public class IdentificationDTO {

	private Long documentNumber;
	private String documentType;
	/**
	 * <b>Descripcion: </b> Retorna el valor de documentNumber
	 * @return El valor de documentNumber
	 */
	public Long getDocumentNumber() {
		return documentNumber;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de documentNumber
	 * @param documentNumber el documentNumber a actualizar
	 */
	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}
	/**
	 * <b>Descripcion: </b> Retorna el valor de documentType
	 * @return El valor de documentType
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de documentType
	 * @param documentType el documentType a actualizar
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
}
