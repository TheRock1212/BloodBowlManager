package it.unipi.dataset.Dao;

import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StadiumDao {

    static public List<String> getTipologie() throws SQLException {
        List<String> tipologie = new ArrayList<>();
        String query = "SELECT DISTINCT cat FROM stadium";
        PreparedStatement ps = App.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tipologie.add(rs.getString("cat"));
        }
        ps.close();
        rs.close();
        return tipologie;
    }

    static public List<String> getStadiums(String cat) throws SQLException {
        List<String> stadiums = new ArrayList<>();
        String query = "SELECT name FROM stadium WHERE cat = ?";
        PreparedStatement ps = App.getConnection().prepareStatement(query);
        int i = 0;
        ps.setString(++i, cat);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            stadiums.add(rs.getString("name"));
        }
        ps.close();
        rs.close();
        return stadiums;
    }

}
