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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe per efectuar les interaccions amb la bbdd
 */
public class BackofficeDB {
    
    private Connection con;
    private Statement stat;
    private final String host;
    private final String port;
    private final String db;
    private final String user;
    private final String pass;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor.">
    public BackofficeDB() {
        host = DBProperties.host;
        port = DBProperties.port;
        db = DBProperties.db;
        user = DBProperties.user;
        pass = DBProperties.pass;
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Main per fer proves.">
    public static void main(String[] args) {
        BackofficeDB bdb = new BackofficeDB();
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Mètode de la classe per connectar-se a la bbdd.">
    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + db, user, pass);
            stat = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que retorna tots els usuaris.">
    public HashMap getUsuaris() {
        HashMap res = new HashMap<>();
        try {
            connect();
            String select = ""
                    + "SELECT id_usuari, nom_usu, email_usu, nom_pais, codi_pais, dni_usu, nom_tip, u.id_tipus_usuari AS id_tip"
                    + " FROM usuari AS u"
                    + " INNER JOIN pais as p"
                    + "     ON u.nacionalitat_usu = p.codi_pais"
                    + " INNER JOIN tipus_usuari AS t"
                    + "     ON u.id_tipus_usuari = t.id_tipus_usuari"
                    + " ORDER BY id_usuari";
            ResultSet rs = stat.executeQuery(select);
            String[] usu = new String[7]; //String que contindrà les dades de l'usuari
            while (rs.next()) {
                String id = toUtf8(rs.getString("id_usuari"));
                usu[0] = toUtf8(rs.getString("nom_usu"));
                usu[1] = toUtf8(rs.getString("email_usu"));
                usu[2] = toUtf8(rs.getString("nom_pais"));
                usu[3] = toUtf8(rs.getString("dni_usu"));
                usu[4] = toUtf8(rs.getString("nom_tip"));
                usu[5] = toUtf8(rs.getString("codi_pais"));
                usu[6] = toUtf8(rs.getString("id_tip"));
                res.put(id, (String[])usu.clone());
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
        return new AccessDB().getTipusUsuaris();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que retorna totes les reserves.">
    public HashMap getReserves() {
        HashMap res = new HashMap<>();
        try {
            connect();
            String select = ""
                    + "SELECT id_reserva, email_usu, id_habitacio, DATE(data_inici_res) AS inici, DATE(data_fi_res) AS fi, nom_est, er.id_estat_reserva"
                    + " FROM reserva AS r"
                    + " INNER JOIN usuari as u"
                    + "     ON u.id_usuari = r.id_usuari"
                    + " INNER JOIN estat_reserva AS er"
                    + "     ON er.id_estat_reserva = r.id_estat_reserva"
                    + " ORDER BY data_inici_res";
            ResultSet rs = stat.executeQuery(select);
            String[] usu = new String[6]; //String que contindrà les dades de la reserva
            while (rs.next()) {
                String id = toUtf8(rs.getString("id_reserva"));
                usu[0] = toUtf8(rs.getString("email_usu"));
                usu[1] = toUtf8(rs.getString("id_habitacio"));
                usu[2] = toUtf8(rs.getString("inici"));
                usu[3] = toUtf8(rs.getString("fi"));
                usu[4] = toUtf8(rs.getString("nom_est"));
                usu[5] = toUtf8(rs.getString("er.id_estat_reserva"));
                res.put(id, (String[])usu.clone());
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

    // <editor-fold defaultstate="collapsed" desc="Mètode que retorna tots els estats de reserves.">
    public HashMap getEstatsReserva() {
        HashMap res = new HashMap<>();
        try {
            connect();
            ResultSet rs = stat.executeQuery("SELECT * FROM estat_reserva;");
            while (rs.next()) {
                String id = toUtf8(rs.getString("id_estat_reserva"));
                String nom = toUtf8(rs.getString("nom_est"));
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

    // <editor-fold defaultstate="collapsed" desc="Mètode que retorna tots els preus.">
    public HashMap getPreus() {
        HashMap res = new HashMap<>();
        try {
            connect();
            ResultSet rs = stat.executeQuery("SELECT * FROM import_habitacio;");
            while (rs.next()) {
                String id = toUtf8(rs.getString("places_hab"));
                String valor = toUtf8(rs.getString("valor_imp"));
                res.put(id, valor);
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
    
    // <editor-fold defaultstate="collapsed" desc="Mètode que actualitza un usuari.">
    /**
     * Actualitza un usuari
     * @param params Paràmetres a actualitzar
     * @param id ID de l'usuari
     * @return Les files actualitzades
     */
    public int actualitzarUsuari(String[] params, String id) {
        int res = 0;
        String update = ""
            + "UPDATE usuari SET "
            + "nom_usu = '" + params[0] + "', "
            + "email_usu = '" + params[1] + "', "
            + "nacionalitat_usu = '" + params[2] + "', "
            + "dni_usu = '" + params[3] + "', "
            + "id_tipus_usuari = " + params[4] + " "
            + "WHERE id_usuari = " + id + ";";
        try {
            connect();
            res = stat.executeUpdate(update);
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
    }
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Mètode que actualitza una reserva.">
    /**
     * Actualitza una reserva
     * @param params Paràmetres a actualitzar
     * @param id ID de la reserva
     * @return Les files actualitzades
     */
    public int actualitzarReserva(String[] params, String id) {
        int res = 0;
        String update = ""
            + "UPDATE reserva SET "
            + "id_estat_reserva = " + params[0] + " "
            + "WHERE id_reserva = " + id + ";";
        try {
            connect();
            res = stat.executeUpdate(update);
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Mètode que actualitza els preus.">
    /**
     * Actualitza el preu d'un tipus d'habitacions
     * @param params Paràmetres a actualitzar
     * @param id Places de les habitacions de les quals s'ha d'actualitzar el preu
     * @return Les files actualitzades
     */
    public int actualitzarPreu(String[] params, String id) {
        int res = 0;
        String update = ""
            + "UPDATE import_habitacio SET "
            + "valor_imp = " + params[0] + " "
            + "WHERE places_hab = " + id + ";";
        try {
            connect();
            res = stat.executeUpdate(update);
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que elimina una fila.">
    /**
     * Efectua una baixa
     * @param taula La taula d'on eliminar
     * @param id L'id de l'element a eliminar
     * @return El número de files afectades
     */
    public int baixa(String taula, String id) {
        int res = 0;
        String pk;
        switch (taula) {
            case "usuari": pk = "id_usuari"; break;
            case "tipus_usuari": pk = "id_tipus_usuari"; break;
            case "descompte": pk = "id_descompte"; break;
            case "estat_reserva": pk = "id_estat_reserva"; break;
            default: pk = ""; break;
        }
        try {
            connect();
            String delete = "DELETE FROM " + taula + " WHERE " + pk + " = " + id + ";";
            res = stat.executeUpdate(delete);
            stat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mètode que insereix una fila.">
    /**
     * Efectua una baixa
     * @param taula La taula d'on eliminar
     * @param params Els elements de la fila a inserir
     * @return L'ID de la fila inserida
     */
    public int alta(String taula, String[] params) {
        int res = 0;
        String elems = Utils.join(params, "', '");
        String columnes;
        switch (taula) {
            case "tipus_usuari": columnes = "nom_tip"; break;
            case "descompte": columnes = "concepte_des, valor_des"; break;
            case "estat_reserva": columnes = "nom_est"; break;
            default: columnes = ""; break;
        }
        try {
            connect();
            String insert = "INSERT INTO " + taula + "(" + columnes + ") VALUES('" + elems + "');";
            System.out.println(insert);
            stat.executeUpdate(insert, Statement.RETURN_GENERATED_KEYS);
            ResultSet keys = stat.getGeneratedKeys();    
            keys.next();  
            res = keys.getInt(1);
            stat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return res;
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
            Logger.getLogger(BackofficeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    // </editor-fold>  
}
