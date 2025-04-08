package it.unipi.dataset.Model;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.utility.Pair;
import it.unipi.utility.ResultGame;

import java.sql.*;

public class Result {
    private int id;
    public int league;
    public int teamH;
    public int teamA;
    public int tdh;
    public int tda;
    public int cash;
    public int casa;
    public int killh;
    public int killa;
    public int cph;
    public int cpa;
    public int dech;
    public int deca;
    public int inth;
    public int inta;
    public int dfh;
    public int dfa;
    public int winh;
    public int wina;
    public boolean played;
    public Date date;
    private boolean playoff;

    public Result(int league, int teamH, int teamA, int tdh, int tda, int cash, int casa, int killh, int killa, int cph, int cpa, int dech, int deca, int inth, int inta, int dfh, int dfa, int winh, int wina, boolean played, Date date, boolean playoff) {
        this.league = league;
        this.teamH = teamH;
        this.teamA = teamA;
        this.tdh = tdh;
        this.tda = tda;
        this.cash = cash;
        this.casa = casa;
        this.killh = killh;
        this.killa = killa;
        this.cph = cph;
        this.cpa = cpa;
        this.dech = dech;
        this.deca = deca;
        this.inth = inth;
        this.inta = inta;
        this.dfh = dfh;
        this.dfa = dfa;
        this.winh = winh;
        this.wina = wina;
        this.played = played;
        this.date = date;
        this.playoff = playoff;
    }

    public Result() {
    }

    public Result(Result r) {
        this.id = r.getId();
        this.league = r.league;
        this.teamH = r.teamH;
        this.teamA = r.teamA;
        this.tdh = r.tdh;
        this.tda = r.tda;
        this.cash = r.cash;
        this.casa = r.casa;
        this.killh = r.killh;
        this.killa = r.killa;
        this.cph = r.cph;
        this.cpa = r.cpa;
        this.dech = r.dech;
        this.deca = r.deca;
        this.inth = r.inth;
        this.inta = r.inta;
        this.dfh = r.dfh;
        this.dfa = r.dfa;
        this.winh = r.winh;
        this.wina = r.wina;
        this.played = r.played;
        this.date = r.date;
        this.playoff = r.playoff;
    }

    public Result(int id, int league, int teamH, int teamA, int tdh, int tda, int cash, int casa, int killh, int killa, int cph, int cpa, int dech, int deca, int inth, int inta, int dfh, int dfa, int winh, int wina, boolean played, Date date, boolean playoff) {
        this.id = id;
        this.league = league;
        this.teamH = teamH;
        this.teamA = teamA;
        this.tdh = tdh;
        this.tda = tda;
        this.cash = cash;
        this.casa = casa;
        this.killh = killh;
        this.killa = killa;
        this.cph = cph;
        this.cpa = cpa;
        this.dech = dech;
        this.deca = deca;
        this.inth = inth;
        this.inta = inta;
        this.dfh = dfh;
        this.dfa = dfa;
        this.winh = winh;
        this.wina = wina;
        this.played = played;
        this.date = date;
        this.playoff = playoff;
    }

    public Result(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.league = rs.getInt("league");
        this.teamH = rs.getInt("teamH");
        this.teamA = rs.getInt("teamA");
        this.tdh = rs.getInt("tdH");
        this.tda = rs.getInt("tdA");
        this.cash = rs.getInt("casH");
        this.casa = rs.getInt("casA");
        this.killh = rs.getInt("killH");
        this.killa = rs.getInt("killA");
        this.cph = rs.getInt("cpH");
        this.cpa = rs.getInt("cpA");
        this.dech = rs.getInt("decH");
        this.deca = rs.getInt("decA");
        this.inth = rs.getInt("intH");
        this.inta = rs.getInt("intA");
        this.dfh = rs.getInt("dfH");
        this.dfa = rs.getInt("dfA");
        this.winh = rs.getInt("winH");
        this.wina = rs.getInt("winA");
        this.played = rs.getBoolean("played");
        this.date = rs.getDate("date");
        this.playoff = rs.getBoolean("playoff");
    }

    public Result(ResultGame rg) {
        this.id = rg.getId();
        this.league = rg.league;
        this.teamH = rg.teamH;
        this.teamA = rg.teamA;
        this.tdh = rg.tdh;
        this.tda = rg.tda;
        this.cash = rg.cash;
        this.casa = rg.casa;
        this.killh = rg.killh;
        this.killa = rg.killa;
        this.cph = rg.cph;
        this.cpa = rg.cpa;
        this.dech = rg.dech;
        this.deca = rg.deca;
        this.inth = rg.inth;
        this.inta = rg.inta;
        this.dfh = rg.dfh;
        this.dfa = rg.dfa;
        this.winh = rg.winh;
        this.wina = rg.wina;
        this.played = rg.played;
        this.date = rg.date;
        this.playoff = rg.isPlayoff();
    }

    public Result(int league, int teamH, int teamA) {
        this.league = league;
        this.teamH = teamH;
        this.teamA = teamA;
    }



    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public int getTeamH() {
        return teamH;
    }

    public void setTeamH(int teamH) {
        this.teamH = teamH;
    }

    public int getTeamA() {
        return teamA;
    }

    public void setTeamA(int teamA) {
        this.teamA = teamA;
    }

    public int getTdh() {
        return tdh;
    }

    public void setTdh(int tdh) {
        this.tdh = tdh;
    }

    public int getTda() {
        return tda;
    }

    public void setTda(int tda) {
        this.tda = tda;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCasa() {
        return casa;
    }

    public void setCasa(int casa) {
        this.casa = casa;
    }

    public int getKillh() {
        return killh;
    }

    public void setKillh(int killh) {
        this.killh = killh;
    }

    public int getKilla() {
        return killa;
    }

    public void setKilla(int killa) {
        this.killa = killa;
    }

    public int getCph() {
        return cph;
    }

    public void setCph(int cph) {
        this.cph = cph;
    }

    public int getCpa() {
        return cpa;
    }

    public void setCpa(int cpa) {
        this.cpa = cpa;
    }

    public int getDech() {
        return dech;
    }

    public void setDech(int dech) {
        this.dech = dech;
    }

    public int getDeca() {
        return deca;
    }

    public void setDeca(int deca) {
        this.deca = deca;
    }

    public int getInth() {
        return inth;
    }

    public void setInth(int inth) {
        this.inth = inth;
    }

    public int getInta() {
        return inta;
    }

    public void setInta(int inta) {
        this.inta = inta;
    }

    public int getDfh() {
        return dfh;
    }

    public void setDfh(int dfh) {
        this.dfh = dfh;
    }

    public int getDfa() {
        return dfa;
    }

    public void setDfa(int dfa) {
        this.dfa = dfa;
    }

    public int getWinh() {
        return winh;
    }

    public void setWinh(int winh) {
        this.winh = winh;
    }

    public int getWina() {
        return wina;
    }

    public void setWina(int wina) {
        this.wina = wina;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String[] getNames() throws SQLException {
        String[] res = new String[2];
        Team t = new Team();
        res[0] = TeamDao.getName(getTeamH());
        res[1] = TeamDao.getName(getTeamA());
        return res;
    }

    public boolean isPlayoff() {
        return playoff;
    }

    public void setPlayoff(boolean playoff) {
        this.playoff = playoff;
    }
}
