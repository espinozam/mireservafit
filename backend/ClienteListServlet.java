import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/ClienteListServlet")
public class ClienteListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mireservafit_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static String toHtml(ArrayList<Cliente> lista) {
        String html = "";
        html += "<button onclick=\"mostrarFormularioAgregar()\">Añadir Cliente</button><br><br>";
        html += "<table border='1'>";
        html += "<tr><th>ID</th><th>Nombre</th><th>Email</th><th>Acciones</th></tr>";
        for (Cliente c : lista) {
            html += "<tr>";
            html += "<td>" + c.getId() + "</td>";
            html += "<td>" + c.getNombre() + "</td>";
            html += "<td>" + c.getEmail() + "</td>";
            html += "<td>";
            html += "<button onclick='editarCliente(" + c.getId() + ", \"" + c.getNombre() + "\", \"" + c.getEmail() + "\")'>Editar</button>";
            html += "<button onclick='eliminarCliente(" + c.getId() + ")'>Eliminar</button>";
            html += "</td>";
            html += "</tr>";
        }
        html += "</table>";
        return html;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Habilitar CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        ArrayList<Cliente> listaClientes = new ArrayList<>();
        Gimnasio.cargarClientes(listaClientes);
        
        response.getWriter().append(toHtml(listaClientes));
        System.out.println("Lista de clientes solicitada");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
