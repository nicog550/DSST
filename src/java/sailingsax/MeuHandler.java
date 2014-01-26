/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sailingsax;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author mascport
 */
public class MeuHandler extends DefaultHandler {

    private ArrayList[] mat = null;
    private boolean biusus = false;
    private boolean bfusus = false;
    private boolean biusu = false;
    private boolean bfusu = false;
    private boolean binom = false;
    private boolean bfnom = false;
    private boolean biclau = false;
    private boolean bfclau = false;

    public MeuHandler(ArrayList[] m) {
        mat = m;
    }

    public MeuHandler() {
    }

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(XMLMaker.usustag)) {
            biusus = true;
        } else if (qName.equalsIgnoreCase(XMLMaker.usutag)) {
            biusu = true;
        } else if (qName.equalsIgnoreCase(XMLMaker.nomtag)) {
            binom = true;
        } else if (qName.equalsIgnoreCase(XMLMaker.clatag)) {
            biclau = true;
        }
    }

    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        if (qName.equalsIgnoreCase(XMLMaker.usustag)) {
            biusus = false;
        } else if (qName.equalsIgnoreCase(XMLMaker.usutag)) {
            biusu = false;
        } else if (qName.equalsIgnoreCase(XMLMaker.nomtag)) {
            binom = false;
        } else if (qName.equalsIgnoreCase(XMLMaker.clatag)) {
            biclau = false;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if (binom) {
            if (mat != null) {
                mat[0].add(new String(ch, start, length));
            }
            binom = false;
        } else if (biclau) {
            if (mat != null) {
                mat[1].add(new String(ch, start, length));
            }
            biclau = false;
        }
    }
}
