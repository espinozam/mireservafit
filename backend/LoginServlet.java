import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // CORS
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");

        String tipo = request.getParameter("tipo");
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        boolean valido = Bbdd.validarUsuario(tipo, usuario, password);

        response.setContentType("text/plain");
        response.getWriter().write(valido ? "ok" : "error");
    }
}