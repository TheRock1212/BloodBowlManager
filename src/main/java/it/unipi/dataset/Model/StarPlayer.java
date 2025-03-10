package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StarPlayer {
    private int id;
    public String name;
    public int ma;
    public int st;
    public int ag;
    public int pa;
    public int av;
    public String skill;
    public int cost;
    public String rules;

    public StarPlayer() {

    }

    public StarPlayer(int id, String name, int ma, int st, int ag, int pa, int av, String skill, int cost, String rules) {
        this.id = id;
        this.name = name;
        this.ma = ma;
        this.st = st;
        this.ag = ag;
        this.pa = pa;
        this.av = av;
        this.skill = skill;
        this.cost = cost;
        this.rules = rules;
    }

    public StarPlayer(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.ma = rs.getInt("MA");
        this.st = rs.getInt("ST");
        this.ag = rs.getInt("AG");
        this.pa = rs.getInt("PA");
        this.av = rs.getInt("AV");
        this.skill = rs.getString("skill");
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

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public int getAg() {
        return ag;
    }

    public void setAg(int ag) {
        this.ag = ag;
    }

    public int getPa() {
        return pa;
    }

    public void setPa(int pa) {
        this.pa = pa;
    }

    public int getAv() {
        return av;
    }

    public void setAv(int av) {
        this.av = av;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
