package co.urbaNatt.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FacturaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idFactura;
	private String tipo;
	private String numeroFactura;
	private String idCliente;
	private String fechaCreacion;
	private Date fechaFactura;
	private BigDecimal valorDeuda;
	private BigDecimal valorPagado;
	private String estado;
	private String nombreCliente;
	private BigDecimal valorFactura;
	private String descripcion;
	private Integer dias;
	private List<ProductoDTO> productos;
	private List<ProductoDTO> productosRegalo;
	private List<DetalleProductoDTO> detallesDTO;
	private Long idSucursal;
	private String nombreSucursal;
	private String descuento;
	private String vendedor;
	private String direccion;
	/**
	 * @return the nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}
	/**
	 * @param nombreCliente the nombreCliente to set
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	/**
	 * @return the valorFactura
	 */
	public BigDecimal getValorFactura() {
		return valorFactura;
	}
	/**
	 * @param valorFactura the valorFactura to set
	 */
	public void setValorFactura(BigDecimal valorFactura) {
		this.valorFactura = valorFactura;
	}
	/**
	 * @return the numeroFactura
	 */
	public String getNumeroFactura() {
		return numeroFactura;
	}
	/**
	 * @param numeroFactura the numeroFactura to set
	 */
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	/**
	 * @return the idCliente
	 */
	public String getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the fechaCreacion
	 */
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the fechaFactura
	 */
	public Date getFechaFactura() {
		return fechaFactura;
	}
	/**
	 * @param fechaFactura the fechaFactura to set
	 */
	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	/**
	 * @return the valorDeuda
	 */
	public BigDecimal getValorDeuda() {
		return valorDeuda;
	}
	/**
	 * @param valorDeuda the valorDeuda to set
	 */
	public void setValorDeuda(BigDecimal valorDeuda) {
		this.valorDeuda = valorDeuda;
	}
	/**
	 * @return the valorPagado
	 */
	public BigDecimal getValorPagado() {
		return valorPagado;
	}
	/**
	 * @param valorPagado the valorPagado to set
	 */
	public void setValorPagado(BigDecimal valorPagado) {
		this.valorPagado = valorPagado;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the idFactura
	 */
	public Long getIdFactura() {
		return idFactura;
	}
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}
	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	/**
	 * @return the productos
	 */
	public List<ProductoDTO> getProductos() {
		return productos;
	}
	/**
	 * @param productos the productos to set
	 */
	public void setProductos(List<ProductoDTO> productos) {
		this.productos = productos;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the idSucursal
	 */
	public Long getIdSucursal() {
		return idSucursal;
	}
	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}
	/**
	 * @return the nombreSucursal
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}
	/**
	 * @param nombreSucursal the nombreSucursal to set
	 */
	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}
	/**
	 * @return the productosRegalo
	 */
	public List<ProductoDTO> getProductosRegalo() {
		return productosRegalo;
	}
	/**
	 * @param productosRegalo the productosRegalo to set
	 */
	public void setProductosRegalo(List<ProductoDTO> productosRegalo) {
		this.productosRegalo = productosRegalo;
	}
	/**
	 * @return the detallesDTO
	 */
	public List<DetalleProductoDTO> getDetallesDTO() {
		return detallesDTO;
	}
	/**
	 * @param detallesDTO the detallesDTO to set
	 */
	public void setDetallesDTO(List<DetalleProductoDTO> detallesDTO) {
		this.detallesDTO = detallesDTO;
	}
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
