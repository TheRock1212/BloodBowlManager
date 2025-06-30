package it.unipi.dataset.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Bounty {
    private int league;
    private int team;
    private int player;
    private int reward;

    private String nameTeam;
    private String namePlayer;

    public Bounty() {

    }

    public Bounty(int league, int team, int player, int reward) {
        this.league = league;
        this.team = team;
        this.player = player;
        this.reward = reward;
    }

    public Bounty(ResultSet rs) throws SQLException {
        this.team = rs.getInt("team");
        this.player = rs.getInt("player");
        this.reward = rs.getInt("reward");
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }
}
