package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.StadiumDao;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class StadiumController {

    @FXML private ComboBox<String> stadium, type;

    @FXML public void initialize() throws SQLException {
        type.getItems().addAll(StadiumDao.getTipologie());
    }

    @FXML public void setStadiums() throws SQLException {
        stadium.getItems().clear();
        stadium.getItems().addAll(StadiumDao.getStadiums(type.getValue()));
        stadium.getSelectionModel().select(0);
    }

    @FXML public void save() throws IOException {
        App.getTeam().setStadium(stadium.getValue());
        Stage stage = (Stage) stadium.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }

    @FXML public void reset() throws IOException {
        App.getTeam().setStadium("none");
        Stage stage = (Stage) stadium.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }

}
