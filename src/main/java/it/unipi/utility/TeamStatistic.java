package it.unipi.utility;

import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.RaceDao;
import it.unipi.dataset.Model.Team;
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

    public TeamStatistic(Team t) throws SQLException {
        this.name = t.getName();
        this.coach = t.getCoach();
        this.tdFor = t.tdScored;
        this.tdAgainst = t.tdConceded;
        this.casFor = t.casInflicted;
        this.casAgainst = t.casSuffered;
        this.kill = PlayerDao.getStatistic(t.getId(), "kill");
        this.cp = PlayerDao.getStatistic(t.getId(), "cp");
        this.inter = PlayerDao.getStatistic(t.getId(), "int");
        this.value = this.tdFor;
        this.img = new Image(getClass().getResource("/it/unipi/bloodbowlmanager/race/" + RaceDao.getRace(t.getRace()).url + ".png").toExternalForm());
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
