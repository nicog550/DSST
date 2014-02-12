/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import hotel1beans.AccessDB;
import hotel1sax.XMLLector;
import hotel1sax.XMLMaker;
import javax.jws.WebParam;

@WebService(serviceName = "bdhotel1")
public class BDHotel1 {

    /**
     * Web service operation
     * @return XML que indica si hi ha disponibilitat o no
     */
    @WebMethod(operationName = "hiHaDisp")
    public String listUsers() {
        boolean disp = new AccessDB().hiHaDisponibilitat("2014-06-01", "2014-06-03", "2");
        String res = new XMLMaker().hiHaDisp(disp);
        String cont = "-----------XML-------------\n";
        cont = cont + res + "\n";
        byte[] sort = cont.getBytes();
        return res;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "hihadisponibilitat")
    public byte[] hiHaDisponibilitat(@WebParam(name = "dataIni") String dataIni, @WebParam(name = "dataFi") String dataFi, @WebParam(name = "places") String places) {
        boolean disp = new AccessDB().hiHaDisponibilitat("2014-06-01", "2014-06-03", "2");
        String res = new XMLMaker().hiHaDisp(disp);
        String cont = "-----------XML-------------\n";
        cont = cont + res + "\n";
        System.out.println(cont);
        byte[] sort = cont.getBytes();
        return sort;
    }
    
    public static void main(String[] args) {
        
    }
    
    /*public String crearReserva() {
        String res = "NOP";
        File fic = null;
        FileOutputStream fos = null;
        try {
            String path = "SailingHotels";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            path = path + "/";
            fic = new File(path+"mevasortida.txt");
            fos = new FileOutputStream(fic);
            if (!fic.exists()) {
                fic.createNewFile();
            }
            ArrayList[] lista = new AccessDB().hiHaDisponibilitat("2014-06-01", "2014-06-03", "2");
            res = (new XMLMaker()).listaUsuarios(lista);
            String cont = "-----------XML-------------\n";
            cont = cont + res + "\n";
            byte[] sort = cont.getBytes();
            fos.write(sort);
            lista = (new XMLLector()).listaUsuarios(res);
            cont = "----------DADES------------\n";
            for (int i = 0; i < lista[0].size(); i++) {
                cont = cont + lista[0].get(i) + "," + lista[1].get(i) + "\n";
            }
            sort = cont.getBytes();
            fos.write(sort);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
               if (fos != null) {
                   fos.close();
               }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return res;
    }*/
}
