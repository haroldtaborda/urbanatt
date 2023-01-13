/**
 * ManagerSecurityEJB.java
 */
package co.urbaNatt.ejbs;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import co.urbaNatt.DAO.ProductosDAO;
import co.urbaNatt.DTO.ActualizarPrecioIndividualDTO;
import co.urbaNatt.DTO.ActualizarPreciosDTO;
import co.urbaNatt.DTO.DetalleFacturaDTO;
import co.urbaNatt.DTO.FacturaDTO;
import co.urbaNatt.DTO.InsertsBDInDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.DTO.PreciosClienteDTO;
import co.urbaNatt.DTO.ProductoDTO;
import co.urbaNatt.DTO.ReporteDTO;
import co.urbaNatt.DTO.ResultSecurityDTO;
import co.urbaNatt.constans.CamposTablaConstans;
import co.urbaNatt.constans.EnumServiceTypeError;
import co.urbaNatt.constans.EnumWebServicesErrors;
import co.urbaNatt.constans.Error;
import co.urbaNatt.constans.MensajesConstans;
import co.urbaNatt.constans.SecuenciasConstans;
import co.urbaNatt.constans.TablasConstans;
import co.urbaNatt.enums.EstadoFacturasEnum;
import co.urbaNatt.enums.EstadoPagosEnum;
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
public class FacturasEJB implements IFacturasEJBLocal {
	/**
	 * Instancia del log
	 */
	private static Logger log = Logger.getLogger(FacturasEJB.class.getCanonicalName());

	OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public FacturasEJB() {
	}

	public String crearFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try {
			//calculamos el valor descuento
			
			BigDecimal valorCalculado= BigDecimal.ZERO;
			if(usuarioInDTO.getDescuento() != null || !usuarioInDTO.getDescuento().isEmpty()) {
				BigDecimal descuento=new BigDecimal(usuarioInDTO.getDescuento());
				BigDecimal valorMultiplicacion=usuarioInDTO.getValorFactura().multiply(descuento);
				valorCalculado=usuarioInDTO.getValorFactura().subtract((valorMultiplicacion.divide(new BigDecimal(100))));
			}
			else {
				valorCalculado=usuarioInDTO.getValorFactura();
			}
			usuarioInDTO.setValorFactura(valorCalculado); 
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
			insertsBDInDTO.setConexion(conexion);
			insertsBDInDTO.setTabla(TablasConstans.FACTURAS);
			insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_FACTURA);

			parametros.add(SecuenciasConstans.NEXT_FACTURAS);
			parametros.add(usuarioInDTO.getNumeroFactura());
			parametros.add(usuarioInDTO.getIdCliente());
			parametros.add(operacionesBD.fechaStringPorDate(new Date()));
			parametros.add(operacionesBD.fechaStringPorDate(usuarioInDTO.getFechaFactura()));
			// cuando se crea el valor deuda = a valro factura
			if (usuarioInDTO.getTipo().equalsIgnoreCase("CONTADO")) {
				parametros.add(BigDecimal.ZERO);
				parametros.add(usuarioInDTO.getValorFactura());
				parametros.add(EstadoFacturasEnum.PAGADA.getEstado());
			} else {
				parametros.add(usuarioInDTO.getValorFactura());
				parametros.add(BigDecimal.ZERO);
				parametros.add(usuarioInDTO.getEstado() == null || usuarioInDTO.getEstado().isEmpty()
						? EstadoFacturasEnum.NUEVA.getEstado() : usuarioInDTO.getEstado());
			}

