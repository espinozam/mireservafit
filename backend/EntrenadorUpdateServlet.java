import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/EntrenadorUpdateServlet")
public class EntrenadorUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Actualiza si tienes contraseña

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        
            // conexión a BBDD igual que otros servlets
                String idStr = request.getParameter("id");
                String nombre = request.getParameter("nombre");
                String especialidad = request.getParameter("especialidad");

                if (idStr == null || nombre == null || especialidad == null) {
                    response.getWriter().println("Faltan parámetros.");
                    return;
                }

                int id = Integer.parseInt(idStr);

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "UPDATE entrenador SET nombre = ?, especialidad = ? WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, nombre);
                        stmt.setString(2, especialidad);
                        stmt.setInt(3, id);

                        int filas = stmt.executeUpdate();
                        if (filas > 0) {
                            response.getWriter().println("Entrenador actualizado correctamente.");
                        } else {
                            response.getWriter().println("No se encontró el entrenador.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.getWriter().println("Error SQL: " + e.getMessage());
                }
       	}
        

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta POST.");
    }
}