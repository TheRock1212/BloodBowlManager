package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.*;
import it.unipi.dataset.Model.*;
import it.unipi.utility.PlayerPreview;
import it.unipi.utility.TemplateImage;
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

    @FXML public void initialize() throws SQLException {
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

        r = RaceDao.getRace(App.getTeam().getRace());

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

    public void getTable() throws SQLException{
        //Ottengono informazioni relative atutti i giocatori acquistabili da quella razza
        int cont;
        List<Player> players = PlayerDao.getPlayers(App.getTeam().getId(), true);
        for(cont = 0; cont < players.size(); cont++)
            player[cont] = players.get(cont);
        List<PlayerTemplate> templates = PlayerTemplateDao.getTemplate(App.getTeam().getRace());
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
    public void switchToDashboard() throws IOException, SQLException {
        //Controllo se è stato acquistato qualcosa. In caso positivo, salvo il nuovo contenuto
        if(checkPurchase()) {
            //App.getTeam().updateStaff();
            TeamDao.updateTeam(App.getTeam(), false);
        }
        TeamDao.updateTeam(App.getTeam(), true);
        TeamDao.saveSponsor(App.getTeam().getId(), App.getTeam().getSponsor(), 0);
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
            TeamDao.updateTeam(App.getTeam(), false);
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
        PlayerDao.removePlayer(tmp.getId());
        if(App.getLeague().isPerennial()) {
            List<Bounty> bounties = BountyDao.getBountyByPlayer(App.getConnection(), tmp.getId());
            bounties.forEach(bounty -> {
                try {
                    List<Team> teams = TeamDao.getTeam(bounty.getTeam(), App.getLeague().getId());
                    if(!teams.isEmpty()) {
                        Team t = teams.getFirst();
                        t.treasury += bounty.getReward();
                    }
                    BountyDao.delete(App.getConnection(), bounty);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        p.remove(tmp);
        App.getTeam().setNgiocatori(App.getTeam().getNgiocatori() - 1);
        if(!tmp.MNG)
            App.getTeam().value -= tmp.val;
        TeamDao.updateTeam(App.getTeam(), true);
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
        if(ResultDao.isLastOfRegular(App.getLeague().getId(), App.getTeam().getId())) {
            List<Player> players = PlayerDao.getMNG(App.getTeam().getId());
            for(Player p : players) {
                PlayerTemplate template = PlayerTemplateDao.getPlayer(p.getTemplate());
                p.setMng(false);
                App.getTeam().value += (p.value + template.cost);
            }
            PlayerDao.updateResults(players);
        }
        Player[] jrm = PlayerDao.getJourneymans(App.getTeam());
        PlayerTemplate pt = PlayerTemplateDao.getJourneyman(App.getTeam().getJourneyman());
        if(jrm != null) {
            for(Player p : jrm) {
                if(!p.mng)
                    App.getTeam().value -= pt.cost;
                PlayerDao.deletePlayer(p.getId());
            }
        }
        //List<Player> listPlayers = PlayerDao.getStarting(App.getTeam().getId());
        int nPlayer = PlayerDao.countPlayers(App.getTeam().getId(), true);
        List<Player> journey = new ArrayList<>();
        int number = 100;
        while(nPlayer < 11) {
            journey.add(new Player(number++, "Journeyman", App.getTeam().getJourneyman(), App.getTeam().getId(), 0, 0, "Loner(4+)", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, true, 0));
            App.getTeam().value += pt.cost;
            nPlayer++;
        }
        if(!journey.isEmpty()) {
            PlayerDao.addPlayer(journey);
        }
        TeamDao.updateTeam(App.getTeam(), true);
        if(checkPurchase()) {
            TeamDao.updateTeam(App.getTeam(), false);
        }

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
