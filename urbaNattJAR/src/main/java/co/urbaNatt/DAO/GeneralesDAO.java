/**
 * UserManagerDAO.java
 */
package co.urbaNatt.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.urbaNatt.DTO.ObjetoDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.constans.ConsultasDinamicasConstans;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.BD.OperacionesBD;

/**
 * <b>Descripcion: </b> Dao asociado a la tabla UserManager.
 * 
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 15/06/2016
 */
public class GeneralesDAO {

	/**
	 * Referencia unica de la clase
	 */
	private static final GeneralesDAO instance = new GeneralesDAO();
	private static final OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public GeneralesDAO() {
	}

	

	/**
	 * 
	 * <b>Descripcion: </b> Metodo encargado de implementar el patron solitario
	 * 
	 * @return Una unica instancia de la clase.
	 */
	public static GeneralesDAO getInstance() {
		return instance;
	}

	
	
	public List<ObjetoDTO> consultarCiudadesPorDepto(Long idDepto, Connection connection) throws TechnicalException {
		 List<ObjetoDTO> lista = new ArrayList<ObjetoDTO>();
		 ObjetoDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_CIUDADES_POR_DEPTO);
				parametros.add(idDepto);
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), connection,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto = new ObjetoDTO();
				dto.setId(rs.getLong(1));
				dto.setNombre(rs.getString(2));
				dto.setIdPadre(rs.getLong(3));
				lista.add(dto);
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			operacionesBD.cerrarStatement();
		}
		return lista;
	}
	
	
	public List<ObjetoDTO> consultarDeptos(Connection connection) throws TechnicalException {
		 List<ObjetoDTO> lista = new ArrayList<ObjetoDTO>();
		 ObjetoDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_DEPTOS);
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), connection,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto = new ObjetoDTO();
				dto.setId(rs.getLong(1));
				dto.setNombre(rs.getString(2));
				dto.setIdPadre(rs.getLong(3));
				lista.add(dto);
			}
		} catch (Exception e) {
			throw new TechnicalException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			operacionesBD.cerrarStatement();
		}
		return lista;
	}

}
	

