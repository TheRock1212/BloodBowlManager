package it.unipi.bloodbowlmanager;

import it.unipi.dataset.League;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        League league = new League("Robe", 10, 5, 2, 0, true, true, true, 1050);
        league.addLeague();
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}