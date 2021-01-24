/**
 * UserManagerDAO.java
 */
package co.urbaNatt.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.urbaNatt.DTO.DetalleFacturaDTO;
import co.urbaNatt.DTO.DetalleProductoDTO;
import co.urbaNatt.DTO.FacturaDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.DTO.PreciosClienteDTO;
import co.urbaNatt.DTO.ProductoDTO;
import co.urbaNatt.constans.ConsultasDinamicasConstans;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.BD.OperacionesBD;

/**
 * <b>Descripcion: </b> Dao asociado a la tabla UserManager.
 * 
 * @author HAROLD- <a href="mailto:haroldtaborda93@gmail.com">htaborda</a>
 * @date 15/06/2016
 */
public class ProductosDAO {

	/**
	 * Referencia unica de la clase
	 */
	private static final ProductosDAO instance = new ProductosDAO();
	private static final OperacionesBD operacionesBD = new OperacionesBD();

	/**
	 * Constructor de la clase
	 */
	public ProductosDAO() {
	}

	
	/**
	 * 
	 * <b>Descripcion: </b> Metodo encargado de implementar el patron solitario
	 * 
	 * @return Una unica instancia de la clase.
	 */
	public static ProductosDAO getInstance() {
		return instance;
	}

	
	
	/**
	 * 
	 * @param credentials
	 * @param connection
	 * @return
	 * @throws TechnicalException
	 */
	public List<ProductoDTO> consultaProductos(String nombre, String tipo, Connection connection) throws TechnicalException {
		 List<ProductoDTO> lista = new ArrayList<ProductoDTO>();
		 ProductoDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_PRODUCTO);
			if(nombre != null && !nombre.isEmpty() && !nombre.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_PRODUCTO_NOMBRE);
				parametros.add("%"+nombre.toUpperCase()+"%");
			}
			if(tipo != null && !tipo.isEmpty()  && !tipo.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_PRODUCTO_TIPO);
				parametros.add(tipo);
			}
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), connection,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto = new ProductoDTO();
				dto.setIdProducto(rs.getLong(1));
				dto.setDescripcion(rs.getString(5));
				dto.setNombreProducto(rs.getString(2));
				dto.setTipo(rs.getString(3));
				dto.setUnidadMedidad(rs.getString(4));
				dto.setCantidad(rs.getLong(6));
				dto.setPeso(rs.getString(7));
				//dto.setValor(rs.getBigDecimal(8));
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


	public List<FacturaDTO> consultarFacturas(String numeroFactura, String estado, String numeroId, Integer dias, Connection conexion) throws TechnicalException {
		 List<FacturaDTO> lista = new ArrayList<FacturaDTO>();
		 FacturaDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_FACTURA_BASE);
			if(numeroFactura != null && !numeroFactura.isEmpty() && !numeroFactura.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_FACTURA_NUMFAC);
				parametros.add(numeroFactura);
			}
			if(estado != null && !estado.isEmpty()  && !estado.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_FACTURA_ESTADO);
				parametros.add(estado);
			}
			if(numeroId != null && !numeroId.isEmpty()  && !numeroId.equals("TODOS")){
				sb.append(ConsultasDinamicasConstans.CONSULTAR_FACTURA_NUMID);
				parametros.add(numeroId);
			}
			
			
			sb.append(ConsultasDinamicasConstans.ORDER_BY_FACTURAS);
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				if(dias != null && dias > 0){
				if(dias <= rs.getInt(15)){
					dto = new FacturaDTO();
					dto.setIdFactura(rs.getLong(1));
					dto.setNumeroFactura(rs.getString(2));
					dto.setIdCliente(rs.getString(3));
					dto.setFechaCreacion(rs.getString(4));
					dto.setFechaFactura(operacionesBD.fechaDateforString(rs.getString(5)));
					dto.setValorDeuda(rs.getBigDecimal(6));
					dto.setValorPagado(rs.getBigDecimal(7));
					dto.setEstado(rs.getString(8));
					dto.setValorFactura(rs.getBigDecimal(9));
					dto.setDescripcion(rs.getString(10));
					dto.setNombreCliente(rs.getString(11));
					dto.setTipo(rs.getString(12));
					dto.setDetallesDTO(consultarDetallesProducto(dto.getIdFactura(),conexion));
					dto.setIdSucursal(rs.getLong(13));
					dto.setNombreSucursal(rs.getString(14));
					dto.setDescuento(rs.getString(15));
					dto.setVendedor(rs.getString(16));
					dto.setDias(rs.getInt(17));
					lista.add(dto);
			     	}
				}
				else{
				dto = new FacturaDTO();
				dto.setIdFactura(rs.getLong(1));
				dto.setNumeroFactura(rs.getString(2));
				dto.setIdCliente(rs.getString(3));
				dto.setFechaCreacion(rs.getString(4));
				dto.setFechaFactura(operacionesBD.fechaDateforString(rs.getString(5)));
				dto.setValorDeuda(rs.getBigDecimal(6));
				dto.setValorPagado(rs.getBigDecimal(7));
				dto.setEstado(rs.getString(8));
				dto.setValorFactura(rs.getBigDecimal(9));
				dto.setDescripcion(rs.getString(10));
				dto.setNombreCliente(rs.getString(11));
				dto.setTipo(rs.getString(12));
				dto.setDetallesDTO(consultarDetallesProducto(dto.getIdFactura(),conexion));
				dto.setIdSucursal(rs.getLong(13));
				dto.setNombreSucursal(rs.getString(14));
				dto.setDescuento(rs.getString(15));
				dto.setVendedor(rs.getString(16));
				dto.setDias(rs.getInt(17));
				lista.add(dto);
				}
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


	private List<DetalleProductoDTO> consultarDetallesProducto(Long idFactura, Connection conexion)  throws TechnicalException {
		DetalleProductoDTO p=null;
		ResultSet rs=null;
		List<DetalleProductoDTO> lista= new ArrayList<>();
		OperacionesBDInDTO consultasInDTO= new OperacionesBDInDTO();
		consultasInDTO.setConexion(conexion);
		consultasInDTO.setConsulta("SELECT ID_PRODUCTO,NOMBRE_PRODUCTO,CANTIDAD,TIPO,VALOR_UNITARIO FROM FACTURA_PRODUCTO WHERE ID_FACTURA= ?");
		List<Object> parametros= new ArrayList<>();
		parametros.add(idFactura);
		consultasInDTO.setParametros(parametros);
		try{
		rs=operacionesBD.ejecutarConsulta(consultasInDTO);
		while(rs.next()){
			p=new DetalleProductoDTO();
			p.setIdProducto(rs.getLong(1));
			p.setNombreProducto(rs.getString(2));
			p.setCantidad(rs.getLong(3));
			p.setTipo(rs.getString(4));
			p.setValor(rs.getBigDecimal(5));
			lista.add(p);
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


	private List<ProductoDTO> consultarProductos(Long idFactura, Connection conexion) throws TechnicalException {
		ProductoDTO p=null;
		ResultSet rs=null;
		List<ProductoDTO> lista= new ArrayList<>();
		OperacionesBDInDTO consultasInDTO= new OperacionesBDInDTO();
		consultasInDTO.setConexion(conexion);
		consultasInDTO.setConsulta("SELECT ID_PRODUCTO,NOMBRE_PRODUCTO,CANTIDAD FROM FACTURA_PRODUCTO WHERE ID_FACTURA= ?");
		List<Object> parametros= new ArrayList<>();
		parametros.add(idFactura);
		consultasInDTO.setParametros(parametros);
		try{
		rs=operacionesBD.ejecutarConsulta(consultasInDTO);
		while(rs.next()){
			p=new ProductoDTO();
			p.setIdProducto(rs.getLong(1));
			p.setNombreProducto(rs.getString(2));
			p.setCantidad(rs.getLong(3));
			lista.add(p);
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


	public List<DetalleFacturaDTO> consultarAbonos(Long idFactura, Connection conexion)  throws TechnicalException {
		 List<DetalleFacturaDTO> lista = new ArrayList<DetalleFacturaDTO>();
		 DetalleFacturaDTO dto = null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_ABONOS_FROM);
				parametros.add(idFactura);
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto = new DetalleFacturaDTO();
				dto.setNumeroFactura(rs.getString(1));
				dto.setNumeroRecibo(rs.getString(2));
				dto.setValorPagado(rs.getBigDecimal(3));
				dto.setIdDetalle(rs.getLong(4));
				dto.setIdFactura(idFactura);
				dto.setFechaAbono(rs.getString(6));
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


	public List<ProductoDTO> consultarPrecios(String idCliente, Connection conexion)  throws TechnicalException {
		 List<ProductoDTO> lista = new ArrayList<ProductoDTO>();
		 ProductoDTO pDTO=null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE_PRECIOS);
				parametros.add(idCliente);
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				pDTO= new ProductoDTO();
				pDTO.setIdProducto(rs.getLong(3));
				pDTO.setNombreProducto(rs.getString(4));
				pDTO.setValor(rs.getBigDecimal(5));
				lista.add(pDTO);
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


	public List<PreciosClienteDTO> consultarPreciosTabla(String idCliente, Connection conexion) throws TechnicalException {
		 List<PreciosClienteDTO> lista = new ArrayList<PreciosClienteDTO>();
		 PreciosClienteDTO dto=null;
		ResultSet rs = null;
		try {
			List<Object> parametros = new ArrayList<Object>();
			StringBuilder sb = new StringBuilder();
				
				if(idCliente!=null && !idCliente.isEmpty() && !idCliente.equalsIgnoreCase("TODOS")) {
					parametros.add(idCliente);
					sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE_PADRE);
				}
				else {
					sb.append(ConsultasDinamicasConstans.CONSULTAR_CLIENTE_PADRE_S);
				}
				
			OperacionesBDInDTO inDTO = new OperacionesBDInDTO(sb.toString(), conexion,
					parametros);
			rs = operacionesBD.ejecutarConsulta(inDTO);
			while (rs.next()) {
				dto= new PreciosClienteDTO();
				dto.setIdClientePrecios(rs.getLong(1));
				dto.setNombreCliente(rs.getString(5));
				dto.setIdCliente(idCliente);
				dto.setEstado(rs.getString(3));
				dto.setFechaCreacion(rs.getString(4));
				dto.setProductos(consultarPrecios(idCliente, conexion));
				
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
	

