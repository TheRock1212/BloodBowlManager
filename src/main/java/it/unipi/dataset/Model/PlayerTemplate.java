package it.unipi.dataset.Model;

import it.unipi.bloodbowlmanager.App;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerTemplate {
    private int id;
    public String position;
    private int race;
    public int ma;
    public int st;
    public int ag;
    public int pa;
    public int av;
    public String skill;
    public int maxQty;
    public String primary;
    public String secondary;
    public int cost;
    private String url;
    private boolean bigGuy;

    public PlayerTemplate() {

    }

    public PlayerTemplate(int id, String position, int race, int ma, int st, int ag, int pa, int av, String skill, int maxQty, String primary, String secondary, int cost, String url, boolean bigGuy) {
        this.id = id;
        this.position = position;
        this.race = race;
        this.ma = ma;
        this.st = st;
        this.ag = ag;
        this.pa = pa;
        this.av = av;
        this.skill = skill;
        this.maxQty = maxQty;
        this.primary = primary;
        this.secondary = secondary;
        this.cost = cost;
        this.url = url;
        this.bigGuy = bigGuy;
    }

    public PlayerTemplate(PlayerTemplate pt) {
        this.id = pt.getId();
        this.position = pt.getPosition();
        this.race = pt.getRace();
        this.ma = pt.getMa();
        this.st = pt.getSt();
        this.ag = pt.getAg();
        this.pa = pt.getPa();
        this.av = pt.getAv();
        this.skill = pt.getSkill();
        this.maxQty = pt.getMaxQty();
        this.primary = pt.getPrimary();
        this.secondary = pt.getSecondary();
        this.cost = pt.getCost();
        this.url = pt.getUrl();
        this.bigGuy = pt.isBigGuy();
    }

    public PlayerTemplate(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.position = rs.getString("position");
        this.race = rs.getInt("race");
        this.ma = rs.getInt("MA");
        this.st = rs.getInt("ST");
        this.ag = rs.getInt("AG");
        this.pa = rs.getInt("PA");
        this.av = rs.getInt("AV");
        this.skill = rs.getString("skill");
        this.maxQty = rs.getInt("max_qty");
        this.primary = rs.getString("primary");
        this.secondary = rs.getString("secondary");
        this.cost = rs.getInt("cost");
        this.url = rs.getString("url");
        this.bigGuy = rs.getBoolean("big_guy");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
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

    public int getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(int maxQty) {
        this.maxQty = maxQty;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isBigGuy() {
        return bigGuy;
    }

    public void setBigGuy(boolean bigGuy) {
        this.bigGuy = bigGuy;
    }

}
