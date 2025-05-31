import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Gimnasio> listaGimnasios = new ArrayList<>();
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        ArrayList<Reserva> listaReservas = new ArrayList<>();
        boolean salir = false;

        // Datos de prueba
        Gimnasio g1 = new Gimnasio(
            "GymFit Palma",
            "Calle Mayor 1",
            "971111111",
            "gymfit@palma.com",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        Gimnasio g2 = new Gimnasio(
            "StrongLife",
            "Avenida Salud 22",
            "971222222",
            "contacto@stronglife.com",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        listaGimnasios.add(g1);
        listaGimnasios.add(g2);

        Cliente c1 = new Cliente(
            "12345678A",
            "Edwin",
            "Espinoza",
            "Mercado",
            30,
            "edwin@mail.com",
            "aContraseña1",
            g1,
            "Calle 13",
            "666111222",
            new ArrayList<>()
        );
        listaPersonas.add(c1);

        Entrenador e1 = new Entrenador(
            "87654321B",
            "Laura",
            "García",
            "López",
            28,
            "laura@mail.com",
            "lPassword2",
            g2,
            "Crossfit"
        );
        listaPersonas.add(e1);

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
                // Selección de gimnasio
                System.out.println("Elige tu gimnasio:");
                for (int i = 0; i < listaGimnasios.size(); i++) {
                    System.out.println((i + 1) + ". " + listaGimnasios.get(i).getNombre());
                }
                int gymIndex = teclado.nextInt() - 1;
                teclado.nextLine();
                if (gymIndex >= 0 && gymIndex < listaGimnasios.size()) {
                    nuevoCliente.setGimnasio(listaGimnasios.get(gymIndex));
                }
                listaPersonas.add(nuevoCliente);
                System.out.println("Cliente registrado correctamente.");
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
                listaPersonas.add(nuevoEntrenador);
                System.out.println("Entrenador registrado correctamente.");
            } else if (opcion.equals("3")) {
                System.out.println("----- Lista de personas -----");
                for (Persona p : listaPersonas) {
                    System.out.println(p);
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
