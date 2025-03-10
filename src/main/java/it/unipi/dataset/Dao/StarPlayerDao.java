package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.Inducements;
import it.unipi.dataset.Model.Race;
import it.unipi.dataset.Model.StarPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        if(r.special1.contains("Favoured of") && !r.special1.equals("Favoured of...")) {
            ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules LIKE ?");
            ps.setString(1, "%Favoured of...%");
            rs = ps.executeQuery();
            while (rs.next()) {
                stars.add(rs.getString("name"));
            }
        }
        if(!r.special2.isEmpty()) {
            ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules like ?");
            ps.setString(1, "%" + r.special2 + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                stars.add(rs.getString("name"));
            }
            if(r.special2.contains("Favoured of") && !r.special2.equals("Favoured of...")) {
                ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules LIKE ?");
                ps.setString(1, "%Favoured of...%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    stars.add(rs.getString("name"));
                }
            }
        }
        if(!r.special3.isEmpty()) {
            ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules like ?");
            ps.setString(1, "%" + r.special3 + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                stars.add(rs.getString("name"));
            }
            if(r.special3.contains("Favoured of") && !r.special3.equals("Favoured of...")) {
                ps = App.getConnection().prepareStatement("SELECT * FROM star_player WHERE rules LIKE ?");
                ps.setString(1, "%Favoured of...%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    stars.add(rs.getString("name"));
                }
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

    public static synchronized List<StarPlayer> getStar(int race, int petty) throws SQLException {
        List<StarPlayer> model = new ArrayList<>();
        PreparedStatement ps = App.getConnection().prepareStatement("SELECT * FROM race WHERE id = ?");
        ps.setInt(1, race);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Race r = new Race(rs);
        String sql = " SELECT * FROM star_player WHERE cost <= ? AND ( rules LIKE '%Any%' ";

        sql += " OR rules LIKE ? ";
        if(r.special1.contains("Favoured of") && !r.special1.equals("Favoured of...")) {
            sql += " OR rules LIKE '%Favoured of...%' ";
        }
        if(!r.special2.isEmpty()) {
            sql += " OR rules LIKE ?";
            if(r.special2.contains("Favoured of") && !r.special2.equals("Favoured of...")) {
                sql += " OR rules LIKE '%Favoured of...%' ";
            }
        }
        if(!r.special3.isEmpty()) {
            sql += " OR rules LIKE ?";
            if(r.special3.contains("Favoured of") && !r.special3.equals("Favoured of...")) {
                sql += " OR rules LIKE '%Favoured of...%' ";
            }
        }
        sql += " ) ";
        ps = App.getConnection().prepareStatement(sql);
        int i = 0;
        ps.setInt(++i, petty);
        ps.setString(++i, "%" + r.special1 + "%");
        if(!r.special2.isEmpty()) {
            ps.setString(++i, "%" + r.special2 + "%");
        }
        if(!r.special3.isEmpty()) {
            ps.setString(++i, "%" + r.special3 + "%");
        }

        rs = ps.executeQuery();
        while (rs.next()) {
            model.add(new StarPlayer(rs));
        }

        ps.close();
        rs.close();

        int index = -1;
        if((r.special1 + " " + r.special2 + " " + r.special3).contains("Sylvanian Spotlight")) {
            for(i = 0; i < model.size(); i++) {
                if(model.get(i).getName().contains("Morg'n'Thorg")) {
                    index = i;
                }
            }
        }
        if(index != -1)
            model.remove(index);


        model.sort(Comparator.comparing(StarPlayer::getName));
        return model;
    }

}
