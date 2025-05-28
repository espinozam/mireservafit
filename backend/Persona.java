
public abstract class Persona {
	
    protected Long id;
    protected String nombre;

    // Constructor vacío
    public Persona() {}

    // Constructor con todos los parámetros
    public Persona(Long id, String nombre) {
        this.setId(id);
        this.setNombre(nombre);
    }

    // Getters y setters
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
    
}
