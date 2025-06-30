package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class League {
    private Integer id;

    private String name;

    private int teams;

    private int win;

    private int tie;

    private int loss;

    private boolean ptsTd;

    private boolean ptsCas;

    private boolean ptsTdCon;

    private int treasury;

    private int round;

    private int playoff;

    private boolean tvr;

    private boolean sppstar;

    private boolean perennial;

    private int fixture;

    private Date expire;

    public League() {
    }

    public League(Integer id, String name, int teams, int win, int tie, int loss, boolean ptstd, boolean ptscas, boolean ptstdcon, int treasury, int round, int playoff, boolean tvr, boolean sppstar, boolean perennial, int fixture, Date expire) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.win = win;
        this.tie = tie;
        this.loss = loss;
        this.ptsTd = ptstd;
        this.ptsCas = ptscas;
        this.ptsTdCon = ptstdcon;
        this.treasury = treasury;
        this.round = round;
        this.playoff = playoff;
        this.tvr = tvr;
        this.sppstar = sppstar;
        this.perennial = perennial;
        this.fixture = fixture;
        this.expire = expire;
    }

    public League(String name, int teams, int win, int tie, int loss, boolean ptstd, boolean ptscas, boolean ptstdcon, int treasury, int round, int playoff, boolean tvr, boolean sppstar, boolean perennial, int fixture, Date expire) {
        this.name = name;
        this.teams = teams;
        this.win = win;
        this.tie = tie;
        this.loss = loss;
        this.ptsTd = ptstd;
        this.ptsCas = ptscas;
        this.ptsTdCon = ptstdcon;
        this.treasury = treasury;
        this.round = round;
        this.playoff = playoff;
        this.tvr = tvr;
        this.sppstar = sppstar;
        this.perennial = perennial;
        this.fixture = fixture;
        this.expire = expire;
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

    public int getTeams() {
        return teams;
    }

    public void setTeams(int teams) {
        this.teams = teams;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getTie() {
        return tie;
    }

    public void setTie(int tie) {
        this.tie = tie;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public boolean isPtsTd() {
        return ptsTd;
    }

    public void setPtsTd(boolean ptsTd) {
        this.ptsTd = ptsTd;
    }

    public boolean isPtsCas() {
        return ptsCas;
    }

    public void setPtsCas(boolean ptsCas) {
        this.ptsCas = ptsCas;
    }

    public boolean isPtsTdCon() {
        return ptsTdCon;
    }

    public void setPtsTdCon(boolean ptsTdCon) {
        this.ptsTdCon = ptsTdCon;
    }

    public int getTreasury() {
        return treasury;
    }

    public void setTreasury(int treasury) {
        this.treasury = treasury;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
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

    public boolean isSppstar() {
        return sppstar;
    }

    public void setSppstar(boolean sppstar) {
        this.sppstar = sppstar;
    }

    public boolean isPerennial() {
        return perennial;
    }

    public void setPerennial(boolean perennial) {
        this.perennial = perennial;
    }

    public int getFixture() {
        return fixture;
    }

    public void setFixture(int fixture) {
        this.fixture = fixture;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
