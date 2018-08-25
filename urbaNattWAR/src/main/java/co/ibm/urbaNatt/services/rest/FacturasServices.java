/**
 * UsuariosServices.java
 */
package co.ibm.urbaNatt.services.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import co.urbaNatt.DTO.DetalleFacturaDTO;
import co.urbaNatt.DTO.FacturaDTO;
import co.urbaNatt.DTO.ReporteDTO;
import co.urbaNatt.ejbs.IFacturasEJBLocal;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.EJBBusinessLookup;

@Path("/facturas")
public class FacturasServices {

	private static Logger log = Logger.getLogger(FacturasServices.class.getCanonicalName());

	@SuppressWarnings("unchecked")
	@POST
	@Path("/crearFactura")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearFactura(FacturaDTO productoDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.crearFactura(productoDTO);
			response.put("resultado", res);
			response.put("responseResult", ResponseUtilities.getResponse(true, null));
			return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (NamingException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TechnicalException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (BusinessException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/eliminarFactura")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarFactura(FacturaDTO productoDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.eliminarFactura(productoDTO);
			response.put("resultado", res);
			response.put("responseResult", ResponseUtilities.getResponse(true, null));
			return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (NamingException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TechnicalException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (BusinessException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/revertirFactura")
	@Produces(MediaType.APPLICATION_JSON)
	public Response revertirFactura(FacturaDTO productoDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.revertirFactura(productoDTO);
			response.put("resultado", res);
			response.put("responseResult", ResponseUtilities.getResponse(true, null));
			return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (NamingException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TechnicalException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (BusinessException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/abonarFactura")
	@Produces(MediaType.APPLICATION_JSON)
	public Response abonarFactura(DetalleFacturaDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.abonarFactura(usuarioInDTO);
			response.put("resultado", res);
			response.put("responseResult", ResponseUtilities.getResponse(true, null));
			return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (NamingException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TechnicalException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (BusinessException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/generarReporte")
	@Produces(MediaType.APPLICATION_JSON)
	public Response generarReporte(ReporteDTO reporteDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.generarReporte(reporteDTO);
			response.put("resultado", res);
			response.put("responseResult", ResponseUtilities.getResponse(true, null));
			return Response.status(200).entity(response).header("Access-Control-Allow-Origin", "*").build();
		} catch (NamingException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (TechnicalException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		} catch (BusinessException e) {
			response.put("responseResult", ResponseUtilities.getResponse(false, e));
			return Response.status(200).entity(response.toJSONString()).header("Access-Control-Allow-Origin", "*")
					.build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/consultasFacturas/{numeroFactura}/{estado}/{numeroId}/{dias}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultasFacturas(
			@PathParam("numeroFactura") String numeroFactura,
			@PathParam("estado") String estado, @PathParam("numeroId") String numeroId, @PathParam("dias") Integer dias) {

		JSONObject json = null;
		List<FacturaDTO> usuarios = new ArrayList<FacturaDTO>();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			usuarios = ejb.consultasFacturas(numeroFactura, estado, numeroId, dias);
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
