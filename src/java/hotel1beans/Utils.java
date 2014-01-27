/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel1beans;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Utils/* implements Serializable*/ {
    
    /**
     * Guarda a l'objecte <code>session</code> les dades de l'usuari
     * @param request
     * @param nom Nom de l'usuari
     * @param email Email de l'usuari
     * @param dni DNI de l'usuari
     * @param nac Nacionalitat de l'usuari
     * @param id ID de l'usuari
     * @param tipus Tipus d'usuari de l'usuari
     */
    public static void setSessio(HttpServletRequest request, String nom, String email, String dni, String nac, String id,
                                String tipus) {
        
        HttpSession sessio = request.getSession();
        sessio.setAttribute("nom", nom);
        sessio.setAttribute("nacionalitat", nac);
        sessio.setAttribute("dni", dni);
        sessio.setAttribute("tipus", tipus);
        sessio.setAttribute("id", id);
        sessio.setAttribute("email", email);
        /*try {
            response.sendRedirect("index.jsp");
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
