package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStats {
    private int player;
    private int td;
    private int cas;
    private int kill;
    private int pass;
    private int deception;
    private int interception;

    public PlayerStats() {

    }

    public PlayerStats(ResultSet rs) throws SQLException {
        this.player = rs.getInt("player");
        this.td = rs.getInt("td");
        this.cas = rs.getInt("cas");
        this.kill = rs.getInt("kill");
        this.pass = rs.getInt("pass");
        this.deception = rs.getInt("deception");
        this.interception = rs.getInt("interception");
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
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
}
