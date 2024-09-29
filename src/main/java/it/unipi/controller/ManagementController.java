package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Player;
import it.unipi.dataset.PlayerTemplate;
import it.unipi.dataset.Race;
import it.unipi.dataset.Team;
import it.unipi.utility.PlayerPreview;
import it.unipi.utility.TemplateImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

public class ManagementController {

    @FXML
    TableView<PlayerPreview> players = new TableView<>();
    ObservableList<PlayerPreview> p;

    private Player[] player = new Player[27];
    private Race r = new Race();

    @FXML private Label costRr, treasury, apoUn, apoAcq, error;
    @FXML private RadioButton option;
    @FXML private ComboBox<Integer> rr, ac, ch;

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

        players.getColumns().addAll(image, nr, namePlayer, nomeCol, ma, st, ag, pa, av, skills, unspp, totalspp, mng, nig, Cost);
        p = FXCollections.observableArrayList();
        players.setItems(p);

        ResultSet rs = r.getRace(App.getTeam().getRace());
        while(rs.next()) {
            r = new Race(rs.getInt("id"), rs.getString("name"), rs.getInt("positional"), rs.getInt("cost_reroll"), rs.getBoolean("apothecary"), rs.getString("special_1"), rs.getString("special_2"), rs.getString("special_3"));
        }

        getTable();
        //Metto il tesoro rimanente e le altre informazioni relative alle razze
        treasury.setText(Integer.toString(App.getTeam().getTreasury()) + "k");

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
    }

    public void getTable() throws SQLException{
        //Ottengono informazioni relative atutti i giocatori acquistabili da quella razza
        Player tmp = new Player();
        ResultSet rs = tmp.getPlayers(App.getTeam().getId(), true);
        int cont = 0;
        while(rs.next()) {
            player[cont++] = new Player(rs.getInt("id"), rs.getInt("number"), rs.getString("name"), rs.getInt("player_template"), rs.getInt("team"), rs.getInt("unspentSPP"), rs.getInt("SPP"), rs.getString("new_skill"), rs.getInt("MA_inc"), rs.getInt("ST_inc"), rs.getInt("AG_inc"), rs.getInt("PA_inc"), rs.getInt("AV_inc"), rs.getInt("MA_dec"), rs.getInt("ST_dec"), rs.getInt("AG_dec"), rs.getInt("PA_dec"), rs.getInt("AV_dec"), rs.getInt("NIG"), rs.getBoolean("MNG"), rs.getInt("val"), rs.getInt("TD"), rs.getInt("CAS"), rs.getInt("K"), rs.getInt("CP"), rs.getInt("D"), rs.getInt("I"), rs.getInt("lev"), rs.getBoolean("status"), rs.getBoolean("isjourney"));
        }
        PlayerTemplate pt = new PlayerTemplate();
        rs = pt.getTemplate(App.getTeam().getRace());
        TemplateImage[] ti = new TemplateImage[r.getPositional()];
        cont = 0;
        while(rs.next()) {
            pt = new PlayerTemplate(rs.getInt("id"), rs.getString("position"), rs.getInt("race"), rs.getInt("MA"), rs.getInt("ST"), rs.getInt("AG"), rs.getInt("PA"), rs.getInt("AV"), rs.getString("skill"), rs.getInt("max_qty"), rs.getString("primary"), rs.getString("secondary"), rs.getInt("cost"), rs.getString("url"), rs.getBoolean("big_guy"));
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
            App.getTeam().updateStaff();
        }
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
                App.getTeam().setTreasury(App.getTeam().getTreasury() - 50);
            }
            App.getTeam().setTreasury(App.getTeam().getTreasury() - (dac * 10) - (dch * 10) - (drr * r.getReroll()) * 2);
            return true;
        }
        return false;
    }

    //Cambia scena in quella per acquistare nuovi giocatori
    @FXML
    public void switchToPurchase() throws IOException, SQLException {
        App.setNewTeam(false);
        if(checkPurchase()) {
            App.getTeam().updateStaff();
        }
        App.setRoot("player/player_purchase");

    }

    //Lincenzia un giocatore e salva le modifiche nel database
    @FXML
    public void deletePlayer() throws SQLException {
        PlayerPreview tmp = players.getSelectionModel().getSelectedItem();
        //Connection.getConnection("/team/fire", "POST", Integer.toString(tmp.getId()));
        Player pl = new Player();
        pl.removePlayer(tmp.getId());
        p.remove(tmp);
        App.getTeam().setNgiocatori(App.getTeam().getNgiocatori() - 1);
        App.getTeam().setValue(App.getTeam().getValue() - tmp.getVal());
        int mng = 0, nj = 0;
        for(int j = 0; j < 27; j++) {
            if(player[j] == null)
                break;
            if(player[j].isJourney())
                nj++;
            if(player[j].mng)
                mng++;
        }
        ResultSet rs = null;
        PlayerTemplate pt;
        if((App.getTeam().getNgiocatori() - mng + nj) < 11) {
            pt = new PlayerTemplate();
            rs = pt.getJourneyman(App.getTeam().getJourneyman());
            rs.next();
        }
        int nrPlayer = 0;
        while(nrPlayer < (11 - (App.getTeam().getNgiocatori() - mng + nj))) {
            int i = 0, journeyNbr = 100;
            for(; i < 27; i++) {
                if(player[i] == null) {
                    break;
                }
                if(player[i].isJourney())
                    journeyNbr++;
            }
            player[i] = new Player(journeyNbr, "Journeyman", rs.getInt("id"), App.getTeam().getId(), 0, 0, "Loner(4+)", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, true);
            player[i].addPlayer();
            pt = new PlayerTemplate(pt = new PlayerTemplate(rs.getInt("id"), rs.getString("position"), rs.getInt("race"), rs.getInt("MA"), rs.getInt("ST"), rs.getInt("AG"), rs.getInt("PA"), rs.getInt("AV"), rs.getString("skill"), rs.getInt("max_qty"), rs.getString("primary"), rs.getString("secondary"), rs.getInt("cost"), rs.getString("url"), rs.getBoolean("big_guy")));
            TemplateImage ti = new TemplateImage(pt);
            ti = new TemplateImage(pt);
            ti.img = new ImageView();
            ti.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
            PlayerPreview pp = new PlayerPreview(ti, player[i]);
            p.add(pp);
            //App.getTeam().setNgiocatori(App.getTeam().getNgiocatori() + 1);
            App.getTeam().setValue(App.getTeam().getValue() + pp.getVal());
            nrPlayer++;
        }
        App.getTeam().updatePlayer();
    }

    //Apre una scena di riepilogo del giocatore selezionato
    @FXML
    public void switchToPlayerPreview() throws IOException {
        App.setPlayer(players.getSelectionModel().getSelectedItem());
        App.setRoot("player/player_preview");
    }

}
