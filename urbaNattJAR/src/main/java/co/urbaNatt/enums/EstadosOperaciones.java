/**
 * 
 */
package co.urbaNatt.enums;

/**
 * @author Harold
 *
 */
public enum EstadosOperaciones {
	
	EXITO("EXITO", 1), ERROR("ERROR", 0), DATOS_INCOMPLETOS("DATOS_INCOMPLETOS", 2);
	
	private String estado;
	private Integer id;
	EstadosOperaciones(String estado, Integer id){
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
