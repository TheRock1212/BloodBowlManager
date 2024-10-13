package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.PlayerTemplateDao;
import it.unipi.dataset.Dao.ResultDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Result;
import it.unipi.utility.Fixture;
import it.unipi.utility.State;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

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

    //Sezione Overview
    @FXML private Label homeTd, homeCas, awayTd, awayCas, homeTeam, awayTeam, scorerTDH, scorerTDA, scorerCASH, scorerCASA, scorerKILLH, scorerKILLA, scorerCPH, scorerCPA, scorerDECH, scorerDECA, scorerINTH, scorerINTA;
    private static String tdh = "", tda = "", cash = "", casa = "", kh = "", ka = "", cph = "", cpa = "", dh = "", da = "", ih = "", ia = "";

    //Sezione SetEvent
    @FXML private Label title, active, vittima, inf;
    @FXML private ComboBox<String> team, entity;
    @FXML private ComboBox<Integer> player, suff;
    @FXML private Button set;


    private Stage stage = new Stage();
    private Scene scene;

    private static Scene overview;

    public void initialize() {
        switch (stato) {
            case FIXTURE: {
                for(int i = 2; i < App.getLeague().getNTeams(); i++)
                    rounds.getItems().add(i);
                mode.getItems().add("Round Robin");
                //mode.getItems().add("One at a Time");
                break;
            }
            case SELECTMODE: {
                teamH.setText(App.getResult().home);
                teamA.setText(App.getResult().away);
                SpinnerValueFactory<Integer> win = new SpinnerValueFactory.IntegerSpinnerValueFactory(5000, 200000, 5000, 5000);
                winH.setValueFactory(win);
                win = new SpinnerValueFactory.IntegerSpinnerValueFactory(5000, 200000, 5000, 5000);
                winA.setValueFactory(win);
                SpinnerValueFactory<Integer> df = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 1, 0, 1);
                dfH.setValueFactory(df);
                df = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 1, 0, 1);
                dfA.setValueFactory(df);
                for(Player p : App.getResult().getPlayersH())
                    mvpH.getItems().add(p.number);
                for(Player p : App.getResult().getPlayersA())
                    mvpA.getItems().add(p.number);
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
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
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
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
                break;
            }
            case SETINF: {
                title.setText("Add INF");
                active.setVisible(false);
                player.setVisible(false);
                team.getItems().add(App.getResult().home);
                team.getItems().add(App.getResult().away);
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

    @FXML public void setFixture() throws SQLException, IOException {
        Fixture fixture = new Fixture();
        fixture.RoundRobin(rounds.getValue());
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
        for(Player p : App.getResult().getPlayersH())
            if(mvpH.getValue() == p.number) {
                p.spp += 4;
                p.unspentSPP += 4;
            }
        for(Player p : App.getResult().getPlayersA())
            if(mvpA.getValue() == p.number) {
                p.spp += 4;
                p.unspentSPP += 4;
            }
        App.getResult().gettH().df = Math.max(0, App.getResult().gettH().df + App.getResult().dfh);
        App.getResult().gettA().df = Math.max(0, App.getResult().gettA().df + App.getResult().dfa);
        App.getResult().gettH().treasury += (App.getResult().winh / 1000);
        App.getResult().gettA().treasury += (App.getResult().wina / 1000);
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

    @FXML public void set() throws IOException, SQLException {
        switch(getStato()) {
            case SETTD: {
                if(team.getValue().equals(App.getResult().home)) {
                    App.getResult().tdh++;
                    App.getResult().gettH().tdScored++;
                    App.getResult().gettA().tdConceded++;
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
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.cas++;
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
                            }
                            break;
                        }
                    }
                    if(!entity.getValue().equals("BH")) {
                        int i = 0;
                        for (Player p : App.getResult().getPlayersA()) {
                            if (p.number == suff.getValue()) {
                                p.mng = true;
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
                            }
                            break;
                        }
                    }
                    if(!entity.getValue().equals("BH")) {
                        int i = 0;
                        for (Player p : App.getResult().getPlayersH()) {
                            if (p.number == suff.getValue()) {
                                p.mng = true;
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
                    for(Player p : App.getResult().getPlayersH()) {
                        if(p.number == player.getValue()) {
                            p.cp++;
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
                if(team.getValue().equals(App.getResult().home)) {
                    int i = 0;
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
                    for(Player p : App.getResult().getPlayersA()) {
                        if (p.number == suff.getValue()) {
                            addInf(i, false);
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        Stage stage = (Stage) set.getScene().getWindow();
        stage.close();
        setStato(State.OVERVIEW);
        //App.setRoot("result/overview");
        overview.setRoot(App.load("result/overview"));
    }

    private void addInf(int i, boolean home) throws SQLException {
        Player p;
        if(home)
            p = App.getResult().getPlayersH().get(i);
        else
            p = App.getResult().getPlayersA().get(i);
        PlayerTemplate pt = PlayerTemplateDao.getPlayer(p.getTemplate());
        switch(entity.getValue()) {
            case "NIG": {
                p.nig = (p.nig < 4) ? p.nig + 1 : p.nig;
                break;
            }
            case "MA-": {
                if(p.getMaDec() < 4 && (pt.ma + p.getMaInc() - p.getMaDec()) > 1) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;
                    }
                    p.setMaDec(p.getMaDec() + 1);
                }
                break;
            }
            case "ST-": {
                if(p.getStDec() < 4 && (pt.st + p.getStInc() - p.getStDec()) > 1) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;
                    }
                    p.setStDec(p.getStDec() + 1);
                }
                break;
            }
            case "AG-": {
                if(p.getAgDec() < 4 && (pt.ag - p.getAgInc() + p.getAgDec()) < 6) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;
                    }
                    p.setAgDec(p.getAgDec() + 1);
                }
                break;
            }
            case "PA-": {
                if(p.getPaDec() < 4 && (pt.pa - p.getPaInc() + p.getPaDec()) < 7) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;
                    }
                    p.setPaDec(p.getPaDec() + 1);
                }
                break;
            }
            case "AV-": {
                if(p.getAvDec() < 4 && (pt.av + p.getAvInc() - p.getAvDec()) > 3) {
                    if((p.getMaDec() + p.getStDec() + p.getAgDec() + p.getPaDec() + p.getAvDec()) == 0) {
                        p.value -= 5;
                        if(home)
                            App.getResult().gettH().value -= 5;
                        else
                            App.getResult().gettA().value -= 5;
                    }
                    p.setAvDec(p.getAvDec() + 1);
                }
                break;
            }
            case "DEAD": {
                if(home) {
                    App.getResult().getKilledH().add(p.getId());
                    App.getResult().gettH().ngiocatori--;
                }
                else {
                    App.getResult().getKilledA().add(p.getId());
                    App.getResult().gettA().ngiocatori--;
                }
            }
        }
    }

    @FXML public void sendResult() throws IOException, SQLException {
        setResult();
        ResultDao.setResult(new Result(App.getResult()));
        TeamDao.updateResult(App.getResult().gettH());
        TeamDao.updateResult(App.getResult().gettA());
        PlayerDao.updateResults(App.getResult().getPlayersH());
        PlayerDao.updateResults(App.getResult().getPlayersA());
        PlayerDao.setDead(App.getResult().getKilledH());
        PlayerDao.setDead(App.getResult().getKilledA());
        Stage stage = (Stage) homeTeam.getScene().getWindow();
        stage.close();
        App.setRoot("dashboard");
    }

    private void setResult() {
        App.getResult().gettH().g++;
        App.getResult().gettA().g++;
        if(App.getResult().tdh > App.getResult().tda) {
            App.getResult().gettH().w++;
            App.getResult().gettA().l++;
            App.getResult().gettH().points += App.getLeague().getPtsWin();
            App.getResult().gettA().points += App.getLeague().getPtsLose();
        }
        else if (App.getResult().tdh < App.getResult().tda) {
            App.getResult().gettA().w++;
            App.getResult().gettH().l++;
            App.getResult().gettA().points += App.getLeague().getPtsWin();
            App.getResult().gettH().points += App.getLeague().getPtsLose();
        }
        else {
            App.getResult().gettA().n++;
            App.getResult().gettH().n++;
            App.getResult().gettA().points += App.getLeague().getPtsTie();
            App.getResult().gettH().points += App.getLeague().getPtsTie();
        }
        //Gestione Punti Secondari
        if(App.getLeague().isPtsTD()) {
            if(App.getResult().tdh > 2)
                App.getResult().gettH().points++;
            if(App.getResult().tda > 2)
                App.getResult().gettA().points++;
        }
        if(App.getLeague().isPtsCAS()) {
            if(App.getResult().cash > 2)
                App.getResult().gettH().points++;
            if(App.getResult().casa > 2)
                App.getResult().gettA().points++;
        }
        if(App.getLeague().isPtsTDConceded()) {
            if(App.getResult().tdh == 0)
                App.getResult().gettA().points++;
            if(App.getResult().tda == 0)
                App.getResult().gettH().points++;
        }
    }
}
