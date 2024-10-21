package it.unipi.dataset.Model;

import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    private int groups;

    private int playoff;

    public boolean tvr;

    public boolean sppStar;

    public League() {
    }

    public League(Integer id, String name, Integer nTeams, int ptsWin, int ptsTie, int ptsLose, boolean ptsTD, boolean ptsCAS, boolean ptsTDConceded, int treasury, int groups, int playoff, boolean tvr, boolean sppStar) {
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
        this.groups = groups;
        this.playoff = playoff;
        this.tvr = tvr;
        this.sppStar = sppStar;
    }

    public League(String name, int nTeams, int ptsWin, int ptsTie, int ptsLose, boolean ptsTD, boolean ptsCAS, boolean ptsTDConceded, int treasury, int groups, int playoff, boolean tvr, boolean sppStar) {
        this.name = name;
        this.nTeams = nTeams;
        this.ptsWin = ptsWin;
        this.ptsTie = ptsTie;
        this.ptsLose = ptsLose;
        this.ptsTD = ptsTD;
        this.ptsCAS = ptsCAS;
        this.ptsTDConceded = ptsTDConceded;
        this.treasury = treasury;
        this.groups = groups;
        this.playoff = playoff;
        this.tvr = tvr;
        this.sppStar = sppStar;
    }

    public League(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.nTeams = rs.getInt("nteams");
        this.ptsWin = rs.getInt("pts_win");
        this.ptsTie = rs.getInt("pts_tie");
        this.ptsLose = rs.getInt("pts_loss");
        this.ptsTD = rs.getBoolean("pts_td");
        this.ptsCAS = rs.getBoolean("pts_cas");
        this.ptsTDConceded = rs.getBoolean("pts_td_conceded");
        this.treasury = rs.getInt("treasury");
        this.groups = rs.getInt("round");
        this.playoff = rs.getInt("playoff");
        this.tvr = rs.getBoolean("tvr");
        this.sppStar = rs.getBoolean("spp_star");
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

    public Integer getNTeams() {
        return nTeams;
    }

    public void setNTeams(Integer nTeams) {
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

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public int getPlayoff() {
        return playoff;
    }

    public void setPlayoff(int playoff) {
        this.playoff = playoff;
    }

    public boolean isTvr() {
        return tvr;
    }

    public void setTvr(boolean tvr) {
        this.tvr = tvr;
    }

    public boolean isSppStar() {
        return sppStar;
    }

    public void setSppStar(boolean sppStar) {
        this.sppStar = sppStar;
    }
}
