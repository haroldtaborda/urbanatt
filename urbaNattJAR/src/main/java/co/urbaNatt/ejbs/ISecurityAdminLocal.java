/**
 * IManagerSecurityEJBLocal.java
 */
package co.urbaNatt.ejbs;

import javax.ejb.Local;

import co.urbaNatt.DTO.CredentialsDTO;
import co.urbaNatt.DTO.LoginAdminOutDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.BusinessRollBackExceptions;
import co.urbaNatt.exceptions.TechnicalException;


@Local
public interface ISecurityAdminLocal {
	/**
	 * 
	 * <b>Descripcion: </b> Metodo encargado de realizar el login de un manager.
	 * @param credentials Las credenciales
	 * @return El token
	 * @throws TechnicalException Cuando ocurre una excepción tecnica
	 * @throws BusinessException Cuando ocurre una excepción de negocio que no maneje rollback
	 * @throws BusinessRollBackExceptions Cuandpo ocurre una excepción tecnica que requeire de rollback.
	 */
	public LoginAdminOutDTO login(CredentialsDTO credentials) throws TechnicalException, BusinessException, BusinessRollBackExceptions;
	
}
