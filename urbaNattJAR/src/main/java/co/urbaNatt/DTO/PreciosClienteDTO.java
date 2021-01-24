package co.urbaNatt.DTO;

import java.io.Serializable;
import java.util.List;

public class PreciosClienteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idCliente;
	private String nombreCliente;
	private Long idClientePrecios;
	private String fechaCreacion;
	private List<ProductoDTO> productos;
	private String estado;
	
	
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public List<ProductoDTO> getProductos() {
		return productos;
	}
	public void setProductos(List<ProductoDTO> productos) {
		this.productos = productos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getIdClientePrecios() {
		return idClientePrecios;
	}
	public void setIdClientePrecios(Long idClientePrecios) {
		this.idClientePrecios = idClientePrecios;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	
	

}
