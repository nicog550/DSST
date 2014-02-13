/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel1sax;

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

    public ArrayList[] llistaHostes(String s, StringBuilder dataIni, StringBuilder dataFi, ArrayList[] res) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            InputSource in = new InputSource(new StringReader(s));
            MeuHandler handler = new MeuHandler(res, dataIni, dataFi);
            saxParser.parse(in, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
