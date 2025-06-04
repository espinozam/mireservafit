import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private static Long contadorId = 1L; // Contador estático para autoincrementar IDs

    private Long id; // id reserva
    private Cliente cliente;
    private Entrenador entrenador;
    private LocalDate fecha;
    private LocalTime hora;
    private int duracion; // Duración en minutos

    // Constructor vacío
    public Reserva() {
        this.setId(contadorId++);
    }

    // Constructor para crear desde BBDD
    public Reserva(Long id, Cliente cliente, Entrenador entrenador, LocalDate fecha, LocalTime hora, int duracion) {
        this.setId(id);
        this.setCliente(cliente);
        this.setEntrenador(entrenador);
        this.setFecha(fecha);
        this.setHora(hora);
        this.setDuracion(duracion);
    }

    @Override
    public String toString() {
        return "Reserva [" +
                "id=" + this.getId() +
                ", cliente=" + (this.getCliente() != null ? this.getCliente().getNombre() : "null") +
                ", entrenador=" + (this.getEntrenador() != null ? this.getEntrenador().getNombre() : "null") +
                ", fecha=" + this.getFecha() +
                ", hora=" + this.getHora() +
                ", duracion=" + this.getDuracion() +
                "]";
    }

    // Getters y setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Entrenador getEntrenador() {
        return this.entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return this.hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    // Getter y setter para duracion
    public int getDuracion() {
        return this.duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

}
