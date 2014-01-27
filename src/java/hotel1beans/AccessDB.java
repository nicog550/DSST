/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel1beans;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe per efectuar les interaccions amb la bbdd
 */
public class AccessDB {
    
    private Connection con;
    private final String host;
    private final String port;
    private final String db;
    private final String user;
    private final String pass;
    
    /** <code>String</code> Tipus d'usuari adult */
    public static String TIPUS_ADULT = "3";
    
    // <editor-fold defaultstate="collapsed" desc="Constructor.">
    public AccessDB() {
        host = DBProperties.host;
        port = DBProperties.port;
        db = DBProperties.db;
        user = DBProperties.user;
        pass = DBProperties.pass;
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Main per fer proves.">
    public static void main(String[] args) {
        AccessDB adb = new AccessDB();
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Mètode de la classe per connectar-se a la bbdd.">
    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + db, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que comprova si hi ha disponibilitat.">
    /**
     * Comprova si hi ha disponibilitat d'habitacions per unes dates concretes
     * @param dataIni Data d'entrada a l'hotel
     * @param dataFi Data de sortida de l'hotel
     * @param places Número de places a ocupar
     * @return Vertader si hi ha disponibilitat, fals si no
     */
    public boolean hiHaDisponibilitat(String dataIni, String dataFi, String places) {
        int acum = 0, num = Integer.parseInt(places);
        try {
            connect();
            Statement stat = con.createStatement();
            String sql = ""
                    +   "SELECT h.places_hab" +
                        "	FROM habitacio AS h" +
                        "	GROUP BY h.id_habitacio" +
                        "	HAVING NOT EXISTS (" +
                        "        SELECT *" +
                        "        	FROM reserva AS r" +
                        "        	WHERE r.id_habitacio = h.id_habitacio" +
                        "        		AND r.data_inici_res <= '" + dataIni + "'" +
                        "				AND r.data_fi_res >= '" + dataFi + "'" +
                        "		);";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                acum += Integer.parseInt(rs.getString("places_hab"));
                //No necessitam recórrer totes les files; quan l'acumulat superi el sol·licitat aturam
                if (acum >= num) break;
            }
            stat.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return acum >= Integer.parseInt(places);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que efectua un login.">
    /**
     * Efectua el login.
     * @param email L'email de l'usuari
     * @param pass La contrasenya de l'usuari
     * @return Nom, nacionalitat, DNI i tipus d'usuari si l'usuari existeix o quatre camps buits si no
     */
    public String login(String email, String pass) {
        String nom, nac, dni, tip, id;
        nom = nac = dni = tip = id = "";
        try {
            connect();
            Statement stat = con.createStatement();
            String sql = "SELECT id_usuari, nom_usu, nacionalitat_usu, dni_usu, id_tipus_usuari"
                    + "     FROM usuari"
                    + "     WHERE email_usu = '" + email + "'"
                    + "         AND contrasenya_usu = '" + pass + "'";
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                nom = toUtf8(rs.getString("nom_usu"));
                nac = toUtf8(rs.getString("nacionalitat_usu"));
                dni = toUtf8(rs.getString("dni_usu"));
                tip = toUtf8(rs.getString("id_tipus_usuari"));
                id = toUtf8(rs.getString("id_usuari"));
                break;
            }
            stat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        String res = "";
        if (!nom.equals("")) res = nom + "_" + nac + "_" + dni + "_" + tip + "_" + id;
        return res;
    }
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Mètode que efectua el registre d'un usuari.">
    /**
     * Guarda un usuari
     * @param nom Noms de l'usuari
     * @param email Adreces d'email de l'usuari
     * @param nac Nacionalitat de l'usuari
     * @param dni DNIs de l'usuari
     * @param tipus Tipus d'usuari de l'usuari
     * @param pass Contrasenya de l'usuari
     * @return ID de l'usuari
     */
    public String registrar(String nom, String email, String nac, String dni, String pass, String tipus) {
        ResultSet res;
        String id = ""; //Contindrà l'id de l'usuari
        String insert = ""
            + "INSERT INTO usuari (nom_usu, email_usu, nacionalitat_usu, dni_usu, id_tipus_usuari, contrasenya_usu)"
            + "  VALUES ('" + nom + "', '" + email + "', '" + nac + "', '" + dni + "', " + tipus + ", '" + pass + "');";
        try {
            connect();
            Statement stat = con.createStatement();
            stat.executeUpdate(insert);
            //Cercam l'id de l'usuari
            res = stat.executeQuery("SELECT id_usuari FROM usuari WHERE dni_usu = '" + dni + "';");
            while(res.next()) {
                id = res.getString("id_usuari");
                break; //Ens asseguram de prendre només una fila
            }
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return id;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Mètode que retorna totes les nacionalitats.">
    public HashMap getPaisos() {
        HashMap res = new HashMap<>();
        try {
            connect();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM pais;");
            while (rs.next()) {
                String codi = toUtf8(rs.getString("codi_pais"));
                String nom = toUtf8(rs.getString("nom_pais"));
                res.put(codi, nom);
            }
            stat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que retorna tots els tipus d'usuaris.">
    public HashMap getTipusUsuaris() {
        HashMap res = new HashMap<>();
        try {
            connect();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM tipus_usuari;");
            while (rs.next()) {
                String id = toUtf8(rs.getString("id_tipus_usuari"));
                String nom = toUtf8(rs.getString("nom_tip"));
                res.put(id, nom);
            }
            stat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Mètode que crea una reserva.">
    /**
     * Guarda els usuaris i crea una reserva per ells
     * @param noms Els noms de tots els usuaris
     * @param emails Els emails de tots els usuaris
     * @param dnis Els DNIs de tots els usuaris
     * @param nacionalitats Les nacionalitats de tots els usuaris
     * @param tipus Els tipus d'usuari de tots els usuaris
     * @param dataIni Data d'entrada
     * @param dataFi Data de sortida
     * @return Vertader si les insercions s'han efectuat correctament, fals si no
     */
    public boolean crearReserva(String[] noms, String[] emails, String[] dnis, String[] nacionalitats, int[] tipus,
                                String dataIni, String dataFi) {
        boolean ret = false;
        try {
            connect();
            Statement stat = con.createStatement();
            int places = noms.length;
            
            int[] ids = guardarUsuaris(places, noms, emails, nacionalitats, dnis, tipus, stat); //Guardam els usuaris
            crearReserves(places, ids, dataIni, dataFi, stat); //Cream les reserves
            
            ret = true;
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return ret;
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Guarda els usuaris a la BBDD.">
    /**
     * Guarda els usuaris donats si no existeixen ja
     * @param places Quantitat d'usuaris
     * @param noms Noms de tots els usuaris
     * @param emails Adreces d'email de tots els usuaris
     * @param nacionalitats Nacionalitats de tots els usuaris
     * @param dnis DNIs de tots els usuaris
     * @param tipus Tipus d'usuari de tots els usuaris
     * @param stat
     * @return IDs de tots els usuaris inclosos en la consulta
     * @throws SQLException 
     */
    private int[] guardarUsuaris(int places, String[] noms, String[] emails, String[] nacionalitats, String[] dnis,
                                 int[] tipus, Statement stat) throws SQLException {
        String insert;
        ResultSet res;
        int[] ids = new int[places]; //Contindrà l'id de cada usuari
        for (int i = 0; i < places; i++) {
            insert = ""
            + "INSERT INTO usuari (nom_usu, email_usu, nacionalitat_usu, dni_usu, id_tipus_usuari)"
            + " SELECT '" + noms[i] + "', '" + emails[i] + "', '" + nacionalitats[i] + "', '" + dnis[i] + "', " + tipus[i]
            + "     FROM dual"
            + "     WHERE NOT EXISTS (SELECT 1"
            + "         FROM usuari"
            + "         WHERE nom_usu = '" + noms[i] + "'"
            + "         AND email_usu = '" + emails[i] + "'"
            + "         AND dni_usu = '" + dnis[i] + "')";
            stat.executeUpdate(insert);
            //Cercam l'id de l'usuari, tant si s'ha inserit ara com si ja existia
            res = stat.executeQuery("SELECT id_usuari FROM usuari WHERE dni_usu = '" + dnis[i] + "';");
            while(res.next()) {
                ids[i] += Integer.parseInt(res.getString("id_usuari"));
                break; //Ens asseguram de prendre només una fila
            }
        }
        return ids;
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Creació efectiva de les reserves.">
    /**
     * Creació efectiva de les reserves
     * @param places Número de places a reservar
     * @param ids IDs dels usuaris que reserven
     * @param dataIni Data d'inici de les reserves
     * @param dataFi Data de fi de les reserves
     * @param stat
     * @throws Exception Llança excepció quan falla una inserció o quan no hi ha disponibilitat
     */
    private void crearReserves(int places, int[] ids, String dataIni, String dataFi, Statement stat) throws Exception {
        //'Habitacions' indicarà quantes habitacions i de quantes places s'han de reservar
        ArrayList<Integer> habitacions = new ArrayList<>();
        switch (places) {
            case 1: habitacions.add(1); break;
            case 2: habitacions.add(2); break;
            case 3: habitacions.add(3); break;
            case 4: habitacions.add(2); habitacions.add(2); break;
            case 5: habitacions.add(3); habitacions.add(2); break;
            case 6: habitacions.add(3); habitacions.add(3); break;
            case 7: habitacions.add(3); habitacions.add(2); habitacions.add(2); break;
            case 8: habitacions.add(3); habitacions.add(3); habitacions.add(2); break;
            case 9: habitacions.add(3); habitacions.add(3); habitacions.add(3); break;
            case 10: habitacions.add(3); habitacions.add(3); habitacions.add(2); habitacions.add(2); break;
            default: break;
        }

        //Cream les reserves
        int num, idHab, acum = 0;
        String sql;
        for (int i = 0; i < habitacions.size(); i++) {
            idHab = 0;
            num = habitacions.get(i); //Comprovam quantes persones hem d'assignar a cada habitació
            sql = ""
                + "SELECT h.id_habitacio"
                + "     FROM habitacio AS h"
                + "     WHERE h.places_hab = " + num
                + "     GROUP BY h.id_habitacio"
                + "     HAVING NOT EXISTS ("
                + "        SELECT *"
                + "        	FROM reserva AS r"
                + "             WHERE r.id_habitacio = h.id_habitacio"
                + "                 AND r.data_inici_res <= '" + dataIni + "'"
                + "                 AND r.data_fi_res >= '" + dataFi + "')"
                + "     LIMIT 1;";
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()) {
                idHab = Integer.parseInt(rs.getString("id_habitacio"));
                break; //Ens asseguram de prendre només una fila
            }
            if (idHab == 0) throw new Exception("No hi ha habitacions disponibles");
            for (int j = 0; j < num; j++) {
                sql = "INSERT INTO reserva (id_usuari, id_habitacio, data_inici_res, data_fi_res)"
                        + "VALUES (" + ids[acum++] + ", " + idHab + ", '" + dataIni + "', '" + dataFi + "');";
                if (stat.executeUpdate(sql) == 0)
                    throw new Exception("Error amb la inserció de l'usuari " + ids[acum - 1]);
            }
        }
        
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Mètodes per desconnectarse de la bbdd i per convertir a UTF-8.">
    private void disconnect() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String toUtf8(String s) {
        String res = "no-UTF8";
        byte[] b;
        try {
            b = s.getBytes("UTF-8");
            res = new String(b);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    // </editor-fold>  
}
