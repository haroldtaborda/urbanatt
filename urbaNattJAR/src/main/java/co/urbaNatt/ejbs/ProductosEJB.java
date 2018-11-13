/**
 * ManagerSecurityEJB.java
 */
package co.urbaNatt.ejbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import co.urbaNatt.DAO.ProductosDAO;
import co.urbaNatt.DTO.InsertsBDInDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.DTO.ProductoDTO;
import co.urbaNatt.DTO.ResultSecurityDTO;
import co.urbaNatt.constans.CamposTablaConstans;
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
public class ProductosEJB implements IProductosEJBLocal {
	/**
	 * Instancia del log
	 */
	private static Logger log = Logger.getLogger(ProductosEJB.class.getCanonicalName());

	OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public ProductosEJB() {
	}

	public String crearProducto(ProductoDTO usuarioInDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try {
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
		       insertsBDInDTO.setConexion(conexion);
		       insertsBDInDTO.setTabla(TablasConstans.PRODUCTOS);
			if (usuarioInDTO.getIdProducto() == null) {
				insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_PRODUCTO);
				
				parametros.add(SecuenciasConstans.NEXT_PRODUCTOS);
				parametros.add(usuarioInDTO.getNombreProducto());
				parametros.add(usuarioInDTO.getTipo());
				parametros.add(usuarioInDTO.getUnidadMedidad());
				usuarioInDTO.setUnidadMedidad(usuarioInDTO.getUnidadMedidad() != null && !usuarioInDTO.getUnidadMedidad().isEmpty() ? usuarioInDTO.getUnidadMedidad() : "Lb");
				usuarioInDTO.setDescripcion(usuarioInDTO.getDescripcion() != null && !usuarioInDTO.getDescripcion().isEmpty() ? usuarioInDTO.getDescripcion() : "N/A");
				parametros.add(usuarioInDTO.getDescripcion());
				parametros.add(usuarioInDTO.getCantidad());
				parametros.add(usuarioInDTO.getPeso());
				parametros.add(usuarioInDTO.getValor());
				insertsBDInDTO.setParametros(parametros);
				resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			} else {
				List<Object[]> ups = new ArrayList<Object[]>();
				usuarioInDTO.setDescripcion(usuarioInDTO.getDescripcion() != null && !usuarioInDTO.getDescripcion().isEmpty() ? usuarioInDTO.getDescripcion() : "N/A");
				usuarioInDTO.setUnidadMedidad(usuarioInDTO.getUnidadMedidad() != null && !usuarioInDTO.getUnidadMedidad().isEmpty() ? usuarioInDTO.getUnidadMedidad() : "Lb");
				ups=llenarDatosProducto(usuarioInDTO);
				insertsBDInDTO.setUpdates(ups);
				insertsBDInDTO.setFiltro(usuarioInDTO.getIdProducto());
				insertsBDInDTO.setId("ID_PRODUCTO");
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

	private List<Object[]> llenarDatosProducto(ProductoDTO usuarioInDTO) {

		List<Object[]> ups = new ArrayList<Object[]>();
		Object[] o= new Object[2];
		o[0]="NOMBRE";
		o[1]=usuarioInDTO.getNombreProducto();
		ups.add(o);
		o= new Object[2];
		o[0]="TIPO";
		o[1]=usuarioInDTO.getTipo();
		ups.add(o);
		o= new Object[2];
		o[0]="UNIDAD";
		o[1]=usuarioInDTO.getUnidadMedidad();
		ups.add(o);
		o= new Object[2];
		o[0]="DESCRIPCION";
		o[1]=usuarioInDTO.getDescripcion();
		ups.add(o);
		o= new Object[2];
		o[0]="CANTIDAD";
		o[1]=usuarioInDTO.getCantidad();
		ups.add(o);
		o= new Object[2];
		o[0]="PESO";
		o[1]=usuarioInDTO.getPeso();
		ups.add(o);

		
		return ups;
	
	}

	
	@Override
	public String eliminarProducto(ProductoDTO usuarioInDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO();
			ejecutarInDTO.setConexion(conexion);
			ejecutarInDTO.setConsulta(ConsultasDinamicasConstans.ELIMINAR_PRODUCTO_POR_ID);
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(usuarioInDTO.getIdProducto());
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
	public List<ProductoDTO> consultaProductos(String nombreUsuario, String rol)
			throws TechnicalException, BusinessException {

		Connection conexion = null;
		try {
			List<ProductoDTO> result = null;
			ProductosDAO dao = ProductosDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultaProductos(nombreUsuario, rol, conexion);
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
