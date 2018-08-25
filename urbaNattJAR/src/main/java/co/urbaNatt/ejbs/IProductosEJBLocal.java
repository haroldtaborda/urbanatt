/**
 * IManagerSecurityEJBLocal.java
 */
package co.urbaNatt.ejbs;

import java.util.List;

import javax.ejb.Local;

import co.urbaNatt.DTO.ProductoDTO;
import co.urbaNatt.DTO.UsuarioOutDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;


@Local
public interface IProductosEJBLocal {
	
	
	public String crearProducto(ProductoDTO usuarioInDTO) throws TechnicalException, BusinessException;

	public List<ProductoDTO> consultaProductos(String nombreUsuario, String rol)  throws TechnicalException, BusinessException;

	public String eliminarProducto(ProductoDTO usuarioInDTO) throws TechnicalException, BusinessException;
	
}
