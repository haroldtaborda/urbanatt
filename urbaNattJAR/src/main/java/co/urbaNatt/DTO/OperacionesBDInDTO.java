/**
 * 
 */
package co.urbaNatt.DTO;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

/**
 * @author Harold
 *
 */
public class OperacionesBDInDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String consulta;
	private Connection conexion;
	private List<Object> parametros;
	
	
	public OperacionesBDInDTO(){
		
	}
	/**
	 * @param consulta
	 * @param conexion
	 * @param parametros
	 */
	public OperacionesBDInDTO(String consulta, Connection conexion, List<Object> parametros) {
		super();
		this.consulta = consulta;
		this.conexion = conexion;
		this.parametros = parametros;
	}
	/**
	 * @return the consulta
	 */
	public String getConsulta() {
		return consulta;
	}
	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(String consulta) {
		this.consulta = consulta;
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
	
	
	
	
}
