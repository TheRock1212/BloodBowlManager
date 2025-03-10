package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.Inducements;
import it.unipi.dataset.Model.Race;
import it.unipi.dataset.Model.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InducementsDao {

    public static synchronized List<Inducements> getInducements(Team t, int petty) throws SQLException {
        List<Inducements> models = new ArrayList<>();
        List<Inducements> results = new ArrayList<>();
        Race r = RaceDao.getRace(t.getRace());

        PreparedStatement ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule = 'Any'");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            models.add(new Inducements(rs));
        }
        ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule LIKE ?");
        ps.setString(1, "%" + r.special1 + "%");
        rs = ps.executeQuery();
        while (rs.next()) {
            models.add(new Inducements(rs));
        }
        if(r.special1.contains("Favoured of") && !r.special1.equals("Favoured of...")) {
            ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule LIKE ?");
            ps.setString(1, "%Favoured of...%");
            rs = ps.executeQuery();
            while (rs.next()) {
                models.add(new Inducements(rs));
            }
        }

        if(r.special2 != null && !r.special2.isEmpty()) {
            ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule LIKE ?");
            ps.setString(1, "%" + r.special2 + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                models.add(new Inducements(rs));
            }
            if(r.special2.contains("Favoured of") && !r.special2.equals("Favoured of...")) {
                ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule LIKE ?");
                ps.setString(1, "%Favoured of...%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    models.add(new Inducements(rs));
                }
            }
        }

        if(r.special3 != null && !r.special3.isEmpty()) {
            ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule LIKE ?");
            ps.setString(1, "%" + r.special3 + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                models.add(new Inducements(rs));
            }
            if(r.special3.contains("Favoured of") && !r.special3.equals("Favoured of...")) {
                ps = App.getConnection().prepareStatement("SELECT * FROM inducements WHERE rule LIKE ?");
                ps.setString(1, "%Favoured of...%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    models.add(new Inducements(rs));
                }
            }
        }

        for(Inducements model : models) {
            if(model.name.equals("Halfling Master Chef") && t.getRace() == 16)
                model.cost = 100;
            if(model.name.equals("Bribes") && ("Bribery and Corruption".equals(r.special1) || "Bribery and Corruption".equals(r.special2) || "Bribery and Corruption".equals(r.special3)))
                model.cost = 50;
            if(model.cost > petty)
                continue;
            model.qty = Math.min((petty / model.cost), model.qty);
            results.add(model);
        }

        return results;
    }
}
