/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice;

import hotel1beans.AccessDB;
import hotel1sax.XMLMaker;
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
        String res = new XMLMaker().hiHaDisp(disp);
        res += "\n";
        return res;
    }
}
