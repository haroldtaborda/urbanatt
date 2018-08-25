/**
 * EnumExternalLoginErrors.java
 */
package co.ibm.urbaNatt.services.rest.error;


import co.urbaNatt.DTO.ResultSecurityDTO;
import co.urbaNatt.constans.EnumServiceTypeError;
import co.urbaNatt.constans.Error;

/**
 * <b>Descripcion: </b>Enum que define los posibles errores de los web services de integracion
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">Hugo Hernadno Rey</a>
 * @date 19/11/2015
 */
public enum EnumWebServicesErrors {

	NO_EXISTE_PACIENTE		(1, EnumServiceTypeError.BUSINESS.getTipoError(),	"El usuario ingresado no existe. Por favor verificar"),
	ERROR_MAXIMO_PRODUCTOS	(15, EnumServiceTypeError.BUSINESS.getTipoError(),	"Supero el limite disponible para el producto seleccionado"),
	NO_EXISTE_REG_PACIENTE	(2, EnumServiceTypeError.BUSINESS.getTipoError(),	"El asegurado no se ha registrado en la aplicación."),
	WRONG_PASSWORD			(3, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El usuario y/o contraseña ingresados son inválidos. Por favor verificar."),
	WRONG_TOKEN				(6, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El código ingresado no coincide. Por favor verificar"),
	NO_TOKEN_PASS			(10, EnumServiceTypeError.BUSINESS.getTipoError(), 	"No se ha realizado la solicitud del cambio de contraseña"),
	TOKEN_PASS_OLD			(11, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El tiempo para el cambio de la contraseña se venció. Es necesario volver a realizar una solicitud de cambio de contraseña"),
	TOKEN_NOT_EXIST			(13, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El código o token no existe. Es necesario iniciar sesión."),
	WRONG_WORKER			(14, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El paciente no se encuentro dentro de sus casos. Es posible que lo hayan reasignado a otro médico. Por favor actualice su lista de pacientes"),
	AGENDA_NO_DISPONIBLE 	(39, EnumServiceTypeError.BUSINESS.getTipoError(), 	"No se puede crear la agenda en este rango, ya tiene un rango configurado."),
	CHAT_NO_EXISTE 			(50, EnumServiceTypeError.BUSINESS.getTipoError(), 	"No se ha iniciado una conversación de chat. Intente nuevamente"),
	PROBLEMAS_COMUNICACION 	(99, EnumServiceTypeError.TECHNICAL.getTipoError(),	"En este momento hay problemas de comunicación. Por favor intentar más tarde."),
	ERROR_ESTADO_CONEXION 	(100, EnumServiceTypeError.BUSINESS.getTipoError(), "El estado ingresado no es válido."),
	USUARIO_YA_EXISTE 		(102, EnumServiceTypeError.BUSINESS.getTipoError(), "El usuario ya se encuentra registrado en el sistema."),
	NO_REGSITRO_CLINICO 	(200, EnumServiceTypeError.BUSINESS.getTipoError(), "No encontramos el registro clínico seleccionado."),
	AGENDA_INI_MAY_FIN 		(300, EnumServiceTypeError.BUSINESS.getTipoError(), "La fecha de inicio no puede ser mayor que la fecha final, por favor verificar"),
	AGENDA_INI_MEN_DIA 		(301, EnumServiceTypeError.BUSINESS.getTipoError(), "La fecha de inicio no puede ser inferior a la fecha actual, por favor verificar"),
	AGENDA_DIF_RAN_DAY 		(302, EnumServiceTypeError.BUSINESS.getTipoError(), "Los días ingresados no coinciden con el rango de fechas"),
	AGENDA_MAY_YEAR 		(303, EnumServiceTypeError.BUSINESS.getTipoError(), "No se puede crear agendas por periodos superiores a un año "),
	AGENDA_NO_DAYS 			(304, EnumServiceTypeError.BUSINESS.getTipoError(), "Debe seleccionar un rango de horas, por favor verificar"),
	AGENDA_WRONG_TIME 		(305, EnumServiceTypeError.BUSINESS.getTipoError(), "La fecha u hora final no puede ser anterior a la fecha u hora inicial"),
	CHANGE_MANAGER_INVALID_RANGE 	(400, EnumServiceTypeError.BUSINESS.getTipoError(), "El rango seleccionado es inválido, por favor verificar"),
	CHANGE_MANAGER_NO_DISPONIBLE 	(401, EnumServiceTypeError.BUSINESS.getTipoError(), "No se puede crear la solicitud en este rango de fecha, ya tiene una configurada dentro de este rango."),
	NO_DATA_REPORTS 		(40, EnumServiceTypeError.BUSINESS.getTipoError(), "No se encontraron registros"),
	NO_FIRMA 				(41, EnumServiceTypeError.BUSINESS.getTipoError(), "Actualmente no tienes la firma registrada. Por favor, solicita la firma para poder crear registros clínicos."),
	NO_SOLICITUD 			(402, EnumServiceTypeError.BUSINESS.getTipoError(), "No exite una solicitud"),
	ERROR_DINAMICA 			(500, EnumServiceTypeError.BUSINESS.getTipoError(), "No podemos completar la operación con Dinámica"),
	EXISTE_AGENDA 			(42, EnumServiceTypeError.BUSINESS.getTipoError(), "Existe acutalmente una cita médica en la franja disponible"),
	NO_VERSION				(43, EnumServiceTypeError.BUSINESS.getTipoError(), "No pudimos identificar la versión de la aplicación"),
	UPDATE_VERSION			(44, EnumServiceTypeError.BUSINESS.getTipoError(), "Hay una nueva versión de la aplicación. Por favor actualizar"),
	EXISTE_ACTIVIDAD_AGENDA	(600, EnumServiceTypeError.BUSINESS.getTipoError(), "Existe una actividad programada en el periodo que desea crear la agenda, por favor verificar"),
	NO_USER_TYPE			(45, EnumServiceTypeError.BUSINESS.getTipoError(), "No pudimos identificar el tipo de usuario");
	
	
	/**
	 * Define el código del salida 
	 */
	private Integer codigo;
	
	/**
	 * Define el tipo de error 
	 */
	private String tipo;
	
	
	/**
	 * Define el nombre o descripción de la salida
	 */
	private String descripcion;
	
	/**
	 * Constructor de la clase
	 * @param codigo
	 * @param descripcion
	 */
	private EnumWebServicesErrors(Integer codigo, String tipo ,String descripcion){
		this.codigo = codigo;
		this.tipo = tipo;
		this.descripcion = descripcion;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de codigo
	 * @return El valor de codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de codigo
	 * @param codigo el codigo a actualizar
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de descripcion
	 * @return El valor de descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de descripcion
	 * @param descripcion el descripcion a actualizar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * <b>Descripcion: </b> Retorna el valor de tipo
	 * @return El valor de tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * <b>Descripcion: </b>Actualiza el valor de tipo
	 * @param tipo el tipo a actualizar
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * <b>Descripcion: </b>Devuelve el error asociado a un enum de error
	 * @return Error
	 */
	public Error getError(){
		Error error = new Error();
		error.errorCode = codigo;
		error.errorType = tipo;
		error.errorDescription = descripcion;
		return error;
	}
	
	/**
	 * <b>Descripcion: </b>Obtiene rel result con el error actul
	 * @return ResultSecurityDTO
	 */
	public ResultSecurityDTO getResultError(){
		ResultSecurityDTO result = new ResultSecurityDTO();
		result.result=false;
		result.error = getError();
		return result;
	}
	
	
	
}
