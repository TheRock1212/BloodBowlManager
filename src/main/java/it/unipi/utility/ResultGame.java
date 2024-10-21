package it.unipi.utility;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.Result;
import it.unipi.dataset.Model.Team;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultGame extends ResultTable {
    private Team tH;
    private Team tA;
    private List<Player> playersH;
    private List<Player> playersA;
    private List<Integer> killedH, killedA;

    public ResultGame(ResultTable rt) throws SQLException {
        super(rt.getR(), rt.home, rt.away);
        this.tH = TeamDao.getTeam(this.teamH, App.getLeague().getId()).getFirst();
        this.tA = TeamDao.getTeam(this.teamA, App.getLeague().getId()).getFirst();
        this.playersH = PlayerDao.getStarting(this.tH.getId());
        this.playersA = PlayerDao.getStarting(this.tA.getId());
        PlayerDao.setMng(this.tH);
        PlayerDao.setMng(this.tA);
        this.killedH = new ArrayList<>();
        this.killedA = new ArrayList<>();
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
}
