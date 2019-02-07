/**
 * 
 */
package co.urbaNatt.constans;

/**
 * @author Harold
 *
 */
public class ConsultasDinamicasConstans {

	public static final String CONSULTAR_USUARIO_LOGIN = "SELECT NOMBRE_USUARIO,ESTADO,CONTRASENIA_USUARIO,NOMBRE_COMPLETO,ROL_USUARIO FROM USUARIOS WHERE UPPER(NOMBRE_USUARIO) = ? AND CONTRASENIA_USUARIO = ?";
	public static final String INSER_BASE = "INSERT INTO ";
	public static final String DELETE_BASE = "DELETE FROM ";
	public static final String UPDATE_BASE = "UPDATE ";
	public static final String SET = " SET ";
	public static final String WHERE = " WHERE ";
	public static final String CARACTER_PARENTECIS_ABRIR = " ( ";
	public static final String CARACTER_PARENTECIS_CERRAR = " ) ";
	public static final String CARACTER_PARENTECIS_APOSTROFE = "'";
	public static final String VALUES_BASE = " VALUES ";
	
	public static final String CONSULTAR_SUCURSALES= "SELECT ID_SUCURSAL,NOMBRESUCURSAL,TIPO,DIRECCION,DEPARTAMENTO, CIUDAD, ID_CLIENTE FROM SUCURSALES WHERE ID_CLIENTE = ?";
	public static final String CONSULTAR_USUARIO = "SELECT ID_USUARIO,NOMBRE_USUARIO,CONTRASENIA_USUARIO,FECHA_CREACION,CANTIDAD_INTENTOS_INGRESO,ESTADO,NOMBRE_COMPLETO,ID_ROL_USUARIO, NOTA FROM USUARIOS WHERE ID_USUARIO IS NOT NULL  " ;
	public static final String CONSULTAR_USUARIO_NOMBRE = " AND UPPER(NOMBRE_USUARIO) LIKE ? ";
	public static final String CONSULTAR_USUARIO_ROL = " AND ID_ROL_USUARIO = ? ";
	
	public static final String ELIMINAR_USUARIO_POR_ID = "DELETE FROM USUARIOS WHERE ID_USUARIO = ?";
	
	public static final String ELIMINAR_SUCURSALES_POR_ID = "DELETE FROM SUCURSALES WHERE ID_SUCURSAL = ?";
	
	public static final String CONSULTAR_PRODUCTO = "SELECT ID_PRODUCTO, NOMBRE, TIPO,UNIDAD,DESCRIPCION,CANTIDAD,PESO,VALOR  FROM PRODUCTOS WHERE ID_PRODUCTO IS NOT NULL  " ;
	public static final String CONSULTAR_PRODUCTO_NOMBRE = " AND UPPER(NOMBRE) LIKE ? ";
	public static final String CONSULTAR_PRODUCTO_TIPO = " AND TIPO LIKE ? ";
	
	public static final String ELIMINAR_PRODUCTO_POR_ID = "DELETE FROM PRODUCTOS WHERE ID_PRODUCTO = ?";
	
	
	public static final String CONSULTAR_CLIENTE = "SELECT ID_CLIENTE, TIPOID, NUMID, NOMBRECOMPLETO,FIJO, CELULAR, CORREO, DIRECCION, DEPARTAMENTO, CIUDAD, ESTADO, "
			+ " FECHACREACION, D.NOMBRE,C.NOMBRE FROM CLIENTES    INNER JOIN DEPARTAMENTOS D ON DEPARTAMENTO =D.ID INNER JOIN CIUDADES C ON CIUDAD = C.ID WHERE ID_CLIENTE IS NOT NULL  ";
	public static final String CONSULTAR_CLIENTE_NOMBRE = " AND UPPER(NOMBRECOMPLETO) LIKE ? ";
	public static final String CONSULTAR_CLIENTE_TIPOID = " AND TIPOID = ? ";
	public static final String CONSULTAR_CLIENTE_NUMID = " AND NUMID = ? ";
	public static final String ELIMINAR_CLIENTE_POR_ID = "DELETE FROM CLIENTES WHERE ID_CLIENTE = ?";
	
	public static final String CONSULTAR_CIUDADES_POR_DEPTO = "SELECT ID,NOMBRE,ID_DEPARTAMENTO FROM CIUDADES WHERE ID_DEPARTAMENTO = ?";
	public static final String CONSULTAR_DEPTOS = "SELECT ID,NOMBRE,ID_PAIS FROM DEPARTAMENTOS";
	
	
	public static final String CONSULTAR_FACTURA_BASE ="SELECT DISTINCT F.ID_FACTURA,F.NUMERO_FACTURA,F.ID_CLIENTE, F.FECHA_CREACION, F.FECHA_FACTURA, F.VALOR_DEUDA, F.VALOR_PAGADO, F.ESTADO, F.VALOR_FACTURA, F.DESCRIPCION, C.NOMBRECOMPLETO, F.TIPO, F.ID_SUCURSAL, S.NOMBRESUCURSAL, sysdate-TO_DATE(F.FECHA_FACTURA, 'dd/MM/yyyy') FROM FACTURAS F "+ 
   " INNER JOIN CLIENTES C ON C.NUMID =F.ID_CLIENTE LEFT JOIN SUCURSALES S ON S.ID_SUCURSAL=F.ID_SUCURSAL  WHERE F.ID_FACTURA IS NOT NULL ";
	public static final String CONSULTAR_FACTURA_NUMFAC = " AND NUMERO_FACTURA = ? ";
	public static final String CONSULTAR_FACTURA_NUMID = " AND F.ID_CLIENTE = ? ";
	public static final String CONSULTAR_FACTURA_ESTADO = " AND ESTADO LIKE ? ";
	public static final String ELIMINAR_FACTURA_POR_ID = "DELETE FROM FACTURAS WHERE ID_FACTURA = ?";
	
	public static final String ORDER_BY_FACTURAS= " ORDER BY C.NOMBRECOMPLETO";
	public static final String CONSULTAR_SUCURSALES_CC= "SELECT S.ID_SUCURSAL,S.NOMBRESUCURSAL,S.TIPO,S.DIRECCION,S.DEPARTAMENTO, S.CIUDAD, S.ID_CLIENTE FROM SUCURSALES S WHERE S.ID_CLIENTE = ?";
	
	
	public static final String CONSULTAR_ABONOS_FROM=" select NUMERO_FACTURA,NUMERO_RECIBO,VALOR_PAGADO,ID_DETALLE,ID_FACTURA from DETALLE_FACTURA WHERE  ID_FACTURA= ?";
	
}


