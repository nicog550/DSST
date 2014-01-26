/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sailingsax;

/**
 *
 * @author mascport
 */
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;

public class XMLLector {

    public ArrayList[] listaUsuarios(String s) {
        ArrayList[] res = new ArrayList[2];
        res[0] = new ArrayList<String>();
        res[1] = new ArrayList<String>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            InputSource in = new InputSource(new StringReader(s));
            MeuHandler handler = new MeuHandler(res);
            saxParser.parse(in, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
