public class Entrenador extends Persona {

    private Gimnasio gimnasio;
    private String especialidad;

    // Constructor vacío
    public Entrenador() {
        super();
    }

    // Constructor con todos los parámetros
    public Entrenador(Long id, String nombre, Gimnasio gimnasio, String especialidad) {
        super(id, nombre);
        this.gimnasio = gimnasio;
        this.especialidad = especialidad;
    }

    // Getters y setters
    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
