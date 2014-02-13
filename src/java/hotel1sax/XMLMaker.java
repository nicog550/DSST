/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel1sax;

public class XMLMaker {

    public String hiHaDisp(boolean disp) {
        String res = disp ? "true" : "false";
        res = "<disponibilitat>" + res + "</disponibilitat>\n";
        res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + res;
        return res;
    }

    public String crearReserva(boolean creada) {
        String res = creada ? "true" : "false";
        res = "<reserva>" + res + "</reserva>\n";
        res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + res;
        return res;
    }
}
