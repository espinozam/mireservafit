import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ClienteDeleteServlet")
public class ClienteDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cabeceras CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        String idParam = request.getParameter("id");

        try (PrintWriter out = response.getWriter()) {
            if (idParam == null || idParam.isEmpty()) {
                out.println("ID del cliente no proporcionado.");
                return;
            }

            int id = Integer.parseInt(idParam);

            String resultado = Bbdd.eliminarCliente(id);
            out.println(resultado);

        } catch (NumberFormatException e) {
            response.getWriter().println("ID inválido.");
        }
    }

    // Para prevenir errores por acceso GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta POST.");
    }
}
