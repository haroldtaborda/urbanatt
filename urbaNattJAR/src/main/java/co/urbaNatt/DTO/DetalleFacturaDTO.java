package co.urbaNatt.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class DetalleFacturaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDetalle;
	private Long idFactura;
	private BigDecimal valorPagado;
	private String numeroRecibo;
	private String estado;
	private String numeroFactura;
	private String fechaAbono;
	/**
	 * @return the idDetalle
	 */
	public Long getIdDetalle() {
		return idDetalle;
	}
	/**
	 * @param idDetalle the idDetalle to set
	 */
	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
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
	 * @return the numeroRecibo
	 */
	public String getNumeroRecibo() {
		return numeroRecibo;
	}
	/**
	 * @param numeroRecibo the numeroRecibo to set
	 */
	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
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
	 * @return the fechaAbono
	 */
	public String getFechaAbono() {
		return fechaAbono;
	}
	/**
	 * @param fechaAbono the fechaAbono to set
	 */
	public void setFechaAbono(String fechaAbono) {
		this.fechaAbono = fechaAbono;
	}
	
	

}
