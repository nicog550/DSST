
package backoffice;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hotel1beans.AccessDB;
import hotel1beans.Utils;

/**
 * Efectua el login d'usuari i el guarda a l'objecte session
 */
@WebServlet(name = "backLogin", urlPatterns = {"/backLogin"})
public class Login extends HttpServlet {

    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("mail");
        String pass = request.getParameter("pass");
        
        //Els usuaris que no tenen contrasenya no han de poder loguejar-se
        if (!pass.equals("")) {
            String str = new AccessDB().login(email, pass);
            if (!str.equals("")) { //Si s'ha trobat un usuari, setejam la sessió i redirigim a usuaris.jsp
                String[] strArr = str.split("_");
                if (strArr[3].equals(AccessDB.TIPUS_ADMIN)) {
                    Utils.setSessio(request, strArr[0], email, strArr[2], strArr[1], strArr[4], strArr[3]);
                    response.sendRedirect("backoffice/reserves.jsp");
                } else { //Si no, recarregam
                    response.sendRedirect("home/home.jsp");
                }
            }
        }
    }
}
