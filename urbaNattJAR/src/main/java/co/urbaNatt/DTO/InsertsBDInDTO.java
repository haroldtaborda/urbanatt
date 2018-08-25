package co.urbaNatt.DTO;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class InsertsBDInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tabla;
	private String campos;
	private Connection conexion;
	private List<Object> parametros;
	private Long filtro;
	private String id;
	private List<Object[]> updates;
	/**
	 * @return the tabla
	 */
	public String getTabla() {
		return tabla;
	}
	/**
	 * @param tabla the tabla to set
	 */
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	/**
	 * @return the campos
	 */
	public String getCampos() {
		return campos;
	}
	/**
	 * @param campos the campos to set
	 */
	public void setCampos(String campos) {
		this.campos = campos;
	}
	/**
	 * @return the parametros
	 */
	public List<Object> getParametros() {
		return parametros;
	}
	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(List<Object> parametros) {
		this.parametros = parametros;
	}
	/**
	 * @param tabla
	 * @param campos
	 * @param parametros
	 */
	public InsertsBDInDTO(String tabla, String campos, List<Object> parametros) {
		super();
		this.tabla = tabla;
		this.campos = campos;
		this.parametros = parametros;
	}
	
	public InsertsBDInDTO(){
		
	}
	/**
	 * @return the conexion
	 */
	public Connection getConexion() {
		return conexion;
	}
	/**
	 * @param conexion the conexion to set
	 */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	/**
	 * @return the filtro
	 */
	public Long getFiltro() {
		return filtro;
	}
	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(Long filtro) {
		this.filtro = filtro;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the updates
	 */
	public List<Object[]> getUpdates() {
		return updates;
	}
	/**
	 * @param updates the updates to set
	 */
	public void setUpdates(List<Object[]> updates) {
		this.updates = updates;
	}
	
	

}
