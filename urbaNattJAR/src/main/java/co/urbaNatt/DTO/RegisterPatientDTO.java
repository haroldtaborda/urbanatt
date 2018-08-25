/**
 * RegisterPatientDTO.java
 */
package co.urbaNatt.DTO;

import java.util.Date;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 16/06/2016
 */
public class RegisterPatientDTO {
	private Date birthDate;
	private String document;
	private String documentType;
	private String userId;
	private String password;
	private String email;
	private String skype;
	private Long concernRoleId;
	private String userType;
	private String name;

	/**
	 * Constructor de la clase
	 */
	public RegisterPatientDTO() {
	}

	/**
	 * Constructor de la clase
	 * @param birthDate La fecha de naciemiento
	 * @param document EL numero de documento
	 * @param documentType El tipo de documento
	 * @param userId EL usuario
	 * @param password El passowrd
	 * @param email EL email
	 * @param skype El skype
	 * @param concernRoleId El concernroleid
	 */
	public RegisterPatientDTO(Date birthDate, String document, String documentType, String userId, String password, String email, String skype,
			Long concernRoleId, String userType) {
		super();
		this.birthDate = birthDate;
		this.document = document;
		this.documentType = documentType;
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.skype = skype;
		this.concernRoleId = concernRoleId;
		this.userType = userType;
	}



	/**
	 * <b>Descripcion: </b> Retorna el valor de concernRoleId
	 * @return El valor de concernRoleId
	 */
	public Long getConcernRoleId() {
		return concernRoleId;
	}



	/**
	 * <b>Descripcion: </b>Actualiza el valor de concernRoleId
	 * @param concernRoleId el concernRoleId a actualizar
	 */
	public void setConcernRoleId(Long concernRoleId) {
		this.concernRoleId = concernRoleId;
	}



	/**
	 * <b>Descripcion: </b> Retorna el valor de birthDate
	 * @return El valor de birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de birthDate
	 * @param birthDate el birthDate a actualizar
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de document
	 * @return El valor de document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de document
	 * @param document el document a actualizar
	 */
	public void setDocument(String document) {
		this.document = document;
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

	/**
	 * <b>Descripcion: </b> Retorna el valor de userId
	 * @return El valor de userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de userId
	 * @param userId el userId a actualizar
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de password
	 * @return El valor de password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de password
	 * @param password el password a actualizar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de email
	 * @return El valor de email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de email
	 * @param email el email a actualizar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de skype
	 * @return El valor de skype
	 */
	public String getSkype() {
		return skype;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de skype
	 * @param skype el skype a actualizar
	 */
	public void setSkype(String skype) {
		this.skype = skype;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de userType
	 * @return El valor de userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de userType
	 * @param userType el userType a actualizar
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de name
	 * @return El valor de name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de name
	 * @param name el name a actualizar
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
