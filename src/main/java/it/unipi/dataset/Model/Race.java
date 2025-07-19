package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Race {
    private int id;
    public String name;
    public int positional;
    public int costreroll;
    public boolean apothecary;
    public String special1, special2, special3;
    public String url;
    private String family;

    public Race() {

    }

    public Race(int id, String name, int positional, int costreroll, boolean apothecary, String special1, String special2, String special3, String url, String family) {
        this.id = id;
        this.name = name;
        this.positional = positional;
        this.costreroll = costreroll;
        this.apothecary = apothecary;
        this.special1 = special1;
        this.special2 = special2;
        this.special3 = special3;
        this.url = url;
        this.family = family;
    }

    public Race(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.positional = rs.getInt("positional");
        this.costreroll = rs.getInt("cost_reroll");
        this.apothecary = rs.getBoolean("apothecary");
        this.special1 = rs.getString("special_1");
        this.special2 = rs.getString("special_2");
        this.special3 = rs.getString("special_3");
        this.url = rs.getString("url");
        this.family = rs.getString("family");
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

    public int getCostreroll() {
        return costreroll;
    }

    public void setCostreroll(int costreroll) {
        this.costreroll = costreroll;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
