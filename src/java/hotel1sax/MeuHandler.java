/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel1sax;

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
    private StringBuilder dataIni, dataFi;
    private final String resTag = "reserva";
    private final String hostTag = "hoste";
    private final String nomTag = "nom";
    private final String mailTag = "mail";
    private final String dniTag = "dni";
    private final String nacTag = "nac";
    private final String tipTag = "tip";
    private boolean bIDataIni, bIDataFi, bINom,bIMail, bIDni, bINac, bITip;

    public MeuHandler(ArrayList[] m, StringBuilder di, StringBuilder df) {
        mat = m;
        dataIni = di;
        dataFi = df;
        bIDataIni = bIDataFi = bINom = bIMail = bIDni = bINac = bITip = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        switch (qName.toLowerCase()) {
            case resTag: bIDataIni = true; break;
            case hostTag: bIDataFi = true; break;
            case nomTag: bINom = true; break;
            case mailTag: bIMail = true; break;
            case dniTag: bIDni = true; break;
            case nacTag: bINac = true; break;
            case tipTag: bITip = true; break;
            default: break;
        }
    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
        switch (qName.toLowerCase()) {
            case resTag: bIDataIni = false; break;
            case hostTag: bIDataFi = false; break;
            case nomTag: bINom = false; break;
            case mailTag: bIMail = false; break;
            case dniTag: bIDni = false; break;
            case nacTag: bINac = false; break;
            case tipTag: bITip = false; break;
            default: break;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (bIDataIni) {
            dataIni.append(new String(ch, start, length));
            bIDataIni = false;
        } else if (bIDataFi) {
            dataFi.append(new String(ch, start, length));
            bIDataFi = false;
        } else if (bINom) {
            if (mat != null) mat[0].add(new String(ch, start, length));
            bINom = false;
        } else if (bIMail) {
            if (mat != null) mat[1].add(new String(ch, start, length));
            bIMail = false;
        } else if (bIDni) {
            if (mat != null) mat[2].add(new String(ch, start, length));
            bIDni = false;
        } else if (bINac) {
            if (mat != null) mat[3].add(new String(ch, start, length));
            bINac = false;
        } else if (bITip) {
            if (mat != null) mat[4].add(new String(ch, start, length));
            bITip = false;
        }
    }
}
