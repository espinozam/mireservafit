import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Reserva {

    private Long id; // id reserva
    private Cliente cliente;
    private Entrenador entrenador;
    private LocalDate fecha;
    private LocalTime hora;

    // Constructor vacío
    public Reserva() {}

    // Constructor con todos los parámetros
    public Reserva(Cliente cliente, Entrenador entrenador, LocalDate fecha, LocalTime hora) {
        this.cliente = cliente;
        this.entrenador = entrenador;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
