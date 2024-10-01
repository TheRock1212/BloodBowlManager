package it.unipi.dataset;

import it.unipi.bloodbowlmanager.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Team {
    private int id;
    public String coach;
    public String name;
    private int race;
    private int league;
    public int ngiocatori;
    public int nreroll;
    public boolean apothecary;
    public int cheerleader;
    public int assistant;
    public int df;
    public int treasury;
    public int g;
    public int w;
    public int n;
    public int l;
    public int tdScored;
    public int tdConceded;
    public int tdNet;
    public int casInflicted;
    public int casSuffered;
    public int casNet;
    public int points;
    public int value;
    private int round;
    private int journeyman;

    public Team() {

    }

    public Team(int id, String coach, String name, int race, int league, int ngiocatori, int nreroll, boolean apothecary, int cheerleader, int assistant, int df, int treasury, int g, int w, int n, int l, int tdScored, int tdConceded, int casInflicted, int casSuffered, int points, int value, int round, int journeyman) {
        this.id = id;
        this.coach = coach;
        this.name = name;
        this.race = race;
        this.league = league;
        this.ngiocatori = ngiocatori;
        this.nreroll = nreroll;
        this.apothecary = apothecary;
        this.cheerleader = cheerleader;
        this.assistant = assistant;
        this.df = df;
        this.treasury = treasury;
        this.g = g;
        this.w = w;
        this.n = n;
        this.l = l;
        this.tdScored = tdScored;
        this.tdConceded = tdConceded;
        this.tdNet = tdScored - tdConceded;
        this.casInflicted = casInflicted;
        this.casSuffered = casSuffered;
        this.casNet = casInflicted - casSuffered;
        this.points = points;
        this.value = value;
        this.round = round;
        this.journeyman = journeyman;
    }

    public Team(String coach, String name, int race, int league, int ngiocatori, int nreroll, boolean apothecary, int cheerleader, int assistant, int df, int treasury, int g, int w, int n, int l, int tdScored, int tdConceded, int casInflicted, int casSuffered, int points, int value, int round, int journeyman) {
        this.coach = coach;
        this.name = name;
        this.race = race;
        this.league = league;
        this.ngiocatori = ngiocatori;
        this.nreroll = nreroll;
        this.apothecary = apothecary;
        this.cheerleader = cheerleader;
        this.assistant = assistant;
        this.df = df;
        this.treasury = treasury;
        this.g = g;
        this.w = w;
        this.n = n;
        this.l = l;
        this.tdScored = tdScored;
        this.tdConceded = tdConceded;
        this.tdNet = tdScored - tdConceded;
        this.casInflicted = casInflicted;
        this.casSuffered = casSuffered;
        this.casNet = casInflicted - casSuffered;
        this.points = points;
        this.value = value;
        this.round = round;
        this.journeyman = journeyman;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public int getNgiocatori() {
        return ngiocatori;
    }

    public void setNgiocatori(int ngiocatori) {
        this.ngiocatori = ngiocatori;
    }

    public int getNreroll() {
        return nreroll;
    }

    public void setNreroll(int nreroll) {
        this.nreroll = nreroll;
    }

    public boolean isApothecary() {
        return apothecary;
    }

    public void setApothecary(boolean apothecary) {
        this.apothecary = apothecary;
    }

    public int getCheerleader() {
        return cheerleader;
    }

    public void setCheerleader(int cheerleader) {
        this.cheerleader = cheerleader;
    }

    public int getAssistant() {
        return assistant;
    }

    public void setAssistant(int assistant) {
        this.assistant = assistant;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public int getTreasury() {
        return treasury;
    }

    public void setTreasury(int treasury) {
        this.treasury = treasury;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getTdScored() {
        return tdScored;
    }

    public void setTdScored(int tdScored) {
        this.tdScored = tdScored;
    }

    public int getTdConceded() {
        return tdConceded;
    }

    public void setTdConceded(int tdConceded) {
        this.tdConceded = tdConceded;
    }

    public int getCasInflicted() {
        return casInflicted;
    }

    public void setCasInflicted(int casInflicted) {
        this.casInflicted = casInflicted;
    }

    public int getCasSuffered() {
        return casSuffered;
    }

    public void setCasSuffered(int casSuffered) {
        this.casSuffered = casSuffered;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTdNet() {
        return tdNet;
    }

    public void setTdNet(int tdNet) {
        this.tdNet = tdNet;
    }

    public int getCasNet() {
        return casNet;
    }

    public void setCasNet(int casNet) {
        this.casNet = casNet;
    }

    public int getJourneyman() {
        return journeyman;
    }

    public void setJourneyman(int journeyman) {
        this.journeyman = journeyman;
    }

    /**
     * Restutisce il resul set dei team
     * @param id identificatore del team. Se è 0 si prendono tutti i team
     * @return
     * @throws SQLException
     */
    public ResultSet getTeam(int id, int league) throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs;
        if(id != 0)
            rs = st.executeQuery("SELECT * FROM team WHERE id = " + id + " AND league = " + league);
        else
            rs = st.executeQuery("SELECT * FROM team" + " WHERE league = " + league);
        return rs;
    }

    public void addTeam() throws SQLException{
        PreparedStatement ps = App.getConnection().prepareStatement("INSERT INTO team(coach, name, race, league, ngiocatori, nreroll, apothecary, cheerleader, assistant, DF, treasury, G, W, N, L, TDScored, TDConceded, CASInflicted, CASSuffered, PTS, value, round, journeyman) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, coach);
        ps.setString(2, name);
        ps.setInt(3, race);
        ps.setInt(4, league);
        ps.setInt(5, ngiocatori);
        ps.setInt(6, nreroll);
        ps.setBoolean(7, apothecary);
        ps.setInt(8, cheerleader);
        ps.setInt(9, assistant);
        ps.setInt(10, df);
        ps.setInt(11, treasury);
        ps.setInt(12, g);
        ps.setInt(13, w);
        ps.setInt(14, n);
        ps.setInt(15, l);
        ps.setInt(16, tdScored);
        ps.setInt(17, tdConceded);
        ps.setInt(18, casInflicted);
        ps.setInt(19, casSuffered);
        ps.setInt(20, points);
        ps.setInt(21, value);
        ps.setInt(22, round);
        ps.setInt(23, journeyman);
        ps.executeUpdate();

        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
        while(rs.next())
            id = rs.getInt(1);

    }

    public void removeTeam() throws SQLException{
        PreparedStatement p = App.getConnection().prepareStatement("DELETE FROM player WHERE team = ?");
        p.setInt(1, id);
        p.executeUpdate();
        PreparedStatement ps = App.getConnection().prepareStatement("DELETE FROM team WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void updatePlayer() throws SQLException{
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE team SET ngiocatori = ?, treasury = ?, value = ? WHERE id = ?");
        ps.setInt(1, ngiocatori);
        ps.setInt(2, treasury);
        ps.setInt(3, value);
        ps.setInt(4, id);
        ps.executeUpdate();
    }

    public void updateStaff() throws SQLException{
        PreparedStatement ps = App.getConnection().prepareStatement("UPDATE team SET nreroll = ?,  apothecary = ?, cheerleader = ?, assistant = ?, treasury = ?, value = ? WHERE id = ?");
        ps.setInt(1, nreroll);
        ps.setInt(3, cheerleader);
        ps.setBoolean(2, apothecary);
        ps.setInt(4, assistant);
        ps.setInt(5, treasury);
        ps.setInt(6, value);
        ps.setInt(7, id);
        ps.executeUpdate();
    }

    public int getPositional(int id) throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM player WHERE player_template = " + id + " AND team = " + this.id + " AND status = 1 AND isjourney = 0");
        rs.next();
        return rs.getInt(1);
    }

    public int[] getPlayerNumbers() throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT number FROM player WHERE team = " + this.id + " AND status = 1 AND isjourney = 0");
        int[] nrs = new int[ngiocatori];
        int i = 0;
        while(rs.next()) {
            nrs[i++] = rs.getInt(1);
        }
        return nrs;
    }

    public String[] getPlayerNames() throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT name FROM player WHERE team = " + this.id + " AND status = 1 AND isjourney = 0");
        String[] names = new String[ngiocatori];
        int i = 0;
        while(rs.next()) {
            names[i++] = rs.getString(1);
        }
        return names;
    }

    public Player[] getJourneymans() throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT COUNT(*) from player WHERE team = " + this.id + " AND status = 1 AND isjourney = 1");
        rs.next();
        System.out.println(rs.getInt(1));
        if(rs.getInt(1) == 0)
            return null;
        Player[] journeymans = new Player[rs.getInt(1)];
        st = App.getConnection().createStatement();
        rs = st.executeQuery("SELECT * from player WHERE team = " + this.id + " AND status = 1 AND isjourney = 1");
        int i = 0;
        while(rs.next()) {
            journeymans[i++] = new Player(rs.getInt("id"), rs.getInt("number"), rs.getString("name"), rs.getInt("player_template"), rs.getInt("team"), rs.getInt("unspentSPP"), rs.getInt("SPP"), rs.getString("new_skill"), rs.getInt("MA_inc"), rs.getInt("ST_inc"), rs.getInt("AG_inc"), rs.getInt("PA_inc"), rs.getInt("AV_inc"), rs.getInt("MA_dec"), rs.getInt("ST_dec"), rs.getInt("AG_dec"), rs.getInt("PA_dec"), rs.getInt("AV_dec"), rs.getInt("NIG"), rs.getBoolean("MNG"), rs.getInt("val"), rs.getInt("TD"), rs.getInt("CAS"), rs.getInt("K"), rs.getInt("CP"), rs.getInt("D"), rs.getInt("I"), rs.getInt("lev"), rs.getBoolean("status"), rs.getBoolean("isjourney"));
        }
        return journeymans;
    }

    public String getName(int team) throws SQLException {
        Statement st = App.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT name FROM team WHERE id = " + team);
        rs.next();
        return rs.getString(1);
    }
}
