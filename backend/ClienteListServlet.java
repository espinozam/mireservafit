import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ClienteListServlet")
public class ClienteListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Habilitar CORS
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id, nombre, email FROM cliente";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                out.println("<html><body>");
                out.println("<h2>Lista de Clientes</h2>");

                out.println("<button onclick=\"mostrarFormularioAgregar()\">Añadir Cliente</button><br><br>");

                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Nombre</th><th>Email</th><th>Acciones</th></tr>");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String email = rs.getString("email");

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + nombre + "</td>");
                    out.println("<td>" + email + "</td>");
                    out.println("<td>");
                    out.println("<button onclick='editarCliente(" + id + ", \"" + nombre + "\", \"" + email + "\")'>Editar</button>");
                    out.println("<button onclick='eliminarCliente(" + id + ")'>Eliminar</button>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");


                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error al obtener la lista de clientes.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
