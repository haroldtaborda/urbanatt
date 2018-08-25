/**
 * LoginUserInDTO.java
 */
package co.urbaNatt.DTO;

/**
 * <b>Descripcion: </b>
 * @author IBM - <a href="mailto:jamartin@co.ibm.com">jamartin</a>
 * @date 15/06/2016
 */
public class LoginUserInDTO {

	private String password;
	private String userName;
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
	 * <b>Descripcion: </b> Retorna el valor de userName
	 * @return El valor de userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * <b>Descripcion: </b>Actualiza el valor de userName
	 * @param userName el userName a actualizar
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


}
