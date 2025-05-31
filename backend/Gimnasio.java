import java.util.ArrayList;

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

    // Constructor con todos los parámetros (id autogenerado)
    public Gimnasio(String nombre, String direccion, String telefono, String email,
                    ArrayList<Cliente> listaClientes, ArrayList<Entrenador> listaEntrenadores, ArrayList<Reserva> listaReservas) {
        this.id = contadorId++;
        this.setNombre(nombre);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setListaClientes(listaClientes);
        this.setListaEntrenadores(listaEntrenadores);
        this.setListaReservas(listaReservas);
    }

    // toString
    @Override
    public String toString() {
        return "Gimnasio [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono +
                ", email=" + email + ", listaClientes=" + listaClientes + ", listaEntrenadores=" + listaEntrenadores +
                ", listaReservas=" + listaReservas + "]";
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    // No se crea setId para evitar modificar el id autogenerado

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
}
