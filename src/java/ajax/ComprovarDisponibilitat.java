
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
 * Servirà per saber si hi ha disponibilitat segons les dates d'entrada i sortida i el número de places
 */
@WebServlet(name = "ComprovarDisponibilitat", urlPatterns = {"/ComprovarDisponibilitat"})
public class ComprovarDisponibilitat extends HttpServlet {

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
            out.println("<title>Servlet ComprovarDisponibilitat</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ComprovarDisponibilitat at " + request.getContextPath() + "</h1>");
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

        String dataIni = request.getParameter("dataIni");
        String dataFi = request.getParameter("dataFi");
        String places = request.getParameter("places");
        StringBuilder sb;

        if (dataIni == null || dataFi == null || places == null) {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        sb = new AccessDB().hiHaDisponibilitat(dataIni, dataFi, places) ? new StringBuilder("true") : new StringBuilder("false");

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<disponibilitat>" + sb.toString() + "</disponibilitat>");
    }

}
