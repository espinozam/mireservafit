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

        String resultado;
        try {
            resultado = Bbdd.actualizarCliente(Integer.parseInt(id), nombre, email);
        } catch (Exception e) {
            resultado = "Error al actualizar cliente: " + e.getMessage();
        }
        response.getWriter().println(resultado);
    }

    // Evita error 405 si alguien accede por GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta POST.");
    }
}
