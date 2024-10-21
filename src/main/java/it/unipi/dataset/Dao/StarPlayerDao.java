package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.Race;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarPlayerDao {

    public static synchronized List<String> getStar(int race) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT * FROM race WHERE id = ?");
        ps.setInt(1, race);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Race r = new Race(rs);
        List<String> stars = new ArrayList<>();
        ps = App.getConnection().prepareStatement("SELECT name FROM star_player WHERE rules = 'ANY'");
        rs = ps.executeQuery();
        while (rs.next()) {
            stars.add(rs.getString("name"));
        }
        ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules like ?");
        ps.setString(1, "%" + r.special1 + "%");
        rs = ps.executeQuery();
        while (rs.next()) {
            stars.add(rs.getString("name"));
        }
        if(!r.special2.isEmpty()) {
            ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules like ?");
            ps.setString(1, "%" + r.special2 + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                stars.add(rs.getString("name"));
            }
        }
        if(!r.special3.isEmpty()) {
            ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules like ?");
            ps.setString(1, "%" + r.special3 + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                stars.add(rs.getString("name"));
            }
        }
        int index = -1;
        if((r.special1 + " " + r.special2 + " " + r.special3).contains("Sylvanian Spotlight")) {
            for(int i = 0; i < stars.size(); i++) {
                if(stars.get(i).contains("Morg'n'Thorg")) {
                    index = i;
                }
            }
        }
        if(index != -1)
            stars.remove(index);
        Collections.sort(stars);
        return stars;
    }
}
