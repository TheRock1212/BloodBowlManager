package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.*;
import it.unipi.dataset.Model.*;
import it.unipi.utility.*;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @FXML private TableView<TeamStatistic> statsTeam;
    private ObservableList<TeamStatistic> ts;

    @FXML private TableView<Bounty> bountyList;
    private ObservableList<Bounty> bo;

    @FXML private ComboBox<String> tipo, tipoTeam;

    @FXML private MenuItem fixture, result;

    @FXML private Button addTeam, groups, playoff, close, pdfs;

    @FXML private Tab bounty;

    @FXML private TabPane pane;

    public Stage stage = new Stage();
    public Scene scene;


    @FXML public void initialize() throws Exception{
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

        tipoTeam.getItems().add("Best Offence");
        tipoTeam.getItems().add("Best Defence");
        tipoTeam.getItems().add("Most Roughtest");
        tipoTeam.getItems().add("Best Toughtest");
        tipoTeam.getItems().add("Best Killers");
        tipoTeam.getItems().add("Most Passes");
        tipoTeam.getItems().add("Most Interceptions");

        tipoTeam.getSelectionModel().selectFirst();

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

        //Colonne tabella statistiche
        TableColumn nameTeamSt = new TableColumn("Name");
        nameTeamSt.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn coachTeam = new TableColumn("Coach");
        coachTeam.setCellValueFactory(new PropertyValueFactory<>("coach"));

        TableColumn stat = new TableColumn("Stat");
        stat.setCellValueFactory(new PropertyValueFactory<>("value"));

        statsTeam.getColumns().addAll(nameTeamSt, coachTeam, stat);
        ts = FXCollections.observableArrayList();
        statsTeam.setItems(ts);

        bounty.setDisable(true);
        playoff.setVisible(false);

        if(App.getLeague().isPerennial()) {
            TableColumn team = new TableColumn("Team");
            team.setCellValueFactory(new PropertyValueFactory<>("nameTeam"));

            TableColumn player = new TableColumn("Player");
            player.setCellValueFactory(new PropertyValueFactory<>("namePlayer"));

            TableColumn reward = new TableColumn("Reward");
            reward.setCellValueFactory(new PropertyValueFactory<>("reward"));

            bountyList.getColumns().addAll(team, player, reward);
            bo = FXCollections.observableArrayList();
            bountyList.setItems(bo);

            bounty.setDisable(false);
        }

        getTable();

        if(!res.isEmpty())
            //fixture.setDisable(false);
            fixture.setVisible(false);

        if(tl.size() == App.getLeague().getTeams()) {
            addTeam.setVisible(false);
            pdfs.setVisible(true);
            fixture.setDisable(false);
            if(App.getLeague().getRound() > 1)
                groups.setVisible(true);
            if(App.getLeague().getPlayoff() > 2 && ResultDao.isAllPlayed(App.getLeague().getId()))
                playoff.setVisible(true);
        }

        teamList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            openTeamManagement();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        resultList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        try {
                            addResult();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        switch(App.getReturnState()) {
            case ROSTERS -> pane.getSelectionModel().select(1);
            case RESULTS -> pane.getSelectionModel().select(2);
            case STATS_PLAYER -> pane.getSelectionModel().select(3);
            case STATS_TEAM -> pane.getSelectionModel().select(4);
            case BOUNTY -> pane.getSelectionModel().select(5);
            default -> pane.getSelectionModel().select(0);
        }

    }

    private void getTable() throws Exception {
        Connection.params.put("league", App.getLeague().getId());
        String data = Connection.getConnection("/api/v1/team/teams", Connection.GET, null);
        List<Team> teams = JsonExploiter.getListFromJson(Team.class, data);
        teams.forEach(Team::setDeltas);
        rl.addAll(teams);
        tl.addAll(teams);

        Comparator<Team> comparator = Comparator.comparingDouble(Team::getDiscr);
        comparator = comparator.reversed();
        FXCollections.sort(rl, comparator);

        //msg = Connection.getConnection("/league/listres", "POST", Integer.toString(App.getLeague().id));
        Connection.params.put("league", App.getLeague().getId());
        data = Connection.getConnection("/api/v1/result/results", Connection.GET, null);
        List<Result> results = JsonExploiter.getListFromJson(Result.class, data);
        for(Result r : results) {
            String[] names = r.getNames();
            ResultTable reta = new ResultTable(r, names[0], names[1]);
            res.add(reta);
        }

        Connection.params.put("league", App.getLeague().getId());
        data = Connection.getConnection("/api/v1/player/playerLeague", Connection.GET, null);
        List<Player> players = JsonExploiter.getListFromJson(Player.class, data);
        for(Player p : players) {
            Connection.params.put("id", p.getTemplate());
            data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
            PlayerTemplate pt = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
            pl.add(new PlayerStatistic(p, pt));
        }
        Comparator<PlayerStatistic> comp = Comparator.comparingInt(PlayerStatistic::getValue);
        comp = comp.reversed();
        FXCollections.sort(pl, comp);

        for(Team team : teams) {
            ts.add(new TeamStatistic(team));
        }
        Comparator<TeamStatistic> comp2 = Comparator.comparingInt(TeamStatistic::getValue);
        comp2 = comp2.reversed();
        FXCollections.sort(ts, comp2);

        if(App.getLeague().isPerennial()) {
            Connection.params.put("league", App.getLeague().getId());
            data = Connection.getConnection("/api/v1/bounty/all", Connection.GET, null);
            List<Bounty> bounties = JsonExploiter.getListFromJson(Bounty.class, data);
            for(Bounty b : bounties) {
                Connection.params.put("id", b.getTeam());
                b.setNameTeam(Connection.getConnection("/api/v1/team/name", Connection.GET, null));
                Connection.params.put("id", b.getPlayer());
                b.setNamePlayer(Connection.getConnection("/api/v1/player/name", Connection.GET, null));
            }
            bo.addAll(bounties);
        }
    }

    @FXML public void createTeam() throws IOException {
        //App.setRoot("team/team_creation");
        scene = new Scene(App.load("team/team_creation"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Team Creation");
        stage.setResizable(false);
        App.setReturnState(Tabs.ROSTERS);
        stage.show();
    }

    @FXML private void deleteTeam() throws Exception {
        Team t = teamList.getSelectionModel().getSelectedItem();
        Connection.params.put("id", t.getId());
        Connection.getConnection("/api/v1/team/remove", Connection.GET, null);
        //elimina l'elemento dalla lista
        tl.remove(teamList.getSelectionModel().getSelectedItem());
        tl.remove(t);
    }

    @FXML public void openTeamManagement() throws IOException {
        App.setTeam(teamList.getSelectionModel().getSelectedItem());
        App.setNewTeam(false);
        App.setReturnState(Tabs.ROSTERS);
        App.setRoot("team/team_management");
    }

    @FXML public void addFixture() throws IOException {
        ResultController.setStato(State.FIXTURE);
        App.setReturnState(Tabs.RESULTS);
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
        App.setReturnState(Tabs.RESULTS);
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
        int giornate = (riga / (App.getLeague().getTeams() / 2));
        int cont = 1;
        for(int i = 0; i <= riga; i++) {
            if(i % (App.getLeague().getTeams() / 2) == 0)
                cont++;
            if(cont < giornate && !res.get(i).played)
                return false;
            if(cont == giornate)
                return true;
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

    @FXML public void changeSortTeam() throws IOException {
        for(TeamStatistic t : ts) {
            t.setValue(tipoTeam.getValue());
        }
        //App.setRoot("dashboard");
        if(tipoTeam.getValue().equals("Best Defence") || tipoTeam.getValue().equals("Most Toughtest"))
            FXCollections.sort(ts, Comparator.comparingInt(TeamStatistic::getValue));
        else
            FXCollections.sort(ts, Comparator.comparingInt(TeamStatistic::getValue).reversed());
    }

    @FXML public void createPDF() throws IOException, SQLException {
        PDFManager.generatePDF(teamList.getSelectionModel().getSelectedItem());
    }

    @FXML public void generatePDFs() throws IOException, SQLException {
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        //fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(pdfs.getScene().getWindow());
        if (selectedFile != null) {
            String dest = selectedFile.getAbsolutePath();*/
            for (Team t : tl) {
                PDFManager.generatePDF(t);
            }
        //}
    }

    @FXML public void printStatsPlayer() throws IOException {
        PDFManager.statsPlayer(pl);
    }

    @FXML public void printStatsTeam() throws IOException {
        PDFManager.statsTeam(ts);
    }

    @FXML public void printStandings() throws IOException, SQLException {
        List<Image> images = new ArrayList<>();
        for(Team t : rl) {
            images.add(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/race/" + RaceDao.getRace(t.getRace()).url + ".png").toExternalForm()));
        }
        PDFManager.standings(rl, images);
    }

    @FXML public void addBounty() throws IOException {
        stage.setTitle("Add Bounty");
        App.setReturnState(Tabs.BOUNTY);
        scene = new Scene(App.load("bounty/bounty"), 200, 320);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void removeBounty() throws Exception {
        Bounty bounty = bountyList.getSelectionModel().getSelectedItem();
        bo.remove(bounty);
        Connection.params.put("id", bounty.getTeam());
        Connection.params.put("league", App.getLeague().getId());
        String data = Connection.getConnection("/api/v1/team/teams", Connection.GET, null);
        List<Team> teams = JsonExploiter.getListFromJson(Team.class, data);
        if(!teams.isEmpty()) {
            Team t = teams.getFirst();
            t.treasury += bounty.getReward();
        }
        //BountyDao.delete(App.getConnection(), bounty);
        Connection.params.put("team", bounty.getTeam());
        Connection.params.put("player", bounty.getPlayer());
        Connection.getConnection("/api/v1/bounty/remove", Connection.GET, null);
    }

}
