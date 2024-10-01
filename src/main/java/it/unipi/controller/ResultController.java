package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.utility.Fixture;
import it.unipi.utility.State;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ResultController {
    private static State stato;

    private @FXML ComboBox<String> mode;
    private @FXML ComboBox<Integer> rounds;

    public void initialize() {
        switch (stato) {
            case SELECTMODE: {
                for(int i = 2; i < App.getLeague().getNTeams(); i++)
                    rounds.getItems().add(i);
                mode.getItems().add("Round Robin");
                //mode.getItems().add("One at a Time");
                break;
            }
        }
    }

    public static State getStato() {
        return stato;
    }

    public static void setStato(State stato) {
        ResultController.stato = stato;
    }

    @FXML public void setFixture() throws SQLException, IOException {
        Fixture fixture = new Fixture();
        fixture.RoundRobin(rounds.getValue());
        Stage stage = (Stage) rounds.getScene().getWindow();
        stage.close();
        App.setRoot("dashboard");
    }
}
