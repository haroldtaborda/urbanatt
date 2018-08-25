/**
 * ResponseUtilities.java
 */
package co.ibm.urbaNatt.services.rest;

import org.json.simple.JSONObject;

import co.urbaNatt.constans.EnumServiceTypeError;
import co.urbaNatt.constans.Error;
import co.urbaNatt.exceptions.BusinessException;
import co.urbaNatt.exceptions.InvalidSessionException;
import co.urbaNatt.exceptions.TechnicalException;

/**
 * <b>Descripcion: </b>Genera el tag de response
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">hreysand</a>
 * @date Jun 15, 2016
 */
public class ResponseUtilities {
	
	
	/**
	 * <b>Descripcion: </b>Genera el objeto json para manejar el response
	 * @param success Indica si la resuesta fue exitosa (true) o no (false)
	 * @param errores Si la respuesta es error debe devolver por lo menos un error
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject getResponse(boolean success, Exception e){
		
		JSONObject response = new JSONObject();
		
		JSONObject dtls = new JSONObject();
		
		if (success){
			response.put("result", true);
			response.put("responseCode", 0);
			response.put("dtls", dtls);
		}else{
			response.put("result", false);
			response.put("responseCode", 1);

			Error error = procesarException(e);
			
			dtls.put("errorCode", error.errorCode);
			dtls.put("errorDescription", error.errorDescription);
			dtls.put("errorType", error.errorType);
			
			response.put("dtls", dtls);
		}
		
		return response;
	}

	/**
	 * <b>Descripcion: </b>Procesa la excepcion y con base en esta informacion genera el mensaje de error
	 * @param e Exception
	 * @return List<Error>
	 */
	private static Error procesarException(Exception e) {
		
		Error error = new Error();
		
		if (e instanceof BusinessException){
			BusinessException be = (BusinessException)e;
			if (be.getResult()!=null){
				error = be.getResult().getError();
			}else{
				error.errorCode=EnumServiceTypeError.BUSINESS.getCodigoError();
				error.errorDescription=e.getMessage();
				error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
			}
		}else if (e instanceof TechnicalException){
			TechnicalException te = (TechnicalException)e;
			error.errorCode=EnumServiceTypeError.TECHNICAL.getCodigoError();
			error.errorDescription=te.getMessage();
			error.errorType = EnumServiceTypeError.TECHNICAL.getTipoError();
		}else if (e instanceof InvalidSessionException){
			InvalidSessionException ie = (InvalidSessionException)e;
			if (ie.getResult()!=null){
				error = ie.getResult().getError();
			}else{
				error.errorCode=EnumServiceTypeError.BUSINESS.getCodigoError();
				error.errorDescription="Se ha presentado un error en la validación de la información. ("+e.getMessage()+")";
				error.errorType = EnumServiceTypeError.BUSINESS.getTipoError();
			}
		}else{
			error.errorCode=EnumServiceTypeError.TECHNICAL.getCodigoError();
			error.errorDescription=e.getMessage() == null?"Error Técnico":e.getMessage();
			error.errorType = EnumServiceTypeError.TECHNICAL.getTipoError();
		}
		return error;
	}

}
