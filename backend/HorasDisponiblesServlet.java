import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

@WebServlet("/HorasDisponiblesServlet")
public class HorasDisponiblesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // CORS
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");

        String html = "<option value=''>Seleccione hora disponible</option>";

        String fecha = request.getParameter("fecha");
        String entrenadorIdStr = request.getParameter("entrenador_id");
        int entrenadorId = Integer.parseInt(entrenadorIdStr);

        LocalDate localDate = LocalDate.parse(fecha);
        String[] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
        String diaSemana = dias[localDate.getDayOfWeek().getValue() - 1];

        // Obtener rango de disponibilidad
        // Ejemplo: ["09:00", "18:00"]
        String[] rango = Bbdd.obtenerRangoDisponibilidad(entrenadorId, diaSemana);

        if (rango == null) {
            response.getWriter().write(html); // Devuelve solo la opción por defecto
            return;
        }

        int horaIni = Integer.parseInt(rango[0].substring(0,2));
        int horaFin = Integer.parseInt(rango[1].substring(0,2));

        // Obtener horas ocupadas
        // Ejemplo: ["09:00", "10:00", "11:00"]
        ArrayList<String> ocupadas = Bbdd.obtenerHorasOcupadas(entrenadorId, fecha);

        for (int hora = horaIni; hora < horaFin; hora++) {
            // Formatear la hora a "HH:00"
            String horaFormateada = (hora < 10 ? "0" : "") + hora + ":00";

            if (ocupadas.contains(horaFormateada)) {
                html += "<option value='" + horaFormateada + "' disabled>" + horaFormateada + " (ocupada)</option>";
            } else {
                html += "<option value='" + horaFormateada + "'>" + horaFormateada + "</option>";
            }
        }

        response.getWriter().write(html);
    }
}