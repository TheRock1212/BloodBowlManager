package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamStats {
    private int team;
    private int td;
    private int tdA;
    private int cas;
    private int casA;
    private int kill;
    private int pass;
    private int deception;
    private int interception;
    private int pts;

    public TeamStats() {

    }

    public TeamStats(ResultSet rs) throws SQLException {
        this.team = rs.getInt("team");
        this.td = rs.getInt("td");
        this.tdA = rs.getInt("tdA");
        this.cas = rs.getInt("cas");
        this.casA = rs.getInt("casA");
        this.kill = rs.getInt("kill");
        this.pass = rs.getInt("pass");
        this.deception = rs.getInt("deception");
        this.interception = rs.getInt("interception");
        this.pts = rs.getInt("pts");
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public int getTdA() {
        return tdA;
    }

    public void setTdA(int tdA) {
        this.tdA = tdA;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
    }

    public int getCasA() {
        return casA;
    }

    public void setCasA(int casA) {
        this.casA = casA;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getDeception() {
        return deception;
    }

    public void setDeception(int deception) {
        this.deception = deception;
    }

    public int getInterception() {
        return interception;
    }

    public void setInterception(int interception) {
        this.interception = interception;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }
}
