package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.LeagueDao;
import it.unipi.dataset.Model.League;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static it.unipi.bloodbowlmanager.App.load;

public class LeagueController {

    @FXML
    private TextField leagueName;
    @FXML
    private CheckBox pts3CAS, pts3TD, pts0TD;
    @FXML
    private Spinner<Integer> treasury, ptsWin, ptsTie, ptsLoss, nTeam, groups;
    @FXML
    private Label error;
    @FXML
    private RadioButton check, chkPF;
    @FXML
    private ComboBox<Integer> playoff;

    @FXML
    private TableView<League> leagueTable;
    private ObservableList<League> ol;

    private Stage stage = new Stage();
    private Scene scene;

    @FXML
    public void initialize() throws SQLException {
        if(!App.isNewTeam()) {
            TableColumn name = new TableColumn("Name");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn nteam = new TableColumn("NrTeam");
            nteam.setCellValueFactory(new PropertyValueFactory<>("nTeams"));

            TableColumn treasury = new TableColumn("StartTreasury");
            treasury.setCellValueFactory(new PropertyValueFactory<>("treasury"));

            TableColumn ptsW = new TableColumn("PtsWin");
            ptsW.setCellValueFactory(new PropertyValueFactory<>("ptsWin"));

            TableColumn ptsN = new TableColumn("PtsTie");
            ptsN.setCellValueFactory(new PropertyValueFactory<>("ptsTie"));

            TableColumn ptsL = new TableColumn("PtsLost");
            ptsL.setCellValueFactory(new PropertyValueFactory<>("ptsLose"));

            leagueTable.getColumns().addAll(name, nteam, treasury, ptsW, ptsN, ptsL);
            ol = FXCollections.observableArrayList();
            leagueTable.setItems(ol);

            getLeagues();
        }
        else {
            SpinnerValueFactory<Integer> pts = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
            pts.setValue(5);
            ptsWin.setValueFactory(pts);
            pts = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
            pts.setValue(2);
            ptsTie.setValueFactory(pts);
            pts = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1);
            pts.setValue(0);
            ptsLoss.setValueFactory(pts);
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1000, 1200, 1000, 5);
            valueFactory.setValue(1000);
            treasury.setValueFactory(valueFactory);
            valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, 1);
            nTeam.setValueFactory(valueFactory);
        }
    }

    private void getLeagues() throws SQLException {
        List<League> leagues = LeagueDao.getLeagues(0);
        ol.addAll(leagues);
    }

    @FXML
    private void createLeague() throws IOException {
        App.setNewTeam(true);
        scene = new Scene(load("league/league_creation"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Create a League");
        stage.setResizable(false);
        stage.show();
        //App.setRoot("league/league_creation");
    }

    @FXML
    private void open() throws IOException {
        App.setLeague(leagueTable.getSelectionModel().getSelectedItem());
        App.setRoot("dashboard");
        //App.changeScene(1000, 563, "dashboard");
    }

    @FXML
    private void remove() throws SQLException{
        League league = leagueTable.getSelectionModel().getSelectedItem();
        LeagueDao.removeLeague(league.getId());
        ol.remove(league);
    }

    @FXML
    private void insertLeague() throws SQLException, IOException {
        if (ptsWin.getValue() <= ptsTie.getValue() || ptsTie.getValue() <= ptsLoss.getValue()) {
            error.setVisible(true);
            error.setText("Error: ptsWin > ptsTie > ptsLoss");
        } else {
            //Crea l'oggetto lega
            int nGroups = 1, pf = 0;
            if(chkPF.isSelected())
                pf = playoff.getValue();
            if(check.isSelected())
                nGroups = groups.getValue();
            League league = new League(leagueName.getText(), nTeam.getValue(), ptsWin.getValue(), ptsTie.getValue(), ptsLoss.getValue(), pts3TD.isSelected(), pts3CAS.isSelected(), pts0TD.isSelected(), (int)treasury.getValue(), nGroups, pf);
            LeagueDao.addLeague(league);
            Stage stage = (Stage) error.getScene().getWindow();
            stage.close();
            App.setNewTeam(false);
            App.setRoot("league/league_dashboard");
        }
    }

    @FXML
    private void setSpinner() {
        if (check.isSelected()) {
            groups.setDisable(false);
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
            valueFactory.setValue(1);
            groups.setValueFactory(valueFactory);
        }
        else {
            groups.setDisable(true);
        }
    }

    @FXML
    private void setPlayoff() {
        if (chkPF.isSelected()) {
            playoff.setDisable(false);
            playoff.getItems().clear();
            int limit = (int)Math.sqrt(nTeam.getValue());
            for(int i = 1; i <= limit; i++)
                playoff.getItems().add((int)Math.pow(2, i));
        }
        else {
            playoff.setDisable(true);
        }
    }
}