			String fechaA = operacionesBD.fechaStringPorDate(new Date());
			parametros.add(usuarioInDTO.getValorFactura());
			parametros.add(usuarioInDTO.getDescripcion());
			parametros.add(usuarioInDTO.getTipo());
			parametros.add(usuarioInDTO.getIdSucursal());
			if (usuarioInDTO.getTipo().equalsIgnoreCase("CONTADO")) {
				parametros.add(fechaA);
			} else {
				parametros.add(null);
			}
			parametros.add(usuarioInDTO.getDescuento());
			parametros.add(usuarioInDTO.getVendedor());
			insertsBDInDTO.setParametros(parametros);
			resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			Long idFactura = 0L;
			idFactura = consultarIdFacturaPorNumero(usuarioInDTO.getNumeroFactura(), conexion);
			String fechaActual=operacionesBD.fechaStringPorDate(new Date());
			for (ProductoDTO p : usuarioInDTO.getProductos()) {
				// se crea un detalle factura por cada producto agregado
				parametros = new ArrayList<Object>();
				insertsBDInDTO.setConexion(conexion);
				insertsBDInDTO.setTabla(TablasConstans.FACTURA_PRODUCTO);
				insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_FACTURA_PRODUCTO);
				parametros.add(SecuenciasConstans.NEXT_FACTURA_PRODUCTO);
				parametros.add(idFactura);
				parametros.add(p.getIdProducto());
				parametros.add(fechaActual);
				parametros.add(p.getCantidad());
				parametros.add(p.getNombreProducto() + " " + p.getPeso() + " " + p.getUnidadMedidad());
				parametros.add("VENTA");
				parametros.add(p.getValor());
				insertsBDInDTO.setParametros(parametros);
				resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
				// se resta del lote la cantidad agregada
				restarCantidadProductos(p, conexion);
			}
			for (ProductoDTO p : usuarioInDTO.getProductosRegalo()) {
				// se crea un detalle factura por cada producto agregado
				parametros = new ArrayList<Object>();
				insertsBDInDTO.setConexion(conexion);
				insertsBDInDTO.setTabla(TablasConstans.FACTURA_PRODUCTO);
				insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_FACTURA_PRODUCTO);
				parametros.add(SecuenciasConstans.NEXT_FACTURA_PRODUCTO);
				parametros.add(idFactura);
				parametros.add(p.getIdProducto());
				parametros.add(fechaActual);
				parametros.add(p.getCantidad());
				parametros.add(p.getNombreProducto() + " " + p.getPeso() + " " + p.getUnidadMedidad());
				parametros.add("REGALO");
				parametros.add(BigDecimal.ZERO);
				insertsBDInDTO.setParametros(parametros);
				resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
				// se resta del lote la cantidad agregada
				restarCantidadProductos(p, conexion);
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
			operacionesBD.cerrarStatement();
		}
	}

	private void restarCantidadProductos(ProductoDTO p, Connection conexion) throws BusinessException {
		List<Object> parametros = new ArrayList<Object>();
		parametros.add(p.getIdProducto());
		OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
		consultasInDTO.setConexion(conexion);
		consultasInDTO.setConsulta("SELECT CANTIDAD, NOMBRE FROM PRODUCTOS WHERE ID_PRODUCTO = ?");
		consultasInDTO.setParametros(parametros);
		// primero se verifica y consulta cantidad
		ResultSet rs = operacionesBD.ejecutarConsulta(consultasInDTO);
		int cant = 0;
		String nombre = "";
		try {
			if (rs.next()) {
				cant = rs.getInt(1);
				nombre = rs.getString(2);
			}
		} catch (Exception e) {
			cant = 0;
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}
		}

		if (p.getCantidad() > cant) {
			ResultSecurityDTO result = new ResultSecurityDTO();
			result.error = new Error();
			result.error.errorCode = EnumWebServicesErrors.ERROR_MAXIMO_PRODUCTOS.getCodigo();
			result.error.errorDescription = "El producto " + nombre + " no cuenta con una disponibilidad de "
					+ p.getCantidad() + " ya que en existencia solo cuenta con " + cant;
			result.result = false;
			result.error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
			throw new BusinessException(result);
		}
		parametros = new ArrayList<Object>();
		parametros.add(p.getCantidad());
		parametros.add(p.getIdProducto());
		OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO(
				"UPDATE PRODUCTOS SET CANTIDAD = (CANTIDAD - ?) WHERE ID_PRODUCTO= ?", conexion, parametros);
		// se hace el update de factura
		operacionesBD.ejecutarOperacionBD(ejecutarInDTO);

	}

	private Long consultarIdFacturaPorNumero(String numeroFactura, Connection conexion) {
		List<Object> parametros = new ArrayList<Object>();
		ResultSet rs = null;
		Long idFactura = 0L;
		try {
			parametros.add(numeroFactura);
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO(
					"SELECT ID_FACTURA FROM FACTURAS WHERE NUMERO_FACTURA = ?", conexion, parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			if (rs.next()) {
				idFactura = rs.getLong(1);
			}
		} catch (Exception e) {
			return 0L;
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
		return idFactura;
	}

	private Long consultarIdPrecioPorNumero(String idCliente, Connection conexion) {
		List<Object> parametros = new ArrayList<Object>();
		ResultSet rs = null;
		Long idFactura = 0L;
		try {
			parametros.add(idCliente);
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO(
					"SELECT ID_CLIENTE_PRECIOS FROM PRECIOS_CLIENTE WHERE ID_CLIENTE = ?", conexion, parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			if (rs.next()) {
				idFactura = rs.getLong(1);
			}
		} catch (Exception e) {
			return 0L;
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
		return idFactura;
	}


	@Override
	public String abonarFactura(DetalleFacturaDTO detalleFacturaDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try {
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
			insertsBDInDTO.setConexion(conexion);
			insertsBDInDTO.setTabla(TablasConstans.DETALLE_FACTURA);
			insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_DETALLE_FACTURA);

			parametros.add(SecuenciasConstans.NEXT_DETALLE_FACTURA);
			parametros.add(detalleFacturaDTO.getIdFactura());
			parametros.add(operacionesBD.fechaStringPorDate(new Date()));
			parametros.add(detalleFacturaDTO.getValorPagado());
			// si el valor abonado es = al valor factura estado es = a total
			BigDecimal valorFactura = consultarValorFactura(detalleFacturaDTO.getIdFactura(), conexion);
			String estado = EstadoPagosEnum.ABONO.getEstado();
			if (valorFactura.compareTo(detalleFacturaDTO.getValorPagado()) == 0) {
				estado = EstadoPagosEnum.PAGO_TOTAL.getEstado();
			}
			parametros.add(estado);
			parametros.add(detalleFacturaDTO.getNumeroRecibo());
			parametros.add(detalleFacturaDTO.getNumeroFactura());
			insertsBDInDTO.setParametros(parametros);
			// se crea el abono
			resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			// se actualizan los valores en la factura
			actualizarValoresFactura(detalleFacturaDTO, conexion);
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

	private String actualizarValoresFactura(DetalleFacturaDTO detalleFacturaDTO, Connection conexion)
			throws TechnicalException, BusinessException {
		ResultSet rs = null;
		try {
			BigDecimal valorDeuda = BigDecimal.ZERO;
			BigDecimal valorPagado = BigDecimal.ZERO;
			BigDecimal valorFactura = BigDecimal.ZERO;
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(detalleFacturaDTO.getIdFactura());
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO(
					"SELECT VALOR_FACTURA, VALOR_DEUDA,VALOR_PAGADO FROM FACTURAS WHERE ID_FACTURA= ?", conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			String estado = EstadoFacturasEnum.EN_ABONO.getEstado();
			if (rs.next()) {
				valorFactura = rs.getBigDecimal(1);
				valorDeuda = rs.getBigDecimal(2);
				valorPagado = rs.getBigDecimal(3);
				valorDeuda = valorDeuda.subtract(detalleFacturaDTO.getValorPagado());
				valorPagado = valorPagado.add(detalleFacturaDTO.getValorPagado());
				if (valorDeuda.compareTo(BigDecimal.ZERO) <= 0) {
					// se cambia el estado de la factura
					estado = EstadoFacturasEnum.PAGADA.getEstado();
					valorDeuda = BigDecimal.ZERO;
				}
				if (valorPagado.compareTo(valorFactura) >= 0) {
					// se cambia el estado de la factura
					estado = EstadoFacturasEnum.PAGADA.getEstado();
					valorPagado = valorFactura;
				}
				parametros = new ArrayList<Object>();
				parametros.add(valorDeuda);
				parametros.add(valorPagado);
				parametros.add(estado);
				String fechaA = operacionesBD.fechaStringPorDate(new Date());
				String query="";
				if (estado == EstadoFacturasEnum.PAGADA.getEstado()) {
					parametros.add(fechaA);
					query="UPDATE FACTURAS SET VALOR_DEUDA = ?, VALOR_PAGADO= ? , ESTADO = ?, FECHA_PAGO_TOTAL= ? WHERE ID_FACTURA= ?";
				} else {
					query="UPDATE FACTURAS SET VALOR_DEUDA = ?, VALOR_PAGADO= ? , ESTADO = ? WHERE ID_FACTURA= ?";
				}
				parametros.add(detalleFacturaDTO.getIdFactura());
				OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO(
						query,
						conexion, parametros);
				// se hace el update de factura
				Integer resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
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
			}
		} catch (Exception e) {
			// no se actualizaron los valores
			System.out.println("Error");

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
		return MensajesConstans.REGISTRO_EXITOSO;

	}

	private BigDecimal consultarValorFactura(Long idFactura, Connection conexion) {
		ResultSet rs = null;
		BigDecimal valorFactura = null;
		try {
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setConsulta("SELECT VALOR_FACTURA FROM FACTURAS WHERE ID_FACTURA = ?");
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(idFactura);
			consultasInDTO.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			if (rs.next()) {
				valorFactura = rs.getBigDecimal(1);
			}
		} catch (Exception e) {
			return BigDecimal.ZERO;
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
		return valorFactura;
	}

	@Override
	public List<FacturaDTO> consultasFacturas(String numeroFactura, String estado, String numeroId, Integer dias,String nombreCliente)
			throws TechnicalException, BusinessException {

		Connection conexion = null;
		try {
			List<FacturaDTO> result = null;
			ProductosDAO dao = ProductosDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarFacturas(numeroFactura, estado, numeroId, dias,nombreCliente, conexion);
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
	public String cambiarEstadoFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String eliminarFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			Integer resultado = null;
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(usuarioInDTO.getIdFactura());
			OperacionesBDInDTO ejecutarInDTO = null;

			parametros = new ArrayList<Object>();
			parametros.add(usuarioInDTO.getIdFactura());
			ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM FACTURA_PRODUCTO WHERE ID_FACTURA = ?", conexion,
					parametros);
			operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM DETALLE_FACTURA WHERE ID_FACTURA = ?", conexion,
					parametros);
			operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM FACTURAS WHERE ID_FACTURA = ?", conexion, parametros);
			resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
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

	@Override
	public String generarReporte(ReporteDTO reporteDTO) throws TechnicalException, BusinessException {
		try {
			if (reporteDTO.getTipoReporte().equals("Facturas")) {
				generarReporteFacturas(reporteDTO);
			} else if (reporteDTO.getTipoReporte().equals("Productos")) {
				generarReporteProductos(reporteDTO);
			} else if (reporteDTO.getTipoReporte().equals("Cuentas por cobrar")) {
				generarReporteCuentasCobrar(reporteDTO);
			} else if (reporteDTO.getTipoReporte().equals("Facturas por cliente")) {
				generarReporteFacturasPorCliente(reporteDTO);
			}
			else if (reporteDTO.getTipoReporte().equals("Total ventas")) {
				generarReporteVentasTotal(reporteDTO);
			}
		} catch (Exception e) {
			return "Error";
		}

		return MensajesConstans.REGISTRO_EXITOSO;
	}

	private void generarReporteVentasTotal(ReporteDTO reporteDTO) {
		if(reporteDTO.getTipoFactura().equalsIgnoreCase("Mes")){
			generarReportePorMes(reporteDTO);
		}
		else{
			//AÑO	
			generarReportePorAnio(reporteDTO);
		}
		
	}

	private void generarReportePorAnio(ReporteDTO reporteDTO) {
		NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(Locale.US);
		// Creacion del documento con los margenes
		Document document = new Document(PageSize.A4);
		SimpleDateFormat format= new SimpleDateFormat("yyyy");
		
		String anio=format.format(reporteDTO.getFecha());
		// El archivo pdf que vamos a generar
		FileOutputStream fileOutputStream;
		ResultSet rs = null;
		try {

			Connection conexion = null;
			try {
				conexion = ConnectionUtils.getInstance().getConnectionBack();
			} catch (Exception e1) {
				 
				e1.printStackTrace();
			}

			String ruta = null;
			ResultSet rs3 = null;
			try {
				OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
				consultasInDTO = new OperacionesBDInDTO("SELECT VALOR FROM PARAMETROS WHERE ID_PARAMETRO = 1", conexion,
						new ArrayList<>());
				rs3 = operacionesBD.ejecutarConsulta(consultasInDTO);
				if (rs3.next()) {
					ruta = rs3.getString(1);
				}
			} catch (Exception e) {
				ruta = "";
				System.out.println(e);
			}
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			Date fechaActual = new Date();
			String fe = operacionesBD.fechaStringhoraPorDateReporte(fechaActual);
			String nombreReporte = ruta + "\\reportePorAnio" + fe + ".pdf";
			fileOutputStream = new FileOutputStream(nombreReporte);
			// Obtener la instancia del PdfWriter
			PdfWriter.getInstance(document, fileOutputStream);
			// Abrir el documento
			document.open();

			Image image = null;
			// Obtenemos el logo de datojava
			image = Image.getInstance("logoIni.jpg");
			image.scaleAbsolute(80f, 60f);
			PdfPTable table = new PdfPTable(1);
			PdfPCell cell = new PdfPCell(image);
			cell.setColspan(5);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			document.add(table);
			
			// Creacion del parrafo
			Paragraph paragraph = new Paragraph();
			String fechaHora=operacionesBD.fechaStringhoraPorDateReporte(new Date());
			// Agregar un titulo con su respectiva fuente
			paragraph.add(new Phrase("URBANATT FUERZA Y VITALIDAD"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Dirección: Calle 13 Cr 11 # 10-42"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Telefonos: 3136425448-3122249865"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Correo: urbanattfuerzayvitalidad@gmail.com"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Fecha-hora: "+fechaHora));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Cuentas por cobrar"));

			
			com.itextpdf.text.Font f4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, new BaseColor(119, 194, 27));
			// Agregar saltos de linea
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			document.add(paragraph);
			List<Object> parametros=null;
			OperacionesBDInDTO consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			consulta.setConsulta(
					"select COUNT(ID_FACTURA),NVL(SUM(VALOR_DEUDA),0), NVL(SUM(VALOR_PAGADO),0), NVL(SUM(VALOR_FACTURA),0) from FACTURAS WHERE TIPO='CREDITO' AND TO_CHAR(TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'),'yyyy')=?");
			parametros= new ArrayList<>();
			parametros.add(anio);
			consulta.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consulta);
			if (rs.next()) {
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Total ventas CREDITO año "+ anio, f4));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total de facturas CREDITO: "+ rs.getInt(1)));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total deuda CREDITO: "+ formatoImporte.format(rs.getBigDecimal(2))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total pagado CREDITO: "+ formatoImporte.format(rs.getBigDecimal(3))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total facturas CREDITO: "+ formatoImporte.format(rs.getBigDecimal(4))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}

			
			consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			consulta.setConsulta(
					"select COUNT(ID_FACTURA),NVL(SUM(VALOR_DEUDA),0), NVL(SUM(VALOR_PAGADO),0), NVL(SUM(VALOR_FACTURA),0) from FACTURAS WHERE TIPO='CONTADO' AND TO_CHAR(TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'),'yyyy')=?");
			parametros= new ArrayList<>();
			parametros.add(anio);
			consulta.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consulta);

			if (rs.next()) {
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Total ventas CONTADO año "+ anio, f4));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total de facturas CONTADO: "+ rs.getInt(1)));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total deuda CONTADO: "+ formatoImporte.format(rs.getBigDecimal(2))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total pagado CONTADO: "+ formatoImporte.format(rs.getBigDecimal(3))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total facturas CONTADO: "+ formatoImporte.format(rs.getBigDecimal(4))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			
			
			consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			consulta.setConsulta(
					"select D.NUMERO_FACTURA, NVL(SUM(D.VALOR_PAGADO),0), COUNT(D.ID_DETALLE) FROM DETALLE_FACTURA D INNER JOIN FACTURAS F ON F.ID_FACTURA=D.ID_FACTURA WHERE TO_CHAR(TO_DATE(F.FECHA_FACTURA, 'dd/MM/yyyy'),'yyyy')=? GROUP BY D.NUMERO_FACTURA");
			parametros= new ArrayList<>();
			parametros.add(anio);
			consulta.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consulta);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Total ABONOS año "+ anio, f4));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			document.add(paragraph);
			

			com.itextpdf.text.Font f5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, new BaseColor(119, 194, 27));

			float[] anchocolumnas = new float[3];
			for (int j = 0; j < 3; j++) {
				anchocolumnas[j] = .50f;
			}
			table = new PdfPTable(anchocolumnas);
			cell = new PdfPCell(new Paragraph("Número Factura", f5));
			 cell.setBackgroundColor(new BaseColor(211, 216, 205));
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Valor abonado", f5));
			 cell.setBackgroundColor(new BaseColor(211, 216, 205));
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Cantidad de abonos", f5));
			 cell.setBackgroundColor(new BaseColor(211, 216, 205));
			table.addCell(cell);
			BigDecimal sumaTotal= BigDecimal.ZERO;
			while (rs.next()) {
				table.addCell(rs.getString(1));
				table.addCell(formatoImporte.format(rs.getBigDecimal(2)) + "");
				table.addCell(rs.getInt(3) + "");
				sumaTotal=sumaTotal.add(rs.getBigDecimal(2));
			}
			
			PdfPCell celdaSum = new PdfPCell(new Paragraph("Valor total abonado " + formatoImporte.format(sumaTotal)));
			celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
			celdaSum.setColspan(3);
			table.addCell(celdaSum);

			
			document.add(table);
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Final del documento. Reporte Anual"));
			document.add(paragraph);

			// Cerrar el documento
			document.close();
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		} catch (MalformedURLException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		} finally {
			operacionesBD.cerrarStatement();
		}

	
		
	
		
	}

	private void generarReportePorMes(ReporteDTO reporteDTO) {

		NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(Locale.US);
		// Creacion del documento con los margenes
		Document document = new Document(PageSize.A4);

		// El archivo pdf que vamos a generar
		FileOutputStream fileOutputStream;
		ResultSet rs = null;
		try {

			Connection conexion = null;
			try {
				conexion = ConnectionUtils.getInstance().getConnectionBack();
			} catch (Exception e1) {
				 
				e1.printStackTrace();
			}

			String ruta = null;
			ResultSet rs3 = null;
			try {
				OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
				consultasInDTO = new OperacionesBDInDTO("SELECT VALOR FROM PARAMETROS WHERE ID_PARAMETRO = 1", conexion,
						new ArrayList<>());
				rs3 = operacionesBD.ejecutarConsulta(consultasInDTO);
				if (rs3.next()) {
					ruta = rs3.getString(1);
				}
			} catch (Exception e) {
				ruta = "";
				System.out.println(e);
			}
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			Date fechaActual = new Date();
			String fe = operacionesBD.fechaStringhoraPorDateReporte(fechaActual);
			String nombreReporte = ruta + "\\reportePorMes" + fe + ".pdf";
			fileOutputStream = new FileOutputStream(nombreReporte);
			// Obtener la instancia del PdfWriter
			PdfWriter.getInstance(document, fileOutputStream);
			// Abrir el documento
			document.open();

			Image image = null;
			// Obtenemos el logo de datojava
			image = Image.getInstance("logoIni.jpg");
			image.scaleAbsolute(80f, 60f);
			PdfPTable table = new PdfPTable(1);
			PdfPCell cell = new PdfPCell(image);
			cell.setColspan(5);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			document.add(table);
			
			// Creacion del parrafo
			Paragraph paragraph = new Paragraph();
			String fechaHora=operacionesBD.fechaStringhoraPorDateReporte(new Date());
			// Agregar un titulo con su respectiva fuente
			paragraph.add(new Phrase("URBANATT FUERZA Y VITALIDAD"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Dirección: Calle 13 Cr 11 # 10-42"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Telefonos: 3136425448-3122249865"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Correo: urbanattfuerzayvitalidad@gmail.com"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Fecha-hora: "+fechaHora));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Cuentas por cobrar"));

			
			com.itextpdf.text.Font f4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, new BaseColor(119, 194, 27));
			// Agregar saltos de linea
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			document.add(paragraph);
			List<Object> parametros=null;
			OperacionesBDInDTO consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			consulta.setConsulta(
					"select COUNT(ID_FACTURA),NVL(SUM(VALOR_DEUDA),0), NVL(SUM(VALOR_PAGADO),0), NVL(SUM(VALOR_FACTURA),0) from FACTURAS WHERE TIPO='CREDITO' AND TO_CHAR(TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'),'MM')=?");
			parametros= new ArrayList<>();
			parametros.add(operacionesBD.consultarNumeroMesPorTexto(reporteDTO.getMes()));
			consulta.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consulta);
			if (rs.next()) {
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Total ventas CREDITO mes "+ reporteDTO.getMes(), f4));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total de facturas CREDITO: "+ rs.getInt(1)));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total deuda CREDITO: "+ formatoImporte.format(rs.getBigDecimal(2))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total pagado CREDITO: "+ formatoImporte.format(rs.getBigDecimal(3))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total facturas CREDITO: "+ formatoImporte.format(rs.getBigDecimal(4))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}

			
			consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			consulta.setConsulta(
					"select COUNT(ID_FACTURA),NVL(SUM(VALOR_DEUDA),0), NVL(SUM(VALOR_PAGADO),0), NVL(SUM(VALOR_FACTURA),0) from FACTURAS WHERE TIPO='CONTADO' AND TO_CHAR(TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'),'MM')=?");
			parametros= new ArrayList<>();
			parametros.add(operacionesBD.consultarNumeroMesPorTexto(reporteDTO.getMes()));
			consulta.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consulta);

			if (rs.next()) {
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Total ventas CONTADO mes "+ reporteDTO.getMes(), f4));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total de facturas CONTADO: "+ rs.getInt(1)));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total deuda CONTADO: "+ formatoImporte.format(rs.getBigDecimal(2))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total pagado CONTADO: "+ formatoImporte.format(rs.getBigDecimal(3))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Total facturas CONTADO: "+ formatoImporte.format(rs.getBigDecimal(4))));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			
			
			consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			consulta.setConsulta(
					"select D.NUMERO_FACTURA, NVL(SUM(D.VALOR_PAGADO),0), COUNT(D.ID_DETALLE) FROM DETALLE_FACTURA D INNER JOIN FACTURAS F ON F.ID_FACTURA=D.ID_FACTURA WHERE TO_CHAR(TO_DATE(F.FECHA_FACTURA, 'dd/MM/yyyy'),'MM')=? GROUP BY D.NUMERO_FACTURA");
			parametros= new ArrayList<>();
			parametros.add(operacionesBD.consultarNumeroMesPorTexto(reporteDTO.getMes()));
			consulta.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consulta);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Total ABONOS mes "+ reporteDTO.getMes(), f4));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			document.add(paragraph);
			

			com.itextpdf.text.Font f5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, new BaseColor(119, 194, 27));

			float[] anchocolumnas = new float[3];
			for (int j = 0; j < 3; j++) {
				anchocolumnas[j] = .50f;
			}
			table = new PdfPTable(anchocolumnas);
			cell = new PdfPCell(new Paragraph("Número Factura", f5));
			 cell.setBackgroundColor(new BaseColor(211, 216, 205));
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Valor abonado", f5));
			 cell.setBackgroundColor(new BaseColor(211, 216, 205));
			table.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Cantidad de abonos", f5));
			 cell.setBackgroundColor(new BaseColor(211, 216, 205));
			table.addCell(cell);
			BigDecimal sumaTotal= BigDecimal.ZERO;
			while (rs.next()) {
				table.addCell(rs.getString(1));
				table.addCell(formatoImporte.format(rs.getBigDecimal(2)) + "");
				table.addCell(rs.getInt(3) + "");
				sumaTotal=sumaTotal.add(rs.getBigDecimal(2));
			}
			
			PdfPCell celdaSum = new PdfPCell(new Paragraph("Valor total abonado " + formatoImporte.format(sumaTotal)));
			celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
			celdaSum.setColspan(3);
			table.addCell(celdaSum);

			
			document.add(table);
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Final del documento. Reporte Mensual"));
			document.add(paragraph);

			// Cerrar el documento
			document.close();
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		} catch (MalformedURLException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		} finally {
			operacionesBD.cerrarStatement();
		}

	
		
	}

	private void generarReporteFacturasPorCliente(ReporteDTO reporteDTO) {
		
		int cantidadFacturas= 0;
		NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(Locale.US);
		// Creacion del documento con los margenes
		Document document = new Document(PageSize.A4);

		// El archivo pdf que vamos a generar
		FileOutputStream fileOutputStream;
		ResultSet rs2 = null;
		ResultSet rs5 = null;
		ResultSet rs = null;
		try {

			Connection conexion = null;
			try {
				conexion = ConnectionUtils.getInstance().getConnectionBack();
			} catch (Exception e1) {
				 
				e1.printStackTrace();
			}

			String ruta = null;
			ResultSet rs3 = null;
			try {
				OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
				consultasInDTO = new OperacionesBDInDTO("SELECT VALOR FROM PARAMETROS WHERE ID_PARAMETRO = 1", conexion,
						new ArrayList<>());
				rs3 = operacionesBD.ejecutarConsulta(consultasInDTO);
				if (rs3.next()) {
					ruta = rs3.getString(1);
				}
			} catch (Exception e) {
				ruta = "";
				System.out.println(e);
			}
			if (rs3 != null) {
				try {
					rs3.close();
					operacionesBD.cerrarStatement();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			Date fechaActual = new Date();
			String fe = operacionesBD.fechaStringhoraPorDateReporte(fechaActual);
			String nombreReporte = ruta + "\\reporteFactuasCliente" + fe + ".pdf";
			fileOutputStream = new FileOutputStream(nombreReporte);
			// Obtener la instancia del PdfWriter
			PdfWriter.getInstance(document, fileOutputStream);
			// Abrir el documento
			document.open();

			Image image = null;
			// Obtenemos el logo de datojava
			image = Image.getInstance("logoIni.jpg");
			image.scaleAbsolute(80f, 60f);
			PdfPTable table = new PdfPTable(1);
			PdfPCell cell = new PdfPCell(image);
			cell.setColspan(5);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			document.add(table);
			
			// Creacion del parrafo
			Paragraph paragraph = new Paragraph();
			String fechaHora=operacionesBD.fechaStringhoraPorDateReporte(new Date());
			// Agregar un titulo con su respectiva fuente
			paragraph.add(new Phrase("URBANATT FUERZA Y VITALIDAD"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Dirección: Calle 13 Cr 11 # 10-42"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Telefonos: 3136425448-3122249865"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Correo: urbanattfuerzayvitalidad@gmail.com"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Fecha-hora: "+fechaHora));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Cuentas por cobrar"));

			// Agregar saltos de linea
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			document.add(paragraph);
			OperacionesBDInDTO consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			if(reporteDTO.getIdCliente() != null){
				consulta.setConsulta(
						"select DISTINCT TIPOID,NUMID, NOMBRECOMPLETO, FIJO,CELULAR,CI.NOMBRE,DIRECCION, C.ID_CLIENTE from CLIENTES C INNER JOIN CIUDADES CI ON C.CIUDAD=CI.ID "
						+ "WHERE NUMID =? ORDER BY C.NOMBRECOMPLETO");
				List<Object> parametros = new ArrayList<Object>();
				parametros.add(reporteDTO.getIdCliente().toString());
				consulta.setParametros(parametros);
			}
			else{
			consulta.setConsulta(
					"select DISTINCT TIPOID,NUMID, NOMBRECOMPLETO, FIJO,CELULAR,CI.NOMBRE,DIRECCION, C.ID_CLIENTE from CLIENTES C INNER JOIN CIUDADES CI ON C.CIUDAD=CI.ID ORDER BY C.NOMBRECOMPLETO");
			}
			rs = operacionesBD.ejecutarConsulta(consulta);
			String tipo = "";
					// Creacion de una tabla

					com.itextpdf.text.Font f4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, new BaseColor(119, 194, 27));

					com.itextpdf.text.Font f3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, new BaseColor(119, 194, 27));
					
					com.itextpdf.text.Font fontDeudas = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.RED);
					com.itextpdf.text.Font fontNormal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);

					float[] anchocolumnas = new float[6];
					for (int j = 0; j < 6; j++) {
						anchocolumnas[j] = .50f;
					}

					float[] anchocolumnas2 = new float[10];
					for (int j = 0; j <10; j++) {
						anchocolumnas2[j] = .50f;
					}
					

					float[] anchocolumnas3 = new float[5];
					for (int j = 0; j < 5; j++) {
						anchocolumnas3[j] = .50f;
					}
					List<Object[]> lista = new ArrayList<Object[]>();
					Object[] ob= new Object[6];
					while (rs.next()) {
						 ob= new Object[6];
						 ob[0]=rs.getLong(1)+"";
						 ob[1]=rs.getString(2);
						 ob[2]=rs.getString(3);
						 ob[3]=rs.getLong(4) + "-" + rs.getLong(5);
						 ob[4]=rs.getString(6);
						 ob[5]=rs.getString(7);
						 lista.add(ob);

					}
					if (rs != null) {
						try {
							rs.close();
							operacionesBD.cerrarStatement();
						} catch (SQLException e) {
							 
							e.printStackTrace();
						}
					}
					
					
				for (Object[] object : lista) {
						paragraph = new Paragraph();
						paragraph.add(new Phrase(Chunk.NEWLINE));
						paragraph.add(new Phrase("Cliente"));
						paragraph.add(new Phrase(Chunk.NEWLINE));
						paragraph.add(new Phrase(Chunk.NEWLINE));
						document.add(paragraph);
						List<Object> parametros = new ArrayList<>();
						 table = new PdfPTable(anchocolumnas);
						 cell = new PdfPCell(new Paragraph("Tipo documento", f4));
						 cell.setBackgroundColor(new BaseColor(211, 216, 205));
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("Documento", f4));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("Nombres", f4));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("Telefonos", f4));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("Ciudad", f4));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						table.addCell(cell);
						cell = new PdfPCell(new Paragraph("Dirección", f4));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						table.addCell(cell);

						PdfPTable tableFacturas = new PdfPTable(anchocolumnas2);
						PdfPTable tableAbonos = new PdfPTable(anchocolumnas3);
						if (object[0].toString().equals("2")) {
							tipo = "Cédula ciudadania";
						} else if (object[0].toString().equals("4")) {
							tipo = "NIT";
						}

						table.addCell(tipo);
						table.addCell(object[1].toString());
						table.addCell(object[2].toString());
						table.addCell(object[3].toString());
						table.addCell(object[4].toString());
						table.addCell(object[5].toString());

						document.add(table);
						parametros.add(object[1].toString());
						consulta = new OperacionesBDInDTO(
								"select fecha_factura,sysdate-TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'), NUMERO_FACTURA,DESCRIPCION, VALOR_FACTURA,VALOR_DEUDA,VALOR_PAGADO,FECHA_PAGO_TOTAL,ID_FACTURA, S.NOMBRESUCURSAL,(select COUNT(D.ID_DETALLE) FROM DETALLE_FACTURA D WHERE D.ID_FACTURA=F.ID_FACTURA)  from FACTURAS F LEFT JOIN SUCURSALES S ON F.ID_SUCURSAL=S.ID_SUCURSAL WHERE F.ID_CLIENTE= ? ORDER BY F.ID_FACTURA ASC ",
								conexion, parametros);
						rs2 = operacionesBD.ejecutarConsulta(consulta);
						
						paragraph = new Paragraph();
						paragraph.add(new Phrase(Chunk.NEWLINE));
						paragraph.add(new Phrase("Facturas por cliente"));
						paragraph.add(new Phrase(Chunk.NEWLINE));
						paragraph.add(new Phrase(Chunk.NEWLINE));
						document.add(paragraph);

						cell = new PdfPCell(new Paragraph("Operación", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Sucursal", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Fecha factura", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Días", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Número factura", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Valor factura", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Valor pagado", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Valor deuda", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Fecha fin pago", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						cell = new PdfPCell(new Paragraph("Cantidad abonos", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableFacturas.addCell(cell);
						BigDecimal sumaTotal = BigDecimal.ZERO;
						BigDecimal sumaPagado = BigDecimal.ZERO;
						BigDecimal sumaDeuda = BigDecimal.ZERO;
						while (rs2.next()) {
							cantidadFacturas++;
							
							if(rs2.getBigDecimal(6).compareTo(BigDecimal.ZERO) > 0){
								cell = new PdfPCell(new Paragraph("Factura", fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(10), fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(1), fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getInt(2) + "", fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(3), fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs2.getBigDecimal(5)) + "", fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs2.getBigDecimal(7)) + "", fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs2.getBigDecimal(6)) + "", fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(8), fontDeudas));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getInt(11)+"", fontDeudas));
								tableFacturas.addCell(cell);
							}
							else{
								cell = new PdfPCell(new Paragraph("Factura", fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(10), fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(1), fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getInt(2) + "", fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(3), fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs2.getBigDecimal(5)) + "", fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs2.getBigDecimal(7)) + "", fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs2.getBigDecimal(6)) + "", fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getString(8), fontNormal));
								tableFacturas.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs2.getInt(11)+"", fontNormal));
								tableFacturas.addCell(cell);
							}
							sumaTotal = sumaTotal.add(rs2.getBigDecimal(5));
							sumaPagado = sumaPagado.add(rs2.getBigDecimal(7));
							sumaDeuda = sumaDeuda.add(rs2.getBigDecimal(6));
							
							
						}
						if (rs2 != null) {
							try {
								rs2.close();
								operacionesBD.cerrarStatement();
							} catch (SQLException e) {
								 
								e.printStackTrace();
							}
						}

						PdfPCell celdaSum = new PdfPCell(new Paragraph("Valor total facutras cliente: " + formatoImporte.format(sumaTotal),fontNormal));
						celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
						celdaSum.setColspan(10);
						tableFacturas.addCell(celdaSum);

						celdaSum = new PdfPCell(new Paragraph("Valor total pagado cliente: " + formatoImporte.format(sumaPagado),fontNormal));
						celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
						celdaSum.setColspan(10);
						tableFacturas.addCell(celdaSum);

						celdaSum = new PdfPCell(new Paragraph("Valor total adeudado cliente: " + formatoImporte.format(sumaDeuda),fontNormal));
						celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
						celdaSum.setColspan(10);
						tableFacturas.addCell(celdaSum);
						document.add(tableFacturas);
						
						
						paragraph = new Paragraph();
						paragraph.add(new Phrase(Chunk.NEWLINE));
						paragraph.add(new Phrase("Abonos facturas cliente"));
						paragraph.add(new Phrase(Chunk.NEWLINE));
						paragraph.add(new Phrase(Chunk.NEWLINE));
						document.add(paragraph);

						cell = new PdfPCell(new Paragraph("Operación", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableAbonos.addCell(cell);
						cell = new PdfPCell(new Paragraph("Número factura", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableAbonos.addCell(cell);
						cell = new PdfPCell(new Paragraph("Fecha abono", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableAbonos.addCell(cell);
						cell = new PdfPCell(new Paragraph("Número recibo", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableAbonos.addCell(cell);
						cell = new PdfPCell(new Paragraph("Valor abonado", f3));
						cell.setBackgroundColor(new BaseColor(211, 216, 205));
						tableAbonos.addCell(cell);
						
						consulta = new OperacionesBDInDTO(
								"SELECT FA.NUMERO_FACTURA,F.FECHA_CREACION,F.NUMERO_RECIBO ,F.VALOR_PAGADO FROM DETALLE_FACTURA F INNER JOIN FACTURAS FA ON FA.ID_FACTURA=F.ID_FACTURA WHERE FA.ID_CLIENTE=? ORDER BY  FA.NUMERO_FACTURA",
								conexion, parametros);
						rs5 = operacionesBD.ejecutarConsulta(consulta);

						while (rs5.next()) {
								cell = new PdfPCell(new Paragraph("Abono", fontNormal));
								tableAbonos.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs5.getString(1), fontNormal));
								tableAbonos.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs5.getString(2)+ "", fontNormal));
								tableAbonos.addCell(cell);
								cell = new PdfPCell(new Paragraph(rs5.getString(3)+ "", fontNormal));
								tableAbonos.addCell(cell);
								cell = new PdfPCell(new Paragraph(formatoImporte.format(rs5.getBigDecimal(4))+"", fontNormal));
								tableAbonos.addCell(cell);
						}
						if (rs5!= null) {
							try {
								rs5.close();
								operacionesBD.cerrarStatement();
							} catch (SQLException e) {
								 
								e.printStackTrace();
							}
						}
						
						document.add(tableAbonos);
					}

					
					
					paragraph = new Paragraph();
					
					paragraph.add(new Phrase("Cantidad total de facturas: "+cantidadFacturas));
					paragraph.add(new Phrase(Chunk.NEWLINE));
					paragraph.add(new Phrase("Final del documento. Reporte total facturas por cliente Urbanatt"));
					document.add(paragraph);

					// Cerrar el documento
					document.close();
				} catch (FileNotFoundException e) {
					 
					e.printStackTrace();
				} catch (DocumentException e) {
					 
					e.printStackTrace();
				} catch (MalformedURLException e) {
					 
					e.printStackTrace();
				} catch (IOException e) {
					 
					e.printStackTrace();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				} finally {
					operacionesBD.cerrarStatement();
				}
		
	}

	private void generarReporteCuentasCobrar(ReporteDTO reporteDTO) {
		int contadorTotalFacturas=0;
		NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(Locale.US);
		// Creacion del documento con los margenes
		Document document = new Document(PageSize.A4);

		// El archivo pdf que vamos a generar
		FileOutputStream fileOutputStream;
		ResultSet rs2 = null;
		ResultSet rs5 = null;
		ResultSet rs = null;
		try {

			Connection conexion = null;
			try {
				conexion = ConnectionUtils.getInstance().getConnectionBack();
			} catch (Exception e1) {
				 
				e1.printStackTrace();
			}

			String ruta = null;
			ResultSet rs3 = null;
			try {
				OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
				consultasInDTO = new OperacionesBDInDTO("SELECT VALOR FROM PARAMETROS WHERE ID_PARAMETRO = 1", conexion,
						new ArrayList<>());
				rs3 = operacionesBD.ejecutarConsulta(consultasInDTO);
				if (rs3.next()) {
					ruta = rs3.getString(1);
				}
			} catch (Exception e) {
				ruta = "";
				System.out.println(e);
			}
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
			Date fechaActual = new Date();
			String fe = operacionesBD.fechaStringhoraPorDateReporte(fechaActual);
			String nombreReporte = ruta + "\\reporteCuentasCobrar" + fe + ".pdf";
			fileOutputStream = new FileOutputStream(nombreReporte);
			// Obtener la instancia del PdfWriter
			PdfWriter.getInstance(document, fileOutputStream);
			// Abrir el documento
			document.open();

			Image image = null;
			// Obtenemos el logo de datojava
			image = Image.getInstance("logoIni.jpg");
			image.scaleAbsolute(80f, 60f);
			PdfPTable table = new PdfPTable(1);
			PdfPCell cell = new PdfPCell(image);
			cell.setColspan(5);
			cell.setBorderColor(BaseColor.WHITE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			document.add(table);
			
			// Creacion del parrafo
			Paragraph paragraph = new Paragraph();
			String fechaHora=operacionesBD.fechaStringhoraPorDateReporte(new Date());
			// Agregar un titulo con su respectiva fuente
			paragraph.add(new Phrase("URBANATT FUERZA Y VITALIDAD"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Dirección: Calle 13 Cr 11 # 10-42"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Telefonos: 3136425448-3122249865"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Correo: urbanattfuerzayvitalidad@gmail.com"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Fecha-hora: "+fechaHora));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Cuentas por cobrar"));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Vendedor: " + reporteDTO.getVendedor() != null && !reporteDTO.getVendedor().isEmpty() ? reporteDTO.getVendedor() : "TODOS"));

			// Agregar saltos de linea
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			document.add(paragraph);
			OperacionesBDInDTO consulta = new OperacionesBDInDTO();
			consulta.setConexion(conexion);
			List<Object> parametros = new ArrayList<>();
			Boolean hayVendedor=Boolean.FALSE;
			if(reporteDTO.getVendedor() != null && !reporteDTO.getVendedor().isEmpty()) {
				hayVendedor=Boolean.TRUE;
				parametros.add(reporteDTO.getVendedor());
			consulta.setConsulta(
					"select DISTINCT TIPOID,NUMID, NOMBRECOMPLETO, FIJO,CELULAR,CI.NOMBRE,DIRECCION, C.ID_CLIENTE from CLIENTES C INNER JOIN CIUDADES CI ON C.CIUDAD=CI.ID WHERE C.NUMID IN (SELECT F.ID_CLIENTE FROM FACTURAS F WHERE f.valor_deuda > 0 AND F.TIPO ='CREDITO' AND F.VENDEDOR= ?) ORDER BY C.NOMBRECOMPLETO");
			consulta.setParametros(parametros);
			}
			else {
				hayVendedor=Boolean.FALSE;
				consulta.setConsulta(
						"select DISTINCT TIPOID,NUMID, NOMBRECOMPLETO, FIJO,CELULAR,CI.NOMBRE,DIRECCION, C.ID_CLIENTE from CLIENTES C INNER JOIN CIUDADES CI ON C.CIUDAD=CI.ID WHERE C.NUMID IN (SELECT F.ID_CLIENTE FROM FACTURAS F WHERE f.valor_deuda > 0 AND F.TIPO ='CREDITO') ORDER BY C.NOMBRECOMPLETO");
				
			}
			
			rs = operacionesBD.ejecutarConsulta(consulta);
			String tipo = "";
			// Creacion de una tabla

			com.itextpdf.text.Font f4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, new BaseColor(119, 194, 27));

			com.itextpdf.text.Font f3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, new BaseColor(119, 194, 27));
			float[] anchocolumnas = new float[6];
			for (int j = 0; j < 6; j++) {
				anchocolumnas[j] = .50f;
			}

			float[] anchocolumnas2 = new float[8];
			for (int j = 0; j < 8; j++) {
				anchocolumnas2[j] = .50f;
			}
			float[] anchocolumnas3 = new float[5];
			for (int j = 0; j < 5; j++) {
				anchocolumnas3[j] = .50f;
			}
			
			String query="";
			
			BigDecimal sumaTotalGeneral = BigDecimal.ZERO;
			BigDecimal sumaPagadoGeneral = BigDecimal.ZERO;
			BigDecimal sumaDeudaGeneral = BigDecimal.ZERO;
				
			while (rs.next()) {
				paragraph = new Paragraph();
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Cliente"));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);
				parametros=new ArrayList<Object>();
				table = new PdfPTable(anchocolumnas);
				cell = new PdfPCell(new Paragraph("Tipo documento", f4));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Documento", f4));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Nombres", f4));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Telefonos", f4));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Ciudad", f4));
				cell.setBackgroundColor(new BaseColor(211, 216, 205));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("Dirección", f4));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				table.addCell(cell);

				PdfPTable tableFacturas = new PdfPTable(anchocolumnas2);
				PdfPTable tableAbonos = new PdfPTable(anchocolumnas3);
				if (rs.getLong(1) == 2) {
					tipo = "Cédula ciudadania";
				} else if (rs.getLong(1) == 4) {
					tipo = "NIT";
				}

				table.addCell(tipo);
				table.addCell(rs.getString(2) + "");
				table.addCell(rs.getString(3));
				table.addCell(rs.getLong(4) + "-" + rs.getLong(5));
				table.addCell(rs.getString(6));
				table.addCell(rs.getString(7));

				document.add(table);
				parametros.add(rs.getString(2));
				
				if(hayVendedor) {
					parametros.add(reporteDTO.getVendedor());
					query="select fecha_factura,sysdate-TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'), NUMERO_FACTURA,DESCRIPCION, VALOR_FACTURA,VALOR_DEUDA,VALOR_PAGADO, NOMBRESUCURSAL from FACTURAS F LEFT JOIN SUCURSALES S ON F.ID_SUCURSAL=S.ID_SUCURSAL WHERE F.ID_CLIENTE= ?  AND F.VALOR_DEUDA > 0 AND F.TIPO ='CREDITO' AND F.VENDEDOR= ? ORDER BY F.ID_FACTURA ASC ";
				}
				else {
					query="select fecha_factura,sysdate-TO_DATE(FECHA_FACTURA, 'dd/MM/yyyy'), NUMERO_FACTURA,DESCRIPCION, VALOR_FACTURA,VALOR_DEUDA,VALOR_PAGADO, NOMBRESUCURSAL from FACTURAS F LEFT JOIN SUCURSALES S ON F.ID_SUCURSAL=S.ID_SUCURSAL WHERE F.ID_CLIENTE= ?  AND F.VALOR_DEUDA > 0 AND F.TIPO ='CREDITO' ORDER BY F.ID_FACTURA ASC ";
				}
				consulta = new OperacionesBDInDTO(
						query,
						conexion, parametros);
				rs2 = operacionesBD.ejecutarConsulta(consulta);

				
				paragraph = new Paragraph();
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Facturas por cliente"));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);

				cell = new PdfPCell(new Paragraph("Operación", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Sucursal", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Fecha factura", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Días", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Número factura", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Valor factura", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Valor pagado", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				cell = new PdfPCell(new Paragraph("Valor deuda", f3));
				 cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableFacturas.addCell(cell);
				BigDecimal sumaTotal = BigDecimal.ZERO;
				BigDecimal sumaPagado = BigDecimal.ZERO;
				BigDecimal sumaDeuda = BigDecimal.ZERO;
				while (rs2.next()) {
					contadorTotalFacturas++;
					tableFacturas.addCell("Factura");
					tableFacturas.addCell(rs2.getString(8));
					tableFacturas.addCell(rs2.getString(1));
					tableFacturas.addCell(rs2.getInt(2) + "");
					tableFacturas.addCell(rs2.getString(3));
					tableFacturas.addCell(formatoImporte.format(rs2.getBigDecimal(5)) + "");
					tableFacturas.addCell(formatoImporte.format(rs2.getBigDecimal(7)) + "");
					tableFacturas.addCell(formatoImporte.format(rs2.getBigDecimal(6)) + "");
					sumaTotal = sumaTotal.add(rs2.getBigDecimal(5));
					sumaPagado = sumaPagado.add(rs2.getBigDecimal(7));
					sumaDeuda = sumaDeuda.add(rs2.getBigDecimal(6));
				}
				if (rs2 != null) {
					try {
						rs2.close();
					} catch (SQLException e) {
						 
						e.printStackTrace();
					}
				}

				PdfPCell celdaSum = new PdfPCell(new Paragraph("Valor total facutras cliente: " + formatoImporte.format(sumaTotal)));
				celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
				celdaSum.setColspan(8);
				tableFacturas.addCell(celdaSum);

				celdaSum = new PdfPCell(new Paragraph("Valor total pagado cliente: " + formatoImporte.format(sumaPagado)));
				celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
				celdaSum.setColspan(8);
				tableFacturas.addCell(celdaSum);

				celdaSum = new PdfPCell(new Paragraph("Valor total adeudado cliente: " + formatoImporte.format(sumaDeuda)));
				celdaSum.setBackgroundColor(new BaseColor(211, 216, 205));
				celdaSum.setColspan(8);
				tableFacturas.addCell(celdaSum);
				document.add(tableFacturas);
				
				//sumo general
				sumaTotalGeneral=sumaTotalGeneral.add(sumaTotal);
				sumaPagadoGeneral=sumaPagadoGeneral.add(sumaPagado);
				sumaDeudaGeneral=sumaDeudaGeneral.add(sumaDeuda);
				
				paragraph = new Paragraph();
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase("Abonos facturas cliente"));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				paragraph.add(new Phrase(Chunk.NEWLINE));
				document.add(paragraph);

				cell = new PdfPCell(new Paragraph("Operación", f3));
				cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableAbonos.addCell(cell);
				cell = new PdfPCell(new Paragraph("Número factura", f3));
				cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableAbonos.addCell(cell);
				cell = new PdfPCell(new Paragraph("Fecha abono", f3));
				cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableAbonos.addCell(cell);
				cell = new PdfPCell(new Paragraph("Número recibo", f3));
				cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableAbonos.addCell(cell);
				cell = new PdfPCell(new Paragraph("Valor abonado", f3));
				cell.setBackgroundColor(new BaseColor(211, 216, 205));
				tableAbonos.addCell(cell);
				
				if(hayVendedor) {
				query="SELECT FA.NUMERO_FACTURA,F.FECHA_CREACION,F.NUMERO_RECIBO ,F.VALOR_PAGADO FROM DETALLE_FACTURA F INNER JOIN FACTURAS FA ON FA.ID_FACTURA=F.ID_FACTURA WHERE FA.ID_CLIENTE=? AND FA.VENDEDOR= ? AND FA.VALOR_DEUDA >0 ORDER BY  FA.NUMERO_FACTURA";
				}
				else {
					query="SELECT FA.NUMERO_FACTURA,F.FECHA_CREACION,F.NUMERO_RECIBO ,F.VALOR_PAGADO FROM DETALLE_FACTURA F INNER JOIN FACTURAS FA ON FA.ID_FACTURA=F.ID_FACTURA WHERE FA.ID_CLIENTE=? AND FA.VALOR_DEUDA >0 ORDER BY  FA.NUMERO_FACTURA";
				}
				consulta = new OperacionesBDInDTO(
						query,
						conexion, parametros);
				rs5 = operacionesBD.ejecutarConsulta(consulta);

				while (rs5.next()) {
						tableAbonos.addCell("Abono");
						tableAbonos.addCell(rs5.getString(1));
						tableAbonos.addCell(rs5.getString(2));
						tableAbonos.addCell(rs5.getString(3));
						tableAbonos.addCell(formatoImporte.format(rs5.getBigDecimal(4)));
				}
				document.add(tableAbonos);
				if (rs5!= null) {
					try {
						rs5.close();
					} catch (SQLException e) {
						 
						e.printStackTrace();
					}
				}
			
			}
			

			paragraph = new Paragraph();
			paragraph.add(new Phrase("Total de facturas: "+ contadorTotalFacturas));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Valor total de las facturas: "+ formatoImporte.format(sumaTotalGeneral)));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Valor pagado de las facturas: "+ formatoImporte.format(sumaPagadoGeneral)));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Valor adeudado de las facturas: "+ formatoImporte.format(sumaDeudaGeneral)));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase(Chunk.NEWLINE));
			paragraph.add(new Phrase("Final del documento. Reporte cuentas por cobrar Urbanatt"));
			document.add(paragraph);

			// Cerrar el documento
			document.close();
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		} catch (MalformedURLException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		} catch (SQLException e) {
			 
			e.printStackTrace();
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

	}

	private void generarReporteFacturas(ReporteDTO reporteDTO) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Hoja excel");

		String[] headers = new String[] { "Número factura", "Nombre cliente", "Valor factura", "Valor pagado",
				"Valor deuda", "Fecha factura", "Días por factura", "Detalle productos", "Ciudad", "Sucursal" };
		Connection conexion = null;
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
		} catch (Exception e1) {
			 
			e1.printStackTrace();
		}

		ResultSet rs = null;
		OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
		try {
			StringBuilder consulta = new StringBuilder();
			List<Object> parametros = new ArrayList<>();
			consulta.append(
					"select DISTINCT F.NUMERO_FACTURA, C.NOMBRECOMPLETO, F.VALOR_FACTURA, F.VALOR_PAGADO, F.VALOR_DEUDA, F.FECHA_FACTURA,sysdate-TO_DATE(F.FECHA_FACTURA, 'dd/MM/yy')  AS DIAS, F.ID_FACTURA,CI.NOMBRE,S.NOMBRESUCURSAL from FACTURAS F "
							+ " INNER JOIN CLIENTES C ON F.ID_CLIENTE=C.NUMID  INNER JOIN CIUDADES CI ON C.CIUDAD=CI.ID LEFT JOIN SUCURSALES S ON F.ID_SUCURSAL=F.ID_SUCURSAL"
							+ " INNER JOIN FACTURA_PRODUCTO FP ON FP.ID_FACTURA=F.ID_FACTURA INNER JOIN PRODUCTOS P ON P.ID_PRODUCTO=FP.ID_PRODUCTO WHERE F.ID_FACTURA IS NOT NULL ");
			if (reporteDTO.getDiasFactura() != null && reporteDTO.getDiasFactura() > 0) {
				consulta.append(" AND sysdate-TO_DATE(F.FECHA_FACTURA, 'dd/MM/yy') >= ? ");
				parametros.add(reporteDTO.getDiasFactura());
			}
			if (reporteDTO.getIdCliente() != null) {
				consulta.append(" AND F.ID_CLIENTE = ? ");
				parametros.add(reporteDTO.getIdCliente());
			}
			if (reporteDTO.getIdProducto() != null) {
				consulta.append(" AND FP.ID_PRODUCTO = ? ");
				parametros.add(reporteDTO.getIdCliente());
			}

			consulta.append(" ORDER BY C.NOMBRECOMPLETO, TO_DATE(F.FECHA_FACTURA, 'dd/MM/yy') DESC ");
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setParametros(parametros);
			consultasInDTO.setConsulta(consulta.toString());
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);

			int contador = 0;
			List<Object[]> lista = new ArrayList<Object[]>();
			Object[] a = null;
			while (rs.next()) {
				contador++;
				a = new Object[10];
				a[0] = rs.getString(1);
				a[1] = rs.getString(2);
				a[2] = rs.getBigDecimal(3);
				a[3] = rs.getBigDecimal(4);
				a[4] = rs.getBigDecimal(5);
				a[5] = rs.getString(6);
				a[6] = rs.getInt(7);
				a[7] = consultarDetalles(rs.getLong(8), conexion);
				a[8] = rs.getString(9);
				a[9] = rs.getString(10);
				lista.add(a);
			}

			Object[][] data = new Object[contador][];
			for (int i = 0; i < lista.size(); i++) {
				data[i] = new Object[10];
				data[i][0] = lista.get(i)[0];
				data[i][1] = lista.get(i)[1];
				data[i][2] = lista.get(i)[2];
				data[i][3] = lista.get(i)[3];
				data[i][4] = lista.get(i)[4];
				data[i][5] = lista.get(i)[5];
				data[i][6] = lista.get(i)[6];
				data[i][7] = lista.get(i)[7];
				data[i][8] = lista.get(i)[8];
				data[i][9] = lista.get(i)[9];
			}

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			HSSFRow headerRow = sheet.createRow(0);
			for (int j = 0; j < headers.length; ++j) {
				String header = headers[j];
				HSSFCell cell = headerRow.createCell(j);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(header);
			}

			for (int i = 0; i < data.length; ++i) {
				HSSFRow dataRow = sheet.createRow(i + 1);

				Object[] d = data[i];
				String numFac = (String) d[0];
				String nonmbre = (String) d[1];
				BigDecimal valFac = (BigDecimal) d[2];
				BigDecimal valPag = (BigDecimal) d[3];
				BigDecimal valDeu = (BigDecimal) d[4];
				String fecha = (String) d[5];
				Integer dias = (Integer) d[6];
				String resumen = (String) d[7];
				String ciudad = (String) d[8];
				String sucursal = (String) d[9];
				dataRow.createCell(0).setCellValue(numFac);
				dataRow.createCell(1).setCellValue(nonmbre);
				dataRow.createCell(2).setCellValue(valFac.doubleValue());
				dataRow.createCell(3).setCellValue(valPag.doubleValue());
				dataRow.createCell(4).setCellValue(valDeu.doubleValue());
				dataRow.createCell(5).setCellValue(fecha);
				dataRow.createCell(6).setCellValue(dias);
				dataRow.createCell(7).setCellValue(resumen);
				dataRow.createCell(8).setCellValue(ciudad);
				dataRow.createCell(9).setCellValue(sucursal);
			}

			// HSSFRow dataRow = sheet.createRow(1 + data.length);
			// HSSFCell total = dataRow.createCell(1);
			// total.setCellType(1);
			// total.setCellStyle(style);
			// total.setCellFormula(String.format("SUM(B2:B%d)", 1 +
			// data.length));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
		}
		FileOutputStream file;
		Date fechaActual = new Date();
		String fe = operacionesBD.fechaStringPorDateReporte(fechaActual);

		String ruta = null;
		rs = null;
		try {
			consultasInDTO = new OperacionesBDInDTO("SELECT VALOR FROM PARAMETROS WHERE ID_PARAMETRO = 1", conexion,
					new ArrayList<>());
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			if (rs.next()) {
				ruta = rs.getString(1);
			}
		} catch (Exception e) {
			ruta = "";
			System.out.println(e);
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}
		}
		operacionesBD.cerrarStatement();
		try {
			reporteDTO.setNombreReporte(
					reporteDTO.getNombreReporte() == null ? "Reporte" : reporteDTO.getNombreReporte());
			file = new FileOutputStream(ruta + "\\" + reporteDTO.getNombreReporte() + fe + ".xls");
			workbook.write(file);
			file.close();
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}

	}

	private void generarReporteProductos(ReporteDTO reporteDTO) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "Hoja excel");

		String[] headers = new String[] { "Producto", "Inventario", "Ventas", "Enero", "Febrero", "Marzo", "Abril",
				"Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
		Connection conexion = null;
		try {
			conexion = ConnectionUtils.getInstance().getConnectionBack();
		} catch (Exception e1) {
			 
			e1.printStackTrace();
		}

		ResultSet rs = null;
		OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
		try {
			StringBuilder consulta = new StringBuilder();
			List<Object> parametros = new ArrayList<>();
			consulta.append(
					"SELECT CONCAT(CONCAT(CONCAT(CONCAT(P.NOMBRE, ' '), P.PESO), ' '), P.UNIDAD) AS PRODUCTO, P.CANTIDAD AS INVENTARIO, "
							+ " (SELECT SUM(FP.CANTIDAD) FROM FACTURA_PRODUCTO FP WHERE FP.ID_PRODUCTO=P.ID_PRODUCTO GROUP BY FP.ID_PRODUCTO) AS  VENTAS , P.ID_PRODUCTO"
							+ " FROM PRODUCTOS P ");
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setParametros(parametros);
			consultasInDTO.setConsulta(consulta.toString());
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);

			int contador = 0;
			List<Object[]> lista = new ArrayList<Object[]>();
			Object[] a = null;
			Object[] meses = null;
			int pos = 3;
			while (rs.next()) {
				contador++;
				a = new Object[15];
				a[0] = rs.getString(1);
				a[1] = rs.getInt(2);
				a[2] = rs.getInt(3);
				pos = 3;
				meses = consultaMeses(conexion, rs.getLong(4));
				for (Object object : meses) {
					a[pos] = object;
					pos++;
				}
				lista.add(a);
			}

			Object[][] data = new Object[contador][];
			for (int i = 0; i < lista.size(); i++) {
				data[i] = new Object[15];
				data[i][0] = lista.get(i)[0];
				data[i][1] = lista.get(i)[1];
				data[i][2] = lista.get(i)[2];
				data[i][3] = lista.get(i)[3];
				data[i][4] = lista.get(i)[4];
				data[i][5] = lista.get(i)[5];
				data[i][6] = lista.get(i)[6];
				data[i][7] = lista.get(i)[7];
				data[i][8] = lista.get(i)[8];
				data[i][9] = lista.get(i)[9];
				data[i][10] = lista.get(i)[10];
				data[i][11] = lista.get(i)[11];
				data[i][12] = lista.get(i)[12];
				data[i][13] = lista.get(i)[13];
				data[i][14] = lista.get(i)[14];
			}

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			HSSFRow headerRow = sheet.createRow(0);
			for (int j = 0; j < headers.length; ++j) {
				String header = headers[j];
				HSSFCell cell = headerRow.createCell(j);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(header);
			}

			for (int i = 0; i < data.length; ++i) {
				HSSFRow dataRow = sheet.createRow(i + 1);

				Object[] d = data[i];
				String nombreProd = (String) d[0];
				Integer cantidad = (Integer) d[1];
				Integer ventas = (Integer) d[2];
				Integer e = (Integer) d[3];
				Integer f = (Integer) d[4];
				Integer m = (Integer) d[5];
				Integer ab = (Integer) d[6];
				Integer my = (Integer) d[7];
				Integer ju = (Integer) d[8];
				Integer jul = (Integer) d[9];
				Integer ag = (Integer) d[10];
				Integer sp = (Integer) d[11];
				Integer oc = (Integer) d[12];
				Integer no = (Integer) d[13];
				Integer di = (Integer) d[14];
				dataRow.createCell(0).setCellValue(nombreProd);
				dataRow.createCell(1).setCellValue(cantidad);
				dataRow.createCell(2).setCellValue(ventas);
				dataRow.createCell(3).setCellValue(e);
				dataRow.createCell(4).setCellValue(f);
				dataRow.createCell(5).setCellValue(m);
				dataRow.createCell(6).setCellValue(ab);
				dataRow.createCell(7).setCellValue(my);
				dataRow.createCell(8).setCellValue(ju);
				dataRow.createCell(9).setCellValue(jul);
				dataRow.createCell(10).setCellValue(ag);
				dataRow.createCell(11).setCellValue(sp);
				dataRow.createCell(12).setCellValue(oc);
				dataRow.createCell(13).setCellValue(no);
				dataRow.createCell(14).setCellValue(di);
			}

			// HSSFRow dataRow = sheet.createRow(1 + data.length);
			// HSSFCell total = dataRow.createCell(1);
			// total.setCellType(1);
			// total.setCellStyle(style);
			// total.setCellFormula(String.format("SUM(B2:B%d)", 1 +
			// data.length));
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
		}
		FileOutputStream file;
		Date fechaActual = new Date();
		String fe = operacionesBD.fechaStringPorDateReporte(fechaActual);

		String ruta = null;
		rs = null;
		try {
			consultasInDTO = new OperacionesBDInDTO("SELECT VALOR FROM PARAMETROS WHERE ID_PARAMETRO = 1", conexion,
					new ArrayList<>());
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			if (rs.next()) {
				ruta = rs.getString(1);
			}
		} catch (Exception e) {
			ruta = "";
			System.out.println(e);
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}
		}
		operacionesBD.cerrarStatement();
		try {
			reporteDTO.setNombreReporte(
					reporteDTO.getNombreReporte() == null ? "Reporte" : reporteDTO.getNombreReporte());
			file = new FileOutputStream(ruta + "\\" + reporteDTO.getNombreReporte() + fe + ".xls");
			workbook.write(file);
			file.close();
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}

	}

	private Object[] consultaMeses(Connection conexion, long idProducto) {
		ResultSet rs = null;
		Object[] meses = new Object[12];
		OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
		try {
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setConsulta("SELECT * FROM (SELECT SUM(FP.CANTIDAD) CANT, "
					+ " extract(month from TO_DATE(FP.FECHA_CREACION,'dd/MM/yyyy')) as mes FROM FACTURA_PRODUCTO FP WHERE ID_PRODUCTO=? "
					+ " GROUP BY extract(month from TO_DATE(FP.FECHA_CREACION,'dd/MM/yyyy'))) "
					+ " pivot (sum(CANT) for mes in (1,2,3,4,5,6,7,8,9,10,11,12))");
			List<Object> parametros = new ArrayList<>();
			parametros.add(idProducto);
			consultasInDTO.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);

			while (rs.next()) {
				meses[0] = rs.getInt(1);
				meses[1] = rs.getInt(2);
				meses[2] = rs.getInt(3);
				meses[3] = rs.getInt(4);
				meses[4] = rs.getInt(5);
				meses[5] = rs.getInt(6);
				meses[6] = rs.getInt(7);
				meses[7] = rs.getInt(8);
				meses[8] = rs.getInt(9);
				meses[9] = rs.getInt(10);
				meses[10] = rs.getInt(11);
				meses[11] = rs.getInt(12);
			}
		} catch (Exception e) {
			return meses;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
		}
		return meses;
	}

	private String consultarDetalles(long long1, Connection conexion) {
		ResultSet rs = null;
		OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
		StringBuilder sb = new StringBuilder();
		try {
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setConsulta(
					"select CONCAT(CONCAT(CONCAT(P.NOMBRE, ' '), FP.CANTIDAD), ' unidades') AS DETALLE from  FACTURA_PRODUCTO FP "
							+ "INNER JOIN PRODUCTOS P ON P.ID_PRODUCTO=FP.ID_PRODUCTO " + "WHERE FP.ID_FACTURA=?");
			List<Object> parametros = new ArrayList<>();
			parametros.add(long1);
			consultasInDTO.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);

			while (rs.next()) {
				sb.append(rs.getString(1)).append(", ");
			}
		} catch (Exception e) {
			return "";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					 
					e.printStackTrace();
				}
			}
		}
		return sb.toString().substring(0, sb.toString().length() - 2);
	}

	@Override
	public String revertirFactura(FacturaDTO facturaDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			Integer resultado = null;
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO();
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(facturaDTO.getIdFactura());
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
			// Lo primero es consultar los productos que se agregaron a la
			// factura para volverlos a sumar
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setConsulta("select CANTIDAD, ID_PRODUCTO from FACTURA_PRODUCTO WHERE ID_FACTURA= ?");
			consultasInDTO.setParametros(parametros);
			ResultSet rsIni = operacionesBD.ejecutarConsulta(consultasInDTO);
			while (rsIni.next()) {
				parametros = new ArrayList<Object>();
				parametros.add(rsIni.getInt(1));
				parametros.add(rsIni.getLong(2));
				ejecutarInDTO = new OperacionesBDInDTO(
						"UPDATE PRODUCTOS SET CANTIDAD = (CANTIDAD + ?) WHERE ID_PRODUCTO= ?", conexion, parametros);
				operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			}
			// luego se borran los detalles factura y abonos
			parametros = new ArrayList<Object>();
			parametros.add(facturaDTO.getIdFactura());
			ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM FACTURA_PRODUCTO WHERE ID_FACTURA = ?", conexion,
					parametros);
			operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM DETALLE_FACTURA WHERE ID_FACTURA = ?", conexion,
					parametros);
			operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM FACTURAS WHERE ID_FACTURA = ?", conexion, parametros);
			resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
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

	@Override
	public List<DetalleFacturaDTO> consultarAbonos(Long numeroFactura) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			List<DetalleFacturaDTO> result = null;
			ProductosDAO dao = ProductosDAO.getInstance();
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			result = dao.consultarAbonos(numeroFactura,conexion);
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
	public String modificarAbono(DetalleFacturaDTO detalleFacturaDTO) throws TechnicalException, BusinessException {

		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try {
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			
			//se consulta el valor de la facturqa, tener encuenta que aca ya esta el abono anterior el q se esta modificando
			// si el valor abonado es = al valor factura estado es = a total
			BigDecimal valorAbonoAnterior = consultarValorAbonoPorId(detalleFacturaDTO.getIdDetalle(), conexion);
			actualizarValorFacturaPorAbono(detalleFacturaDTO,valorAbonoAnterior,conexion);
			
			BigDecimal valorFactura = consultarValorFactura(detalleFacturaDTO.getIdFactura(), conexion);
			
			//ahora si se actualiza el abono y se procede al flujo normal
			String estado = EstadoPagosEnum.ABONO.getEstado();
			if (valorFactura.compareTo(detalleFacturaDTO.getValorPagado()) == 0) {
				estado = EstadoPagosEnum.PAGO_TOTAL.getEstado();
			}
			detalleFacturaDTO.setEstado(estado);
			List<Object[]> ups = new ArrayList<Object[]>();
			ups=llenarDatosDetalle(detalleFacturaDTO);
			insertsBDInDTO.setUpdates(ups);
			insertsBDInDTO.setFiltro(detalleFacturaDTO.getIdDetalle());
			insertsBDInDTO.setId("ID_DETALLE");
			insertsBDInDTO.setTabla(TablasConstans.DETALLE_FACTURA);
			insertsBDInDTO.setConexion(conexion);
			// se actualiza el abono
			resultado = operacionesBD.actualizarRegistro(insertsBDInDTO);
			// se actualizan los valores en la factura
			actualizarValoresFactura(detalleFacturaDTO, conexion);
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

	private List<Object[]> llenarDatosDetalle(DetalleFacturaDTO detalleFacturaDTO) {
		List<Object[]> ups = new ArrayList<Object[]>();
		Object[] o= new Object[2];
		o[0]="VALOR_PAGADO";
		o[1]=detalleFacturaDTO.getValorPagado();
		ups.add(o);
		
		o= new Object[2];
		o[0]="NUMERO_RECIBO";
		o[1]=detalleFacturaDTO.getNumeroRecibo();
		ups.add(o);
		
		o= new Object[2];
		o[0]="ESTADO";
		o[1]=detalleFacturaDTO.getEstado();
		ups.add(o);
		
		return ups;
	
	}

	private String actualizarValorFacturaPorAbono(DetalleFacturaDTO detalleFacturaDTO, BigDecimal valorAbonoAnterior, Connection conexion) {
		ResultSet rs = null;
		try {
			BigDecimal valorDeuda = BigDecimal.ZERO;
			BigDecimal valorPagado = BigDecimal.ZERO;
			BigDecimal valorFactura = BigDecimal.ZERO;
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(detalleFacturaDTO.getIdFactura());
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO(
					"SELECT VALOR_FACTURA, VALOR_DEUDA,VALOR_PAGADO FROM FACTURAS WHERE ID_FACTURA= ?", conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			String estado = EstadoFacturasEnum.EN_ABONO.getEstado();
			if (rs.next()) {
				valorFactura = rs.getBigDecimal(1);
				valorDeuda = rs.getBigDecimal(2);
				valorPagado = rs.getBigDecimal(3);
				valorDeuda = valorDeuda.add(valorAbonoAnterior);
				valorPagado = valorPagado.subtract(valorAbonoAnterior);
				if (valorDeuda.compareTo(BigDecimal.ZERO) <= 0) {
					// se cambia el estado de la factura
					estado = EstadoFacturasEnum.PAGADA.getEstado();
					valorDeuda = BigDecimal.ZERO;
				}
				if (valorPagado.compareTo(valorFactura) >= 0) {
					// se cambia el estado de la factura
					estado = EstadoFacturasEnum.PAGADA.getEstado();
					valorPagado = valorFactura;
				}
				parametros = new ArrayList<Object>();
				parametros.add(valorDeuda);
				parametros.add(valorPagado);
				parametros.add(estado);
				String fechaA = operacionesBD.fechaStringPorDate(new Date());
				String query="";
				if (estado == EstadoFacturasEnum.PAGADA.getEstado()) {
					parametros.add(fechaA);
					query="UPDATE FACTURAS SET VALOR_DEUDA = ?, VALOR_PAGADO= ? , ESTADO = ?, FECHA_PAGO_TOTAL= ? WHERE ID_FACTURA= ?";
				} else {
					query="UPDATE FACTURAS SET VALOR_DEUDA = ?, VALOR_PAGADO= ? , ESTADO = ?, FECHA_PAGO_TOTAL= null WHERE ID_FACTURA= ?";
				}
				parametros.add(detalleFacturaDTO.getIdFactura());
				OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO(
						query,
						conexion, parametros);
				// se hace el update de factura
				Integer resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
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
			}
		} catch (Exception e) {
			// no se actualizaron los valores
			System.out.println("Error");

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
		return MensajesConstans.REGISTRO_EXITOSO;
		
	}

	private BigDecimal consultarValorAbonoPorId(Long idDetalle, Connection conexion) {
		ResultSet rs = null;
		BigDecimal valorFactura = null;
		try {
			OperacionesBDInDTO consultasInDTO = new OperacionesBDInDTO();
			consultasInDTO.setConexion(conexion);
			consultasInDTO.setConsulta("select VALOR_PAGADO from DETALLE_FACTURA WHERE  ID_DETALLE= ?");
			List<Object> parametros = new ArrayList<Object>();
			parametros.add(idDetalle);
			consultasInDTO.setParametros(parametros);
			rs = operacionesBD.ejecutarConsulta(consultasInDTO);
			if (rs.next()) {
				valorFactura = rs.getBigDecimal(1);
			}
		} catch (Exception e) {
			return BigDecimal.ZERO;
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
		return valorFactura;
	}

	@Override
	public String eliminarAbono(DetalleFacturaDTO detalleFacturaDTO) throws TechnicalException, BusinessException {
		Connection conexion = null;
		try {
			Integer resultado;
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			
			//se consulta el valor de la facturqa, tener encuenta que aca ya esta el abono anterior el q se esta modificando
			// si el valor abonado es = al valor factura estado es = a total
			BigDecimal valorAbonoAnterior = consultarValorAbonoPorId(detalleFacturaDTO.getIdDetalle(), conexion);
			actualizarValorFacturaPorAbono(detalleFacturaDTO,valorAbonoAnterior,conexion);
			
			List<Object> parametros = new ArrayList<>();
			parametros.add(detalleFacturaDTO.getIdDetalle());
			OperacionesBDInDTO ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM DETALLE_FACTURA WHERE ID_DETALLE = ?", conexion,
					parametros );
			resultado = operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
		
			if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
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

		@Override
		public String crearPrecios(PreciosClienteDTO productoDTO) throws TechnicalException, BusinessException {


		InsertsBDInDTO insertsBDInDTO = new InsertsBDInDTO();
		Connection conexion = null;
		try { 
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			
			if(productoDTO.getIdClientePrecios() != null) {
				//se elimina el anteriorp ara q parezca edicion
				List<Object> parametros = new ArrayList<Object>();
				parametros.add(productoDTO.getIdCliente());
				OperacionesBDInDTO ejecutarInDTO = null;
				
				ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM PRECIOS_CLIENTE_DETALLES  D WHERE D.ID_CLIENTE_PRECIOS = (SELECT P.ID_CLIENTE_PRECIOS FROM PRECIOS_CLIENTE P WHERE P.ID_CLIENTE = ?)", conexion,
						parametros);
				operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM PRECIOS_CLIENTE WHERE ID_CLIENTE = ?", conexion,
						parametros);
				operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
			}
			String resultado = "";
			conexion = ConnectionUtils.getInstance().getConnectionBack();
			List<Object> parametros = new ArrayList<Object>();
			insertsBDInDTO.setConexion(conexion);
			insertsBDInDTO.setTabla(TablasConstans.PRECIOS_CLIENTE);
			insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_CLIENTE_PRECIOS);

			parametros.add(SecuenciasConstans.NEXT_PRECIOS);
			parametros.add(productoDTO.getIdCliente());
			parametros.add(productoDTO.getEstado());
			parametros.add(operacionesBD.fechaStringPorDate(new Date()));
			
			insertsBDInDTO.setParametros(parametros);
			resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
			Long idPrecio = 0L;
			idPrecio = consultarIdPrecioPorNumero(productoDTO.getIdCliente(), conexion);
			String fechaActual=operacionesBD.fechaStringPorDate(new Date());
			for (ProductoDTO p : productoDTO.getProductos()) {
				// se crea un detalle factura por cada producto agregado
				parametros = new ArrayList<Object>();
				insertsBDInDTO.setConexion(conexion);
				insertsBDInDTO.setTabla(TablasConstans.PRECIOS_CLIENTE_DETALLES);
				insertsBDInDTO.setCampos(CamposTablaConstans.CAMPOS_CLIENTE_PRECIOS_DETALLES);
				parametros.add(SecuenciasConstans.NEXT_PRECIOS_DETALLES);
				parametros.add(idPrecio);
				parametros.add(p.getIdProducto());
				parametros.add(p.getNombreProducto());
				parametros.add(p.getValor());
				parametros.add(fechaActual);
				insertsBDInDTO.setParametros(parametros);
				resultado = operacionesBD.insertarRegistro(insertsBDInDTO);
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
			operacionesBD.cerrarStatement();
		}
	
	}

		private String eliminarRegistrosPrecios(Long idClientePrecios) throws TechnicalException, BusinessException {
			// TODO Auto-generated method stub
			Connection conexion = null;
			try {
				Integer resultado = null;
				conexion = ConnectionUtils.getInstance().getConnectionBack();
				List<Object> parametros = new ArrayList<Object>();
				parametros.add(idClientePrecios);
				OperacionesBDInDTO ejecutarInDTO = null;
				
				ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM PRECIOS_CLIENTE_DETALLES  D WHERE D.ID_CLIENTE_PRECIOS = (SELECT P.ID_CLIENTE_PRECIOS FROM PRECIOS_CLIENTE P WHERE P.ID_CLIENTE = ?)", conexion,
						parametros);
				operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				ejecutarInDTO = new OperacionesBDInDTO("DELETE FROM PRECIOS_CLIENTE WHERE ID_CLIENTE = ?", conexion,
						parametros);
				resultado=operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				if (resultado.compareTo(EstadosOperaciones.EXITO.getId()) == 0) {
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

		@Override
		public List<ProductoDTO> consultarPrecios(String idCliente) throws TechnicalException, BusinessException {


			Connection conexion = null;
			try {
				List<ProductoDTO> result = null;
				ProductosDAO dao = ProductosDAO.getInstance();
				conexion = ConnectionUtils.getInstance().getConnectionBack();
				result = dao.consultarPrecios(idCliente, conexion);
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
		public List<PreciosClienteDTO> consultarPreciosTabla(String idCliente,String nombre)throws TechnicalException, BusinessException {


			Connection conexion = null;
			try {
				List<PreciosClienteDTO> result = null;
				ProductosDAO dao = ProductosDAO.getInstance();
				conexion = ConnectionUtils.getInstance().getConnectionBack();
				result = dao.consultarPreciosTabla(idCliente, nombre, conexion);
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
		public String eliminarPrecio(PreciosClienteDTO productoDTO) throws TechnicalException, BusinessException {
			return 	eliminarRegistrosPrecios(productoDTO.getIdClientePrecios());
		}

		@Override
		public String actualizarPrecios(ActualizarPreciosDTO actulizarDTO)
				throws TechnicalException, BusinessException {
			Connection conexion = null;
			try {
				Integer resultado = null;
				conexion = ConnectionUtils.getInstance().getConnectionBack();
				List<Object> parametros = new ArrayList<Object>();
				OperacionesBDInDTO ejecutarInDTO = null;
				
				
				parametros.add(actulizarDTO.getPorcentaje());
				parametros.add(actulizarDTO.getNumId());
				
				String consulta="";
				if(actulizarDTO.getPorcentaje().compareTo(BigDecimal.ZERO) < 0 ) {
					consulta="UPDATE PRECIOS_CLIENTE_DETALLES PD SET PD.VALOR=PD.VALOR-((PD.VALOR* ?)/100) WHERE pd.id_cliente_precios="
							+ "(select ID_CLIENTE_PRECIOS from PRECIOS_CLIENTE PC WHERE PC.ID_CLIENTE= ?)";
				}
				else {
					consulta="UPDATE PRECIOS_CLIENTE_DETALLES PD SET PD.VALOR=((PD.VALOR * ?)/100)+PD.VALOR WHERE pd.id_cliente_precios="
							+ "(select ID_CLIENTE_PRECIOS from PRECIOS_CLIENTE PC WHERE PC.ID_CLIENTE= ?)";
				}
				
				ejecutarInDTO = new OperacionesBDInDTO(consulta, conexion,
						parametros);
				resultado=operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				
				return "Exito";
				
			} catch (BusinessException e) {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getDescripcion();
				result.result = false;
				result.error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
				throw new BusinessException(result);
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
		public String actualizarPrecioIndividual(ActualizarPrecioIndividualDTO actulizarDTO)
				throws TechnicalException, BusinessException {
			Connection conexion = null;
			try {
				Integer resultado = null;
				conexion = ConnectionUtils.getInstance().getConnectionBack();
				List<Object> parametros = new ArrayList<Object>();
				OperacionesBDInDTO ejecutarInDTO = null;
				
				parametros.add(actulizarDTO.getValorActualizado());
				parametros.add(actulizarDTO.getIdProducto());
				parametros.add(actulizarDTO.getNumId());
				
				
				String consulta="UPDATE PRECIOS_CLIENTE_DETALLES PD SET PD.VALOR=? WHERE  pd.id_producto= ? and pd.id_cliente_precios= "
						+ "(select ID_CLIENTE_PRECIOS from PRECIOS_CLIENTE PC WHERE PC.ID_CLIENTE=?)";
				
				ejecutarInDTO = new OperacionesBDInDTO(consulta, conexion,
						parametros);
				resultado=operacionesBD.ejecutarOperacionBD(ejecutarInDTO);
				
				return "Exito";
				
			} catch (BusinessException e) {
				ResultSecurityDTO result = new ResultSecurityDTO();
				result.error = new Error();
				result.error.errorCode = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getCodigo();
				result.error.errorDescription = EnumWebServicesErrors.ERROR_CREAR_USUARIO.getDescripcion();
				result.result = false;
				result.error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
				throw new BusinessException(result);
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
