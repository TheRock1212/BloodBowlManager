package it.unipi.utility;

import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.PlayerTemplate;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class PlayerStatistic {
    public String name;
    public String coach;
    public String role;
    public int spp;
    public int td;
    public int cas;
    public int kill;
    public int cp;
    public int dec;
    public int inte;
    public String teamName;
    @FXML public ImageView img;
    public int value;

    public PlayerStatistic(Player p, PlayerTemplate ti) throws SQLException {
        this.img = new ImageView();
        this.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
        this.teamName = TeamDao.getName(p.getTeam());
        this.coach = TeamDao.getCoach(p.getTeam());
        this.name = p.getName();
        this.spp = p.getSpp();
        this.td = p.getTd();
        this.cas = p.getCas();
        this.kill = p.getKill();
        this.cp = p.getCp();
        this.dec = p.getDef();
        this.inte = p.getInter();
        this.value = this.spp;
        this.role = ti.position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpp() {
        return spp;
    }

    public void setSpp(int spp) {
        this.spp = spp;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public int getCas() {
        return cas;
    }

    public void setCas(int cas) {
        this.cas = cas;
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

    public int getDec() {
        return dec;
    }

    public void setDec(int dec) {
        this.dec = dec;
    }

    public int getInte() {
        return inte;
    }

    public void setInte(int inte) {
        this.inte = inte;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public int getValue() {
        return value;
    }

    public void setValue(String name) {
        switch (name) {
            case "Best Players": {
                value = spp;
                break;
            }
            case "Best Scorers": {
                value = td;
                break;
            }
            case "Most Vicious": {
                value = cas;
                break;
            }
            case "Best Killers": {
                value = kill;
                break;
            }
            case "Best Passers": {
                value = cp;
                break;
            }
            case "Best Interceptors": {
                value = inte;
                break;
            }
        }
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
