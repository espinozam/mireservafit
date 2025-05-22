import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/EntrenadorListServlet")
public class EntrenadorListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Actualiza si tienes contraseña

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json; charset=UTF-8");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id, nombre, especialidad FROM entrenador";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                JSONArray entrenadores = new JSONArray();

                while (rs.next()) {
                    JSONObject entrenador = new JSONObject();
                    entrenador.put("id", rs.getInt("id"));
                    entrenador.put("nombre", rs.getString("nombre"));
                    entrenador.put("especialidad", rs.getString("especialidad"));
                    entrenadores.put(entrenador);
                }

                PrintWriter out = response.getWriter();
                out.print(entrenadores.toString());
                out.flush();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"error\": \"Error al obtener la lista de entrenadores.\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Este endpoint solo acepta GET.");
    }
}