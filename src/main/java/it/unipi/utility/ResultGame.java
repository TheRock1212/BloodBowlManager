package it.unipi.utility;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.Result;
import it.unipi.dataset.Model.StarPlayer;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultGame extends ResultTable {
    private Team tH;
    private Team tA;
    private List<Player> playersH;
    private List<Player> playersA;
    private List<Integer> killedH, killedA;
    private List<StarPlayer> starsH, starsA;

    public ResultGame(ResultTable rt) throws Exception {
        super(rt.getR(), rt.home, rt.away);
        this.tH = TeamDao.getTeam(this.teamH, App.getLeague().getId()).getFirst();
        this.tA = TeamDao.getTeam(this.teamA, App.getLeague().getId()).getFirst();
        this.playersH = PlayerDao.getStarting(this.tH.getId());
        this.playersA = PlayerDao.getStarting(this.tA.getId());
        PlayerDao.setMng(this.tH);
        PlayerDao.setMng(this.tA);
        this.killedH = new ArrayList<>();
        this.killedA = new ArrayList<>();
        String data = "";
        Connection.params.put("race", this.tH.getRace());
        Connection.params.put("petty", this.tA.value - this.tH.value);
        data = Connection.getConnection("/api/v1/inducements/stars", Connection.GET, null);
        starsH = JsonExploiter.getListFromJson(StarPlayer.class, data);
        Connection.params.put("race", this.tA.getRace());
        Connection.params.put("petty", this.tH.value - this.tA.value);
        data = Connection.getConnection("/api/v1/inducements/stars", Connection.GET, null);
        starsA = JsonExploiter.getListFromJson(StarPlayer.class, data);
    }

    public Team gettH() {
        return tH;
    }

    public void settH(Team tH) {
        this.tH = tH;
    }

    public Team gettA() {
        return tA;
    }

    public void settA(Team tA) {
        this.tA = tA;
    }

    public List<Player> getPlayersH() {
        return playersH;
    }

    public void setPlayersH(List<Player> playersH) {
        this.playersH = playersH;
    }

    public List<Player> getPlayersA() {
        return playersA;
    }

    public void setPlayersA(List<Player> playersA) {
        this.playersA = playersA;
    }

    public List<Integer> getKilledH() {
        return killedH;
    }

    public void setKilledH(List<Integer> killedH) {
        this.killedH = killedH;
    }

    public List<Integer> getKilledA() {
        return killedA;
    }

    public void setKilledA(List<Integer> killedA) {
        this.killedA = killedA;
    }

    public List<StarPlayer> getStarsH() {
        return starsH;
    }

    public void setStarsH(List<StarPlayer> starsH) {
        this.starsH = starsH;
    }

    public List<StarPlayer> getStarsA() {
        return starsA;
    }

    public void setStarsA(List<StarPlayer> starsA) {
        this.starsA = starsA;
    }
}
