/**
 * EJBLookup.java
 */
package co.urbaNatt.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.urbaNatt.ejbs.FacturasEJB;
import co.urbaNatt.ejbs.GeneralesEJB;
import co.urbaNatt.ejbs.IFacturasEJBLocal;
import co.urbaNatt.ejbs.IGeneralesEJBLocal;
import co.urbaNatt.ejbs.IProductosEJBLocal;
import co.urbaNatt.ejbs.ISecurityAdminLocal;
import co.urbaNatt.ejbs.IUsuarioEJBLocal;
import co.urbaNatt.ejbs.ProductosEJB;
import co.urbaNatt.ejbs.SecurityAdminEJB;
import co.urbaNatt.ejbs.UsuarioEJB;

/**
 * <b>Descripcion: </b>Obtiene la referencia de los EJB
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">Hugo Rey</a>
 * @date 1/02/2016
 */
public class EJBBusinessLookup {
	
	/**
	 * Instancia del log
	 */
	private static Logger log = Logger.getLogger(EJBBusinessLookup.class.getCanonicalName());
	/**
	 * Variable que indica el si esta en ambiente de desarrollo
	 */
	private static boolean developEnvironment = System.getProperty("test")==null?false:System.getProperty("test").equals("true");

	

	/**
	 * <b>Descripcion: </b> Obtener referencia al EJB de servicios de AdminSecurity
	 * @return ParametricsEJB
	 * @throws NamingException
	 */
	public static ISecurityAdminLocal getSecurityAdminEJB() throws NamingException {
		ISecurityAdminLocal ejb = null;
		ejb = (ISecurityAdminLocal) jndiUrl(ISecurityAdminLocal.class, SecurityAdminEJB.class);
		return ejb;
	}
	
	public static IUsuarioEJBLocal getUsuarioEJB() throws NamingException {
		IUsuarioEJBLocal ejb = null;
		ejb = (IUsuarioEJBLocal) jndiUrl(IUsuarioEJBLocal.class, UsuarioEJB.class);
		return ejb;
	}
	
	public static IGeneralesEJBLocal getGeneralesEJB() throws NamingException {
		IGeneralesEJBLocal ejb = null;
		ejb = (IGeneralesEJBLocal) jndiUrl(IGeneralesEJBLocal.class, GeneralesEJB.class);
		return ejb;
	}
	
	public static IProductosEJBLocal getProductosEJB() throws NamingException {
		IProductosEJBLocal ejb = null;
		ejb = (IProductosEJBLocal) jndiUrl(IProductosEJBLocal.class, ProductosEJB.class);
		return ejb;
	}
	
	public static IFacturasEJBLocal getFacturasEJB() throws NamingException {
		IFacturasEJBLocal ejb = null;
		ejb = (IFacturasEJBLocal) jndiUrl(IFacturasEJBLocal.class, FacturasEJB.class);
		return ejb;
	}

	
	/**
	 * <b>Descripcion: </b> Metodo encargado de localizar el ejb de ContactPoint
	 * @param interfaceClass
	 * @param clazz
	 * @return
	 */
	public static Object jndiUrl(Class<?> interfaceClass, Class<?> clazz){
		
		String ejbJndi = "java:global/urbaNattEAR-1.0/urbaNatt.war/"+clazz.getSimpleName();
		
		log.log(Level.FINE, "JNDI EJB: "+ejbJndi);
		
		Object classInstance = null;
		
		try {
			InitialContext context = new InitialContext();
			classInstance = context.lookup(ejbJndi);
			
		} catch (NamingException e) {
			if (developEnvironment){
				log.log(Level.WARNING, "Se esta ejecutando el ejb sin contexto");
				try {
					classInstance = clazz.newInstance();
				} catch (Exception e1) {
					log.log(Level.SEVERE, e1.getMessage(), e1);
				}
			}else{
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
		return classInstance;
	}

	
	
	
	
}
