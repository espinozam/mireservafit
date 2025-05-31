import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/EntrenadorListServlet")
public class EntrenadorListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Actualiza si tienes contraseña

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id, nombre, especialidad FROM entrenador";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

            	out.println("<html><body>");
                out.println("<h2>Lista de Entrenadores</h2>");

                out.println("<button onclick=\"mostrarFormularioAgregar()\">Añadir Entrenador</button><br><br>");

                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Nombre</th><th>especialidad</th><th>Acciones</th></tr>");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String especialidad = rs.getString("especialidad");

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + nombre + "</td>");
                    out.println("<td>" + especialidad + "</td>");
                    out.println("<td>");
                    out.println("<button onclick='editarEntrenador(" + id + ")'>Editar</button>");
                    out.println("<button onclick='eliminarEntrenador(" + id + ")'>Eliminar</button>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");


                out.println("</body></html>");
                }

                
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"error\": \"Error al obtener la lista de entrenadores.\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta GET.");
    }
}