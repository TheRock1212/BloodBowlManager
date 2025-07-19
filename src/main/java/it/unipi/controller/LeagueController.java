package it.unipi.controller;

import com.google.gson.Gson;
import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.LeagueDao;
import it.unipi.dataset.Model.League;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static it.unipi.bloodbowlmanager.App.load;

public class LeagueController {

    @FXML
    private TextField leagueName;
    @FXML
    private CheckBox pts3CAS, pts3TD, pts0TD, tvr, spp, perennial;
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

    private Gson gson = new Gson();

    @FXML
    public void initialize() throws Exception {
        if(!App.isNewTeam()) {
            TableColumn name = new TableColumn("Name");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn nteam = new TableColumn("NrTeam");
            nteam.setCellValueFactory(new PropertyValueFactory<>("teams"));

            TableColumn treasury = new TableColumn("StartTreasury");
            treasury.setCellValueFactory(new PropertyValueFactory<>("treasury"));

            TableColumn ptsW = new TableColumn("PtsWin");
            ptsW.setCellValueFactory(new PropertyValueFactory<>("win"));

            TableColumn ptsN = new TableColumn("PtsTie");
            ptsN.setCellValueFactory(new PropertyValueFactory<>("tie"));

            TableColumn ptsL = new TableColumn("PtsLost");
            ptsL.setCellValueFactory(new PropertyValueFactory<>("loss"));

            leagueTable.getColumns().addAll(name, nteam, treasury, ptsW, ptsN, ptsL);
            ol = FXCollections.observableArrayList();
            leagueTable.setItems(ol);

            getLeagues();

            leagueTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            try {
                                open();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
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

    private void getLeagues() throws Exception {
        //List<League> leagues = LeagueDao.getLeagues(0);
        Connection.params.put("id", 0);
        String data = Connection.getConnection("/api/v1/league/all", Connection.GET, null);
        List<League> leagues = JsonExploiter.getListFromJson(League.class, data);
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
    private void remove() throws Exception{
        League league = leagueTable.getSelectionModel().getSelectedItem();
        Connection.params.put("id", league.getId());
        Connection.getConnection("/api/v1/league/remove", Connection.GET, null);
        //LeagueDao.removeLeague(league.getId());
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
            League league = new League(leagueName.getText(), nTeam.getValue(), ptsWin.getValue(), ptsTie.getValue(), ptsLoss.getValue(), pts3TD.isSelected(), pts3CAS.isSelected(), pts0TD.isSelected(), (int)treasury.getValue(), nGroups, pf, tvr.isSelected(), spp.isSelected(), perennial.isSelected(), 0, null);
            //LeagueDao.addLeague(league);
            Connection.getConnection("/api/v1/league/add", Connection.POST, JsonExploiter.toJson(league));
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