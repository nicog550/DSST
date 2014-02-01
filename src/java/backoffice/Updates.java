
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
import hotel1beans.BackofficeDB;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Efectua les eliminacions d'elements de la bbdd
 */
@WebServlet(name = "updates", urlPatterns = {"/updates"})
public class Updates extends HttpServlet {

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
            out.println("<title>Servlet Updates</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Updates at " + request.getContextPath() + "</h1>");
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
        
        String[] params;
        String taula = request.getParameter("taula");
        String id = request.getParameter("id");
        switch (taula) {
            case "usuari":
                params = new String[5];
                params[0] = request.getParameter("nom");
                params[1] = request.getParameter("email");
                params[2] = request.getParameter("nac");
                params[3] = request.getParameter("dni");
                params[4] = request.getParameter("tipus");
                break;
            case "reserva":
                params = new String[1];
                params[0] = request.getParameter("estat");
                break;
            case "import_habitacio":
                params = new String[1];
                params[0] = request.getParameter("preu");
                taula = "preu";
                break;
            default:
                params = new String[0];
                break;
        }
        //Posam el primer caràcter del nom de la taula en majúscules
        String taulaCamelCase = taula.substring(0, 1).toUpperCase() + taula.substring(1);
        Method metode;
        Class[] classes = {String[].class, String.class};
        String res;
        try {
            metode = BackofficeDB.class.getMethod("actualitzar" + taulaCamelCase, classes);
            res = Integer.toString((int)metode.invoke(new BackofficeDB(), params, id));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            res = ex.getClass() + ": " + ex.getMessage();
        }
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<files>" + res + "</files>");
    }
}
