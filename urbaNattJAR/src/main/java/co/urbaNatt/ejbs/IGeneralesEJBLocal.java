/**
 * IManagerSecurityEJBLocal.java
 */
package co.urbaNatt.ejbs;

import java.util.List;

import javax.ejb.Local;

import co.urbaNatt.DTO.ObjetoDTO;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.TechnicalException;


@Local
public interface IGeneralesEJBLocal {
	
	
	public List<ObjetoDTO> consultarCiudadesPorDepto(Long idDepto)  throws TechnicalException, BusinessException;
	public List<ObjetoDTO> consultarDeptos()  throws TechnicalException, BusinessException;

}
