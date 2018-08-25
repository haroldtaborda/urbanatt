package co.urbaNatt.DTO;

import java.io.Serializable;

public class ObjetoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nombre;
	private Long idPadre;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the idPadre
	 */
	public Long getIdPadre() {
		return idPadre;
	}
	/**
	 * @param idPadre the idPadre to set
	 */
	public void setIdPadre(Long idPadre) {
		this.idPadre = idPadre;
	}
	
	
	

}
