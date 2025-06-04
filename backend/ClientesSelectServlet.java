import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/ClientesSelectServlet")
public class ClientesSelectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mireservafit_db", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nombre FROM cliente")) {
            PrintWriter out = response.getWriter();
            while (rs.next()) {
                out.printf("<option value='%d'>%s</option>%n", rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}