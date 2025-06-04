import java.util.Random;
import java.util.Scanner;

public abstract class Persona {
    // Atributos
    protected Long id;
    protected String dni;
    protected String nombre;
    protected String apellido1;
    protected String apellido2;
    protected int edad;
    protected String email;
    protected String contrasena;

    // Letras para codificación/decodificación usando el mentodo César
    private static final String LETTERS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    // Constructor vacío
    // El id se asigna en la subclase
    public Persona() {
        this.setDni("");
        this.setNombre("Sin nombre");
        this.setApellido1("Sin apellido");
        this.setApellido2("Sin apellido");
        this.setEdad(0);
        this.setEmail("");
        //this.setContrasena(""); // No se establece contraseña por defecto
    }

    // Constructor para recuperar datos de la base de datos
    public Persona(String dni, String nombre, String apellido1, String apellido2, int edad, String email, String contrasenaCodificada) {
        // El id se asigna en la subclase
        this.setDni(dni);
        this.setNombre(nombre);
        this.setApellido1(apellido1);
        this.setApellido2(apellido2);
        this.setEdad(edad);
        this.setEmail(email);
        // No codifica de nuevo la contraseña, ya que ya está codificada
        this.setContrasenaCodificada(contrasenaCodificada);
    }

    // toString
    @Override
    public String toString() {
        return "Persona [\n"
                + "  id=" + id + ",\n"
                + "  dni=" + dni + ",\n"
                + "  nombre=" + nombre + ",\n"
                + "  apellido1=" + apellido1 + ",\n"
                + "  apellido2=" + apellido2 + ",\n"
                + "  edad=" + edad + ",\n"
                + "  email=" + email + "\n"
                + "]";
    }

    // Método para solicitar datos de la persona
    public void solicitarDatosPersona() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("/////// Datos básicos de la persona ///////");
        System.out.print("DNI: ");
        this.setDni(teclado.nextLine());
        System.out.print("Nombre: ");
        this.setNombre(teclado.nextLine());
        System.out.print("Primer apellido: ");
        this.setApellido1(teclado.nextLine());
        System.out.print("Segundo apellido: ");
        this.setApellido2(teclado.nextLine());
        System.out.print("Edad: ");
        this.setEdad(teclado.nextInt());
        teclado.nextLine(); // limpiar buffer
        System.out.print("Email: ");
        this.setEmail(teclado.nextLine());
        System.out.print("Contraseña (mínimo 8 caracteres): ");
        this.setContrasena(teclado.nextLine());
    }

    // Método abstracto para cambiar contraseña
    public abstract void cambiarContrasena();

    // Cifrado César para codificar y decodificar la contraseña

    /*
     * Cifrado César: cada letra se sustituye por otra un número fijo de posiciones adelante en el alfabeto.
     * El desplazamiento (offset) se obtiene de la posición de la primera letra (minúscula) del texto.
     * Ejemplo: "aHOLA" (offset 1) se codifica como "aIPMB".
     */

    // Codifica el texto usando un offset aleatorio (la primera letra codificada será la del offset)
    private String codificar(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        // Generar offset aleatorio entre 1 y LETTERS.length() - 1 (evita 0)
        int offset = new Random().nextInt(LETTERS.length() - 1) + 1;
        String textoCodificado = "" + LETTERS.charAt(offset); // Primer carácter es el offset codificado
        for (int i = 0; i < texto.length(); i++) {
            char letter = Character.toUpperCase(texto.charAt(i));
            int index = LETTERS.indexOf(letter);
            if (index != -1) {
                int codedIndex = (index + offset) % LETTERS.length();
                if (Character.isLowerCase(texto.charAt(i))) {
                    textoCodificado += Character.toLowerCase(LETTERS.charAt(codedIndex));
                } else {
                    textoCodificado += LETTERS.charAt(codedIndex);
                }
            } else {
                textoCodificado += texto.charAt(i);
            }
        }
        return textoCodificado;
    }

    // Decodifica el texto usando el offset guardado en el primer carácter
    private String decodificar(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        // El offset se obtiene de la primera letra del texto codificado
        int offset = getOffset(Character.toUpperCase(texto.charAt(0)));
        String textoDecodificado = "";
        for (int i = 1; i < texto.length(); i++) {
            char letter = Character.toUpperCase(texto.charAt(i));
            int index = LETTERS.indexOf(letter);
            if (index != -1) {
                // Decodificar usando el offset
                // Si el offset es 1, la letra 'A' se convierte en 'Z', etc.
                int decodedIndex = (index - offset) % LETTERS.length(); // Asegura que el índice no se salga del rango
                if (decodedIndex < 0) decodedIndex += LETTERS.length();
                
                char decodedChar = LETTERS.charAt(decodedIndex);
                // Mantener mayúsculas/minúsculas originales
                if (Character.isLowerCase(texto.charAt(i))) {
                    textoDecodificado += Character.toLowerCase(decodedChar);
                } else {
                    textoDecodificado += decodedChar;
                }
            } else {
                textoDecodificado += texto.charAt(i);
            }
        }
        return textoDecodificado;
    }

    // Devuelve la posición en el alfabeto de la primera letra si es minúscula, o 0 si no lo es.
    // Ejemplo: getOffset("dHola Miquel") = 4, getOffset("DHola Miquel") = 0
    private int getOffset(char letter) {
        int offset = LETTERS.indexOf(letter);
        if (offset <= 0) { // Si es 0 o no está, usa offset 1 (mínimo desplazamiento)
            return 1;
        }
        return offset;
    }

    // En setContrasena, solo pasa la contraseña del usuario (sin preocuparse del offset)
    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 8) {
            this.contrasena = null;
        } else {
            this.contrasena = codificar(contrasena);
        }
    }

    // Asignar la contraseña ya codificada desde la base de datos
    public void setContrasenaCodificada(String contrasenaCodificada) {
        this.contrasena = contrasenaCodificada;
    }

    // Devuelve la contraseña original decodificada (para pruebas)
    public String getContrasena() {
        return decodificar(this.contrasena);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido1() {
        return apellido1;
    }
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
