package it.unipi.utility;

import it.unipi.dataset.Model.Race;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.scene.image.Image;

import java.sql.SQLException;

public class TeamStatistic {
    private Image img;
    public String name;
    public String coach;
    public int tdFor;
    public int tdAgainst;
    public int casFor;
    public int casAgainst;
    public int kill;
    public int cp;
    public int inter;
    private int value;

    public TeamStatistic(Team t) throws Exception {
        this.name = t.getName();
        this.coach = t.getCoach();
        this.tdFor = t.tdScored;
        this.tdAgainst = t.tdConceded;
        this.casFor = t.casInflicted;
        this.casAgainst = t.casSuffered;
        Connection.params.put("team", t.getId());
        Connection.params.put("type", "kill");
        this.kill = Integer.parseInt(Connection.getConnection("/api/v1/player/stats", Connection.GET, null));
        Connection.params.put("team", t.getId());
        Connection.params.put("type", "cp");
        this.cp = Integer.parseInt(Connection.getConnection("/api/v1/player/stats", Connection.GET, null));
        Connection.params.put("team", t.getId());
        Connection.params.put("type", "int");
        this.inter = Integer.parseInt(Connection.getConnection("/api/v1/player/stats", Connection.GET, null));
        this.value = this.tdFor;
        Connection.params.put("id", t.getRace());
        Race r = JsonExploiter.getObjectFromJson(Race.class, Connection.getConnection("/api/v1/race/race", Connection.GET, null));
        this.img = new Image(getClass().getResource("/it/unipi/bloodbowlmanager/race/" + r.url + ".png").toExternalForm());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public int getTdFor() {
        return tdFor;
    }

    public void setTdFor(int tdFor) {
        this.tdFor = tdFor;
    }

    public int getTdAgainst() {
        return tdAgainst;
    }

    public void setTdAgainst(int tdAgainst) {
        this.tdAgainst = tdAgainst;
    }

    public int getCasFor() {
        return casFor;
    }

    public void setCasFor(int casFor) {
        this.casFor = casFor;
    }

    public int getCasAgainst() {
        return casAgainst;
    }

    public void setCasAgainst(int casAgainst) {
        this.casAgainst = casAgainst;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getInter() {
        return inter;
    }

    public void setInter(int inter) {
        this.inter = inter;
    }

    public int getValue() {
        return value;
    }

    public void setValue(String name) {
        switch (name) {
            case "Best Offence": {
                value = tdFor;
                break;
            }
            case "Best Defence": {
                value = tdAgainst;
                break;
            }
            case "Most Roughtest": {
                value = casFor;
                break;
            }
            case "Most Toughtest": {
                value = casAgainst;
                break;
            }
            case "Best Killers": {
                value = kill;
                break;
            }
            case "Most Passes": {
                value = cp;
                break;
            }
            case "Most Interceptions": {
                value = inter;
                break;
            }
        }
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
