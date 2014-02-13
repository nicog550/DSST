
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

/**
 * Reservarà una sèrie d'habitacions pels usuaris donats
 */
@WebServlet(name = "CrearReserva", urlPatterns = {"/CrearReserva"})
public class CrearReserva extends HttpServlet {

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
            out.println("<title>Servlet CrearReserva</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearReserva at " + request.getContextPath() + "</h1>");
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

        final int places = Integer.parseInt(request.getParameter("placesVal"));
        String[] noms = new String[places];
        String[] emails = new String[places];
        String[] dnis = new String[places];
        String[] nacionalitats = new String[places];
        int[] tipus = new int[places];
        String dataIni = request.getParameter("dataIni");
        String dataFi = request.getParameter("dataFi");
        
        //Guardam tots els paràmetres ordenadament
        for (int i = 0; i < places; i++) {
            noms[i] = request.getParameter("nom-" + i);
            emails[i] = request.getParameter("mail-" + i);
            dnis[i] = request.getParameter("dni-" + i);
            nacionalitats[i] = request.getParameter("nac-" + i);
            tipus[i] = Integer.parseInt(request.getParameter("tip-" + i));
        }
        
        if (new AccessDB().crearReserva(noms, emails, dnis, nacionalitats, tipus, dataIni, dataFi)) {
            request.getSession().setAttribute("preu", request.getParameter("preuFinal"));
            response.sendRedirect("reservaOk/reservaOk.jsp");
        } else {
            request.getSession().setAttribute("error", "S'ha produit un error creant la reserva");
            response.sendRedirect("error/error.jsp");
        }

    }

}
