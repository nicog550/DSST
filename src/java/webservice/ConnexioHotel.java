/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice;

import hotel1beans.AccessDB;
import hotel1sax.XMLLector;
import hotel1sax.XMLMaker;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author yomesmo
 */
@WebService(serviceName = "ConnexioHotel")
public class ConnexioHotel {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "existeixDisponibilitat")
    public String existeixDisponibilitat(@WebParam(name = "dataIni") String dataIni, @WebParam(name = "dataFi") String dataFi, @WebParam(name = "places") String places) {
        boolean disp = new AccessDB().hiHaDisponibilitat(dataIni, dataFi, places);
        return new XMLMaker().hiHaDisp(disp);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "crearReserva")
    public String crearReserva(@WebParam(name = "xml") String xml) {
        StringBuilder dataIni, dataFi;
        dataIni = new StringBuilder();
        dataFi = new StringBuilder();
        ArrayList[] res = new ArrayList[5];
        for (int i = 0; i < res.length; i++) {
            res[i] = new ArrayList<String>();
        }
        //Obtenim els valors des de l'XML
        new XMLLector().llistaHostes(xml, dataIni, dataFi, res);
        
        String[] noms, emails, dnis, nacionalitats;
        noms = emails = dnis = nacionalitats = null;
        noms = new String[res[0].size()]; emails = new String[res[0].size()]; dnis = new String[res[0].size()];
        nacionalitats = new String[res[0].size()]; int[] tipus = new int[res[0].size()];
        convertirValors(res, noms, emails, dnis, nacionalitats, tipus);
        //Cream la reserva
        boolean ret = new AccessDB().crearReserva(noms, emails, dnis, nacionalitats, tipus, dataIni.toString(), dataFi.toString());
        return new XMLMaker().crearReserva(ret);
    }
    
    /**
     * Converteix els elements de l'ArrayList a arrays del tipus corresponent
     * @param res L'ArrayList
     * @param noms
     * @param emails
     * @param dnis
     * @param nacionalitats
     * @param tipus 
     */
    private void convertirValors(ArrayList[] res, String[] noms, String[] emails, String[] dnis, String[] nacionalitats,
                                 int[] tipus) {
        for (int i = 0; i < res[0].size(); i++) {
            noms[i] = (String)res[0].get(i);
        }
        for (int i = 0; i < res[1].size(); i++) {
            emails[i] = (String)res[1].get(i);
        }
        for (int i = 0; i < res[2].size(); i++) {
            dnis[i] = (String)res[2].get(i);
        }
        for (int i = 0; i < res[3].size(); i++) {
            nacionalitats[i] = (String)res[3].get(i);
        }
        
        for (int i = 0; i < res[4].size(); i++) {
            tipus[i] = Integer.parseInt((String)res[4].get(i));
        }
    }
    
}
