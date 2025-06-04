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

    // Constructor para crear desde BBDD
    public Cliente(Long id, String dni, String nombre, String apellido1, String apellido2, int edad, String email, String contrasena,
               Gimnasio gimnasio, String direccion, String telefono, ArrayList<Reserva> reservas) {
        super(dni, nombre, apellido1, apellido2, edad, email, contrasena);
        this.setId(id);
        this.setGimnasio(gimnasio);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setReservas(reservas);
        // Actualizar contadorId
        if (id >= contadorId) {
            Cliente.contadorId = id + 1;
        }
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

        // Elegir gimnasio
        this.setGimnasio(Gimnasio.elegirGimnasio());

        // Validar que se ha asignado un gimnasio
        if (this.getGimnasio() != null) {
            // Guardar el cliente en la base de datos
            Bbdd.guardarCliente(
                this.getNombre(),
                this.getEmail(),
                this.getTelefono(),
                this.getGimnasio().getId().intValue()
            );
            // Guardar cliente en la lista de clientes del gimnasio
            this.getGimnasio().getListaClientes().add(this);
            System.out.println("Cliente registrado correctamente: " + this.getNombre());
        } else {
            System.out.println("No se ha asignado un gimnasio al cliente.");
        }
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
