/**
 * IManagerSecurityEJBLocal.java
 */
package co.urbaNatt.ejbs;

import java.util.List;

import javax.ejb.Local;

import co.urbaNatt.DTO.DetalleFacturaDTO;
import co.urbaNatt.DTO.FacturaDTO;
import co.urbaNatt.DTO.ReporteDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;


@Local
public interface IFacturasEJBLocal {
	
	
	public String crearFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	public String eliminarFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	public String revertirFactura(FacturaDTO facturaDTO) throws TechnicalException, BusinessException;
	public List<FacturaDTO> consultasFacturas(String numeroFactura, String estado,String numeroId, Integer dias)  throws TechnicalException, BusinessException;

	public String abonarFactura(DetalleFacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	
	public String generarReporte(ReporteDTO reporteDTO) throws TechnicalException, BusinessException;
	
	public String cambiarEstadoFactura(FacturaDTO usuarioInDTO) throws TechnicalException, BusinessException;
	
	
}
