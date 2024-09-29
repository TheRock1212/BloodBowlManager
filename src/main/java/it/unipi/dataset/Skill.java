package it.unipi.dataset;

import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Skill {

    public String name;
    public String category;

    public Skill() {

    }

    public Skill(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getSkill(String[] cat) throws SQLException {
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
