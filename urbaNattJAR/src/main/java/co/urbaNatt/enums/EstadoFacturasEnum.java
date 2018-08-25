/**
 * 
 */
package co.urbaNatt.enums;

/**
 * @author Harold
 *
 */
public enum EstadoFacturasEnum {
	NUEVA("Nueva", 1), PENDIENTE("Pendiente", 2), PAGADA("Pagada", 3), CANCELADA("Cancelada", 4), CERRADA("Cerrada", 5), EN_ABONO("En abono", 6);
	
	private String estado;
	private Integer id;
	EstadoFacturasEnum(String estado, Integer id){
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
