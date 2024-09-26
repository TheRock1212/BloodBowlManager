package it.unipi.dataset;

import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class League {
    private Integer id;

    private String name;

    private int nTeams;

    private int ptsWin;

    private int ptsTie;

    private int ptsLose;

    private boolean ptsTD;

    private boolean ptsCAS;

    private boolean ptsTDConceded;

    private int treasury;

    public League() {
    }

    public League(Integer id, String name, Integer nTeams, int ptsWin, int ptsTie, int ptsLose, boolean ptsTD, boolean ptsCAS, boolean ptsTDConceded, int treasury) {
        this.id = id;
        this.name = name;
        this.nTeams = nTeams;
        this.ptsWin = ptsWin;
        this.ptsTie = ptsTie;
        this.ptsLose = ptsLose;
        this.ptsTD = ptsTD;
        this.ptsCAS = ptsCAS;
        this.ptsTDConceded = ptsTDConceded;
        this.treasury = treasury;
    }

    public League(String name, int nTeams, int ptsWin, int ptsTie, int ptsLose, boolean ptsTD, boolean ptsCAS, boolean ptsTDConceded, int treasury) {
        this.name = name;
        this.nTeams = nTeams;
        this.ptsWin = ptsWin;
        this.ptsTie = ptsTie;
        this.ptsLose = ptsLose;
        this.ptsTD = ptsTD;
        this.ptsCAS = ptsCAS;
        this.ptsTDConceded = ptsTDConceded;
        this.treasury = treasury;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getnTeams() {
        return nTeams;
    }

    public void setnTeams(Integer nTeams) {
        this.nTeams = nTeams;
    }

    public int getPtsWin() {
        return ptsWin;
    }

    public void setPtsWin(int ptsWin) {
        this.ptsWin = ptsWin;
    }

    public int getPtsTie() {
        return ptsTie;
    }

    public void setPtsTie(int ptsTie) {
        this.ptsTie = ptsTie;
    }

    public int getPtsLose() {
        return ptsLose;
    }

    public void setPtsLose(int ptsLose) {
        this.ptsLose = ptsLose;
    }

    public boolean isPtsTD() {
        return ptsTD;
    }

    public void setPtsTD(boolean ptsTD) {
        this.ptsTD = ptsTD;
    }

    public boolean isPtsCAS() {
        return ptsCAS;
    }

    public void setPtsCAS(boolean ptsCAS) {
        this.ptsCAS = ptsCAS;
    }

    public boolean isPtsTDConceded() {
        return ptsTDConceded;
    }

    public void setPtsTDConceded(boolean ptsTDConceded) {
        this.ptsTDConceded = ptsTDConceded;
    }

    public int getTreasury() {
        return treasury;
    }

    public void setTreasury(int treasury) {
        this.treasury = treasury;
    }

    public void addLeague() throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO league(name, nteams, pts_win, pts_tie, pts_loss, pts_td, pts_cas, pts_td_conceded, treasury) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, getName());
        ps.setInt(2, getnTeams());
        ps.setInt(3, getPtsWin());
        ps.setInt(4, getPtsTie());
        ps.setInt(5, getPtsLose());
        ps.setBoolean(6, isPtsTD());
        ps.setBoolean(7, isPtsCAS());
        ps.setBoolean(8, isPtsTDConceded());
        ps.setInt(9, getTreasury());
        ps.executeUpdate();
    }
}
