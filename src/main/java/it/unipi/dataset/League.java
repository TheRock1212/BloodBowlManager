package it.unipi.dataset;

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

    public League() {
    }

    public League(Integer id, String name, Integer nTeams, int ptsWin, int ptsTie, int ptsLose, boolean ptsTD, boolean ptsCAS, boolean ptsTDConceded, int treasury, int groups, int playoff) {
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
    }

    public League(String name, int nTeams, int ptsWin, int ptsTie, int ptsLose, boolean ptsTD, boolean ptsCAS, boolean ptsTDConceded, int treasury, int groups, int playoff) {
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

    /**
     * Aggiunge la lega al database
     * @throws SQLException
     */
    public void addLeague() throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO league(name, nteams, pts_win, pts_tie, pts_loss, pts_td, pts_cas, pts_td_conceded, treasury, round, playoff) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, getName());
        ps.setInt(2, getNTeams());
        ps.setInt(3, getPtsWin());
        ps.setInt(4, getPtsTie());
        ps.setInt(5, getPtsLose());
        ps.setBoolean(6, isPtsTD());
        ps.setBoolean(7, isPtsCAS());
        ps.setBoolean(8, isPtsTDConceded());
        ps.setInt(9, getTreasury());
        ps.setInt(10, getGroups());
        ps.setInt(11, getPlayoff());
        ps.executeUpdate();
    }

    /**
     * Seleziona dal database la/e lega/ghe selezionate
     * @param id identifcatore della lega. Se è 0 allora indica l'elenco di leghe
     * @return
     * @throws SQLException
     */
    public ResultSet getLeagues(int id) throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs;
        if(id != 0)
            rs = st.executeQuery("SELECT * FROM league WHERE id = " + id);
        else
            rs = st.executeQuery("SELECT * FROM league");
        return rs;
    }

    /**
     * Elimina le lega selezionata
     * @param id Identificatore della lega
     * @throws SQLException
     */
    public void removeLeague(int id) throws SQLException {
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM league WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public int getNrGroups() throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT round FROM league WHERE id = " + getId());
        rs.next();
        return rs.getInt("round");
    }

    public int[] getTeams(int gr) throws SQLException {
        int[] teams = new int[getNTeams()];
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM team WHERE league = " + id + " AND round = " + gr);
        int i = 0;
        while (rs.next()) {
            teams[i++] = rs.getInt("id");
        }
        return teams;
    }
}
