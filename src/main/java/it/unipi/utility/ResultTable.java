package it.unipi.utility;

import it.unipi.dataset.Model.Result;

import java.util.Date;

public class ResultTable extends Result {
    public String home, away;
    private Result r;
    public Date datePlayed;

    public ResultTable(Result r, String home, String away) {
        super(r);
        this.home = home;
        this.away = away;
        this.datePlayed = r.date;
        this.r = r;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public Result getR() {
        return r;
    }

    public void setR(Result r) {
        this.r = r;
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
    }
}
