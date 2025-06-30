package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Model.Player;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SponsorController {

    @FXML private Label labelCont, labelMag, playerLabel;
    @FXML private RadioButton continuativo, major;
    @FXML private ToggleGroup choose;
    @FXML private Spinner<Integer> nCont;
    @FXML private ComboBox<String> mag;
    @FXML private ComboBox<Integer> player;
    @FXML private Button card;

    private String data;


    @FXML public void initialize() {
        labelCont.setVisible(false);
        labelMag.setVisible(false);
        SpinnerValueFactory<Integer> cont = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
        nCont.setValueFactory(cont);
        nCont.setVisible(false);
        mag.getItems().add("Farblast & Sons");
        mag.getItems().add("Star Insurance");
        mag.getItems().add("Steelhelm' Sporting");
        mag.getItems().add("McMurty Burger");
        mag.setVisible(false);
        playerLabel.setVisible(false);
        player.setVisible(false);
        card.setVisible(App.getLeague().isPerennial());
    }

    @FXML public void set() {
        if(continuativo.isSelected()) {
            labelCont.setVisible(true);
            nCont.setVisible(true);
            mag.setVisible(false);
            labelMag.setVisible(false);
        }
        else {
            labelCont.setVisible(false);
            nCont.setVisible(false);
            mag.setVisible(true);
            labelMag.setVisible(true);
        }
        playerLabel.setVisible(false);
        player.setVisible(false);
    }

    @FXML public void save() throws IOException, SQLException {
        if(continuativo.isSelected()) {
            App.getTeam().sponsor = nCont.getValue() + " Ongoings";
        }
        else {
            App.getTeam().sponsor = mag.getValue();
            if("Farblast & Sons".equals(mag.getValue())) {
                Connection.params.put("team", App.getTeam().getId());
                Connection.params.put("number", player.getValue());
                Connection.getConnection("/api/v1/player/bombardier", Connection.POST, null);
            }
        }
        Stage stage = (Stage) labelCont.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }

    @FXML public void checkMag() throws IOException, SQLException {
        if("Farblast & Sons".equals(mag.getValue())) {
            Connection.params.put("team", App.getTeam().getId());
            data = Connection.getConnection("/api/v1/player/lineman", Connection.GET, null);
            List<Player> lineman = JsonExploiter.getListFromJson(Player.class, data);
            for(Player p : lineman) {
                player.getItems().add(p.number);
            }
            player.setVisible(true);
            playerLabel.setVisible(true);
        }
    }

    @FXML public void removeSponsor() throws IOException, SQLException {
        App.getTeam().sponsor = "none";
        Stage stage = (Stage) labelCont.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }

    @FXML public void addCard() throws IOException, SQLException {
        App.getTeam().setCards(App.getTeam().getCards() + 1);
        Stage stage = (Stage) labelCont.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }
}
