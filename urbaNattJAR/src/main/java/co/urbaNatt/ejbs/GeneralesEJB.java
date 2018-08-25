/**
 * ManagerSecurityEJB.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import co.urbaNatt.DAO.GeneralesDAO;
import co.urbaNatt.DTO.ObjetoDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.ConnectionUtils;
import co.urbaNatt.utils.BD.OperacionesBD;

/**
 * 
 * @author Harold
 *
 */
@Stateless
public class GeneralesEJB implements IGeneralesEJBLocal {
	/**
	 * Instancia del log
	 */
	private static Logger log = Logger.getLogger(GeneralesEJB.class.getCanonicalName());

	OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public GeneralesEJB() {
	}

	

	@Override
	public List<ObjetoDTO> consultarCiudadesPorDepto(Long idDepto) throws TechnicalException, BusinessException {


		Connection conexion = null;
		try {
			List<ObjetoDTO> result = null;
			GeneralesDAO dao = GeneralesDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarCiudadesPorDepto(idDepto, conexion);
			return result;
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
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error al cerrar la conexion
				}
			}
		}
	
	}

	@Override
	public List<ObjetoDTO> consultarDeptos() throws TechnicalException, BusinessException {

		Connection conexion = null;
		try {
			List<ObjetoDTO> result = null;
			GeneralesDAO dao = GeneralesDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarDeptos(conexion);
			return result;
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
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error al cerrar la conexion
				}
			}
		}
	
	}

}
