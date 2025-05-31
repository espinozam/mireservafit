import java.sql.*;
import java.util.ArrayList;

public class Bbdd {

    private static final String URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    // Conexión centralizada
    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el Driver: " + error.getMessage());
        }
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException error) {
            System.out.println("Error al conectar: " + error.getMessage());
        }
        return conBD;
    }

    // Guardar cliente (ejemplo simple)
    public static void guardarCliente(String nombre, String email) {
        Statement stm = null;
        try {
            stm = conectar().createStatement();
            String sql = "INSERT INTO cliente (nombre, email) VALUES ('" + nombre + "', '" + email + "')";
            stm.executeUpdate(sql);
            System.out.println("Insertado: " + nombre);
        } catch (SQLException error) {
            System.out.println("Error SQL al guardar cliente: " + error.getMessage());
        }
    }

    // Descargar todos los clientes (devuelve lista de nombres)
    public static ArrayList<String> descargarClientes() {
        ArrayList<String> lista = new ArrayList<>();
        Statement stm = null;
        ResultSet rs = null;
        try {
            stm = conectar().createStatement();
            rs = stm.executeQuery("SELECT nombre FROM cliente");
            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }
        } catch (SQLException error) {
            System.out.println("Error SQL al descargar clientes: " + error.getMessage());
        }
        return lista;
    }

    // Eliminar cliente por id
    public static String eliminarCliente(int id) {
        String resultado = "Eliminado con éxito";
        Statement stm = null;
        try {
            stm = conectar().createStatement();
            String query = "DELETE FROM cliente WHERE id='" + id + "'";
            stm.executeUpdate(query);
        } catch (SQLException error) {
            resultado = "Error al intentar eliminar cliente: " + error.getMessage();
        }
        return resultado;
    }

}
