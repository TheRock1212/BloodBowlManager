package it.unipi.dataset;

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

    public ResultSet getNames() throws SQLException {
        Statement st = App.getConnection().createStatement();
        return st.executeQuery("SELECT name FROM race");
    }

    public ResultSet getRace(String name) throws SQLException {
        Statement st = App.getConnection().createStatement();
        return st.executeQuery("SELECT * FROM race WHERE name = '" + name + "'");
    }

    public ResultSet getRace(int id) throws SQLException {
        Statement st = App.getConnection().createStatement();
        return st.executeQuery("SELECT * FROM race WHERE id = " + id);
    }

    public ResultSet getRules(int id) throws SQLException {
        Statement st = App.getConnection().createStatement();
        return st.executeQuery("SELECT special_1, special_2, special_3 FROM race WHERE id = " + id);
    }

    public boolean hasSpecial(int id) throws SQLException{
        ResultSet rs = getRules(id);
        String spe = "";
        while(rs.next()){
            spe = rs.getString("special_1") + " " + rs.getString("special_2") + " " + rs.getString("special_3");
        }
        return spe.contains("Low Cost Lineman");
    }
}
