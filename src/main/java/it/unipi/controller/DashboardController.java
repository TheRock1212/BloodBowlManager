package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.ResultDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Result;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.ResultTable;
import it.unipi.utility.State;
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
import java.util.Comparator;
import java.util.List;

public class DashboardController {

    @FXML private TableView<Team> rankingTable;
    ObservableList<Team> rl;

    @FXML private TableView<Team> teamList;
    private ObservableList<Team> tl;

    @FXML private TableView<ResultTable> resultList;
    private ObservableList<ResultTable> res;

    @FXML private MenuItem fixture, result;

    @FXML private Button addTeam, groups, playoff;

    public Stage stage = new Stage();
    public Scene scene;

    @FXML public void initialize() throws SQLException{
        //Colonne per rankingTable
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn pts = new TableColumn("Points");
        pts.setCellValueFactory(new PropertyValueFactory<>("points"));

        TableColumn matches = new TableColumn("MatchesPlayed");
        matches.setCellValueFactory(new PropertyValueFactory<>("g"));

        TableColumn win = new TableColumn("Wins");
        win.setCellValueFactory(new PropertyValueFactory<>("w"));

        TableColumn tie = new TableColumn("Ties");
        tie.setCellValueFactory(new PropertyValueFactory<>("n"));

        TableColumn loss = new TableColumn("Losses");
        loss.setCellValueFactory(new PropertyValueFactory<>("l"));

        TableColumn tdScore = new TableColumn("TD+");
        tdScore.setCellValueFactory(new PropertyValueFactory<>("tdScored"));

        TableColumn tdConceded = new TableColumn("TD-");
        tdConceded.setCellValueFactory(new PropertyValueFactory<>("tdConceded"));

        TableColumn tdNet = new TableColumn("TDNet");
        tdNet.setCellValueFactory(new PropertyValueFactory<>("tdNet"));

        TableColumn casScore = new TableColumn("CAS+");
        casScore.setCellValueFactory(new PropertyValueFactory<>("casInflicted"));

        TableColumn casConceded = new TableColumn("CAS-");
        casConceded.setCellValueFactory(new PropertyValueFactory<>("casSuffered"));

        TableColumn casNet = new TableColumn("CASNet");
        casNet.setCellValueFactory(new PropertyValueFactory<>("casNet"));


        rankingTable.getColumns().addAll(name, pts, matches, win, tie, loss, tdScore, tdConceded, tdNet, casScore, casConceded, casNet);
        rl = FXCollections.observableArrayList();
        rankingTable.setItems(rl);
        //rankingTable.prefHeightProperty().bind(rankingTable.heightProperty());
        //rankingTable.prefWidthProperty().bind(rankingTable.widthProperty());

        //Colonne per teamList
        TableColumn nameT = new TableColumn("Name");
        nameT.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn players = new TableColumn("Players");
        players.setCellValueFactory(new PropertyValueFactory<>("ngiocatori"));

        TableColumn reroll = new TableColumn("Reroll");
        reroll.setCellValueFactory(new PropertyValueFactory<>("nreroll"));

        TableColumn apotechary = new TableColumn("Apo");
        apotechary.setCellValueFactory(new PropertyValueFactory<>("apothecary"));

        TableColumn df = new TableColumn("DF");
        df.setCellValueFactory(new PropertyValueFactory<>("df"));

        TableColumn cheer = new TableColumn("Cheer");
        cheer.setCellValueFactory(new PropertyValueFactory<>("cheerleader"));

        TableColumn assistant = new TableColumn("Assistant");
        assistant.setCellValueFactory(new PropertyValueFactory<>("assistant"));

        TableColumn treasury = new TableColumn("Treasury");
        treasury.setCellValueFactory(new PropertyValueFactory<>("treasury"));

        TableColumn value = new TableColumn("TeamValue");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));


        teamList.getColumns().addAll(nameT, players, reroll, apotechary, df, cheer, assistant, treasury, value);
        tl = FXCollections.observableArrayList();
        teamList.setItems(tl);
        //teamList.prefWidthProperty().bind(teamList.widthProperty());
        //teamList.prefHeightProperty().bind(teamList.heightProperty());

        TableColumn homeTeam = new TableColumn("Home Team");
        homeTeam.setCellValueFactory(new PropertyValueFactory<>("home"));

        TableColumn awayTeam = new TableColumn("Away Team");
        awayTeam.setCellValueFactory(new PropertyValueFactory<>("away"));

        TableColumn homeTD = new TableColumn("TD Home");
        homeTD.setCellValueFactory(new PropertyValueFactory<>("TDH"));

        TableColumn awayTD = new TableColumn("TD Away");
        awayTD.setCellValueFactory(new PropertyValueFactory<>("TDA"));

        TableColumn homeCAS = new TableColumn("CAS Home");
        homeCAS.setCellValueFactory(new PropertyValueFactory<>("CASH"));

        TableColumn awayCAS = new TableColumn("CAS Away");
        awayCAS.setCellValueFactory(new PropertyValueFactory<>("CASA"));

        resultList.getColumns().addAll(homeTeam, awayTeam, homeTD, awayTD, homeCAS, awayCAS);
        res = FXCollections.observableArrayList();
        resultList.setItems(res);

        fixture.setDisable(true);

        getTable();

        if(tl.size() == App.getLeague().getNTeams()) {
            addTeam.setVisible(false);
            fixture.setDisable(false);
            if(App.getLeague().getGroups() > 1)
                groups.setVisible(true);
            if(App.getLeague().getPlayoff() > 2)
                playoff.setVisible(true);
        }
    }

    private void getTable() throws SQLException {
        List<Team> teams = TeamDao.getTeam(0, App.getLeague().getId());
        rl.addAll(teams);
        tl.addAll(teams);
        Comparator<Team> comparator = Comparator.comparingInt(Team::getPoints);
        comparator = comparator.reversed();
        FXCollections.sort(rl, comparator);

        //msg = Connection.getConnection("/league/listres", "POST", Integer.toString(App.getLeague().id));
        List<Result> results = ResultDao.getResults();
        for(Result r : results) {
            String[] names = r.getNames();
            ResultTable reta = new ResultTable(r, names[0], names[1]);
            res.add(reta);
        }
    }

    @FXML public void createTeam() throws IOException {
        App.setRoot("team/team_creation");
    }

    @FXML private void deleteTeam() throws SQLException {
        Team t = teamList.getSelectionModel().getSelectedItem();
        TeamDao.removeTeam(t.getId());
        //elimina l'elemento dalla lista
        tl.remove(teamList.getSelectionModel().getSelectedItem());
        tl.remove(t);
    }

    @FXML public void openTeamManagement() throws IOException {
        App.setTeam(teamList.getSelectionModel().getSelectedItem());
        App.setNewTeam(false);
        App.setRoot("team/team_management");
    }

    @FXML public void addFixture() throws IOException {
        ResultController.setStato(State.SELECTMODE);
        stage.setTitle("Select Fixtures");
        scene = new Scene(App.load("result/mode"), 200, 300);
        stage.setScene(scene);
        stage.show();
    }
}
