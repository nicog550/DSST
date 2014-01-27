
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
 * Efectua el login d'usuari i el guarda a l'objecte session
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        /*String action = request.getParameter("action");
        String targetId = request.getParameter("id");
        StringBuffer sb = new StringBuffer();

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
        } else {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        boolean namesAdded = false;
        if (action.equals("complete")) {

            // check if user sent empty string
            if (!targetId.equals("")) {

                Iterator it = composers.keySet().iterator();

                while (it.hasNext()) {
                    String id = (String) it.next();
                    Composer composer = (Composer) composers.get(id);

                    if ( // targetId matches first name
                            composer.getFirstName().toLowerCase().startsWith(targetId)
                            || // targetId matches last name
                            composer.getLastName().toLowerCase().startsWith(targetId)
                            || // targetId matches full name
                            composer.getFirstName().toLowerCase().concat(" ")
                            .concat(composer.getLastName().toLowerCase()).startsWith(targetId)) {

                        sb.append("<composer>");
                        sb.append("<id>" + composer.getId() + "</id>");
                        sb.append("<firstName>" + composer.getFirstName() + "</firstName>");
                        sb.append("<lastName>" + composer.getLastName() + "</lastName>");
                        sb.append("</composer>");
                        namesAdded = true;
                    }
                }
            }

            if (namesAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<composers>" + sb.toString() + "</composers>");
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        if (action.equals("lookup")) {

            // put the target composer in the request scope to display 
            if ((targetId != null) && composers.containsKey(targetId.trim())) {
                request.setAttribute("composer", composers.get(targetId));
                context.getRequestDispatcher("/composer.jsp").forward(request, response);
            }
        }*/
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
        
        String res = "";
        //Els usuaris que no tenen contrasenya no han de poder loguejar-se
        if (!pass.equals("")) {
            String str = new AccessDB().login(email, pass);
            if (!str.equals("")) { //Si s'ha trobat un usuari
                String[] strArr = str.split("_");
                res = ""
                    + "<nom>" + strArr[0] + "</nom>"
                    + "<nac>" + strArr[1] + "</nac>"
                    + "<dni>" + strArr[2] + "</dni>"
                    + "<tip>" + strArr[3] + "</tip>";
                Utils.setSessio(request, strArr[0], email, strArr[2], strArr[1], strArr[4], strArr[3]);

                HttpSession session = request.getSession();
                session.setAttribute("nom", strArr[0]);
                session.setAttribute("nacionalitat", strArr[1]);
                session.setAttribute("dni", strArr[2]);
                session.setAttribute("tipus", strArr[3]);
                session.setAttribute("id", strArr[4]);
                session.setAttribute("email", email);
            }
        }
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<resposta>" + res + "</resposta>");
    }
    
    /*
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dataIni = request.getParameter("dataIni");
        String dataFi = request.getParameter("dataFi");
        String places = request.getParameter("places");
        StringBuilder sb;

        if (dataIni == null || dataFi == null || places == null) {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

            boolean namesAdded = false;
            // check if user sent empty string
            if (!targetId.equals("")) {
            ArrayList[] dades = (new AccessDB()).listaMusicos(targetId);
            for (int i = 0; i < dades[0].size(); i++) {
            sb.append("<composer>");
            sb.append("<id>" + dades[0].get(i) + "</id>");
            sb.append("<firstName>" + dades[1].get(i) + "</firstName>");
            sb.append("<lastName>" + dades[2].get(i) + "</lastName>");
            sb.append("</composer>");
            namesAdded = true;
            }
            }
            sb = new AccessDB().hiHaDisponibilitat(dataIni, dataFi, places) ? new StringBuilder("true") : new StringBuilder("false");

                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<disponibilitat>" + sb.toString() + "</disponibilitat>");
                //nothing to show
                //response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
    */

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
