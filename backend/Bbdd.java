import java.sql.*;

public class Bbdd {

    private static final String URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static void guardar(String nom) {
        try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
             Statement stmt = con.createStatement()) {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "INSERT INTO cliente (nombre, email) VALUES ('" + nom + "', 'correo@ejemplo.com')";
            stmt.executeUpdate(sql);
            System.out.println("Insertado: " + nom);

        } catch (ClassNotFoundException e) {
            System.out.println("Error cargando el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
    }

    public static String descargarClientes() {
        StringBuilder resultado = new StringBuilder();

        try (Connection con = DriverManager.getConnection(URL, USUARIO, CLAVE);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre FROM cliente")) {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            while (rs.next()) {
                resultado.append(rs.getString("nombre")).append("<br>");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error cargando el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }

        return resultado.toString();
    }
}
