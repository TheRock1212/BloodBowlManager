package it.unipi.dataset.Model;

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
    public String sponsor;
    private boolean ready;
    private int delta;
    private double discr;
    private boolean active;
    private int cards;
    private String stadium;

    public Team() {

    }

    public Team(int id, String coach, String name, int race, int league, int ngiocatori, int nreroll, boolean apothecary, int cheerleader, int assistant, int df, int treasury, int g, int w, int n, int l, int tdScored, int tdConceded, int casInflicted, int casSuffered, int points, int value, int round, int journeyman, String sponsor, boolean ready, boolean active, int cards, String stadium) {
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
        this.sponsor = sponsor;
        this.ready = ready;
        this.active = active;
        this.cards = cards;
        this.stadium = stadium;
    }

    public Team(String coach, String name, int race, int league, int ngiocatori, int nreroll, boolean apothecary, int cheerleader, int assistant, int df, int treasury, int g, int w, int n, int l, int tdScored, int tdConceded, int casInflicted, int casSuffered, int points, int value, int round, int journeyman, String sponsor, boolean active, int cards, String stadium) {
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
        this.sponsor = sponsor;
        this.ready = true;
        this.delta = this.tdScored * 3 + this.casInflicted * 2;
        this.discr = this.points + ((double) this.delta / 10000) + ((double) this.tdNet / 100000) + ((double) this.casNet / 1000000);
        this.active = active;
        this.cards = cards;
        this.stadium = stadium;
    }

    public Team(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.coach = rs.getString("coach");
        this.name = rs.getString("name");
        this.race = rs.getInt("race");
        this.league = rs.getInt("league");
        this.ngiocatori = rs.getInt("ngiocatori");
        this.nreroll = rs.getInt("nreroll");
        this.apothecary = rs.getBoolean("apothecary");
        this.cheerleader = rs.getInt("cheerleader");
        this.assistant = rs.getInt("assistant");
        this.df = rs.getInt("DF");
        this.treasury = rs.getInt("treasury");
        this.g = rs.getInt("G");
        this.w = rs.getInt("W");
        this.n = rs.getInt("N");
        this.l = rs.getInt("L");
        this.tdScored = rs.getInt("TDscored");
        this.tdConceded = rs.getInt("TDconceded");
        this.tdNet = tdScored - tdConceded;
        this.casInflicted = rs.getInt("CASinflicted");
        this.casSuffered = rs.getInt("CASsuffered");
        this.casNet = casInflicted - casSuffered;
        this.points = rs.getInt("PTS");
        this.value = rs.getInt("value");
        this.round = rs.getInt("round");
        this.journeyman = rs.getInt("journeyman");
        this.sponsor = rs.getString("sponsor");
        this.ready = rs.getBoolean("ready");
        this.active = rs.getBoolean("active");
        this.cards = rs.getInt("cards");
        this.stadium = rs.getString("stadium");
        this.delta = this.tdScored * 3 + this.casInflicted * 2;
        this.discr = this.points + ((double) this.delta / 10000) + ((double) this.tdNet / 100000) + ((double) this.casNet / 1000000);
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

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public double getDiscr() {
        return discr;
    }

    public void setDiscr(double discr) {
        this.discr = discr;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
}
