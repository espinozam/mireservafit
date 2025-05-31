import java.util.ArrayList;
import java.util.Scanner;

public class Cliente extends Persona {

    private static Long contadorId = 1L; // Contador estático para IDs de Cliente

    private Gimnasio gimnasio;
    private String direccion;
    private String telefono;
    private ArrayList<Reserva> reservas;

    // Constructor vacío
    public Cliente() {
        super();
        this.setId(contadorId++);
        this.setGimnasio(null);
        this.setDireccion("");
        this.setTelefono("");
        this.setReservas(new ArrayList<>());
    }

    // Constructor con todos los parámetros
    public Cliente(String dni, String nombre, String apellido1, String apellido2, int edad, String email, String contrasena,
                   Gimnasio gimnasio, String direccion, String telefono, ArrayList<Reserva> reservas) {
        super(dni, nombre, apellido1, apellido2, edad, email, contrasena);
        this.setId(contadorId++);
        this.setGimnasio(gimnasio);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setReservas(reservas);
    }

    @Override
    public String toString() {
        return "Cliente [\n" +
                "  id=" + this.getId() + ",\n" +
                "  dni=" + this.getDni() + ",\n" +
                "  nombre=" + this.getNombre() + ",\n" +
                "  direccion=" + this.getDireccion() + ",\n" +
                "  telefono=" + this.getTelefono() + ",\n" +
                "  email=" + this.getEmail() + ",\n" +
                "  reservas=" + this.getReservas() + ",\n" +
                "  contraseña=" + this.contrasena + ",\n" +
                "  contraseña decodificada=" + this.getContrasena() + "\n" +
                "]";
    }

    // Getters y setters
    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
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

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Solicitar datos del cliente
    @Override
    public void solicitarDatosPersona() {
        super.solicitarDatosPersona();
        Scanner teclado = new Scanner(System.in);
        System.out.println("/////// Datos del cliente ///////");
        System.out.print("Dirección: ");
        this.setDireccion(teclado.nextLine());
        System.out.print("Teléfono: ");
        this.setTelefono(teclado.nextLine());
        // Aquí podrías pedir más datos si lo necesitas
    }

    // Implementación del método abstracto
    @Override
    public void cambiarContrasena() {
        Scanner teclado = new Scanner(System.in);
        String nueva;
        do {
            System.out.print("Introduce la nueva contraseña (mínimo 8 caracteres): ");
            nueva = teclado.nextLine();
            if (nueva.length() < 8) {
                System.out.println("La contraseña debe tener al menos 8 caracteres.");
            }
        } while (nueva.length() < 8);
        this.setContrasena(nueva);
        System.out.println("Contraseña cambiada correctamente!");
    }
}
