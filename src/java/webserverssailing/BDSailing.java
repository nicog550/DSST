/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserverssailing;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import hotel1beans.AccessDB;
import sailingsax.XMLLector;
import sailingsax.XMLMaker;

/**
 *
 * @author mascport
 */
@WebService(serviceName = "BDSailing")
public class BDSailing {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "listUsers")
    public String listUsers() {
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
            /*ArrayList[] lista = (new AccessDB()).listaUsuarios();
            res = (new XMLMaker()).listaUsuarios(lista);
            String cont = "-----------XML-------------\n";
            cont = cont + res + "\n";
            byte[] sort = cont.getBytes();
            fos.write(sort);
            lista = (new XMLLector()).listaUsuarios(res);
            cont = "----------DADES------------\n";
            for (int i = 0; i < lista[0].size(); i++) {
                cont = cont + lista[0].get(i) + ","
                        + lista[1].get(i) + "\n";
            }
            sort = cont.getBytes();
            fos.write(sort);*/
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
    }
}
