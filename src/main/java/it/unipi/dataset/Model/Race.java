package it.unipi.dataset.Model;

import it.unipi.bloodbowlmanager.App;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Race {
    private int id;
    public String name;
    public int positional;
    public int reroll;
    public boolean apothecary;
    public String special1, special2, special3;

    public Race() {

    }

    public Race(int id, String name, int positional, int reroll, boolean apothecary, String special1, String special2, String special3) {
        this.id = id;
        this.name = name;
        this.positional = positional;
        this.reroll = reroll;
        this.apothecary = apothecary;
        this.special1 = special1;
        this.special2 = special2;
        this.special3 = special3;
    }

    public Race(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.positional = rs.getInt("positional");
        this.reroll = rs.getInt("cost_reroll");
        this.apothecary = rs.getBoolean("apothecary");
        this.special1 = rs.getString("special_1");
        this.special2 = rs.getString("special_2");
        this.special3 = rs.getString("special_3");
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

    public int getPositional() {
        return positional;
    }

    public void setPositional(int positional) {
        this.positional = positional;
    }

    public int getReroll() {
        return reroll;
    }

    public void setReroll(int reroll) {
        this.reroll = reroll;
    }

    public boolean isApothecary() {
        return apothecary;
    }

    public void setApothecary(boolean apothecary) {
        this.apothecary = apothecary;
    }

    public String getSpecial1() {
        return special1;
    }

    public void setSpecial1(String special1) {
        this.special1 = special1;
    }

    public String getSpecial2() {
        return special2;
    }

    public void setSpecial2(String special2) {
        this.special2 = special2;
    }

    public String getSpecial3() {
        return special3;
    }

    public void setSpecial3(String special3) {
        this.special3 = special3;
    }

}