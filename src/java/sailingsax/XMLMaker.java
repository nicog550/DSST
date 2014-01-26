/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sailingsax;

import java.util.ArrayList;

/**
 *
 * @author mascport
 */
public class XMLMaker {

    static final String usustag = "usuarios";
    static final String usutag = "usuario";
    static final String nomtag = "nombre";
    static final String clatag = "clave";

    public String listaUsuarios(ArrayList[] dep) {
        String res = "";
        res = res + "<" + usustag + ">" + "\n";
        for (int i = 0; i < dep[0].size(); i++) {
            res = res + "    <" + usutag + ">" + "\n";
            res = res + "        <" + nomtag + ">" + "\n";
            res = res + "            " + dep[0].get(i);
            res = res + "        </" + nomtag + ">" + "\n";
            res = res + "        <" + clatag + ">" + "\n";
            res = res + "            " + dep[1].get(i);
            res = res + "        </" + clatag + ">" + "\n";
            res = res + "    </" + usutag + ">" + "\n";
        }
        res = res + "</" + usustag + ">" + "\n";
        res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + res;
        return res;
    }

    public ArrayList[] listaUsuarios(String s) {
        ArrayList [] res = new ArrayList[2];
        res[0] = new ArrayList <String> ();
        res[1] = new ArrayList <String> ();
        return res;
    }
}
