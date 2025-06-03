import java.util.ArrayList;
import java.util.Scanner;

public class Gimnasio {
    // Atributos
    private static Long contadorId = 1L; // Id autogenerado

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Entrenador> listaEntrenadores;
    private ArrayList<Reserva> listaReservas;

    // Constructor vacío
    public Gimnasio() {
        this.id = contadorId++;
        this.setNombre("Sin nombre");
        this.setDireccion("Sin dirección");
        this.setTelefono("");
        this.setEmail("");
        this.setListaClientes(new ArrayList<>());
        this.setListaEntrenadores(new ArrayList<>());
        this.setListaReservas(new ArrayList<>());
    }

    // Constructor para crear desde BBDD
    public Gimnasio(Long id, String nombre, String direccion, String telefono, String email,
                    ArrayList<Cliente> listaClientes, ArrayList<Entrenador> listaEntrenadores, ArrayList<Reserva> listaReservas) {
        this.setId(id);
        this.setNombre(nombre);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setListaClientes(listaClientes);
        this.setListaEntrenadores(listaEntrenadores);
        this.setListaReservas(listaReservas);
        // Actualizar contadorId si es necesario
        if (id >= contadorId) {
            Gimnasio.contadorId = id + 1;
        }
    }

    // toString
    @Override
    public String toString() {
        return "Gimnasio [\n"
                + "  id=" + this.id + ",\n"
                + "  nombre=" + this.nombre + ",\n"
                + "  direccion=" + this.direccion + ",\n"
                + "  telefono=" + this.telefono + ",\n"
                + "  email=" + this.email + ",\n"
                + "  listaClientes=" + this.listaClientes + ",\n"
                + "  listaEntrenadores=" + this.listaEntrenadores + ",\n"
                + "  listaReservas=" + this.listaReservas + "\n"
                + "]";
    }

    // 
    public static void cargarClientes(ArrayList<Cliente> listaClientes) {
        listaClientes.addAll(Bbdd.obtenerClientes());
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    // No se crea setId para evitar modificar el id autogenerado
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        // Comprobar que nombre empieza por mayúscula
        if (nombre != null && !nombre.isEmpty() && Character.isUpperCase(nombre.charAt(0))) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre debe empezar por mayúscula.");
            this.nombre = "Sin nombre";
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ArrayList<Entrenador> getListaEntrenadores() {
        return listaEntrenadores;
    }

    public void setListaEntrenadores(ArrayList<Entrenador> listaEntrenadores) {
        this.listaEntrenadores = listaEntrenadores;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(ArrayList<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    // Métodos de utilidad
    public void addCliente(Cliente cliente) {
        this.listaClientes.add(cliente);
    }

    public void addEntrenador(Entrenador entrenador) {
        this.listaEntrenadores.add(entrenador);
    }

    public void addReserva(Reserva reserva) {
        this.listaReservas.add(reserva);
    }

    // Mostrar listas
    public void mostrarListaClientes() {
        System.out.println(this.listaClientes);
    }

    public void mostrarListaEntrenadores() {
        System.out.println(this.listaEntrenadores);
    }

    public void mostrarListaReservas() {
        System.out.println(this.listaReservas);
    }

    // Método estático para mostrar lista de clientes desde la base de datos
    public static void mostrarClientesBD() {
        ArrayList<Cliente> listaClientes = Bbdd.obtenerClientes();
        System.out.println("=== Lista de clientes en la base de datos ===");
        for (Cliente cliente : listaClientes) {
            System.out.println(cliente);
        }
    }

    public static Gimnasio elegirGimnasio() {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Gimnasio> listaGimnasios = Bbdd.obtenerGimnasios();
        if (listaGimnasios.isEmpty()) {
            System.out.println("No hay gimnasios disponibles.");
            return null;
        }
        System.out.println("Elige tu gimnasio:");
        for (int i = 0; i < listaGimnasios.size(); i++) {
            System.out.println((i + 1) + ". " + listaGimnasios.get(i).getNombre());
        }
        int gymIndex = teclado.nextInt() - 1;
        teclado.nextLine(); // Limpiar el buffer
        if (gymIndex >= 0 && gymIndex < listaGimnasios.size()) {
            return listaGimnasios.get(gymIndex);
        } else {
            System.out.println("Índice de gimnasio no válido. No se asignará ningún gimnasio.");
            return null;
        }
    }
}
