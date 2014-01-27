
package ajax;

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
import javax.servlet.http.HttpSession;

/**
 * Efectua el registre d'usuari. Després el logueja i el guarda a l'objecte session
 */
@WebServlet(name = "registre", urlPatterns = {"/registre"})
public class Registre extends HttpServlet {

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
            out.println("<title>Servlet Registre</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registre at " + request.getContextPath() + "</h1>");
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

        String nom = request.getParameter("nom");
        String email = request.getParameter("mail");
        String pass = request.getParameter("pass");
        String dni = request.getParameter("dni");
        String nac = request.getParameter("nac");
        
        String res = "<estat>ko</estat>";
        String idUsu = new AccessDB().registrar(nom, email, nac, dni, pass, AccessDB.TIPUS_ADULT);
        if (!idUsu.equals("")) { //Si s'ha creat correctament l'usuari
            res = "<estat>ok</estat><tipus>" + AccessDB.TIPUS_ADULT + "</tipus>";
            Utils.setSessio(request, nom, email, dni, nac, idUsu, AccessDB.TIPUS_ADULT);
        }
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<resposta>" + res + "</resposta>");
    }
    

}
