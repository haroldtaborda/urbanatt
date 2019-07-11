/**
 * 
 */
package co.urbaNatt.utils.BD;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.urbaNatt.DTO.InsertsBDInDTO;
import co.urbaNatt.DTO.OperacionesBDInDTO;
import co.urbaNatt.constans.ConsultasDinamicasConstans;
import co.urbaNatt.enums.EstadosOperaciones;

/**
 * @author Harold
 *
 */
public class OperacionesBD {

	private PreparedStatement statement;

	/**
	 * Sirve para update, deletes, inserts
	 * 
	 * @param ejecutarInDTO
	 * @return
	 */
	public Integer ejecutarOperacionBD(OperacionesBDInDTO ejecutarInDTO) {
		if (ejecutarInDTO == null || ejecutarInDTO.getConexion() == null
				|| (ejecutarInDTO.getConsulta() == null || ejecutarInDTO.getConsulta().isEmpty())) {
			return 0;
		}
		try {
			statement = ejecutarInDTO.getConexion().prepareStatement(ejecutarInDTO.getConsulta());
			if (ejecutarInDTO.getParametros() != null) {
				int cont = 0;
				for (Object object : ejecutarInDTO.getParametros()) {
					if (object instanceof Integer) {
						statement.setInt(++cont, Integer.parseInt(object.toString()));
					} else if (object instanceof String) {
						statement.setString(++cont, object.toString());
					} else if (object instanceof Double) {
						statement.setDouble(++cont, Double.parseDouble(object.toString()));
					} else if (object instanceof BigDecimal) {
						statement.setBigDecimal(++cont, new BigDecimal(object.toString()));
					} else if (object instanceof Boolean) {
						statement.setBoolean(++cont, Boolean.getBoolean(object.toString()));
					} else if (object instanceof Long) {
						statement.setLong(++cont, Long.parseLong(object.toString()));
					}
					
				}
			}
			return statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cerrarStatement();
		}
		return 0;
	}

