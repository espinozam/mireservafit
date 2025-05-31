import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private static Long contadorId = 1L; // Contador estático para autoincrementar IDs

    private Long id; // id reserva
    private Cliente cliente;
    private Entrenador entrenador;
    private LocalDate fecha;
    private LocalTime hora;

    // Constructor vacío
    public Reserva() {
        this.setId(contadorId++);
    }

    // Constructor con todos los parámetros
    public Reserva(Cliente cliente, Entrenador entrenador, LocalDate fecha, LocalTime hora) {
        this.setId(contadorId++);
        this.setCliente(cliente);
        this.setEntrenador(entrenador);
        this.setFecha(fecha);
        this.setHora(hora);
    }

    @Override
    public String toString() {
        return "Reserva [" +
                "id=" + this.getId() +
                ", cliente=" + (this.getCliente() != null ? this.getCliente().getNombre() : "null") +
                ", entrenador=" + (this.getEntrenador() != null ? this.getEntrenador().getNombre() : "null") +
                ", fecha=" + this.getFecha() +
                ", hora=" + this.getHora() +
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

}
