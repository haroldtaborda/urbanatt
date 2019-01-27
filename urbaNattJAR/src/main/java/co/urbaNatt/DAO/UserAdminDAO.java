/**
 * UserManagerDAO.java
 */
package co.urbaNatt.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.urbaNatt.DTO.ClienteDTO;
import co.urbaNatt.DTO.CredentialsDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.DTO.SucursalInDTO;
import co.urbaNatt.DTO.UsuarioOutDTO;
import co.urbaNatt.VO.AdminVO;
import co.urbaNatt.constans.ConsultasDinamicasConstans;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.BD.OperacionesBD;

/**
 * <b>Descripcion: </b> Dao asociado a la tabla UserManager.
 * 
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 15/06/2016
 */
public class UserAdminDAO {

	/**
	 * Referencia unica de la clase
	 */
	private static final UserAdminDAO instance = new UserAdminDAO();
	private static final OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public UserAdminDAO() {
	}

	public Integer logout(CredentialsDTO credentials, Connection connection) throws TechnicalException {

		PreparedStatement stm = null;
		Integer retorno = 0;
		try {
			stm = connection.prepareStatement("");
			stm.setString(1, credentials.getUserName());
			retorno = stm.executeUpdate();
		} catch (Exception e) {
			throw new TechnicalException(e);
		} finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return retorno;
	}

	/**
	 * 
	 * <b>Descripcion: </b> Metodo encargado de implementar el patron solitario
	 * 
	 * @return Una unica instancia de la clase.
	 */
	public static UserAdminDAO getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param credentials
	 * @param connection
	 * @return
	 * @throws TechnicalException
	 */
	public AdminVO consultarUsuario(CredentialsDTO credentials, Connection connection) throws TechnicalException {
		AdminVO user = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(credentials.getUserName().toUpperCase());
			parametros.add(credentials.getPassword());
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(ConsultasDinamicasConstans.CONSULTAR_USUARIO_LOGIN, connection,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			if (rs.next()) {
				user = new AdminVO();
				user.setNombreUsuario(rs.getString(1));
				user.setEstadoUsuario(rs.getString(2));
				user.setContrasenia(rs.getString(3));
				user.setNombreCompleto(rs.getString(4));
				user.setRol(rs.getString(5));
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
		return user;
	}
	
	/**
	 * 
	 * @param credentials
	 * @param connection
	 * @return
	 * @throws TechnicalException
	 */
	public List<UsuarioOutDTO> consultarUsuarios(String nombre, String rol, Connection connection) throws TechnicalException {
		 List<UsuarioOutDTO> lista = new ArrayList<UsuarioOutDTO>();
		 UsuarioOutDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_USUARIO);
			if(nombre != null && !nombre.isEmpty() && !nombre.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_USUARIO_NOMBRE);
				parametros.add("%"+nombre.toUpperCase()+"%");
			}
			if(rol != null && !rol.isEmpty()  && !rol.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_USUARIO_ROL);
				parametros.add(operacionesBD.consultarIdRolPorNombre(rol));
			}
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), connection,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto = new UsuarioOutDTO();
				dto.setIdUsuario(rs.getLong(1));
				dto.setNombreUsuario(rs.getString(2));
				dto.setFechaCreacion(rs.getString(4));
				dto.setCantidadIntentos(rs.getInt(5));
				dto.setEstado(rs.getString(6));
				dto.setNombreCompleto(rs.getString(7));
				dto.setRol(operacionesBD.consultarNombreRolPorId(rs.getLong(8)));
				dto.setNota(rs.getString(9));
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
	

	public List<ClienteDTO> consultarClientes(String tipoId, String numId, String nombre, Connection conexion) throws TechnicalException { 
		
		 List<ClienteDTO> lista = new ArrayList<ClienteDTO>();
		 ClienteDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE);
			if(nombre != null && !nombre.isEmpty() && !nombre.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE_NOMBRE);
				parametros.add("%"+nombre.toUpperCase()+"%");
			}
			
			
			if(tipoId != null && !tipoId.isEmpty()  && !tipoId.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE_TIPOID);
				parametros.add(operacionesBD.consultarIdTipoIdPorNombre(tipoId));
			}
			
			if(numId != null && !numId.isEmpty()  && !numId.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE_NUMID);
				parametros.add(numId);
			}
			
			sb.append(" ORDER BY NOMBRECOMPLETO ");
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto = new ClienteDTO();
				dto.setIdCliente(rs.getLong(1));
				dto.setIdDepartamento(rs.getLong(9));
				dto.setNumId(rs.getString(3));
				dto.setIdCiudad(rs.getLong(10));
				dto.setTipoId(operacionesBD.consultarNobreTipoIdPorId(rs.getLong(2)));
				dto.setNombreCliente(rs.getString(4));
				dto.setCorreo(rs.getString(7));
				dto.setDireccion(rs.getString(8));
				dto.setEstado(rs.getString(11));
				dto.setDepartamento(rs.getString(13));
				dto.setCiudad(rs.getString(14));
				dto.setTelCelular(rs.getLong(6));
				dto.setTelFijo(rs.getLong(5));
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

	public List<SucursalInDTO> consultarSucursales(boolean b, Long idCliente, Connection conexion) throws TechnicalException { 
		List<SucursalInDTO> sucursales= new ArrayList<>();
		SucursalInDTO dto=null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(idCliente);
			
			String consulta= "";
			if(b){
				consulta=ConsultasDinamicasConstans.CONSULTAR_SUCURSALES;
			}
			else {
				consulta=ConsultasDinamicasConstans.CONSULTAR_SUCURSALES_CC;
				}
			
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(consulta, conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto= new SucursalInDTO();
				dto.setIdSucursal(rs.getLong(1));
				dto.setNombreSucursal(rs.getString(2));
				dto.setDireccion(rs.getString(4));
				dto.setTipo(rs.getString(3));
				dto.setIdDepartamento(rs.getLong(5));
				dto.setIdCiudad(rs.getLong(6));
				dto.setIdCliente(idCliente);
				sucursales.add(dto);
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
		return sucursales;
	}
}
	

