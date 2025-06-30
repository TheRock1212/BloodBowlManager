package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.*;
import it.unipi.dataset.Model.*;
import it.unipi.utility.PlayerPreview;
import it.unipi.utility.TemplateImage;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ManagementController {

    @FXML
    TableView<PlayerPreview> players = new TableView<>();
    ObservableList<PlayerPreview> p;

    private Player[] player = new Player[27];
    private Race r = new Race();

    @FXML private Label costRr, treasury, apoUn, apoAcq, error, sponsor, ready, stadium, staLabel, cardLabel, cards;
    @FXML private RadioButton option;
    @FXML private ComboBox<Integer> rr, ac, ch;
    @FXML private Button readyButton, purButton, spoButton, staBtn;
    @FXML private MenuItem edit, fire, journey;

    private static Scene scene;
    private static Stage stage = new Stage();
    private String data;

    @FXML public void initialize() throws Exception {
        //Creazione tabella per info e selezione giocatori
        TableColumn nomeCol = new TableColumn("Position");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn nr = new TableColumn("Nr");
        nr.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn namePlayer = new TableColumn("Name");
        namePlayer.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn ma = new TableColumn("MA");
        ma.setCellValueFactory(new PropertyValueFactory<>("MA"));

        TableColumn st = new TableColumn("ST");
        st.setCellValueFactory(new PropertyValueFactory<>("ST"));

        TableColumn ag = new TableColumn("AG");
        ag.setCellValueFactory(new PropertyValueFactory<>("AG"));

        TableColumn pa = new TableColumn("PA");
        pa.setCellValueFactory(new PropertyValueFactory<>("PA"));

        TableColumn av = new TableColumn("AV");
        av.setCellValueFactory(new PropertyValueFactory<>("AV"));

        TableColumn skills = new TableColumn("Skill");
        skills.setCellValueFactory(new PropertyValueFactory<>("skill"));

        TableColumn nig = new TableColumn("NIG");
        nig.setCellValueFactory(new PropertyValueFactory<>("NIG"));

        TableColumn mng = new TableColumn("MNG");
        mng.setCellValueFactory(new PropertyValueFactory<>("MNG"));

        TableColumn unspp = new TableColumn("UnspentSPP");
        unspp.setCellValueFactory(new PropertyValueFactory<>("UnspentSPP"));

        TableColumn totalspp = new TableColumn("SPP");
        totalspp.setCellValueFactory(new PropertyValueFactory<>("SPP"));

        TableColumn Cost = new TableColumn("Value");
        Cost.setCellValueFactory(new PropertyValueFactory<>("val"));

        TableColumn image = new TableColumn(" ");
        image.setCellValueFactory(new PropertyValueFactory<ImageView, PlayerPreview>("img"));

        if(App.getLeague().isPerennial()) {
            TableColumn season = new TableColumn("Season");
            season.setCellValueFactory(new PropertyValueFactory<>("season"));
            players.getColumns().addAll(image, nr, namePlayer, nomeCol, ma, st, ag, pa, av, skills, unspp, totalspp, mng, nig, Cost, season);
        } else {
            players.getColumns().addAll(image, nr, namePlayer, nomeCol, ma, st, ag, pa, av, skills, unspp, totalspp, mng, nig, Cost);
        }
        p = FXCollections.observableArrayList();
        players.setItems(p);

        Connection.params.put("id", App.getTeam().getRace());
        data = Connection.getConnection("/api/v1/race/race", Connection.GET, null);
        r = JsonExploiter.getObjectFromJson(Race.class, data);

        getTable();
        //Metto il tesoro rimanente e le altre informazioni relative alle razze
        treasury.setText(Integer.toString(App.getTeam().getTreasury()) + "k");

        sponsor.setText(App.getTeam().getSponsor());

        costRr.setText(Integer.toString(r.getReroll() * 2) + " k");
        if(r.isApothecary()) {
            apoUn.setVisible(false);
            if(App.getTeam().isApothecary()) {
                apoAcq.setVisible(true);
                option.setVisible(false);
            }
            else {
                apoAcq.setVisible(false);
                option.setVisible(true);
            }
        }
        else {
            apoUn.setVisible(true);
            apoAcq.setVisible(false);
            option.setVisible(false);
        }
        for(int i = 0; i <= 12; i++) {
            if(i >= App.getTeam().getCheerleader())
                ch.getItems().add(i);
            if(i <= 6 && i >= App.getTeam().getAssistant())
                ac.getItems().add(i);
            if(i <= 8 && i >= App.getTeam().getNreroll())
                rr.getItems().add(i);
        }
        ch.getSelectionModel().select(0);
        ac.getSelectionModel().select(0);
        rr.getSelectionModel().select(0);
        ready.setText(Boolean.toString(App.getTeam().isReady()));
        if(App.getTeam().isReady()) {
            readyButton.setVisible(false);
            purButton.setDisable(true);
            ch.setDisable(true);
            ac.setDisable(true);
            rr.setDisable(true);
            edit.setDisable(true);
            fire.setDisable(true);
            spoButton.setVisible(false);
            if(option.isVisible())
                option.setDisable(true);
        } else {
            players.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 2){
                            try {
                                switchToPlayerPreview();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }

        staLabel.setText(App.getTeam().getStadium());
        cards.setText(Integer.toString(App.getTeam().getCards()));

        if(!App.getLeague().isPerennial()) {
            stadium.setVisible(false);
            staLabel.setVisible(false);
            staBtn.setVisible(false);
            cardLabel.setVisible(false);
            cards.setVisible(false);
        }
    }

    public void getTable() throws Exception{
        //Ottengono informazioni relative atutti i giocatori acquistabili da quella razza
        int cont;
        Connection.params.put("team", App.getTeam().getId());
        Connection.params.put("alive", true);
        data = Connection.getConnection("/api/v1/player/players", Connection.GET, null);
        List<Player> players = JsonExploiter.getListFromJson(Player.class, data);
        for(cont = 0; cont < players.size(); cont++)
            player[cont] = players.get(cont);
        Connection.params.put("race", App.getTeam().getRace());
        data = Connection.getConnection("/api/v1/playerTemplate/template", Connection.GET, null);
        List<PlayerTemplate> templates = JsonExploiter.getListFromJson(PlayerTemplate.class, data);
        TemplateImage[] ti = new TemplateImage[r.getPositional()];
        cont = 0;
        for(PlayerTemplate pt : templates) {
            ti[cont] = new TemplateImage(pt);
            ti[cont].img = new ImageView();
            ti[cont].img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti[cont].getUrl() + ".png").toExternalForm()));
            cont++;
        }
        for(int i = 0; i < 27 && player[i] != null; i++) {
            int j;
            for(j = 0; j < ti.length && player[i].getTemplate() != ti[j].getId(); j++);
            PlayerPreview pp = new PlayerPreview(ti[j], player[i]);
            if(pp.PA == 7)
                pp.PA = 0;
            p.add(pp);
        }
        Comparator<PlayerPreview> comparator = Comparator.comparingInt(PlayerPreview::getNumber);
        FXCollections.sort(p, comparator);
    }

    @FXML
    public void switchToDashboard() throws Exception {
        //Controllo se è stato acquistato qualcosa. In caso positivo, salvo il nuovo contenuto
            //App.getTeam().updateStaff();
        Connection.getConnection("api/v1/team/add", Connection.POST, JsonExploiter.toJson(App.getTeam()));
        //Connection.getConnection("api/v1/team/add", Connection.POST, JsonExploiter.toJson(App.getTeam()));
        App.setRoot("dashboard");
    }

    //Controllo se gli acquisti sono possibili, in base al tesoro rimasto
    @FXML
    public void calcValue() {
        int tmp = App.getTeam().getTreasury();
        System.out.println(tmp);
        if(option.isSelected())
            tmp -= 50;
        tmp -= (ch.getValue() - App.getTeam().getCheerleader()) * 10 + (ac.getValue() - App.getTeam().getAssistant()) * 10;
        tmp -= (rr.getValue() - App.getTeam().getNreroll()) * r.getReroll() * 2;
        if(tmp < 0) {
            error.setVisible(true);
            ch.getSelectionModel().select(0);
            ac.getSelectionModel().select(0);
            rr.getSelectionModel().select(0);
            option.setSelected(false);
            treasury.setText(Integer.toString(App.getTeam().getTreasury()) + "k");
        }
        else {
            treasury.setText(Integer.toString(tmp) + "k");
            error.setVisible(false);
        }
    }

    /**
     * Funzione che controlla se sono stati effettuati acquisti
     * @return true se sono stati fatti acquisti, false altrimenti
     */
    public boolean checkPurchase() {
        int dac = 0, dch = 0, drr = 0;
        boolean apo = false;
        if(r.isApothecary() && option.isSelected())
            apo = true;
        dac = ac.getValue() - App.getTeam().getAssistant();
        dch = ch.getValue() - App.getTeam().getCheerleader();
        drr = rr.getValue() - App.getTeam().getNreroll();
        if(dac > 0 || dch > 0 || drr > 0 || apo) {
            App.getTeam().setAssistant(App.getTeam().getAssistant() + dac);
            App.getTeam().setCheerleader(App.getTeam().getCheerleader() + dch);
            App.getTeam().setNreroll(App.getTeam().getNreroll() + drr);
            if(apo) {
                App.getTeam().setApothecary(apo);
                App.getTeam().treasury -= 50;
                App.getTeam().value += 50;
            }
            App.getTeam().setTreasury(App.getTeam().getTreasury() - (dac * 10) - (dch * 10) - (drr * r.getReroll()) * 2);
            App.getTeam().value += dac * 10 + dch * 10 + (drr * r.getReroll());
            return true;
        }
        return false;
    }

    //Cambia scena in quella per acquistare nuovi giocatori
    @FXML
    public void switchToPurchase() throws IOException, SQLException {
        App.setNewTeam(false);
        if(checkPurchase()) {
            Connection.getConnection("api/v1/team/add", Connection.POST, JsonExploiter.toJson(App.getTeam()));
        }
        scene = new Scene(App.load("player/player_purchase"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Purchase Player");
        stage.setResizable(false);
        stage.show();
        //App.setRoot("player/player_purchase");

    }

    //Lincenzia un giocatore e salva le modifiche nel database
    @FXML
    public void deletePlayer() throws SQLException, IOException {
        PlayerPreview tmp = players.getSelectionModel().getSelectedItem();
        //Player pl = new Player(App.getTeam().getId(), App.getTeam().getId(), 0, "Loner(4+)", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 1, true, true);
        Connection.params.put("id", tmp.getId());
        Connection.getConnection("api/v1/player/fire", Connection.GET, null);
        if(App.getLeague().isPerennial()) {
            Connection.params.put("player", tmp.getId());
            data = Connection.getConnection("/api/v1/bounty/player", Connection.GET, null);
            List<Bounty> bounties = JsonExploiter.getListFromJson(Bounty.class, data);
            bounties.forEach(bounty -> {
                try {
                    Connection.params.put("id", bounty.getTeam());
                    Connection.params.put("league", App.getLeague().getId());
                    data = Connection.getConnection("/api/v1/team/teams", Connection.GET, null);
                    List<Team> teams = JsonExploiter.getListFromJson(Team.class, data);
                    if(!teams.isEmpty()) {
                        Team t = teams.getFirst();
                        t.treasury += bounty.getReward();
                        Connection.getConnection("/api/v1/team/add", Connection.POST, JsonExploiter.toJson(t));
                    }
                    Connection.getConnection("/api/v1/bounty/remove", Connection.POST, JsonExploiter.toJson(bounty));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        p.remove(tmp);
        App.getTeam().setNgiocatori(App.getTeam().getNgiocatori() - 1);
        if(!tmp.MNG)
            App.getTeam().value -= tmp.val;
        Connection.getConnection("/api/v1/team/add", Connection.POST, JsonExploiter.toJson(App.getTeam()));
    }

    //Apre una scena di riepilogo del giocatore selezionato
    @FXML
    public void switchToPlayerPreview() throws IOException {
        App.setPlayer(players.getSelectionModel().getSelectedItem());
        scene = new Scene(App.load("player/player_preview"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Player Preview");
        stage.setResizable(false);
        stage.show();
        //App.setRoot("player/player_preview");
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ManagementController.stage = stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        ManagementController.scene = scene;
    }

    @FXML public void changeSponsor() throws IOException{
        Stage stage = new Stage();
        Scene scene = new Scene(App.load("team/sponsor"), 200, 320);
        stage.setScene(scene);
        stage.setTitle("Sponsor");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setReady() throws SQLException, IOException {
        //List<Player> listPlayers = PlayerDao.getStarting(App.getTeam().getId());
        App.getTeam().setReady(true);
        Connection.params.put("league", App.getLeague().getId());
        Connection.params.put("team", App.getTeam().getId());
        if(Boolean.getBoolean(Connection.getConnection("/api/v1/result/lastOfRegular", Connection.GET, null))) {
            data = Connection.getConnection("/api/v1/player/mng", Connection.POST, JsonExploiter.toJson(App.getTeam()));
            App.setTeam(JsonExploiter.getObjectFromJson(Team.class, data));
        }
        Connection.params.put("team", App.getTeam().getId());
        data = Connection.getConnection("/api/v1/player/journey", Connection.GET, null);
        List<Player> jrm = JsonExploiter.getListFromJson(Player.class, data);
        Connection.params.put("id", App.getTeam().getJourneyman());
        data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
        PlayerTemplate pt = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
        if(!jrm.isEmpty()) {
            for(Player p : jrm) {
                if(!p.mng)
                    App.getTeam().value -= pt.cost;
                Connection.params.put("id", p.getId());
                Connection.getConnection("/api/v1/player/delete", Connection.GET, null);
            }
        }
        //List<Player> listPlayers = PlayerDao.getStarting(App.getTeam().getId());
        Connection.params.put("team", App.getTeam().getId());
        Connection.params.put("journey", true);
        int nPlayer = Integer.parseInt(Connection.getConnection("/api/v1/player/count", Connection.GET, null));
        List<Player> journey = new ArrayList<>();
        int number = 100;
        while(nPlayer < 11) {
            journey.add(new Player(number++, "Journeyman", App.getTeam().getJourneyman(), App.getTeam().getId(), 0, 0, "Loner(4+)", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, true, 0));
            App.getTeam().value += pt.cost;
            nPlayer++;
        }
        if(!journey.isEmpty()) {
            Connection.getConnection("/api/v1/player/addPlayers", Connection.POST, JsonExploiter.toJson(journey));
        }
        Connection.getConnection("api/v1/team/add", Connection.POST, JsonExploiter.toJson(App.getTeam()));

        App.setRoot("team/team_management");
    }

    @FXML public void checkJourney() throws SQLException, IOException {
        PlayerPreview tmp = players.getSelectionModel().getSelectedItem();
        if(tmp.isJourney()) {
            journey.setDisable(false);
            fire.setVisible(false);
            journey.setVisible(true);
            if(App.getTeam().treasury < tmp.val || App.getTeam().isReady() || App.getTeam().ngiocatori < 16)
                journey.setDisable(true);
        }
        else {
            fire.setVisible(true);
            journey.setVisible(false);
        }
    }

    @FXML public void editStadium() throws IOException {
        scene = new Scene(App.load("team/stadium"), 200, 200);
        stage.setScene(scene);
        stage.setTitle("Select Stadium");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void takeJourney() throws SQLException, IOException {}
}
