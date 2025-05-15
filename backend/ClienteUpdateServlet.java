import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ClienteUpdateServlet")
public class ClienteUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Cambia según tu contraseña real

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Solución CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        
        System.out.println("ID recibido: " + id);
        System.out.println("Nombre recibido: " + nombre);
        System.out.println("Email recibido: " + email);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, email);
                stmt.setInt(3, Integer.parseInt(id));
                int filas = stmt.executeUpdate();

                PrintWriter out = response.getWriter();
                if (filas > 0) {
                    out.println("Cliente actualizado correctamente.");
                } else {
                    out.println("No se pudo actualizar el cliente.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    // Evita error 405 si alguien accede por GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta POST.");
    }
}
