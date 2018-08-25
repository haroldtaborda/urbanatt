/**
 * EnumExternalLoginErrors.java
 */
package co.urbaNatt.constans;


import co.urbaNatt.DTO.ResultSecurityDTO;

/**
 * <b>Descripcion: </b>Enum que define los posibles errores de los web services de integracion
 * @author IBM - <a href="mailto:hreysand@co.ibm.com">Hugo Hernadno Rey</a>
 * @date 19/11/2015
 */
public enum EnumWebServicesErrors {

	NO_EXISTE_PACIENTE		(1, EnumServiceTypeError.BUSINESS.getTipoError(),	"El usuario ingresado no existe. Por favor verificar"),
	ERROR_CREAR_USUARIO	(15, EnumServiceTypeError.BUSINESS.getTipoError(),	"El usuario no puso ser creado, contacte su administrador"),
	ERROR_MAXIMO_PRODUCTOS	(2, EnumServiceTypeError.BUSINESS.getTipoError(),	"Supero el limite disponible para el producto seleccionado"),
	ERROR_CREAR_CLIENTE	(4, EnumServiceTypeError.BUSINESS.getTipoError(),	"El cliente no puso ser creado, contacte su administrador"),
	ERROR_CLIENTE_EXISTE	(16, EnumServiceTypeError.BUSINESS.getTipoError(),	"Ya existe un cliente con el número identificaci{on ingresado"),
	ERROR_ELIMINACION_USUARIO (5, EnumServiceTypeError.BUSINESS.getTipoError(),	"No es posible eliminar el usuario, contacte su administrador"),
	WRONG_PASSWORD			(3, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El usuario y/o contraseña ingresados son inválidos. Por favor verificar."),
	WRONG_TOKEN				(6, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El código ingresado no coincide. Por favor verificar"),
	NO_TOKEN_PASS			(10, EnumServiceTypeError.BUSINESS.getTipoError(), 	"No se ha realizado la solicitud del cambio de contraseña"),
	TOKEN_PASS_OLD			(11, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El tiempo para el cambio de la contraseña se venció. Es necesario volver a realizar una solicitud de cambio de contraseña"),
	TOKEN_NOT_EXIST			(13, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El código o token no existe. Es necesario iniciar sesión."),
	WRONG_WORKER			(14, EnumServiceTypeError.BUSINESS.getTipoError(), 	"El paciente no se encuentro dentro de sus casos. Es posible que lo hayan reasignado a otro médico. Por favor actualice su lista de pacientes");
	
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
