import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ClienteAddServlet")
public class ClienteAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Habilitar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String gimnasioIdStr = request.getParameter("gimnasio_id");

        if (nombre == null || email == null || nombre.isEmpty() || email.isEmpty()) {
            response.getWriter().println("Nombre y email son obligatorios.");
            return;
        }

        int gimnasioId = 1; // valor por defecto
        try {
            if (gimnasioIdStr != null && !gimnasioIdStr.isEmpty()) {
                gimnasioId = Integer.parseInt(gimnasioIdStr);
            }
        } catch (NumberFormatException e) {
            // Si no es válido, se mantiene el valor por defecto
        }

        Bbdd.guardarCliente(nombre, email, telefono, gimnasioId);
    }
    
}
