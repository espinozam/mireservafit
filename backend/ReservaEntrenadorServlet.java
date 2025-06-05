import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ReservaEntrenadorServlet")
public class ReservaEntrenadorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Permitir CORS si es necesario
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");

        // Obtener todas las reservas
        ArrayList<Reserva> reservas = Bbdd.obtenerReservas();

        // Filtrar reservas por email del entrenador
        ArrayList<Reserva> reservasEntrenador = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getEntrenador() != null && r.getEntrenador().getEmail().equalsIgnoreCase(email)) {
                reservasEntrenador.add(r);
            }
        }

        // Construir el HTML como un String (HTML puro, sin clases ni CSS)
        String html;
        if (reservasEntrenador.isEmpty()) {
            html = "<p>No tienes reservas asignadas.</p>";
        } else {
            html = "<table border='1'><tr><th>Fecha</th><th>Hora</th><th>Cliente</th><th>Duración</th></tr>";
            for (Reserva r : reservasEntrenador) {
                html += "<tr>";
                html += "<td>" + r.getFecha() + "</td>";
                html += "<td>" + r.getHora() + "</td>";
                html += "<td>" + (r.getCliente() != null ? r.getCliente().getNombre() : "") + "</td>";
                html += "<td>" + r.getDuracion() + " min</td>";
                html += "</tr>";
            }
            html += "</table>";
        }

        response.getWriter().append(html);
    }
}