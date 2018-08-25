/**
 * 
 */
package co.urbaNatt.enums;

/**
 * @author Harold
 *
 */
public enum TiposRolesEnum {
	
	ADMIN("ADMIN"), VENDEDOR("VENDEDOR"), SIN_ROL("SIN_ROL");
	
	private String estado;
	
	TiposRolesEnum(String estado){
		this.estado=estado;
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

}
