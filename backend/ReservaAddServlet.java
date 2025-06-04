import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/ReservaAddServlet")
public class ReservaAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	public ReservaAddServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// CORS
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");

		String fecha = request.getParameter("fecha");
		String hora = request.getParameter("hora");
		String clienteIdStr = request.getParameter("cliente_id");
		String entrenadorIdStr = request.getParameter("entrenador_id");

		int clienteId = 0;
		int entrenadorId = 0;

		try {
			clienteId = Integer.parseInt(clienteIdStr);
			entrenadorId = Integer.parseInt(entrenadorIdStr);
		} catch (NumberFormatException e) {
			response.getWriter().println("ID inválido.");
			return;
		}

		// Validar que la fecha y hora de la reserva no sean anteriores al momento actual
		try {
			LocalDate fechaReserva = LocalDate.parse(fecha);
			LocalTime horaReserva = LocalTime.parse(hora);
			LocalDateTime fechaHoraReserva = LocalDateTime.of(fechaReserva, horaReserva);
			LocalDateTime ahora = LocalDateTime.now();

			if (fechaHoraReserva.isBefore(ahora)) {
				response.getWriter().println("No se puede reservar en una fecha y hora pasada.");
				return;
			}
		} catch (Exception e) {
			response.getWriter().println("Fecha u hora inválida.");
			return;
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			// Comprobar solapamiento de reservas:
			// Esta consulta verifica si ya existe una reserva para el mismo entrenador,
			// en la misma fecha, y en el rango de 59 minutos antes y después de la hora solicitada.
			// Así se evita que dos reservas se crucen, considerando que cada sesión dura 60 minutos.
			String checkSql = "SELECT COUNT(*) FROM reserva WHERE fecha = ? AND entrenador_id = ? " +
							  "AND hora BETWEEN DATE_SUB(STR_TO_DATE(?, '%H:%i'), INTERVAL 59 MINUTE) " +
							  "AND DATE_ADD(STR_TO_DATE(?, '%H:%i'), INTERVAL 59 MINUTE)";
			try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
				checkStmt.setString(1, fecha);
				checkStmt.setInt(2, entrenadorId);
				checkStmt.setString(3, hora);
				checkStmt.setString(4, hora);

				ResultSet rs = checkStmt.executeQuery();
				if (rs.next() && rs.getInt(1) > 0) {
					// Si hay al menos una reserva en ese rango, se informa al usuario y no se guarda la nueva reserva.
					response.getWriter().println("Ya existe una reserva para ese entrenador en esa fecha y hora o en una hora adyacente.");
					return;
				}
			}

			// Si no hay solapamiento, se inserta la nueva reserva
			int duracion = 60;
			String resultado = Bbdd.guardarReserva(fecha, hora, clienteId, entrenadorId, duracion);

			PrintWriter out = response.getWriter();
			out.println(resultado);
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Error al guardar reserva: " + e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Si alguien accede por GET, devolvemos error 405
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta POST.");
	}
}
