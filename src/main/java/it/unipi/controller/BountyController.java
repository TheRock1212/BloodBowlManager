package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.BountyDao;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Bounty;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class BountyController {
    private @FXML ComboBox<String> team, teamVictim;
    private @FXML ComboBox<Integer> victim;
    private @FXML Spinner<Integer> reward;
    private @FXML Label error;

    public static Bounty b = null;

    public @FXML void initialize() throws SQLException {
        List<Team> teams = TeamDao.getTeam(0, App.getLeague().getId());
        teams.forEach(t -> {
            team.getItems().add(t.getName());
            teamVictim.getItems().add(t.getName());
        });
        SpinnerValueFactory<Integer> r = new SpinnerValueFactory.IntegerSpinnerValueFactory(5000, 200000, 5000, 5000);
        reward.setValueFactory(r);
    }

    public @FXML void setTreasury() throws SQLException {
        List<Team> teams = TeamDao.getTeam(0, App.getLeague().getId());
        teamVictim.getItems().clear();
        teams.forEach(t -> teamVictim.getItems().add(t.getName()));
        teamVictim.getItems().remove(team.getValue());
    }

    public @FXML void setPlayers() throws SQLException {
        List<Player> players = PlayerDao.getPlayersByTeamName(teamVictim.getValue(), true);
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
        Team t = TeamDao.getTeam(team.getValue());
        if((reward.getValue() / 1000) > t.treasury) {
            error.setText("Reward too high");
            error.setVisible(true);
            return;
        }
        Bounty bounty = new Bounty();
        bounty.setReward(reward.getValue() / 1000);
        Player p = PlayerDao.getPlayerByNumber(0, victim.getValue(), teamVictim.getValue());
        t.treasury -= bounty.getReward();
        bounty.setPlayer(p.getId());
        bounty.setTeam(t.getId());
        TeamDao.updateTeam(t, true);
        BountyDao.insert(App.getConnection(), bounty);
        Stage stage = (Stage) error.getScene().getWindow();
        stage.close();
        App.setRoot("dashboard");
    }

}
