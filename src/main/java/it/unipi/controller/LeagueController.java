package it.unipi.controller;

import it.unipi.dataset.League;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeagueController {

    @FXML private TableView<League> leagueTable;
    private ObservableList<League> ol;

    @FXML public void initialize() throws SQLException{
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn nteam = new TableColumn("NrTeam");
        nteam.setCellValueFactory(new PropertyValueFactory<>("nTeam"));

        TableColumn treasury = new TableColumn("StartTreasury");
        treasury.setCellValueFactory(new PropertyValueFactory<>("treasury"));

        TableColumn ptsW = new TableColumn("PtsWin");
        ptsW.setCellValueFactory(new PropertyValueFactory<>("ptsWin"));

        TableColumn ptsN = new TableColumn("PtsTie");
        ptsN.setCellValueFactory(new PropertyValueFactory<>("ptsN"));

        TableColumn ptsL = new TableColumn("PtsLost");
        ptsL.setCellValueFactory(new PropertyValueFactory<>("ptsL"));

        leagueTable.getColumns().addAll(name, nteam, treasury, ptsW, ptsN, ptsL);
        ol = FXCollections.observableArrayList();
        leagueTable.setItems(ol);

        getLeagues();
    }

    private void getLeagues() throws SQLException {
        League league = new League();
        ResultSet rs = league.getLeagues();
        while(rs.next()){
            ol.add(new League(rs.getString("name"), rs.getInt("nteams"), rs.getInt("pts_win"), rs.getInt("pts_tie"), rs.getInt("pts_loss"), rs.getBoolean("pts_td"), rs.getBoolean("pts_cas"), rs.getBoolean("pts_td_conceded"), rs.getInt("treasury")));
        }
    }

    @FXML private void createLeague() {}

    @FXML private void open() {}

    @FXML private void remove() {}
}