	public ResultSet ejecutarConsulta(OperacionesBDInDTO consultasInDTO) {

		if (consultasInDTO == null || consultasInDTO.getConexion() == null
				|| (consultasInDTO.getConsulta() == null || consultasInDTO.getConsulta().isEmpty())) {
			return null;
		}
		try {
			statement = consultasInDTO.getConexion().prepareStatement(consultasInDTO.getConsulta());
			if (consultasInDTO.getParametros() != null) {
				int cont = 0;
				for (Object object : consultasInDTO.getParametros()) {
					if (object instanceof Integer) {
						statement.setInt(++cont, Integer.parseInt(object.toString()));
					} else if (object instanceof String) {
						statement.setString(++cont, object.toString());
					} else if (object instanceof Double) {
						statement.setDouble(++cont, Double.parseDouble(object.toString()));
					} else if (object instanceof Boolean) {
						statement.setBoolean(++cont, Boolean.getBoolean(object.toString()));
					} else if (object instanceof Long) {
						statement.setLong(++cont, Long.parseLong(object.toString()));
					}
				}
			}
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void cerrarStatement() {
		try {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String insertarRegistro(InsertsBDInDTO insertsBDInDTO) {

		if (insertsBDInDTO == null || insertsBDInDTO.getCampos() == null || insertsBDInDTO.getCampos().isEmpty()
				|| insertsBDInDTO.getConexion() == null || insertsBDInDTO.getTabla() == null
				|| insertsBDInDTO.getTabla().isEmpty() || insertsBDInDTO.getParametros() == null
				|| insertsBDInDTO.getParametros().isEmpty()) {
			return EstadosOperaciones.DATOS_INCOMPLETOS.getEstado();
		}

		StringBuilder sb = new StringBuilder();
		sb.append(ConsultasDinamicasConstans.INSER_BASE);
		sb.append(insertsBDInDTO.getTabla());
		sb.append(ConsultasDinamicasConstans.CARACTER_PARENTECIS_ABRIR);
		sb.append(insertsBDInDTO.getCampos());
		sb.append(ConsultasDinamicasConstans.CARACTER_PARENTECIS_CERRAR);
		sb.append(ConsultasDinamicasConstans.VALUES_BASE);
		sb.append(ConsultasDinamicasConstans.CARACTER_PARENTECIS_ABRIR);
		for (Object object : insertsBDInDTO.getParametros()) {
			if (object instanceof String) {
				if (object.toString().contains("NEXTVAL")) {
					sb.append(object + ",");
				} else {
					sb.append("'" + object + "'" + ",");
				}
			} else {
				sb.append(object + ",");
			}

		}
		String insert = sb.toString().substring(0, sb.toString().length() - 1)
				+ ConsultasDinamicasConstans.CARACTER_PARENTECIS_CERRAR;
		try {
			statement = insertsBDInDTO.getConexion().prepareStatement(insert);
			statement.executeUpdate();
			return EstadosOperaciones.EXITO.getEstado();
		} catch (Exception e) {
			e.printStackTrace();
			return EstadosOperaciones.ERROR.getEstado();
		} finally {
			cerrarStatement();
		}
	}

	public Long consultarIdRolPorNombre(String nombre) {

		switch (nombre) {
		case "Administrador":
			return 1L;
		case "Vendedor":
			return 2L;
		case "Sin rol":
			return 3L;
		default:
			return 4L;
		}

	}

	public String consultarNombreRolPorId(Long idRol) {

		switch (idRol.toString()) {
		case "1":
			return "Administrador";
		case "2":
			return "Vendedor";
		case "3":
			return "Sin rol";
		default:
			return "N/A";
		}

	}

	public String fechaStringPorDate(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String f = format.format(fecha);
		return f;

	}

	public String fechaStringPorDateReporte(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		String f = format.format(fecha);
		return f;

	}
	
	public String fechaStringhoraPorDateReporte(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String f = format.format(fecha);
		return f;

	}

	
	public String fechaStringhora(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String f = format.format(fecha);
		return f;

	}

	public Date fechaDateforString(String fecha) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date f = null;
		try {
			f = format.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return f;

	}

	public Object consultarIdTipoIdPorNombre(String tipoId) {
		switch (tipoId) {
		case "Tarjeta de identidad":
			return 1L;
		case "Cédula ciudadania":
			return 2L;
		case "Cédula extrangeria":
			return 3L;
		case "NIT":
			return 4L;
		default:
			return 5L;
		}
	}

	public String consultarNobreTipoIdPorId(Long tipoId) {
		switch (tipoId.toString()) {
		case "1":
			return "Tarjeta de identidad";
		case "2":
			return "Cédula ciudadania";
		case "3":
			return "Cédula extrangeria";
		case "4":
			return "NIT";
		default:
			return "";
		}
	}

	public String actualizarRegistro(InsertsBDInDTO insertsBDInDTO) {
		if (insertsBDInDTO == null || insertsBDInDTO.getConexion() == null || insertsBDInDTO.getTabla() == null
				|| insertsBDInDTO.getTabla().isEmpty() || insertsBDInDTO.getUpdates() == null) {
			return EstadosOperaciones.DATOS_INCOMPLETOS.getEstado();
		}

		StringBuilder sb = new StringBuilder();
		sb.append(ConsultasDinamicasConstans.UPDATE_BASE);
		sb.append(insertsBDInDTO.getTabla());
		sb.append(ConsultasDinamicasConstans.SET);
		for (Object[] elemento : insertsBDInDTO.getUpdates()) {
			if (elemento[1] instanceof String) {
				sb.append(elemento[0].toString() + " = " + "'"+elemento[1].toString()+"'");
				sb.append(", ");
			} else if (elemento[1] instanceof Integer) { 
				sb.append(elemento[0].toString() + " = " + Integer.parseInt(elemento[1].toString()));
				sb.append(", ");
			}
			else if (elemento[1] instanceof BigDecimal) { 
				sb.append(elemento[0].toString() + " = " + new BigDecimal(elemento[1].toString()));
				sb.append(", ");
			}
			else { 
				sb.append(elemento[0].toString() + " = " + Long.parseLong(elemento[1].toString()));
				sb.append(", ");
			}

		}
		//elimino la ultima ,
		String update = sb.toString().substring(0, sb.toString().length() - 2);
		sb = new StringBuilder();
		sb.append(update);
		sb.append(ConsultasDinamicasConstans.WHERE);
		sb.append(insertsBDInDTO.getId() + " = " + insertsBDInDTO.getFiltro());
		String insert = sb.toString().substring(0, sb.toString().length());
		try {
			statement = insertsBDInDTO.getConexion().prepareStatement(insert);
			statement.executeUpdate();
			return EstadosOperaciones.EXITO.getEstado();
		} catch (Exception e) {
			e.printStackTrace();
			return EstadosOperaciones.ERROR.getEstado();
		} finally {
			cerrarStatement();
		}
	}

	public Integer restarFechas(Date fechaMayor, Date fechaMenor) {
		Calendar fechaA = Calendar.getInstance();
		Calendar fechaB = Calendar.getInstance();
		fechaB.setTime(fechaMenor);
		fechaA.setTime(fechaMayor);
		return null;
	}

	public int[] getDiferenciaFechas(Date fecha) {

		int[] arregloFechas = new int[3];
		int[] fechaInicial = new int[3];
		int anios = 0, meses = 0, dias = 0;
		int[] fechaActual = new int[3];

		fechaInicial = getYearMonthDay(fecha);
		Date fechaAct = new Date();
		fechaActual = getYearMonthDay(fechaAct);

		if (fechaActual[0] >= fechaInicial[0]) {

			dias = fechaActual[2] - fechaInicial[2];
			meses = fechaActual[1] - fechaInicial[1];
			anios = fechaActual[0] - fechaInicial[0];
			if (dias < 0) {
				dias = dias + 30;
				if (meses > 0) {
					meses = meses - 1;
				} else {
					meses = meses - 1 + 12;
					anios = anios - 1;
				}
			} else {
				if (meses < 0) {
					meses = meses + 12;
					anios = anios - 1;
				}
			}
		}
		arregloFechas[0] = dias;
		arregloFechas[1] = meses;
		arregloFechas[2] = anios;

		return arregloFechas;
	}

	public int[] getYearMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int[] arr = new int[3];
		arr[0] = calendar.get(Calendar.YEAR);
		arr[1] = calendar.get(Calendar.MONTH) + 1;
		arr[2] = calendar.get(Calendar.DAY_OF_MONTH);

		return arr;
	}

	public String consultarNumeroMesPorTexto(String mes) {
		switch (mes) {
		case "Enero":
			return "01";
		case "Febrero":
			return "02";
		case "Marzo":
			return "03";
		case "Abril":
			return "04";
		case "Mayo":
			return "05";
		case "Junio":
			return "06";
		case "Julio":
			return "07";
		case "Agosto":
			return "08";
		case "Septiembre":
			return "09";
		case "Octubre":
			return "10";
		case "Noviembre":
			return "11";
		case "Diciembre":
			return "12";
			
		default:
			return "";
		}
	}

}
