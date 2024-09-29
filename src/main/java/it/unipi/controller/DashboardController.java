package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

public class DashboardController {

    @FXML private TableView<Team> rankingTable;
    ObservableList<Team> rl;

    @FXML private TableView<Team> teamList;
    private ObservableList<Team> tl;


    @FXML private Button addTeam, groups, playoff;

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

        getTable();

        if(tl.size() == App.getLeague().getNTeams()) {
            addTeam.setVisible(false);
            if(App.getLeague().getGroups() > 1)
                groups.setVisible(true);
            if(App.getLeague().getPlayoff() > 2)
                playoff.setVisible(true);
        }
    }

    private void getTable() throws SQLException {
        Team team = new Team();
        ResultSet rs = team.getTeam(0);
        while (rs.next()) {
            Team t = new Team(rs.getInt("id"), rs.getString("coach"), rs.getString("name"), rs.getInt("race"), rs.getInt("league"), rs.getInt("ngiocatori"), rs.getInt("nreroll"), rs.getBoolean("apothecary"), rs.getInt("cheerleader"), rs.getInt("assistant"), rs.getInt("DF"), rs.getInt("treasury"), rs.getInt("G"), rs.getInt("W"), rs.getInt("N"), rs.getInt("L"), rs.getInt("TDscored"), rs.getInt("TDconceded"), rs.getInt("CASInflicted"), rs.getInt("CASSuffered"), rs.getInt("PTS"), rs.getInt("value"), rs.getInt("round"), rs.getInt("journeyman"));
            rl.add(t);
            tl.add(t);
        }
        Comparator<Team> comparator = Comparator.comparingInt(Team::getPoints);
        comparator = comparator.reversed();
        FXCollections.sort(rl, comparator);
    }

    @FXML public void createTeam() throws IOException {
        App.setRoot("team/team_creation");
    }

    @FXML private void deleteTeam() throws SQLException {
        Team t = teamList.getSelectionModel().getSelectedItem();
        t.removeTeam();
        //elimina l'elemento dalla lista
        tl.remove(teamList.getSelectionModel().getSelectedItem());
        tl.remove(t);
    }

    @FXML public void openTeamManagement() throws IOException {
        App.setTeam(teamList.getSelectionModel().getSelectedItem());
        App.setNewTeam(false);
        App.setRoot("team/team_management");
    }

}
