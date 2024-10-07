package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillDao {

    /**
     * Ottiene le skill da un array di categorie
     * @param cat array di categorie
     * @return array di skill
     * @throws SQLException
     */
    public static synchronized String[] getSkill(String[] cat) throws SQLException {
        String[] names = new String[cat.length * 12];
        for(int i = 0; i < cat.length; i++) {
            PreparedStatement ps = App.getConnection().prepareStatement("SELECT name FROM skill WHERE category = ?");
            ps.setString(1, cat[i]);
            ResultSet rs = ps.executeQuery();
            int j = i * 12;
            while (rs.next()) {
                names[j++] = rs.getString(1);
            }
        }
        return names;
    }
}
