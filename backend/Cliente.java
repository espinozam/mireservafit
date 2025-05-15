public class Cliente extends Persona {
	
    private Gimnasio gimnasio;
    private String email;
    private String telefono;

    // Constructor vacío
    public Cliente() {
        super();
    }

    // Constructor con todos los parámetros
    public Cliente(Long id, String nombre, Gimnasio gimnasio, String email, String telefono) {
        super(id, nombre);
        this.gimnasio = gimnasio;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y setters
    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
