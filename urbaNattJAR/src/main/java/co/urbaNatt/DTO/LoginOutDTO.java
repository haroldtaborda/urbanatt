/**
 * LoginOutDTO.java
 */
package co.urbaNatt.DTO;

/**
 * <b>Descripcion: </b>
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 16/06/2016
 */
public class LoginOutDTO {
	private String token;
	private Long concernRoleId;
	/**
	 * Constructor de la clase
	 */
	public LoginOutDTO() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor de la clase
	 * @param token
	 */
	public LoginOutDTO(String token) {
		super();
		this.token = token;
	}
	
	/**
	 * Constructor de la clase
	 * @param token
	 * @param concernRoleId
	 */
	public LoginOutDTO(String token, Long concernRoleId) {
		super();
		this.token = token;
		this.concernRoleId = concernRoleId;
	}
	/**
	 * <b>Descripcion: </b> Retorna el valor de token
	 * @return El valor de token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de token
	 * @param token el token a actualizar
	 */
	public void setToken(String token) {
		this.token = token;
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

}
