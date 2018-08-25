/**
 * ManagerSecurityEJB.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import co.urbaNatt.DAO.UserAdminDAO;
import co.urbaNatt.DTO.ClienteDTO;
import co.urbaNatt.DTO.InsertsBDInDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.DTO.ResultSecurityDTO;
import co.urbaNatt.DTO.SucursalInDTO;
import co.urbaNatt.DTO.UsuarioInDTO;
import co.urbaNatt.DTO.UsuarioOutDTO;
import co.urbaNatt.constans.CamposTablaConstans;
import co.urbaNatt.constans.ConstansGenericas;
import co.urbaNatt.constans.ConsultasDinamicasConstans;
import co.urbaNatt.constans.EnumServiceTypeError;
import co.urbaNatt.constans.EnumWebServicesErrors;
import co.urbaNatt.constans.Error;
import co.urbaNatt.constans.MensajesConstans;
import co.urbaNatt.constans.SecuenciasConstans;
import co.urbaNatt.constans.TablasConstans;
import co.urbaNatt.enums.EstadosOperaciones;
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
public class UsuarioEJB implements IUsuarioEJBLocal {
	/**
	 * Instancia del log
	 */
	private static Logger log = Logger.getLogger(UsuarioEJB.class.getCanonicalName());

	OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public UsuarioEJB() {
	}

	public String crearUsuario(UsuarioInDTO usuarioInDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try {
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
		       insertsBDInDTO.setConexion(conexion);
		       insertsBDInDTO.setTabla(TablasConstans.USUARIOS);
			if (usuarioInDTO.getIdUsuario() == null) {
				insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_USUARIO);
				
				parametros.add(SecuenciasConstans.NEXT_USUARIOS);
				parametros.add(usuarioInDTO.getNombreUsuario());
				parametros.add(usuarioInDTO.getContrasenia());
				parametros.add(operacionesBD.fechaStringPorDate(new Date()));
				parametros.add(ConstansGenericas.CANTIDAD_REINTENTOS);
				parametros.add(usuarioInDTO.getEstado());
				parametros.add(usuarioInDTO.getNombreCompleto());
				parametros.add(operacionesBD.consultarIdRolPorNombre(usuarioInDTO.getRol()));
				parametros.add(usuarioInDTO.getNota());
				insertsBDInDTO.setParametros(parametros);
				resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			} else {
				List<Object[]> ups = new ArrayList<Object[]>();
				ups=llenarDatosUsuario(usuarioInDTO);
				insertsBDInDTO.setUpdates(ups);
				insertsBDInDTO.setFiltro(usuarioInDTO.getIdUsuario());
				insertsBDInDTO.setId("ID_USUARIO");
				resultado = operacionesBD.actualizarRegistro(insertsBDInDTO);
			}
			if (resultado.equals(EstadosOperaciones.EXITO.getEstado())) {
				return MensajesConstans.REGISTRO_EXITOSO;
			} else {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getDescripcion();
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
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error al cerrar la conexion
				}
			}
		}
	}
	
	
	public String crearSucursal(SucursalInDTO sucursalInDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try {
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
		       insertsBDInDTO.setConexion(conexion);
		       insertsBDInDTO.setTabla(TablasConstans.SUCURSALES);
			if (sucursalInDTO.getIdSucursal() == null) {
				insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_SUCURSAL);
				
				parametros.add(SecuenciasConstans.NEXT_SUCURSALES);
				parametros.add(sucursalInDTO.getNombreSucursal());
				parametros.add(sucursalInDTO.getTipo());
				parametros.add(sucursalInDTO.getDireccion());
				parametros.add(sucursalInDTO.getIdDepartamento());
				parametros.add(sucursalInDTO.getIdCiudad());
				parametros.add(sucursalInDTO.getIdCliente());
				insertsBDInDTO.setParametros(parametros);
				resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			} else {
				List<Object[]> ups = new ArrayList<Object[]>();
				ups=llenarDatosSucursal(sucursalInDTO);
				insertsBDInDTO.setUpdates(ups);
				insertsBDInDTO.setFiltro(sucursalInDTO.getIdSucursal());
				insertsBDInDTO.setId("ID_SUCURSAL");
				resultado = operacionesBD.actualizarRegistro(insertsBDInDTO);
			}
			if (resultado.equals(EstadosOperaciones.EXITO.getEstado())) {
				return MensajesConstans.REGISTRO_EXITOSO;
			} else {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getDescripcion();
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
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error al cerrar la conexion
				}
			}
		}
	}

	private List<Object[]> llenarDatosSucursal(SucursalInDTO sucursalInDTO) {

		List<Object[]> ups = new ArrayList<Object[]>();
		Object[] o= new Object[2];
		o[0]="NOMBRESUCURSAL";
		o[1]=sucursalInDTO.getNombreSucursal();
		ups.add(o);
		o= new Object[2];
		o[0]="TIPO";
		o[1]=sucursalInDTO.getTipo();
		ups.add(o);
		o= new Object[2];
		o[0]="DIRECCION";
		o[1]=sucursalInDTO.getDireccion();
		ups.add(o);
		o= new Object[2];
		o[0]="DEPARTAMENTO";
		o[1]=sucursalInDTO.getIdDepartamento();
		ups.add(o);
		o= new Object[2];
		o[0]="CIUDAD";
		o[1]=sucursalInDTO.getIdCiudad();
		ups.add(o);
		return ups;
	
	}

	private List<Object[]> llenarDatosUsuario(UsuarioInDTO usuarioInDTO) {

		List<Object[]> ups = new ArrayList<Object[]>();
		Object[] o= new Object[2];
		o[0]="NOMBRE_USUARIO";
		o[1]=usuarioInDTO.getNombreUsuario();
		ups.add(o);
		o= new Object[2];
		o[0]="CONTRASENIA_USUARIO";
		o[1]=usuarioInDTO.getContrasenia();
		ups.add(o);
		o= new Object[2];
		o[0]="FECHA_CREACION";
		o[1]=operacionesBD.fechaStringPorDate(new Date());
		ups.add(o);
		o= new Object[2];
		o[0]="CANTIDAD_INTENTOS_INGRESO";
		o[1]=ConstansGenericas.CANTIDAD_REINTENTOS;
		ups.add(o);
		o= new Object[2];
		o[0]="ESTADO";
		o[1]=usuarioInDTO.getEstado();
		ups.add(o);
		o= new Object[2];
		o[0]="NOMBRE_COMPLETO";
		o[1]=usuarioInDTO.getNombreCompleto();
		ups.add(o);
		o= new Object[2];
		o[0]="NOTA";
		o[1]=usuarioInDTO.getNota();
		ups.add(o);
		
		
		return ups;
	
	}

	@Override
	public String crearCliente(ClienteDTO clienteDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		String resultado="";
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();			
			insertsBDInDTO.setConexion(conexion);
			insertsBDInDTO.setTabla(TablasConstans.CLIENTES);
				
			if(clienteDTO.getIdCliente() ==null){
			validarExistenciaCliente(conexion,clienteDTO);
			
			insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_CLIENTE);
			
			parametros.add(SecuenciasConstans.NEXT_CLIENTES);
			parametros.add(operacionesBD.consultarIdTipoIdPorNombre(clienteDTO.getTipoId()));
			parametros.add(clienteDTO.getNumId());
			parametros.add(clienteDTO.getNombreCliente());
			parametros.add(clienteDTO.getTelFijo());
			parametros.add(clienteDTO.getTelCelular());
			parametros.add(clienteDTO.getCorreo());
			parametros.add(clienteDTO.getDireccion());
			parametros.add(clienteDTO.getIdDepartamento());
			parametros.add(clienteDTO.getIdCiudad());
			parametros.add(clienteDTO.getEstado());
			parametros.add(operacionesBD.fechaStringPorDate(new Date()));
			insertsBDInDTO.setParametros(parametros);
			 resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			}
			else {
				List<Object[]> ups = new ArrayList<Object[]>();
				ups=llenarListaCliente(clienteDTO);
				insertsBDInDTO.setUpdates(ups);
				insertsBDInDTO.setFiltro(clienteDTO.getIdCliente());
				insertsBDInDTO.setId("ID_CLIENTE");
				resultado = operacionesBD.actualizarRegistro(insertsBDInDTO);
			}
			if (resultado.equals(EstadosOperaciones.EXITO.getEstado())) {
				return MensajesConstans.REGISTRO_EXITOSO;
			} else {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_CREAR_CLIENTE.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_CREAR_CLIENTE.getDescripcion();
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
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					// Error al cerrar la conexion
				}
			}
		}
	}

	private void validarExistenciaCliente(Connection conexion, ClienteDTO clienteDTO) throws BusinessException {
		List<Object> parametros = new ArrayList<Object>();
		parametros.add(clienteDTO.getNumId());
		OperacionesBDInDTO consultasInDTO= new OperacionesBDInDTO();
		consultasInDTO.setConexion(conexion);
		consultasInDTO.setConsulta("SELECT ID_CLIENTE FROM CLIENTES WHERE NUMID = ?");
		consultasInDTO.setParametros(parametros);
		//se valida existencia de ese id
		ResultSet rsIn=operacionesBD.ejecutarConsulta(consultasInDTO);
		try {
			if(rsIn.next()){
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_CLIENTE_EXISTE.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_CLIENTE_EXISTE.getDescripcion();
				result.result = false;
				result.error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
				throw new BusinessException(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	private List<Object[]> llenarListaCliente(ClienteDTO clienteDTO) {
		List<Object[]> ups = new ArrayList<Object[]>();
		Object[] o= new Object[2];
		o[0]="TIPOID";
		o[1]=operacionesBD.consultarIdTipoIdPorNombre(clienteDTO.getTipoId());
		ups.add(o);
		o= new Object[2];
		o[0]="NUMID";
		o[1]=clienteDTO.getNumId();
		ups.add(o);
		o= new Object[2];
		o[0]="NOMBRECOMPLETO";
		o[1]=clienteDTO.getNombreCliente();
		ups.add(o);
		o= new Object[2];
		o[0]="FIJO";
		o[1]=clienteDTO.getTelFijo();
		ups.add(o);
		o= new Object[2];
		o[0]="CELULAR";
		o[1]=clienteDTO.getTelCelular();
		ups.add(o);
		o= new Object[2];
		o[0]="CORREO";
		o[1]=clienteDTO.getCorreo();
		ups.add(o);
		o= new Object[2];
		o[0]="DIRECCION";
		o[1]=clienteDTO.getDireccion();
		ups.add(o);
		o= new Object[2];
		o[0]="DEPARTAMENTO";
		o[1]=clienteDTO.getIdDepartamento();
		ups.add(o);
		o= new Object[2];
		o[0]="CIUDAD";
		o[1]=clienteDTO.getIdCiudad();
		ups.add(o);
		o= new Object[2];
		o[0]="ESTADO";
		o[1]=clienteDTO.getEstado();
		ups.add(o);
		o= new Object[2];
		o[0]="FECHACREACION";
		o[1]=operacionesBD.fechaStringPorDate(new Date());
		ups.add(o);
		
		return ups;
	}

	@Override
	public List<UsuarioOutDTO> consultarUsuarios(String nombreUsuario, String rol)
			throws TechnicalException, BusinessException {

		Connection conexion = null;
		try {
			List<UsuarioOutDTO> result = null;
			UserAdminDAO dao = UserAdminDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarUsuarios(nombreUsuario, rol, conexion);
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
	public String eliminarUsuario(UsuarioInDTO usuarioInDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO();
			ejecutarInDTO.setConexion(conexion);
			ejecutarInDTO.setConsulta(ConsultasDinamicasConstans.ELIMINAR_USUARIO_POR_ID);
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(usuarioInDTO.getIdUsuario());
			ejecutarInDTO.setParametros(parametros);
			Integer resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
				return MensajesConstans.ELIMINACION_EXITOSA;
			} else {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_ELIMINACION_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_ELIMINACION_USUARIO.getDescripcion();
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
	public String eliminarCliente(ClienteDTO clienteDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO();
			ejecutarInDTO.setConexion(conexion);
			ejecutarInDTO.setConsulta(ConsultasDinamicasConstans.ELIMINAR_CLIENTE_POR_ID);
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(clienteDTO.getIdCliente());
			ejecutarInDTO.setParametros(parametros);
			Integer resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
				return MensajesConstans.ELIMINACION_EXITOSA;
			} else {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_ELIMINACION_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_ELIMINACION_USUARIO.getDescripcion();
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
	public List<ClienteDTO> consultarClientes(String tipoId, String numId, String nombre)
			throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			List<ClienteDTO> result = null;
			UserAdminDAO dao = UserAdminDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarClientes(tipoId, numId, nombre, conexion);
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
	public String eliminarSucursal(SucursalInDTO sucursalInDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO();
			ejecutarInDTO.setConexion(conexion);
			ejecutarInDTO.setConsulta(ConsultasDinamicasConstans.ELIMINAR_SUCURSALES_POR_ID);
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(sucursalInDTO.getIdSucursal());
			ejecutarInDTO.setParametros(parametros);
			Integer resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
				return MensajesConstans.ELIMINACION_EXITOSA;
			} else {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_ELIMINACION_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_ELIMINACION_USUARIO.getDescripcion();
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
	public List<SucursalInDTO> consultarSucursalesPorCC(Long idCliente) throws TechnicalException, BusinessException {

		Connection conexion = null;
		try {
			List<SucursalInDTO> result = null;
			UserAdminDAO dao = UserAdminDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarSucursales(false,idCliente, conexion);
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
	public List<SucursalInDTO> consultarSucursales(Long idCliente) throws TechnicalException, BusinessException {

		Connection conexion = null;
		try {
			List<SucursalInDTO> result = null;
			UserAdminDAO dao = UserAdminDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarSucursales(true, idCliente, conexion);
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
