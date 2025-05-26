import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ReservaListServlet")
public class ReservaListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("text/html; charset=UTF-8");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = """
                SELECT r.id, r.fecha, r.hora, c.nombre AS cliente, e.nombre AS entrenador, r.duracion AS duracion, c.id AS cliente_id, e.id AS entrenador_id
                FROM reserva r
                JOIN cliente c ON r.cliente_id = c.id
                JOIN entrenador e ON r.entrenador_id = e.id
                ORDER BY r.fecha, r.hora
                """;

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery();
                 PrintWriter out = response.getWriter()) {

                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Fecha</th><th>Hora</th><th>Cliente</th><th>Entrenador</th><th>Duración</th><th>Acciones</th></tr>");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String fecha = rs.getString("fecha");
                    String hora = rs.getString("hora");
                    String cliente = rs.getString("cliente");
                    String entrenador = rs.getString("entrenador");
                    String duracion = rs.getString("duracion");
                    int clienteId = rs.getInt("cliente_id");
                    int entrenadorId = rs.getInt("entrenador_id");
                    
                    out.printf(
                            "<tr>" +
                                "<td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>" +
                                "<td>" +
                                    "<button class='btn-editar-reserva' " +
                                        "data-id='%d' data-fecha='%s' data-hora='%s' data-cliente-id='%d' data-entrenador-id='%d'>Modificar</button> " +
                                    "<button class='btn-eliminar-reserva' data-id='%d'>Eliminar</button>" +
                                "</td>" +
                            "</tr>%n",
                            id, fecha, hora, cliente, entrenador, duracion,
                            id, fecha, hora, clienteId, entrenadorId, id
                        );
                }

                out.println("</table>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al obtener reservas: " + e.getMessage());
        }
    }
}
