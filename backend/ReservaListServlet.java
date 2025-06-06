import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ReservaListServlet")
public class ReservaListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static String toHtml(ArrayList<Reserva> lista) {
        String html = "";
        html += "<table border='1'>";
        html += "<tr><th>ID</th><th>Fecha</th><th>Hora</th><th>Cliente</th><th>Entrenador</th><th>Duración</th><th>Acciones</th></tr>";
        for (Reserva r : lista) {
            html += "<tr>";
            html += "<td>" + r.getId() + "</td>";
            html += "<td>" + r.getFecha() + "</td>";
            html += "<td>" + r.getHora() + "</td>";
            html += "<td>" + (r.getCliente() != null ? r.getCliente().getNombre() : "") + "</td>";
            html += "<td>" + (r.getEntrenador() != null ? r.getEntrenador().getNombre() : "") + "</td>";
            html += "<td>" + r.getDuracion() + " min</td>";
            html += "<td>";
            html += "<button onclick='editarReserva("
                + r.getId() + ", "
                + "\"" + r.getFecha() + "\", "
                + "\"" + r.getHora() + "\", "
                + (r.getCliente() != null ? r.getCliente().getId() : "''") + ", "
                + (r.getEntrenador() != null ? r.getEntrenador().getId() : "''")
                + ")'>Editar</button>";
            html += "<button onclick='eliminarReserva(" + r.getId() + ")'>Eliminar</button>";
            html += "</td>";
            html += "</tr>";
        }
        html += "</table>";
        return html;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Habilitar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String emailEntrenador = request.getParameter("emailEntrenador");

        ArrayList<Reserva> listaReservas = Bbdd.obtenerReservas();

        // Si se pasa el parámetro emailEntrenador, filtrar la lista
        if (emailEntrenador != null && !emailEntrenador.isEmpty()) {
            ArrayList<Reserva> filtradas = new ArrayList<>();
            for (Reserva r : listaReservas) {
                if (r.getEntrenador() != null && r.getEntrenador().getEmail().equalsIgnoreCase(emailEntrenador)) {
                    filtradas.add(r);
                }
            }
            listaReservas = filtradas;
        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().append(toHtml(listaReservas));
        System.out.println("Lista de reservas solicitada");
    }
}
