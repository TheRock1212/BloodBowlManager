package it.unipi.dataset;

public class Team {
    private int id;
    private String coach;
    private String name;
    private int race;
    private int league;
    private int ngiocatori;
    private int nreroll;
    private boolean apothecary;
    private int cheerleader;
    private int assistant;
    private int df;
    private int treasury;
    private int g;
    private int w;
    private int n;
    private int l;
    private int tdScored;
    private int tdConceded;
    private int casInflicted;
    private int casSuffered;
    private int points;
    private int value;
    private int round;

    public Team() {

    }

    public Team(int id, String coach, String name, int race, int league, int ngiocatori, int nreroll, boolean apothecary, int cheerleader, int assistant, int df, int treasury, int g, int w, int n, int l, int tdScored, int tdConceded, int casInflicted, int casSuffered, int points, int value, int round) {
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
        this.casInflicted = casInflicted;
        this.casSuffered = casSuffered;
        this.points = points;
        this.value = value;
        this.round = round;
    }

    public Team(String coach, String name, int race, int league, int ngiocatori, int nreroll, boolean apothecary, int cheerleader, int assistant, int df, int treasury, int g, int w, int n, int l, int tdScored, int tdConceded, int casInflicted, int casSuffered, int points, int value, int round) {
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
        this.casInflicted = casInflicted;
        this.casSuffered = casSuffered;
        this.points = points;
        this.value = value;
        this.round = round;
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
}
