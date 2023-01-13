/**
 * IManagerSecurityEJBLocal.java
 */
package co.urbaNatt.ejbs;

import java.util.List;

import javax.ejb.Local;

import co.urbaNatt.DTO.ActualizarPrecioIndividualDTO;
import co.urbaNatt.DTO.ActualizarPreciosDTO;
import co.urbaNatt.DTO.DetalleFacturaDTO;
import co.urbaNatt.DTO.FacturaDTO;
import co.urbaNatt.DTO.PreciosClienteDTO;
import co.urbaNatt.DTO.ProductoDTO;
import co.urbaNatt.DTO.ReporteDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;


@Local
public interface IFacturasEJBLocal {
	
	
	public String crearFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	public String eliminarFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	public String revertirFactura(FacturaDTO facturaDTO) throws TechnicalException, BusinessException;
	public List<FacturaDTO> consultasFacturas(String numeroFactura, String estado,String numeroId, Integer dias, String nombreCliente)  throws TechnicalException, BusinessException;

	public String abonarFactura(DetalleFacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	
	public String generarReporte(ReporteDTO reporteDTO) throws TechnicalException, BusinessException;
	
	public String cambiarEstadoFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	public List<DetalleFacturaDTO> consultarAbonos(Long numeroFactura) throws TechnicalException, BusinessException;
	public String modificarAbono(DetalleFacturaDTO usuarioInDTO)  throws TechnicalException, BusinessException;
	public String eliminarAbono(DetalleFacturaDTO usuarioInDTO)  throws TechnicalException, BusinessException;
	public String crearPrecios(PreciosClienteDTO productoDTO)  throws TechnicalException, BusinessException;
	public List<ProductoDTO> consultarPrecios(String idCliente) throws TechnicalException, BusinessException;
	public List<PreciosClienteDTO> consultarPreciosTabla(String idCliente, String nombre) throws TechnicalException, BusinessException;
	public String eliminarPrecio(PreciosClienteDTO productoDTO) throws TechnicalException, BusinessException;
	public String actualizarPrecios(ActualizarPreciosDTO actulizarDTO) throws TechnicalException, BusinessException;
	public String actualizarPrecioIndividual(ActualizarPrecioIndividualDTO actulizarDTO) throws TechnicalException, BusinessException;
	
}
