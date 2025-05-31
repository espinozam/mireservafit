import java.util.Scanner;

public class Entrenador extends Persona {

    private static Long contadorId = 1L; // Contador estático para IDs de entrenador

    private Gimnasio gimnasio;
    private String especialidad;

    // Constructor vacío
    public Entrenador() {
        super();
        this.setId(contadorId++);
        this.setGimnasio(null);
        this.setEspecialidad("");
    }

    // Constructor con todos los parámetros
    public Entrenador(String dni, String nombre, String apellido1, String apellido2, int edad, String email, String contrasena,
                      Gimnasio gimnasio, String especialidad) {
        super(dni, nombre, apellido1, apellido2, edad, email, contrasena);
        this.setId(contadorId++);
        this.setGimnasio(gimnasio);
        this.setEspecialidad(especialidad);
    }

    @Override
    public String toString() {
        return "Entrenador [\n" +
                "  id=" + this.getId() + ",\n" +
                "  dni=" + this.getDni() + ",\n" +
                "  nombre=" + this.getNombre() + ",\n" +
                "  especialidad=" + this.getEspecialidad() + ",\n" +
                "  gimnasio=" + (this.getGimnasio() != null ? this.getGimnasio().getNombre() : "Sin gimnasio") + ",\n" +
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Solicitar datos del entrenador
    @Override
    public void solicitarDatosPersona() {
        super.solicitarDatosPersona();
        Scanner teclado = new Scanner(System.in);
        System.out.println("/////// Datos del entrenador ///////");
        System.out.print("Especialidad: ");
        this.setEspecialidad(teclado.nextLine());
        // Aquí podrías pedir el gimnasio si lo deseas
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
        System.out.println("¡Contraseña cambiada correctamente!");
    }
}
