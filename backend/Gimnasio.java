import java.util.ArrayList;

public class Gimnasio {

	// Atributos
    private Long id;
    private String nombre;
    private String direccion;
    private ArrayList<Cliente> clientes;
    private ArrayList<Entrenador> entrenadores;
    private ArrayList<Reserva> reservas;

	static Long contador = 0L; // ID inicializado a 0

	// constructor vacío
	public Gimnasio() {
		this.setId(contador++);
		// Inicializamos los atributos con valores por defecto
		this.setDireccion("No definida");
		this.setNombre("nombre no definido");
		this.setClientes(new ArrayList<>());
		this.setEntrenadores(new ArrayList<>());
		this.setReservas(new ArrayList<>());
	}

	// constructor con parámetros
	public Gimnasio(Long id, String nombre, String direccion,
		ArrayList<Cliente> clientes, ArrayList<Entrenador> entrenadores,
		ArrayList<Reserva> reservas) {
		this.setId(id);
		this.setNombre(nombre);
		this.setDireccion(direccion);
		this.setClientes(clientes);
		this.setEntrenadores(entrenadores);
		this.setReservas(reservas);
	}
    
    // getters y setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public ArrayList<Cliente> getClientes() {
	    return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
	    this.clientes = clientes;
	}

	public ArrayList<Entrenador> getEntrenadores() {
	    return entrenadores;
	}

	public void setEntrenadores(ArrayList<Entrenador> entrenadores) {
	    this.entrenadores = entrenadores;
	}

	public ArrayList<Reserva> getReservas() {
	    return reservas;
	}

	public void setReservas(ArrayList<Reserva> reservas) {
	    this.reservas = reservas;
	}
    
}
