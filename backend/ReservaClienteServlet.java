import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ReservaClienteServlet")
public class ReservaClienteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");

        // Obtener todas las reservas
        ArrayList<Reserva> reservas = Bbdd.obtenerReservas();

        // Filtrar reservas por email del cliente
        ArrayList<Reserva> reservasCliente = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getCliente() != null && r.getCliente().getEmail().equalsIgnoreCase(email)) {
                reservasCliente.add(r);
            }
        }

        String html;
        if (reservasCliente.isEmpty()) {
            html = "<p>No tienes reservas.</p>";
        } else {
            html = "<table border='1'><tr><th>Fecha</th><th>Hora</th><th>Entrenador</th><th>Duración</th></tr>";
            for (Reserva r : reservasCliente) {
                html += "<tr>";
                html += "<td>" + r.getFecha() + "</td>";
                html += "<td>" + r.getHora() + "</td>";
                html += "<td>" + (r.getEntrenador() != null ? r.getEntrenador().getNombre() : "") + "</td>";
                html += "<td>" + r.getDuracion() + " min</td>";
                html += "</tr>";
            }
            html += "</table>";
        }

        response.getWriter().append(html);
    }
}