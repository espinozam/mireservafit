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

@WebServlet("/ReservaUpdateServlet")
public class ReservaUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        String idStr = request.getParameter("id");
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");
        String clienteIdStr = request.getParameter("cliente_id");
        String entrenadorIdStr = request.getParameter("entrenador_id");

        int id, clienteId, entrenadorId;

        try {
            id = Integer.parseInt(idStr);
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

            // Validar que el cliente exista
            String clienteCheckSql = "SELECT COUNT(*) FROM cliente WHERE id = ?";
            try (PreparedStatement clienteCheckStmt = conn.prepareStatement(clienteCheckSql)) {
                clienteCheckStmt.setInt(1, clienteId);
                ResultSet rsCliente = clienteCheckStmt.executeQuery();
                if (rsCliente.next() && rsCliente.getInt(1) == 0) {
                    response.getWriter().println("El cliente no existe.");
                    return;
                }
            }

            // Validar que el entrenador exista
            String entrenadorCheckSql = "SELECT COUNT(*) FROM entrenador WHERE id = ?";
            try (PreparedStatement entrenadorCheckStmt = conn.prepareStatement(entrenadorCheckSql)) {
                entrenadorCheckStmt.setInt(1, entrenadorId);
                ResultSet rsEntrenador = entrenadorCheckStmt.executeQuery();
                if (rsEntrenador.next() && rsEntrenador.getInt(1) == 0) {
                    response.getWriter().println("El entrenador no existe.");
                    return;
                }
            }

            // Comprobar solapamiento de reservas (excluyendo la reserva actual)
            String checkSql = "SELECT COUNT(*) FROM reserva WHERE fecha = ? AND entrenador_id = ? " +
                    "AND hora BETWEEN DATE_SUB(STR_TO_DATE(?, '%H:%i'), INTERVAL 59 MINUTE) " +
                    "AND DATE_ADD(STR_TO_DATE(?, '%H:%i'), INTERVAL 59 MINUTE) AND id <> ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, fecha);
                checkStmt.setInt(2, entrenadorId);
                checkStmt.setString(3, hora);
                checkStmt.setString(4, hora);
                checkStmt.setInt(5, id);

                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    response.getWriter().println("Ya existe una reserva para ese entrenador en esa fecha y hora o en una hora adyacente.");
                    return;
                }
            }

            // Actualizar la reserva
            String sql = "UPDATE reserva SET fecha = ?, hora = ?, cliente_id = ?, entrenador_id = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fecha);
                stmt.setString(2, hora);
                stmt.setInt(3, clienteId);
                stmt.setInt(4, entrenadorId);
                stmt.setInt(5, id);

                int filas = stmt.executeUpdate();

                PrintWriter out = response.getWriter();
                if (filas > 0) {
                    out.println("Reserva actualizada correctamente");
                } else {
                    out.println("No se pudo actualizar la reserva");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al actualizar reserva: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta POST.");
    }
}