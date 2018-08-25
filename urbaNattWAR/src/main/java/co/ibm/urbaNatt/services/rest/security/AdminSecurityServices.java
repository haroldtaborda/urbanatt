/**
 * AdminSecurityServices.java
 */
package co.ibm.urbaNatt.services.rest.security;

import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import co.ibm.urbaNatt.services.rest.ResponseUtilities;
import co.urbaNatt.DTO.CredentialsDTO;
import co.urbaNatt.DTO.LoginAdminOutDTO;
import co.urbaNatt.ejbs.ISecurityAdminLocal;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.BusinessRollBackExceptions;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.EJBBusinessLookup;

@Path("/admin")
public class AdminSecurityServices {
	
	private static Logger log = Logger.getLogger(AdminSecurityServices.class.getCanonicalName());
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(CredentialsDTO credentials){
		JSONObject response = new JSONObject();
		try {
			ISecurityAdminLocal ejb = EJBBusinessLookup.getSecurityAdminEJB();
			LoginAdminOutDTO login = ejb.login(credentials);
//			JSONObject encapsuledResponse = new JSONObject();
//			encapsuledResponse.put("response", Boolean.TRUE);
			response.put("loginResult", login);
			response.put("responseResult", ResponseUtilities.getResponse(true, null));
			return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (NamingException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		} catch (TechnicalException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		} catch (BusinessException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		} catch (BusinessRollBackExceptions e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	


}
