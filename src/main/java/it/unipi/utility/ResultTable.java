package it.unipi.utility;

import it.unipi.dataset.Model.Result;

public class ResultTable extends Result {
    public String home, away;

    public ResultTable(Result r, String home, String away) {
        super(r);
        this.home = home;
        this.away = away;
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
}
