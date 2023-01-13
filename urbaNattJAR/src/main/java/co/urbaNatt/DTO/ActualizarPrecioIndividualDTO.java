package co.urbaNatt.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

public class ActualizarPrecioIndividualDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal valorActualizado;
	private Long idProducto;
	private String numId;
	public BigDecimal getValorActualizado() {
		return valorActualizado;
	}
	public void setValorActualizado(BigDecimal valorActualizado) {
		this.valorActualizado = valorActualizado;
	}
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public String getNumId() {
		return numId;
	}
	public void setNumId(String numId) {
		this.numId = numId;
	}
	
	
}
