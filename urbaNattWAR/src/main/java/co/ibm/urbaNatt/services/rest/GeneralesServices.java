/**
 * UsuariosServices.java
 */
package co.ibm.urbaNatt.services.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import co.urbaNatt.DTO.ObjetoDTO;
import co.urbaNatt.ejbs.IGeneralesEJBLocal;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.EJBBusinessLookup;

@Path("/generales")
public class GeneralesServices {

	private static Logger log = Logger.getLogger(GeneralesServices.class.getCanonicalName());


	@SuppressWarnings("unchecked")
	@GET
	@Path("/consultarCiudadesPorDepto/{idDepto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarCiudadesPorDepto(
			@PathParam("idDepto") Long idDepto) {

		JSONObject json = null;
		List<ObjetoDTO> usuarios = new ArrayList<ObjetoDTO>();
		try {
			IGeneralesEJBLocal ejb = EJBBusinessLookup.getGeneralesEJB();
			usuarios = ejb.consultarCiudadesPorDepto(idDepto);
			Response response = Response.status(200).entity(usuarios).header("Access-Control-Allow-Origin", "*").build();

			return response;

		} catch (TechnicalException e) {
			json = new JSONObject();
			json.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(401).entity(json.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		} catch (BusinessException e) {
			json = new JSONObject();
			json.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(401).entity(json.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		}

		catch (Exception e) {
			json = new JSONObject();
			json.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(json.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		}

	}



	@SuppressWarnings("unchecked")
	@GET
	@Path("/consultarDeptos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarDeptos() {

		JSONObject json = null;
		List<ObjetoDTO> usuarios = new ArrayList<ObjetoDTO>();
		try {
			IGeneralesEJBLocal ejb = EJBBusinessLookup.getGeneralesEJB();
			usuarios = ejb.consultarDeptos();
			Response response = Response.status(200).entity(usuarios).header("Access-Control-Allow-Origin", "*").build();

			return response;

		} catch (TechnicalException e) {
			json = new JSONObject();
			json.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(401).entity(json.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		} catch (BusinessException e) {
			json = new JSONObject();
			json.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(401).entity(json.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		}

		catch (Exception e) {
			json = new JSONObject();
			json.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(json.toJSONString()).header("Access-Control-Allow-Origin", "*").build();
		}

	}
}
