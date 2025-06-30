package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.StadiumDao;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class StadiumController {

    @FXML private ComboBox<String> stadium, type;
    private String data;

    @FXML public void initialize() throws Exception {
        data = Connection.getConnection("/api/v1/stadium/categories", Connection.GET, null);
        type.getItems().addAll(JsonExploiter.getListFromJson(String.class, data));
    }

    @FXML public void setStadiums() throws Exception {
        stadium.getItems().clear();
        Connection.params.put("cat", type.getValue());
        data = Connection.getConnection("/api/v1/stadium/stadium", Connection.GET, null);
        stadium.getItems().addAll(JsonExploiter.getListFromJson(String.class, data));
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
