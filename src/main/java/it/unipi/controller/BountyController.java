package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.BountyDao;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Bounty;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class BountyController {
    private @FXML ComboBox<String> team, teamVictim;
    private @FXML ComboBox<Integer> victim;
    private @FXML Spinner<Integer> reward;
    private @FXML Label error;

    public static Bounty b = null;
    private String data;

    public @FXML void initialize() throws Exception {
        Connection.params.put("league", App.getLeague().getId());
        data = Connection.getConnection("/api/v1/team/teams", Connection.GET, null);
        List<Team> teams = JsonExploiter.getListFromJson(Team.class, data);
        teams.forEach(t -> {
            team.getItems().add(t.getName());
            teamVictim.getItems().add(t.getName());
        });
        SpinnerValueFactory<Integer> r = new SpinnerValueFactory.IntegerSpinnerValueFactory(5000, 200000, 5000, 5000);
        reward.setValueFactory(r);
    }

    public @FXML void setTreasury() throws Exception {
        Connection.params.put("league", App.getLeague().getId());
        data = Connection.getConnection("/api/v1/team/teams", Connection.GET, null);
        List<Team> teams = JsonExploiter.getListFromJson(Team.class, data);
        teamVictim.getItems().clear();
        teams.forEach(t -> teamVictim.getItems().add(t.getName()));
        teamVictim.getItems().remove(team.getValue());
    }

    public @FXML void setPlayers() throws Exception {
        Connection.params.put("teamName", URLEncoder.encode(teamVictim.getValue()));
        Connection.params.put("status", true);
        data = Connection.getConnection("/api/v1/player/teamName", Connection.GET, null);
        List<Player> players = JsonExploiter.getListFromJson(Player.class, data);
        players.sort(Comparator.comparing(Player::getNumber));
        players.forEach(player -> victim.getItems().add(player.number));
        victim.getSelectionModel().select(0);
    }

    public @FXML void add() throws SQLException, IOException {
        if(team.getSelectionModel().getSelectedItem() == null || teamVictim.getSelectionModel().getSelectedItem() == null) {
            error.setText("Please select a team");
            error.setVisible(true);
            return;
        }
        Connection.params.put("name", URLEncoder.encode(team.getValue()));
        data = Connection.getConnection("/api/v1/team/team", Connection.GET, null);
        Team t = JsonExploiter.getObjectFromJson(Team.class, data);
        if((reward.getValue() / 1000) > t.treasury) {
            error.setText("Reward too high");
            error.setVisible(true);
            return;
        }
        Bounty bounty = new Bounty();
        bounty.setReward(reward.getValue() / 1000);
        Connection.params.put("number", victim.getValue());
        Connection.params.put("teamName", URLEncoder.encode(teamVictim.getValue()));
        data = Connection.getConnection("/api/v1/player/playerNumbers", Connection.GET, null);
        Player p = JsonExploiter.getObjectFromJson(Player.class, data);
        t.treasury -= bounty.getReward();
        bounty.setPlayer(p.getId());
        bounty.setTeam(t.getId());
        bounty.setLeague(App.getLeague().getId());
        Connection.getConnection("/api/v1/team/add", Connection.POST, JsonExploiter.toJson(t));
        Connection.getConnection("/api/v1/bounty/add", Connection.POST, JsonExploiter.toJson(bounty));
        Stage stage = (Stage) error.getScene().getWindow();
        stage.close();
        App.setRoot("dashboard");
    }

}
