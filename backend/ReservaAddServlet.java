import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

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

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "INSERT INTO reserva (fecha, hora, cliente_id, entrenador_id) VALUES (?, ?, ?, ?)";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, fecha);
				stmt.setString(2, hora);
				stmt.setInt(3, clienteId);
				stmt.setInt(4, entrenadorId);
				
				int filas = stmt.executeUpdate();

				PrintWriter out = response.getWriter();
				if (filas > 0) {
					out.println("Reserva guardada correctamente");
				} else {
					out.println("No se pudo guardar la reserva");
				}
			}
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
