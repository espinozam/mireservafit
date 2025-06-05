import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/EntrenadorIdServlet")
public class EntrenadorIdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain; charset=UTF-8");

        String email = request.getParameter("email");
        String id = "";

        try (Connection conn = Bbdd.conectar();
             Statement stmt = conn.createStatement()) {
                
            String sql = "SELECT id FROM entrenador WHERE email = '" + email.replace("'", "''") + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = String.valueOf(rs.getInt("id"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.getWriter().write(id);
    }
}