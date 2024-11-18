package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Inducements {
    private int id;
    public String name;
    public int cost;
    public int qty;
    public String rule;

    public Inducements() {

    }

    public Inducements(int id, String name, int cost, int qty, String rule) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.qty = qty;
        this.rule = rule;
    }

    public Inducements(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.cost = rs.getInt("cost");
        this.qty = rs.getInt("qty");
        this.rule = rs.getString("rule");
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
