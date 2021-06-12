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
import co.urbaNatt.DTO.PreciosClienteDTO;
import co.urbaNatt.DTO.ProductoDTO;
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
	@Path("/crearPrecios")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearPrecios(PreciosClienteDTO productoDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.crearPrecios(productoDTO);
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
	@Path("/eliminarPrecio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarPrecio(PreciosClienteDTO productoDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.eliminarPrecio(productoDTO);
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
	@Path("/modificarAbono")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarAbono(DetalleFacturaDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.modificarAbono(usuarioInDTO);
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
	@Path("/eliminarAbono")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarAbono(DetalleFacturaDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			String res = ejb.eliminarAbono(usuarioInDTO);
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
	@Path("/consultasFacturas/{numeroFactura}/{estado}/{numeroId}/{dias}/{nombreCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultasFacturas(
			@PathParam("numeroFactura") String numeroFactura,
			@PathParam("estado") String estado, @PathParam("numeroId") String numeroId, @PathParam("dias") Integer dias,
			@PathParam("nombreCliente") String nombreCliente) {

		JSONObject json = null;
		List<FacturaDTO> usuarios = new ArrayList<FacturaDTO>();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			usuarios = ejb.consultasFacturas(numeroFactura, estado, numeroId, dias,nombreCliente);
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
	@Path("/consultarPrecios/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarPrecios(
			@PathParam("idCliente") String idCliente) {

		JSONObject json = null;
		List<ProductoDTO> usuarios = new ArrayList<ProductoDTO>();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			usuarios = ejb.consultarPrecios(idCliente);
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
	@Path("/consultarPreciosTabla/{idCliente}/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarPreciosTabla(
			@PathParam("idCliente") String idCliente,
			@PathParam("nombre") String nombre) {

		JSONObject json = null;
		List<PreciosClienteDTO> usuarios = new ArrayList<PreciosClienteDTO>();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			usuarios = ejb.consultarPreciosTabla(idCliente,nombre);
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
	@Path("/consultarAbonos/{numeroFactura}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarAbonos(
			@PathParam("numeroFactura") Long idFactura) {

		JSONObject json = null;
		List<DetalleFacturaDTO> usuarios = new ArrayList<DetalleFacturaDTO>();
		try {
			IFacturasEJBLocal ejb = EJBBusinessLookup.getFacturasEJB();
			usuarios = ejb.consultarAbonos(idFactura);
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
