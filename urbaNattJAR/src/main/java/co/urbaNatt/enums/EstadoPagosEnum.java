/**
 * 
 */
package co.urbaNatt.enums;

/**
 * @author Harold
 *
 */
public enum EstadoPagosEnum {
	ABONO("ABONO", 1), CANCELADO("CANCELADO", 2), PAGO_TOTAL("PAGO_TOTAL", 3);
	
	private String estado;
	private Integer id;
	EstadoPagosEnum(String estado, Integer id){
		this.estado=estado;
		this.id=id;
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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
