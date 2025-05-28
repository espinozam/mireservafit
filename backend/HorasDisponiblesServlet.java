import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/HorasDisponiblesServlet")
public class HorasDisponiblesServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// CORS
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<option value=''>Seleccione hora disponible</option>");

        String fecha = request.getParameter("fecha");
        String entrenadorIdStr = request.getParameter("entrenador_id");
        if (fecha == null || entrenadorIdStr == null) return;

        int entrenadorId;
        try { entrenadorId = Integer.parseInt(entrenadorIdStr); }
        catch (NumberFormatException e) { return; }

        // Convertir fecha a LocalDate y obtener el día de la semana
        // Coje la fecha en formato yyyy-MM-dd y determina el día de la semana
        java.time.LocalDate localDate = java.time.LocalDate.parse(fecha);
        String[] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
        // El día de la semana en LocalDate.getDayOfWeek() devuelve 1 para Lunes, 2 para Martes, etc.
        String diaSemana = dias[localDate.getDayOfWeek().getValue() - 1]; // Ejemplo: "Lunes" que conide con la tabla disponibilidad_entrenador

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            
            // Buscar disponibilidad para ese entrenador y día
            String sql = "SELECT hora_inicio, hora_fin FROM disponibilidad_entrenador WHERE entrenador_id=? AND dia_semana=?";
            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, entrenadorId);
                st.setString(2, diaSemana);
                ResultSet rs = st.executeQuery();

                if (!rs.next()) return; // No disponible ese día

                // Obtener horas de inicio y fin
                // Ejemplo: "10:00:00" a "18:00:00"
                java.time.LocalTime ini = rs.getTime("hora_inicio").toLocalTime();
                java.time.LocalTime fin = rs.getTime("hora_fin").toLocalTime();

                // Buscar horas ocupadas
                // Busca las horas reservadas para ese entrenador y fecha   
                ArrayList<String> ocupadas = new ArrayList<>();
                String sql2 = "SELECT hora FROM reserva WHERE fecha=? AND entrenador_id=?";
                try (PreparedStatement st2 = con.prepareStatement(sql2)) {
                    // Convertir fecha a formato adecuado
                    // Ejemplo: "2023-10-01"
                    st2.setString(1, fecha);
                    st2.setInt(2, entrenadorId);
                    ResultSet rs2 = st2.executeQuery();

                    // Recoger horas ocupadas
                    while (rs2.next()) {
                        // Añadir la hora ocupada a la lista ocupadas
                        // Ejemplo: "10:00:00" -> "10:00"
                        // substring(0,5) para obtener "HH:mm"
                        ocupadas.add(rs2.getString("hora").substring(0,5));
                    }
                }

                // Mostrar solo horas libres en bloques de 1h 
                int horaIni = rs.getTime("hora_inicio").toLocalTime().getHour();
                int horaFin = rs.getTime("hora_fin").toLocalTime().getHour();

                for (int h = horaIni; h < horaFin; h++) {
                    // Formatear la hora a "HH:00"
                    String horaStr = (h < 10 ? "0" : "") + h + ":00";
                    if (!ocupadas.contains(horaStr))
                        out.printf("<option value='%s'>%s</option>%n", horaStr, horaStr);
                }
            }
        } catch (Exception ignored) {}
    }
}