import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Gimnasio> listaGimnasios = new ArrayList<>();
        ArrayList<Entrenador> listaEntrenadores = new ArrayList<>();
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        ArrayList<Reserva> listaReservas = new ArrayList<>();
        
        // Datos de prueba
        // descargar gimnasios de la base de datos
        listaGimnasios = Bbdd.obtenerGimnasios();
        // descargar clientes de la base de datos
        listaClientes = Bbdd.obtenerClientes();
        // descargar entrenadores de la base de datos
        listaEntrenadores = Bbdd.obtenerEntrenadores();
        // descargar reservas de la base de datos
        listaReservas = Bbdd.obtenerReservas();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n----- MiReservaFit -----");
            System.out.println("1. Registrarse como Cliente");
            System.out.println("2. Registrarse como Entrenador");
            System.out.println("3. Ver lista de personas");
            System.out.println("4. Ver lista de gimnasios");
            System.out.println("9. Salir");
            System.out.print("Elige una opción: ");
            String opcion = teclado.nextLine();

            if (opcion.equals("1")) {
                Cliente nuevoCliente = new Cliente();
                nuevoCliente.solicitarDatosPersona();
                listaClientes.add(nuevoCliente);

            } else if (opcion.equals("2")) {
                Entrenador nuevoEntrenador = new Entrenador();
                nuevoEntrenador.solicitarDatosPersona();
                // Selección de gimnasio
                System.out.println("Elige tu gimnasio:");
                for (int i = 0; i < listaGimnasios.size(); i++) {
                    System.out.println((i + 1) + ". " + listaGimnasios.get(i).getNombre());
                }
                int gymIndexE = teclado.nextInt() - 1;
                teclado.nextLine();
                if (gymIndexE >= 0 && gymIndexE < listaGimnasios.size()) {
                    nuevoEntrenador.setGimnasio(listaGimnasios.get(gymIndexE));
                }
                listaEntrenadores.add(nuevoEntrenador);
                System.out.println("Entrenador registrado correctamente.");
            } else if (opcion.equals("3")) {
                System.out.println("----- Lista de clientes -----");
                for (Cliente c : listaClientes) {
                    System.out.println(c);
                }
                System.out.println("----- Lista de entrenadores -----");
                for (Entrenador e : listaEntrenadores) {
                    System.out.println(e);
                }
            } else if (opcion.equals("4")) {
                System.out.println("----- Lista de gimnasios -----");
                for (Gimnasio g : listaGimnasios) {
                    System.out.println(g);
                }
            } else if (opcion.equals("9")) {
                salir = true;
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opción incorrecta, intenta de nuevo.");
            }
        }
        teclado.close();
    }
}
