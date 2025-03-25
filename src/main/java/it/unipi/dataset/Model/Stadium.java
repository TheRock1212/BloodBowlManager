package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stadium {
    public String name, cat;

    public Stadium() {

    }

    public Stadium(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.cat = rs.getString("cat");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
