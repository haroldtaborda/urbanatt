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

import co.urbaNatt.DTO.ClienteDTO;
import co.urbaNatt.DTO.SucursalInDTO;
import co.urbaNatt.DTO.UsuarioInDTO;
import co.urbaNatt.DTO.UsuarioOutDTO;
import co.urbaNatt.ejbs.IUsuarioEJBLocal;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;
import co.urbaNatt.utils.EJBBusinessLookup;

@Path("/usuarios")
public class UsuariosServices {

	private static Logger log = Logger.getLogger(UsuariosServices.class.getCanonicalName());

	@SuppressWarnings("unchecked")
	@POST
	@Path("/crearUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearUsuario(UsuarioInDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			String res = ejb.crearUsuario(usuarioInDTO);
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
	@Path("/crearSucursal")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearSucursal(SucursalInDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			String res = ejb.crearSucursal(usuarioInDTO);
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
	@Path("/crearCliente")
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearCliente(ClienteDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			String res = ejb.crearCliente(usuarioInDTO);
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
	@Path("/eliminarUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarUsuario(UsuarioInDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			String res = ejb.eliminarUsuario(usuarioInDTO);
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
	@Path("/eliminarSucursal")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarSucursal(SucursalInDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			String res = ejb.eliminarSucursal(usuarioInDTO);
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
	@Path("/eliminarCliente")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarCliente(ClienteDTO usuarioInDTO) {
		JSONObject response = new JSONObject();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			String res = ejb.eliminarCliente(usuarioInDTO);
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
	@Path("/consultarUsuarios/{nombreUsuario}/{rol}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarUsuarios(
			@PathParam("nombreUsuario") String nombreUsuario,
			@PathParam("rol") String rol) {

		JSONObject json = null;
		List<UsuarioOutDTO> usuarios = new ArrayList<UsuarioOutDTO>();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			usuarios = ejb.consultarUsuarios(nombreUsuario, rol);
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
	@Path("/consultarSucursalesPorCC/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarSucursalesPorCC(
			@PathParam("idCliente") Long idCliente) {

		JSONObject json = null;
		List<SucursalInDTO> usuarios = new ArrayList<SucursalInDTO>();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			usuarios = ejb.consultarSucursalesPorCC(idCliente);
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
	@Path("/consultarSucursales/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarSucursales(
			@PathParam("idCliente") Long idCliente) {

		JSONObject json = null;
		List<SucursalInDTO> usuarios = new ArrayList<SucursalInDTO>();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			usuarios = ejb.consultarSucursales(idCliente);
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
	@Path("/consultarClientes/{tipoId}/{numId}/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarClientes(
			@PathParam("tipoId") String tipoId,
			@PathParam("numId") String numId, 
			@PathParam("nombre") String nombre) {

		JSONObject json = null;
		List<ClienteDTO> usuarios = new ArrayList<ClienteDTO>();
		try {
			IUsuarioEJBLocal ejb = EJBBusinessLookup.getUsuarioEJB();
			usuarios = ejb.consultarClientes(tipoId, numId, nombre);
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
