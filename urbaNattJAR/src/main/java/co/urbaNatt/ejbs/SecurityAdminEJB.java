/**
 * ManagerSecurityEJB.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.urbaNatt.DAO.UserAdminDAO;
import co.urbaNatt.DTO.CredentialsDTO;
import co.urbaNatt.DTO.LoginAdminOutDTO;
import co.urbaNatt.DTO.ResultSecurityDTO;
import co.urbaNatt.VO.AdminVO;
import co.urbaNatt.constans.EnumServiceTypeError;
import co.urbaNatt.constans.EnumWebServicesErrors;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.BusinessRollBackExceptions;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.ConnectionUtils;
import co.urbaNatt.constans.Error;

/**
 * 
 * @author Harold
 *
 */
@Stateless
public class SecurityAdminEJB implements ISecurityAdminLocal {
	/**
	 * Instancia del log
	 */
	private static Logger log = Logger.getLogger(SecurityAdminEJB.class.getCanonicalName());

	/**
	 * Constructor de la clase
	 */
	public SecurityAdminEJB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <b>Descripcion: </b> Metodo encargado de realciar el login de un asefurado
	 * @param credentials Las credenciales del usuario
	 * @return El token del usuario
	 * @throws TechnicalException Cuando ocurre una excepción tecnica
	 * @throws BusinessException Cuando ocurre una excepcion de negocio que no qrequiere rollback
	 * @throws BusinessRollBackExceptions Cuando requeire una excepción de negocio que requiera rollback
	 * @see co.ibm.healthcare.business.back.security.ISecurityManagerEJBLocal#login(co.ibm.healthcare.dto.security.CredentialsDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public LoginAdminOutDTO login(CredentialsDTO credentials) throws TechnicalException, BusinessException, BusinessRollBackExceptions {

		Connection connection = null;
		LoginAdminOutDTO retorno = null;
		try {
			connection = ConnectionUtils.getInstance().getConnectionBack();
			UserAdminDAO dao = UserAdminDAO.getInstance();
			AdminVO vo=dao.consultarUsuario(credentials,connection);
			if(vo != null){
			retorno=new LoginAdminOutDTO();
			retorno.setNombreCompleto(vo.getNombreCompleto());
			retorno.setNombreUsuario(vo.getNombreUsuario());
			retorno.setRol(vo.getRol());
			}
			else{
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.WRONG_PASSWORD.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.WRONG_PASSWORD.getDescripcion();
				result.result = false;
				result.error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
				throw new BusinessException(result);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			if (e instanceof TechnicalException) {
				throw (TechnicalException) e;
			} else {
				throw new TechnicalException(e);
			}

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// Error al cerrar la conexion
				}
			}
		}
		return retorno;
	}

	
}
