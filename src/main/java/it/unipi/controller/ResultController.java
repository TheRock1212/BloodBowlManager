package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.*;
import it.unipi.dataset.Model.*;
import it.unipi.utility.Fixture;
import it.unipi.utility.State;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultController {
    private static State stato;
    //Inserimento Calendario
    private @FXML ComboBox<String> mode;
    private @FXML ComboBox<Integer> rounds;

    //Sezione Post-Partita
    @FXML private Label teamH, teamA;
    @FXML private Spinner<Integer> dfH, dfA, winH, winA;
    @FXML private DatePicker play;
    @FXML private ComboBox<Integer> mvpH, mvpA;
    @FXML private CheckBox conH, conA;

    //Sezione Overview
    @FXML private Label homeTd, homeCas, awayTd, awayCas, homeTeam, awayTeam, scorerTDH, scorerTDA, scorerCASH, scorerCASA, scorerKILLH, scorerKILLA, scorerCPH, scorerCPA, scorerDECH, scorerDECA, scorerINTH, scorerINTA;
    private static String tdh = "", tda = "", cash = "", casa = "", kh = "", ka = "", cph = "", cpa = "", dh = "", da = "", ih = "", ia = "";

    //Sezione SetEvent
    @FXML private Label title, active, vittima, inf, qty;
    @FXML private ComboBox<String> team, entity;
    @FXML private ComboBox<Integer> player, suff;
    @FXML private Button set;
    @FXML private CheckBox nuffle, star_active, star_victim;
    @FXML private Spinner<Integer> spp;
    @FXML private ComboBox<String> star_active_combo, star_victim_combo;


    private Stage stage = new Stage();
    private Scene scene;

    private static Scene overview;
    private static boolean raisedH = true, raisedA = true;
    private String data;

    public void initialize() {
        switch (stato) {
            case FIXTURE: {
                for(int i = 2; i < App.getLeague().getTeams(); i++)
                    rounds.getItems().add(i);
                mode.getItems().add("Round Robin");
                mode.getItems().add("RR Collapse Groups");
                //mode.getItems().add("One at a Time");
                break;
            }
            case SELECTMODE: {
                teamH.setText(App.getResult().home);
                teamA.setText(App.getResult().away);
                SpinnerValueFactory<Integer> win = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100000, 200000, 5000, 5000);
                winH.setValueFactory(win);
                win = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100000, 200000, 5000, 5000);
                winA.setValueFactory(win);
                SpinnerValueFactory<Integer> df = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 1, 0, 1);
                dfH.setValueFactory(df);
                df = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 1, 0, 1);
                dfA.setValueFactory(df);
                Comparator<Player> comparator = Comparator.comparingInt(Player::getNumber);
                App.getResult().getPlayersH().sort(comparator);
                App.getResult().getPlayersA().sort(comparator);
                for(Player p : App.getResult().getPlayersH())
                    mvpH.getItems().add(p.number);
                for(Player p : App.getResult().getPlayersA())
                    mvpA.getItems().add(p.number);
                raisedH = raisedA = true;
                break;
            }
            case OVERVIEW: {
                homeTeam.setText(App.getResult().home);
                awayTeam.setText(App.getResult().away);
                homeTd.setText(Integer.toString(App.getResult().tdh));
                awayTd.setText(Integer.toString(App.getResult().tda));
                homeCas.setText("(" + App.getResult().cash + ")");
                awayCas.setText("(" + App.getResult().casa + ")");
                scorerTDH.setText(tdh);
                scorerTDA.setText(tda);
                scorerCPH.setText(cph);
                scorerCPA.setText(cpa);
                scorerDECH.setText(dh);
                scorerDECA.setText(da);
                scorerINTH.setText(ih);
                scorerINTA.setText(ia);
                scorerCASH.setText(cash);
                scorerCASA.setText(casa);
                scorerKILLH.setText(kh);
                scorerKILLA.setText(ka);
                break;
            }
            case SETTD: {
                title.setText("Add TD");
                suff.setVisible(false);
                inf.setVisible(false);
                vittima.setVisible(false);
                entity.setVisible(false);
                nuffle.setVisible(false);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                star_victim.setVisible(false);
                break;
            }
            case SETCAS: {
                title.setText("Add CAS");
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                break;
            }
            case SETCP: {
                title.setText("Add CP");
                suff.setVisible(false);
                inf.setVisible(false);
                vittima.setVisible(false);
                entity.setVisible(false);
                star_victim.setVisible(false);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                break;
            }
            case SETDEC: {
                title.setText("Add DEC");
                suff.setVisible(false);
                inf.setVisible(false);
                vittima.setVisible(false);
                entity.setVisible(false);
                nuffle.setVisible(false);
                star_victim.setVisible(false);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                break;
            }
            case SETINT: {
                title.setText("Add INT");
                suff.setVisible(false);
                inf.setVisible(false);
                vittima.setVisible(false);
                entity.setVisible(false);
                nuffle.setVisible(false);
                star_victim.setVisible(false);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                break;
            }
            case SETINF: {
                title.setText("Add INF");
                active.setVisible(false);
                player.setVisible(false);
                nuffle.setVisible(false);
                star_active.setVisible(false);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                break;
            }
            case SETEXTRA: {
                suff.setVisible(false);
                inf.setVisible(false);
                vittima.setVisible(false);
                entity.setVisible(false);
                nuffle.setVisible(false);
                star_active.setVisible(false);
                star_victim.setVisible(false);
                qty.setVisible(true);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                spp.setVisible(true);
                SpinnerValueFactory<Integer> s = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
                spp.setValueFactory(s);
                break;
            }
        }
    }

    public static State getStato() {
        return stato;
    }

    public static void setStato(State stato) {
        ResultController.stato = stato;
    }

    @FXML public void setFixture() throws Exception {
        Fixture fixture = new Fixture();
        boolean collapse = mode.getValue().contains("Collapse");
        fixture.RoundRobin(rounds.getValue(), collapse);
        Stage stage = (Stage) rounds.getScene().getWindow();
        stage.close();
        App.setRoot("dashboard");
    }

    @FXML public void switchToOverview() throws IOException {
        App.getResult().dfh = dfH.getValue();
        App.getResult().dfa = dfA.getValue();
        App.getResult().winh = winH.getValue();
        App.getResult().wina = winA.getValue();
        App.getResult().date = Date.valueOf(play.getValue());
        if(conH.isSelected()) {
            for(Player p : App.getResult().getPlayersA())
                if(mvpH.getValue() == p.number) {
                    p.spp += 4;
                    p.unspentSPP += 4;
                }
            for(Player p : App.getResult().getPlayersA())
                if(mvpA.getValue() == p.number) {
                    p.spp += 4;
                    p.unspentSPP += 4;
                }
        } else if(conA.isSelected()) {
            for(Player p : App.getResult().getPlayersH())
                if(mvpH.getValue() == p.number) {
                    p.spp += 4;
                    p.unspentSPP += 4;
                }
            for(Player p : App.getResult().getPlayersH())
                if(mvpA.getValue() == p.number) {
                    p.spp += 4;
                    p.unspentSPP += 4;
                }
        } else {
            for (Player p : App.getResult().getPlayersH())
                if (mvpH.getValue() == p.number) {
                    p.spp += 4;
                    p.unspentSPP += 4;
                }
            for (Player p : App.getResult().getPlayersA())
                if (mvpA.getValue() == p.number) {
                    p.spp += 4;
                    p.unspentSPP += 4;
                }
        }
        App.getResult().gettH().df = Math.max(0, App.getResult().gettH().df + App.getResult().dfh);
        App.getResult().gettA().df = Math.max(0, App.getResult().gettA().df + App.getResult().dfa);
        App.getResult().gettH().treasury += (App.getResult().winh / 1000);
        App.getResult().gettA().treasury += (App.getResult().wina / 1000);
        App.getResult().gettH().setReady(false);
        App.getResult().gettA().setReady(false);
        Stage stage = (Stage) play.getScene().getWindow();
        stage.close();
        setStato(State.OVERVIEW);
        overview = new Scene(App.load("result/overview"), 600, 600);
        stage.setScene(overview);
        stage.setTitle("Report");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setPlayer() throws IOException {
        if(!player.getItems().isEmpty())
            player.getItems().clear();
        if(team.getValue().equals(App.getResult().home)) {
            for(Player p : App.getResult().getPlayersH()) {
                player.getItems().add(p.number);
            }
        }
        else {
            for(Player p : App.getResult().getPlayersA()) {
                player.getItems().add(p.number);
            }
        }
        if(getStato() == State.SETCAS) {
            if(team.getValue().equals(App.getResult().home))
                for(Player p : App.getResult().getPlayersA()) {
                    suff.getItems().add(p.number);
                }
            else
                for(Player p : App.getResult().getPlayersH()) {
                    suff.getItems().add(p.number);
                }
            setEntity();
        }
        if(getStato() == State.SETINF) {
            if(team.getValue().equals(App.getResult().home))
                for(Player p : App.getResult().getPlayersH()) {
                    suff.getItems().add(p.number);
                }
            else {
                for(Player p : App.getResult().getPlayersA()) {
                    suff.getItems().add(p.number);
                }
            }
            setEntity();
        }
    }

    private void setEntity() {
        entity.getItems().add("BH");
        entity.getItems().add("MNG");
        entity.getItems().add("NIG");
        entity.getItems().add("MA-");
        entity.getItems().add("AV-");
        entity.getItems().add("PA-");
        entity.getItems().add("AG-");
        entity.getItems().add("ST-");
        entity.getItems().add("DEAD");
    }

    @FXML public void setTD() throws IOException {
        setStato(State.SETTD);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set TD");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setCAS() throws IOException {
        setStato(State.SETCAS);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set CAS");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setCP() throws IOException {
        setStato(State.SETCP);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set CP");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setDEC() throws IOException {
        setStato(State.SETDEC);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set DEC");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setINT() throws IOException {
        setStato(State.SETINT);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set INT");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setINF() throws IOException {
        setStato(State.SETINF);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set INF");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void setExtra() throws IOException {
        setStato(State.SETEXTRA);
        scene = new Scene(App.load("result/set_event"), 250, 400);
        stage.setScene(scene);
        stage.setTitle("Set EXTRA");
        stage.setResizable(false);
        stage.show();
    }

    @FXML public void  set() throws Exception {
        switch(getStato()) {
            case SETTD: {
                if(team.getValue().equals(App.getResult().home)) {
                    App.getResult().tdh++;
                    App.getResult().gettH().tdScored++;
                    App.getResult().gettA().tdConceded++;
                    if(star_active.isSelected()) {
                        if(!tdh.isEmpty())
                            tdh += ", ";
                        tdh += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.td++;
                            p.spp += 3;
                            p.unspentSPP += 3;
                            if(!tdh.isEmpty())
                                tdh += ", ";
                            tdh += p.name;
                            break;
                        }
                    }
                }
                else {
                    App.getResult().tda++;
                    App.getResult().gettA().tdScored++;
                    App.getResult().gettH().tdConceded++;
                    if(star_active.isSelected()) {
                        if(!tda.isEmpty())
                            tda += ", ";
                        tda += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersA()) {
                        if(p.number == player.getValue()) {
                            p.td++;
                            p.spp += 3;
                            p.unspentSPP += 3;
                            if(!tda.isEmpty())
                                tda += ", ";
                            tda += p.name;
                            break;
                        }
                    }
                }
                break;
            }
            case SETCAS: {
                if(team.getValue().equals(App.getResult().home)) {
                    App.getResult().cash++;
                    App.getResult().gettH().casInflicted++;
                    App.getResult().gettA().casSuffered++;
                    if(entity.getValue().equals("DEAD"))
                        App.getResult().killh++;
                    if(star_active.isSelected()) {
                        if(!cash.isEmpty())
                            cash += ", ";
                        cash += star_active_combo.getValue();
                        if(entity.getValue().equals("DEAD")) {
                            if(!kh.isEmpty())
                                kh += ", ";
                            kh += star_active_combo.getValue();
                        }
                        break;
                    }
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.cas++;
                            if(nuffle.isSelected()) {
                                p.spp++;
                                p.unspentSPP++;
                            }
                            p.spp += 2;
                            p.unspentSPP += 2;
                            if(!cash.isEmpty())
                                cash += ", ";
                            cash += p.name;
                            if(entity.getValue().equals("DEAD")) {
                                if(!kh.isEmpty())
                                    kh += ", ";
                                kh += p.name;
                                p.kill++;
                                if(star_active.isSelected() && App.getLeague().isSppstar()) {
                                    p.spp += 2;
                                    p.unspentSPP += 2;
                                }
                            }
                            break;
                        }
                    }
                    if(star_active.isSelected()) {
                        break;
                    }
                    if(!entity.getValue().equals("BH")) {
                        int i = 0;
                        for (Player p : App.getResult().getPlayersA()) {
                            if (p.number == suff.getValue()) {
                                addInf(i, false);
                                break;
                            }
                            i++;
                        }
                    }
                }
                else {
                    App.getResult().casa++;
                    App.getResult().gettA().casInflicted++;
                    App.getResult().gettH().casSuffered++;
                    if(entity.getValue().equals("DEAD"))
                        App.getResult().killa++;
                    if(star_active.isSelected()) {
                        if(!casa.isEmpty())
                            casa += ", ";
                        casa += star_active_combo.getValue();
                        if(entity.getValue().equals("DEAD")) {
                            if(!ka.isEmpty())
                                ka += ", ";
                            ka += star_active_combo.getValue();
                        }
                        break;
                    }
                    for(Player p : App.getResult().getPlayersA()) {
                        if(p.number == player.getValue()) {
                            p.cas++;
                            p.spp += 2;
                            p.unspentSPP += 2;
                            if(!casa.isEmpty())
                                casa += ", ";
                            casa += p.name;
                            if(entity.getValue().equals("DEAD")) {
                                if(!ka.isEmpty())
                                    ka += ", ";
                                ka += p.name;
                                p.kill++;
                                if(star_active.isSelected() && App.getLeague().isSppstar()) {
                                    p.spp += 2;
                                    p.unspentSPP += 2;
                                }
                            }
                            break;
                        }
                    }
                    if(star_active.isSelected()) {
                        break;
                    }
                    if(!entity.getValue().equals("BH")) {
                        int i = 0;
                        for (Player p : App.getResult().getPlayersH()) {
                            if (p.number == suff.getValue()) {
                                addInf(i, true);
                                break;
                            }
                            i++;
                        }
                    }
                }
                break;
            }
            case SETCP: {
                if(team.getValue().equals(App.getResult().home)) {
                    App.getResult().cph++;
                    if(star_active.isSelected()) {
                        if(!cph.isEmpty())
                            cph += ", ";
                        cph += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.cp++;
                            if(nuffle.isSelected()) {
                                p.spp++;
                                p.unspentSPP++;
                            }
                            p.spp ++;
                            p.unspentSPP++;
                            if(!cph.isEmpty())
                                cph += ", ";
                            cph += p.name;
                            break;
                        }
                    }
                }
                else {
                    App.getResult().cpa++;
                    if(star_active.isSelected()) {
                        if(!cpa.isEmpty())
                            cpa += ", ";
                        cpa += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersA()) {
                        if(p.number == player.getValue()) {
                            p.cp++;
                            p.spp ++;
                            p.unspentSPP++;
                            if(!cpa.isEmpty())
                                cpa += ", ";
                            cpa += p.name;
                            break;
                        }
                    }
                }
                break;
            }
            case SETDEC: {
                if(team.getValue().equals(App.getResult().home)) {
                    App.getResult().dech++;
                    if(star_active.isSelected()) {
                        if(!dh.isEmpty())
                            dh += ", ";
                        dh += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.def++;
                            p.spp++;
                            p.unspentSPP++;
                            if(!dh.isEmpty())
                                dh += ", ";
                            dh += p.name;
                            break;
                        }
                    }
                }
                else {
                    App.getResult().deca++;
                    if(star_active.isSelected()) {
                        if(!da.isEmpty())
                            da += ", ";
                        da += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersA()) {
                        if(p.number == player.getValue()) {
                            p.def++;
                            p.spp++;
                            p.unspentSPP++;
                            if(!da.isEmpty())
                                da += ", ";
                            da += p.name;
                            break;
                        }
                    }
                }
                break;
            }
            case SETINT: {
                if(team.getValue().equals(App.getResult().home)) {
                    App.getResult().inth++;
                    if(star_active.isSelected()) {
                        if(!ih.isEmpty())
                            ih += ", ";
                        ih += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.inter++;
                            p.spp += 2;
                            p.unspentSPP += 2;
                            if(!ih.isEmpty())
                                ih += ", ";
                            ih += p.name;
                            break;
                        }
                    }
                }
                else {
                    App.getResult().inta++;
                    if(star_active.isSelected()) {
                        if(!ia.isEmpty())
                            ia += ", ";
                        ia += star_active_combo.getValue();
                        break;
                    }
                    for(Player p : App.getResult().getPlayersA()) {
                        if(p.number == player.getValue()) {
                            p.inter++;
                            p.spp += 2;
                            p.unspentSPP += 2;
                            if(!ia.isEmpty())
                                ia += ", ";
                            ia += p.name;
                            break;
                        }
                    }
                }
                break;
            }
            case SETINF: {
                if(star_active.isSelected()) {
                    break;
                }
                if(team.getValue().equals(App.getResult().home)) {
                    int i = 0;
                    if(entity.getValue().equals("BH"))
                        break;
                    for(Player p : App.getResult().getPlayersH()) {
                        if (p.number == suff.getValue()) {
                            addInf(i, true);
                            break;
                        }
                        i++;
                    }
                }
                else {
                    int i = 0;
                    if(entity.getValue().equals("BH"))
                        break;
                    for(Player p : App.getResult().getPlayersA()) {
                        if (p.number == suff.getValue()) {
                            addInf(i, false);
                            break;
                        }
                        i++;
                    }
                }
                break;
            }
            case SETEXTRA: {
                if(star_victim.isSelected()) {
                    break;
                }
                if(team.getValue().equals(App.getResult().home)) {
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.spp += spp.getValue();
                            p.unspentSPP += spp.getValue();
                            break;
                        }
                    }
                } else if(team.getValue().equals(App.getResult().away)) {
                    for(Player p : App.getResult().getPlayersA()) {
                        if(p.number == player.getValue()) {
                            p.spp += spp.getValue();
                            p.unspentSPP += spp.getValue();
                            break;
                        }
                    }
                }
                break;
            }
        }
        Stage stage = (Stage) set.getScene().getWindow();
        stage.close();
        setStato(State.OVERVIEW);
        //App.setRoot("result/overview");
        overview.setRoot(App.load("result/overview"));
    }

    private void addInf(int i, boolean home) throws Exception {
        Player p;
        if(home)
            p = App.getResult().getPlayersH().get(i);
        else
            p = App.getResult().getPlayersA().get(i);
        Connection.params.put("id", p.getTemplate());
        data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
        PlayerTemplate pt = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
        if(home) {
            Connection.params.put("id", App.getResult().gettH().getRace());
            data = Connection.getConnection("/api/v1/race/haslcl", Connection.GET, null);
            if("true".equals(data) && App.getResult().gettH().getJourneyman() == p.getTemplate())
                App.getResult().gettH().value -= p.value;
            else
                App.getResult().gettH().value -= (p.value + pt.cost);
        }
        else {
            Connection.params.put("id", App.getResult().gettA().getRace());
            data = Connection.getConnection("/api/v1/race/haslcl", Connection.GET, null);
            if("true".equals(data) && App.getResult().gettA().getJourneyman() == p.getTemplate())
                App.getResult().gettA().value -= p.value;
            else
                App.getResult().gettA().value -= (p.value + pt.cost);
        }
        p.mng = true;
        switch(entity.getValue()) {
            case "NIG": {
                p.nig = (p.nig < 4) ? p.nig + 1 : p.nig;
                break;
            }
            case "MA-": {
                if(p.getMaDec() < 4 && (pt.ma + p.getMaInc() - p.getMaDec()) > 1) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        /*if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;*/
                    }
                    p.setMaDec(p.getMaDec() + 1);
                }
                break;
            }
            case "ST-": {
                if(p.getStDec() < 4 && (pt.st + p.getStInc() - p.getStDec()) > 1) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        /*if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;*/
                    }
                    p.setStDec(p.getStDec() + 1);
                }
                break;
            }
            case "AG-": {
                if(p.getAgDec() < 4 && (pt.ag - p.getAgInc() + p.getAgDec()) < 6) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        /*if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;*/
                    }
                    p.setAgDec(p.getAgDec() + 1);
                }
                break;
            }
            case "PA-": {
                if(p.getPaDec() < 4 && (pt.pa - p.getPaInc() + p.getPaDec()) < 7) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        /*if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;*/
                    }
                    p.setPaDec(p.getPaDec() + 1);
                }
                break;
            }
            case "AV-": {
                if(p.getAvDec() < 4 && (pt.av + p.getAvInc() - p.getAvDec()) > 3) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                            p.value -= 5;
                        /*if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;*/
                    }
                    p.setAvDec(p.getAvDec() + 1);
                }
                break;
            }
            case "DEAD": {
                if(home) {
                    if(App.getLeague().isPerennial()) {
                        Connection.params.put("player", p.getId());
                        data = Connection.getConnection("/api/v1/bounty/player", Connection.GET, null);
                        List<Bounty> bounties = JsonExploiter.getListFromJson(Bounty.class, data);
                        if(!bounties.isEmpty()) {
                            List<Bounty> eliminare = new ArrayList<>();
                            bounties.forEach(bounty -> {
                                App.getResult().gettA().treasury += bounty.getReward();
                                eliminare.add(bounty);
                            });
                            if(!eliminare.isEmpty()) {
                                Connection.getConnection("/api/v1/bounty/removeAll", Connection.DELETE, JsonExploiter.toJson(eliminare));
                            }
                        }
                    }
                    App.getResult().getKilledH().add(p.getId());
                    App.getResult().gettH().ngiocatori--;
                    p.setStatus(false);
                    Connection.params.put("id", App.getResult().gettA().getRace());
                    data = Connection.getConnection("/api/v1/race/raised", Connection.GET, null);
                    if("true".equals(data) && raisedA) {
                        Connection.params.put("id", p.getTemplate());
                        data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
                        PlayerTemplate template = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
                        String skills = template.skill + p.skill;
                        if((template.st + p.getStInc() - p.getStDec()) <= 4 && !skills.contains("Stunty") && !skills.contains("Regeneration") && (App.getResult().gettA().getNgiocatori() < 16)) {
                            raisedA = false;
                            Player risorto = new Player(200, p.name, App.getResult().gettA().getJourneyman(), App.getResult().gettA().getId(), 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, false, 0);
                            Connection.getConnection("/api/v1/player/add", Connection.POST, JsonExploiter.toJson(risorto));
                            Connection.params.put("id", App.getResult().gettA().getJourneyman());
                            data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
                            PlayerTemplate newPlayer = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
                            App.getResult().gettA().value += newPlayer.cost;
                            App.getResult().gettA().ngiocatori++;
                        }
                    }
                    //App.getResult().gettH().value -= (template.cost + p.value);
                }
                else {
                    if(App.getLeague().isPerennial()) {
                        Connection.params.put("player", p.getId());
                        data = Connection.getConnection("/api/v1/bounty/player", Connection.GET, null);
                        List<Bounty> bounties = JsonExploiter.getListFromJson(Bounty.class, data);
                        if(!bounties.isEmpty()) {
                            List<Bounty> eliminare = new ArrayList<>();
                            bounties.forEach(bounty -> {
                                App.getResult().gettH().treasury += bounty.getReward();
                                eliminare.add(bounty);
                            });
                            if(!eliminare.isEmpty()) {
                                Connection.getConnection("/api/v1/bounty/removeAll", Connection.DELETE, JsonExploiter.toJson(eliminare));
                            }
                        }
                    }
                    App.getResult().getKilledA().add(p.getId());
                    App.getResult().gettA().ngiocatori--;
                    p.setStatus(false);
                    Connection.params.put("id", App.getResult().gettH().getRace());
                    data = Connection.getConnection("/api/v1/race/raised", Connection.GET, null);
                    if("true".equals(data) && raisedH) {
                        Connection.params.put("id", p.getTemplate());
                        data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
                        PlayerTemplate template = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
                        String skills = template.skill + p.skill;
                        if((template.st + p.getStInc() - p.getStDec()) <= 4 && !skills.contains("Stunty") && !skills.contains("Regeneration") && App.getResult().gettH().getNgiocatori() < 16) {
                            raisedH = false;
                            Player risorto = new Player(200, p.name, App.getResult().gettH().getJourneyman(), App.getResult().gettH().getId(), 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, false, 0);
                            Connection.getConnection("/api/v1/player/add", Connection.POST, JsonExploiter.toJson(risorto));
                            Connection.params.put("id", App.getResult().gettH().getJourneyman());
                            data = Connection.getConnection("/api/v1/playerTemplate/id", Connection.GET, null);
                            PlayerTemplate newPlayer = JsonExploiter.getObjectFromJson(PlayerTemplate.class, data);
                            App.getResult().gettH().value += newPlayer.cost;
                            App.getResult().gettH().ngiocatori++;
                        }
                    }
                }
            }
        }
    }

    @FXML public void sendResult() throws IOException, SQLException {
        setResult();
        App.getResult().played = true;
        Result result = new Result(App.getResult());
        Connection.getConnection("/api/v1/result/update", Connection.POST, JsonExploiter.toJson(result));
        List<Player> players = new ArrayList<>(App.getResult().getPlayersH());
        players.addAll(App.getResult().getPlayersA());
        Connection.getConnection("/api/v1/player/updateAll", Connection.POST, JsonExploiter.toJson(players));

        //setUpJourneyman(false);
        List<Team> teams = new ArrayList<>();
        teams.add(App.getResult().gettH());
        teams.add(App.getResult().gettA());
        Connection.getConnection("/api/v1/team/update", Connection.POST, JsonExploiter.toJson(teams));
        Stage stage = (Stage) homeTeam.getScene().getWindow();
        stage.close();
        App.setResult(null);
        raisedA = raisedH = true;
        App.setRoot("dashboard");
    }

    private void setResult() {
        tdh = tda = cash = casa = kh = ka = cph = cpa = dh = da = ih = ia = "";
        App.getResult().gettH().g++;
        App.getResult().gettA().g++;
        if(App.getResult().tdh > App.getResult().tda) {
            App.getResult().gettH().w++;
            App.getResult().gettA().l++;
            App.getResult().gettH().points += App.getLeague().getWin();
            App.getResult().gettA().points += App.getLeague().getLoss();
        }
        else if (App.getResult().tdh < App.getResult().tda) {
            App.getResult().gettA().w++;
            App.getResult().gettH().l++;
            App.getResult().gettA().points += App.getLeague().getWin();
            App.getResult().gettH().points += App.getLeague().getLoss();
        }
        else {
            App.getResult().gettA().n++;
            App.getResult().gettH().n++;
            App.getResult().gettA().points += App.getLeague().getTie();
            App.getResult().gettH().points += App.getLeague().getTie();
        }
        //Gestione Punti Secondari
        if(App.getLeague().isPtsTd()) {
            if(App.getResult().tdh > 2)
                App.getResult().gettH().points++;
            if(App.getResult().tda > 2)
                App.getResult().gettA().points++;
        }
        if(App.getLeague().isPtsCas()) {
            if(App.getResult().cash > 2)
                App.getResult().gettH().points++;
            if(App.getResult().casa > 2)
                App.getResult().gettA().points++;
        }
        if(App.getLeague().isPtsTdCon()) {
            if(App.getResult().tdh == 0)
                App.getResult().gettA().points++;
            if(App.getResult().tda == 0)
                App.getResult().gettH().points++;
        }

    }

    @FXML public void setActive() throws Exception {
        if(star_active.isSelected()) {
            player.setVisible(false);
            star_active_combo.setVisible(true);
            star_active_combo.getItems().clear();
            List<StarPlayer> stars;
            if(team.getValue().equals(App.getResult().home)) {
                stars = App.getResult().getStarsH();
            } else {
                stars = App.getResult().getStarsA();
            }
            for(StarPlayer s : stars)
                star_active_combo.getItems().add(s.name);
        }
        else {
            star_active_combo.getItems().clear();
            star_active_combo.setVisible(false);
            player.setVisible(true);
        }
    }

    @FXML public void setVictim() throws Exception {
        if(star_victim.isSelected()) {
            suff.setVisible(false);
            star_victim_combo.setVisible(true);
            star_victim_combo.getItems().clear();
            List<StarPlayer> stars;
            if(team.getValue().equals(App.getResult().home)) {
                stars = App.getResult().getStarsA();
            } else {
                stars = App.getResult().getStarsH();
            }
            for(StarPlayer s : stars)
                star_victim_combo.getItems().add(s.name);
        }
        else {
            star_victim_combo.getItems().clear();
            star_victim_combo.setVisible(false);
            suff.setVisible(true);
        }
    }

    @FXML public void setConceiding() throws SQLException {
        if(conH.isSelected()) {
            mvpH.getItems().clear();
            mvpH.getItems().addAll(mvpA.getItems());
        }
        else if(conA.isSelected()) {
            mvpA.getItems().clear();
            mvpA.getItems().addAll(mvpH.getItems());
        }
    }
}
