package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.Race;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RaceDao {

    /**
     * Cerca la lista delle razze disponibili
     * @return Lista delle razze
     * @throws SQLException
     */
    public static synchronized List<String> getNames(boolean secret) throws SQLException {
        String query = "SELECT name FROM race";
        if(!secret) {
            query += " WHERE secret = 0";
        }
        PreparedStatement st = App.getConnection().prepareStatement(query);
        ResultSet rs = st.executeQuery();
        List<String> names = new ArrayList<>();
        while (rs.next()) {
            names.add(rs.getString("name"));
        }
        st.close();
        rs.close();
        return names;
    }

    /**
     * Cerca le informazioni sulla razza desiderata
     * @param name nome della razza
     * @return Oggetto Race della razza
     * @throws SQLException
     */
    public static synchronized Race getRace(String name) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT * FROM race WHERE name = ?");
        st.setString(1, name);
        ResultSet rs =  st.executeQuery();
        rs.next();
        Race race = new Race(rs);
        st.close();
        rs.close();
        return race;
    }

    /**
     * Cerca le informazioni sulla razza desiderata
     * @param id id della razza
     * @return Oggetto Race della razza
     * @throws SQLException
     */
    public static synchronized Race getRace(int id) throws SQLException {
        PreparedStatement st = App.getConnection().prepareStatement("SELECT * FROM race WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        Race race = new Race(rs);
        st.close();
        rs.close();
        return race;
    }

    /**
     * Controlla se la razza ha "Low Cost Lineman"
     * @param id id della razza
     * @return true se ha la regola, false altrimenti
     * @throws SQLException
     */
    public static synchronized boolean hasLowCostLineman(int id) throws SQLException{
        PreparedStatement st = App.getConnection().prepareStatement("SELECT special_1, special_2, special_3 FROM race WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        String spe = "";
        while(rs.next()){
            spe = rs.getString("special_1") + " " + rs.getString("special_2") + " " + rs.getString("special_3");
        }
        st.close();
        rs.close();
        return spe.contains("Low Cost Lineman");
    }

    public static synchronized boolean hasRaisedRule(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT special_1, special_2, special_3 FROM race WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String spe = "";
        while(rs.next()){
            spe = rs.getString("special_1") + " " + rs.getString("special_2") + " " + rs.getString("special_3");
        }
        ps.close();
        rs.close();
        return spe.contains("Masters of Undeath") || spe.contains("Vampire Lord");
    }
}
