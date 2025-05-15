
import java.util.ArrayList;

public class Gimnasio {
    private Long id;
    private String nombre;
    private String direccion;
    
    private ArrayList<Cliente> clientes;
    private ArrayList<Entrenador> entrenadores;
    private ArrayList<Reserva> reservas;
    
    public Gimnasio() {}

    public Gimnasio(Long id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
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
    
}
