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

import co.urbaNatt.DTO.ProductoDTO;
import co.urbaNatt.ejbs.IProductosEJBLocal;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.EJBBusinessLookup;

@Path("/productos")
public class ProductosServices {

	private static Logger log = Logger.getLogger(ProductosServices.class.getCanonicalName());

	@SuppressWarnings("unchecked")
	@POST
	@Path("/crearProducto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearProducto(ProductoDTO productoDTO) {
		JSONObject response = new JSONObject();
		try {
			IProductosEJBLocal ejb = EJBBusinessLookup.getProductosEJB();
			String res = ejb.crearProducto(productoDTO);
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
	@Path("/eliminarProducto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarProducto(ProductoDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IProductosEJBLocal ejb = EJBBusinessLookup.getProductosEJB();
			String res = ejb.eliminarProducto(usuarioInDTO);
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
	@Path("/consultarProductos/{nombreProducto}/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarProductos(
			@PathParam("nombreProducto") String nombreProducto,
			@PathParam("tipo") String tipo) {

		JSONObject json = null;
		List<ProductoDTO> usuarios = new ArrayList<ProductoDTO>();
		try {
			IProductosEJBLocal ejb = EJBBusinessLookup.getProductosEJB();
			usuarios = ejb.consultaProductos(nombreProducto, tipo);
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
