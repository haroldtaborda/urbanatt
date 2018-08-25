/**
 * IManagerSecurityEJBLocal.java
 */
package co.urbaNatt.ejbs;

import java.util.List;

import javax.ejb.Local;

import co.urbaNatt.DTO.ClienteDTO;
import co.urbaNatt.DTO.SucursalInDTO;
import co.urbaNatt.DTO.UsuarioInDTO;
import co.urbaNatt.DTO.UsuarioOutDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;


@Local
public interface IUsuarioEJBLocal {
	
	
	public String crearUsuario(UsuarioInDTO usuarioInDTO) throws TechnicalException, BusinessException;
	
	public String crearSucursal(SucursalInDTO sucursalInDTO) throws TechnicalException, BusinessException;

	public List<UsuarioOutDTO> consultarUsuarios(String nombreUsuario, String rol)  throws TechnicalException, BusinessException;
	
	public List<SucursalInDTO> consultarSucursalesPorCC(Long  idCliente)  throws TechnicalException, BusinessException;
	
	public List<SucursalInDTO> consultarSucursales(Long  idCliente)  throws TechnicalException, BusinessException;

	public String eliminarUsuario(UsuarioInDTO usuarioInDTO) throws TechnicalException, BusinessException;
	
	public String eliminarSucursal(SucursalInDTO sucursalInDTO) throws TechnicalException, BusinessException;
	
	public String eliminarCliente(ClienteDTO clienteDTO) throws TechnicalException, BusinessException;

	public List<ClienteDTO> consultarClientes(String tipoId, String numId, String nombre) throws TechnicalException, BusinessException;

	public String crearCliente(ClienteDTO usuarioInDTO) throws TechnicalException, BusinessException;
}
