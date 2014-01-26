/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel1beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mascport
 */
public class Utils implements Serializable {

    public void entradaUsuarioClave(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {
        String us = (String) request.getParameter("usuario");
        String cl = (String) request.getParameter("clave");
        /*int nivell = (new AccessDB()).compruebaClave(us, cl);
        if (nivell > 0) {
            session.setAttribute("usuario", us);
            session.setAttribute("clave", cl);
            session.setAttribute("nivel", new Integer(nivell));
        }
        try {
            response.sendRedirect("index.jsp");
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
