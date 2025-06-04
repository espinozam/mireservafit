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

    // Guardar cliente
    public static void guardarCliente(String nombre, String email, String telefono, int gimnasioId) {
        Statement stm = null;
        Connection con = null;
        try {
            con = conectar();
            String sql = "INSERT INTO cliente (nombre, email, telefono, gimnasio_id) VALUES ('"
                    + nombre + "', '" + email + "', '" + telefono + "', " + gimnasioId + ")";
            stm = con.createStatement();
            stm.executeUpdate(sql);
            System.out.println("Insertado: " + nombre);
        } catch (SQLException error) {
            System.out.println("Error SQL al guardar cliente: " + error.getMessage());
        }
    }

    // Recuperar lista de clientes
    public static ArrayList<Cliente> obtenerClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = conectar();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id, dni, nombre, apellido1, apellido2, edad, email, contrasena, gimnasio_id, direccion, telefono FROM cliente");
            while (resultSet.next()) {
                // Recuperar el gimnasio asociado
                Gimnasio gimnasio = null;
                Long gimnasioId = resultSet.getLong("gimnasio_id");
                ArrayList<Gimnasio> listaGimnasios = obtenerGimnasios();
                for (Gimnasio g : listaGimnasios) {
                    if (g.getId().equals(gimnasioId)) {
                        gimnasio = g;
                        break;
                    }
                }
                Cliente cliente = new Cliente(
                    resultSet.getLong("id"),
                    resultSet.getString("dni"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido1"),
                    resultSet.getString("apellido2"),
                    resultSet.getInt("edad"),
                    resultSet.getString("email"),
                    resultSet.getString("contrasena"),
                    gimnasio,
                    resultSet.getString("direccion"),
                    resultSet.getString("telefono"),
                    new ArrayList<>()
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clientes: " + e.getMessage());
        }
        return listaClientes;
    }

    // Actualizar cliente
    public static String actualizarCliente(int id, String nombre, String email) {
        String resultado = "No se pudo actualizar el cliente.";
        String sql = "UPDATE cliente SET nombre = ?, email = ? WHERE id = ?";
        try (
            Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                resultado = "Cliente actualizado correctamente.";
            }
        } catch (SQLException e) {
            resultado = "Error al actualizar cliente: " + e.getMessage();
        }
        return resultado;
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

    
    // Recuperar lista de gimnasios
    public static ArrayList<Gimnasio> obtenerGimnasios() {
        ArrayList<Gimnasio> listaGimnasios = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = conectar();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id, nombre, direccion, telefono, email FROM gimnasio");
            while (resultSet.next()) {
                Gimnasio gimnasio = new Gimnasio(
                    resultSet.getLong("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("direccion"),
                    resultSet.getString("telefono"),
                    resultSet.getString("email"),
                    new ArrayList<>(), // listaClientes
                    new ArrayList<>(), // listaEntrenadores
                    new ArrayList<>()  // listaReservas
                );
                listaGimnasios.add(gimnasio);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener gimnasios: " + e.getMessage());
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception ignored) {}
            try { if (statement != null) statement.close(); } catch (Exception ignored) {}
            try { if (connection != null) connection.close(); } catch (Exception ignored) {}
        }
        return listaGimnasios;
    }

    // Recuperar lista de entrenadores
    public static ArrayList<Entrenador> obtenerEntrenadores() {
        ArrayList<Entrenador> listaEntrenadores = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = conectar();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id, dni, nombre, apellido1, apellido2, edad, email, contrasena, gimnasio_id, especialidad FROM entrenador");
            // Recuperar todos los gimnasios una sola vez
            ArrayList<Gimnasio> listaGimnasios = obtenerGimnasios();
            while (resultSet.next()) {
                // Buscar el gimnasio asociado
                Gimnasio gimnasio = null;
                Long gimnasioId = resultSet.getLong("gimnasio_id");
                for (Gimnasio g : listaGimnasios) {
                    if (g.getId().equals(gimnasioId)) {
                        gimnasio = g;
                        break;
                    }
                }
                Entrenador entrenador = new Entrenador(
                    resultSet.getLong("id"),
                    resultSet.getString("dni"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido1"),
                    resultSet.getString("apellido2"),
                    resultSet.getInt("edad"),
                    resultSet.getString("email"),
                    resultSet.getString("contrasena"),
                    gimnasio,
                    resultSet.getString("especialidad")
                );
                listaEntrenadores.add(entrenador);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener entrenadores: " + e.getMessage());
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception ignored) {}
            try { if (statement != null) statement.close(); } catch (Exception ignored) {}
            try { if (connection != null) connection.close(); } catch (Exception ignored) {}
        }
        return listaEntrenadores;
    }

    // Recuperar lista de reservas
    public static ArrayList<Reserva> obtenerReservas() {
        ArrayList<Reserva> listaReservas = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = conectar();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id, cliente_id, entrenador_id, fecha, hora, duracion FROM reserva");
            // Recuperar listas de clientes y entrenadores una sola vez
            ArrayList<Cliente> listaClientes = Bbdd.obtenerClientes();
            ArrayList<Entrenador> listaEntrenadores = obtenerEntrenadores();
            while (resultSet.next()) {
                Cliente cliente = null;
                Entrenador entrenador = null;
                Long clienteId = resultSet.getLong("cliente_id");
                Long entrenadorId = resultSet.getLong("entrenador_id");
                for (Cliente c : listaClientes) {
                    if (c.getId().equals(clienteId)) {
                        cliente = c;
                        break;
                    }
                }
                for (Entrenador e : listaEntrenadores) {
                    if (e.getId().equals(entrenadorId)) {
                        entrenador = e;
                        break;
                    }
                }
                Reserva reserva = new Reserva(
                    resultSet.getLong("id"),
                    cliente,
                    entrenador,
                    resultSet.getDate("fecha").toLocalDate(),
                    resultSet.getTime("hora").toLocalTime(),
                    resultSet.getInt("duracion")
                );
                listaReservas.add(reserva);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener reservas: " + e.getMessage());
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception ignored) {}
            try { if (statement != null) statement.close(); } catch (Exception ignored) {}
            try { if (connection != null) connection.close(); } catch (Exception ignored) {}
        }
        return listaReservas;
    }

    // Guardar reserva
    public static String guardarReserva(String fecha, String hora, int clienteId, int entrenadorId, int duracion) {
        String resultado = "No se pudo guardar la reserva";
        String sql = "INSERT INTO reserva (fecha, hora, cliente_id, entrenador_id, duracion) VALUES (?, ?, ?, ?, ?)";
        try (
            Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, fecha);
            stmt.setString(2, hora);
            stmt.setInt(3, clienteId);
            stmt.setInt(4, entrenadorId);
            stmt.setInt(5, duracion);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                resultado = "Reserva guardada correctamente";
            }
        } catch (SQLException e) {
            resultado = "Error al guardar reserva: " + e.getMessage();
        }
        return resultado;
    }

    // Obtener horas ocupadas por un entrenador en una fecha específica
    public static ArrayList<String> obtenerHorasOcupadas(int entrenadorId, String fecha) {
        ArrayList<String> ocupadas = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = conectar();
            st = con.createStatement();
            String sql = "SELECT hora FROM reserva WHERE fecha='" + fecha + "' AND entrenador_id=" + entrenadorId;
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ocupadas.add(rs.getString("hora").substring(0, 5));
            }
        } catch (Exception e) {
            System.out.println("Error en obtenerHorasOcupadas: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
        return ocupadas;
    }

    public static String[] obtenerRangoDisponibilidad(int entrenadorId, String diaSemana) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String[] rango = null;
        try {
            con = conectar();
            st = con.createStatement();
            String sql = "SELECT hora_inicio, hora_fin FROM disponibilidad_entrenador WHERE entrenador_id=" + entrenadorId +
                    " AND dia_semana='" + diaSemana + "'";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                rango = new String[] {
                    rs.getString("hora_inicio").substring(0,5),
                    rs.getString("hora_fin").substring(0,5)
                };
            }
        } catch (Exception e) {
            System.out.println("Error en obtenerRangoDisponibilidad: " + e.getMessage());
        }
        return rango;
    }
}
