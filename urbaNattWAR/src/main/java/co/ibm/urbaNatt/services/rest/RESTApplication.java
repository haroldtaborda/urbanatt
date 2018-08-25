/**
 * RESTApplication.java
 */
package co.ibm.urbaNatt.services.rest;

import java.util.HashSet;
import java.util.Set;

import co.ibm.urbaNatt.services.rest.security.AdminSecurityServices;


/**
 * 
 * @author Harold
 *
 */
public class RESTApplication extends javax.ws.rs.core.Application {

	private Set<Object> singletons = new HashSet<Object>();

	/**
	 * Constructor de la clase
	 */
	public RESTApplication() {
		singletons.add(new AdminSecurityServices());
		singletons.add(new UsuariosServices());
		singletons.add(new GeneralesServices());
		singletons.add(new ProductosServices());
		singletons.add(new FacturasServices());
	}

	/** 
	 * @see javax.ws.rs.core.Application#getSingletons()
	 */
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}