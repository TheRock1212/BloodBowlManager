package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SponsorController {

    @FXML private Label labelCont, labelMag;
    @FXML private RadioButton continuativo, major;
    @FXML private ToggleGroup choose;
    @FXML private Spinner<Integer> nCont;
    @FXML private ComboBox<String> mag;


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
    }

    @FXML public void save() throws IOException {
        if(continuativo.isSelected()) {
            App.getTeam().sponsor = nCont.getValue() + " Ongoings";
        }
        else {
            App.getTeam().sponsor = mag.getValue();
        }
        Stage stage = (Stage) labelCont.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }
}
