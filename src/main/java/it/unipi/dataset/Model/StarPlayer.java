package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StarPlayer {
    private int id;
    public String name;
    public int cost;
    public String rules;

    public StarPlayer() {

    }

    public StarPlayer(int id, String name, int cost, String rules) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.rules = rules;
    }

    public StarPlayer(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.cost = rs.getInt("cost");
        this.rules = rs.getString("rules");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
