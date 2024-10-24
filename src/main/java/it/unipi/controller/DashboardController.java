package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.PlayerTemplateDao;
import it.unipi.dataset.Dao.ResultDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Result;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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

    @FXML private TableView<PlayerStatistic> stats;
    private ObservableList<PlayerStatistic> pl;

    @FXML private ComboBox<String> tipo;

    @FXML private MenuItem fixture, result;

    @FXML private Button addTeam, groups, playoff, close;

    public Stage stage = new Stage();
    public Scene scene;

    @FXML public void initialize() throws SQLException{
        //Colonne per rankingTable
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn coach = new TableColumn("Coach");
        coach.setCellValueFactory(new PropertyValueFactory<>("coach"));

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


        rankingTable.getColumns().addAll(name, coach, pts, matches, win, tie, loss, tdScore, tdConceded, tdNet, casScore, casConceded, casNet);
        rl = FXCollections.observableArrayList();
        rankingTable.setItems(rl);
        //rankingTable.prefHeightProperty().bind(rankingTable.heightProperty());
        //rankingTable.prefWidthProperty().bind(rankingTable.widthProperty());

        //Colonne per teamList
        TableColumn nameT = new TableColumn("Name");
        nameT.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn coachT = new TableColumn("Coach");
        coachT.setCellValueFactory(new PropertyValueFactory<>("coach"));

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


        teamList.getColumns().addAll(nameT, coachT, players, reroll, apotechary, df, cheer, assistant, treasury, value);
        tl = FXCollections.observableArrayList();
        teamList.setItems(tl);
        //teamList.prefWidthProperty().bind(teamList.widthProperty());
        //teamList.prefHeightProperty().bind(teamList.heightProperty());

        TableColumn homeTeam = new TableColumn("Home Team");
        homeTeam.setCellValueFactory(new PropertyValueFactory<>("home"));

        TableColumn awayTeam = new TableColumn("Away Team");
        awayTeam.setCellValueFactory(new PropertyValueFactory<>("away"));

        TableColumn homeTD = new TableColumn("TD Home");
        homeTD.setCellValueFactory(new PropertyValueFactory<>("tdh"));

        TableColumn awayTD = new TableColumn("TD Away");
        awayTD.setCellValueFactory(new PropertyValueFactory<>("tda"));

        TableColumn homeCAS = new TableColumn("CAS Home");
        homeCAS.setCellValueFactory(new PropertyValueFactory<>("cash"));

        TableColumn awayCAS = new TableColumn("CAS Away");
        awayCAS.setCellValueFactory(new PropertyValueFactory<>("casa"));

        TableColumn datePlayed = new TableColumn("Date");
        datePlayed.setCellValueFactory(new PropertyValueFactory<>("datePlayed"));

        resultList.getColumns().addAll(datePlayed, homeTeam, awayTeam, homeTD, awayTD, homeCAS, awayCAS);
        res = FXCollections.observableArrayList();
        resultList.setItems(res);

        tipo.getItems().add("Best Players");
        tipo.getItems().add("Best Scorers");
        tipo.getItems().add("Most Vicious");
        tipo.getItems().add("Best Killers");
        tipo.getItems().add("Best Passers");
        tipo.getItems().add("Best Interceptors");

        tipo.getSelectionModel().selectFirst();

        //Colonne tabella statistiche
        TableColumn image = new TableColumn(" ");
        image.setCellValueFactory(new PropertyValueFactory<ImageView, PlayerStatistic>("img"));

        TableColumn namePlayer = new TableColumn("Name");
        namePlayer.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn role = new TableColumn("Position");
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn coachPlayer = new TableColumn("Coach");
        coachPlayer.setCellValueFactory(new PropertyValueFactory<>("coach"));

        TableColumn nameTeam = new TableColumn("Team");
        nameTeam.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        TableColumn statistics = new TableColumn("Stat");
        statistics.setCellValueFactory(new PropertyValueFactory<>("value"));

        stats.getColumns().addAll(image, namePlayer, role, nameTeam, coachPlayer, statistics);
        pl = FXCollections.observableArrayList();
        stats.setItems(pl);

        getTable();

        if(!res.isEmpty())
            //fixture.setDisable(false);
            fixture.setVisible(false);

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

        List<Player> players = PlayerDao.getPlayers(App.getLeague().getId());
        for(Player p : players) {
            PlayerTemplate pt = PlayerTemplateDao.getPlayer(p.getTemplate());
            pl.add(new PlayerStatistic(p, pt));
        }
        Comparator<PlayerStatistic> comp = Comparator.comparingInt(PlayerStatistic::getValue);
        comp = comp.reversed();
        FXCollections.sort(pl, comp);
    }

    @FXML public void createTeam() throws IOException {
        //App.setRoot("team/team_creation");
        scene = new Scene(App.load("team/team_creation"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Team Creation");
        stage.setResizable(false);
        stage.show();
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
        ResultController.setStato(State.FIXTURE);
        stage.setTitle("Select Fixtures");
        scene = new Scene(App.load("result/mode"), 200, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void addResult() throws IOException, SQLException {
        if(!checkResult()) {
            stage.setTitle("Error");
            scene = new Scene(App.load("error/result"), 200, 100);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            return;
        }
        ResultController.setStato(State.SELECTMODE);
        App.setResult(new ResultGame(resultList.getSelectionModel().getSelectedItem()));
        stage.setTitle("Post-Game Phase");
        scene = new Scene(App.load("result/post"), 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private boolean checkResult() {
        int riga = resultList.getSelectionModel().getSelectedIndex();
        if(res.get(riga).played)
            return false;
        int giornata = riga / (App.getLeague().getNTeams() / 2);
        int cont = 0;
        for(int i = 0; i <= riga; i++) {

            if(cont < giornata && !res.get(i).played)
                return false;
            if(cont == giornata)
                return true;
            if(i % 5 == 4)
                cont++;
        }
        return false;
    }

    @FXML public void changeSort() throws IOException {
        for(PlayerStatistic ps : pl) {
            ps.setValue(tipo.getValue());
        }
        //App.setRoot("dashboard");
        FXCollections.sort(pl, Comparator.comparingInt(PlayerStatistic::getValue).reversed());
    }
}
