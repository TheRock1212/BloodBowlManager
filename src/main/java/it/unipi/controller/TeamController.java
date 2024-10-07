package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerTemplateDao;
import it.unipi.dataset.Dao.RaceDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Race;
import it.unipi.dataset.Model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeamController {
    @FXML private ComboBox<String> race;
    @FXML private ComboBox<Integer> cheer, assi, df, reroll;
    @FXML private TextField teamName, coach;
    @FXML private Label rules, noApo, rrValue, error, treasury, journeyman;
    @FXML private ToggleGroup journey;
    @FXML private CheckBox apo;
    @FXML private RadioButton zombie, skeleton;

    private Team t = new Team();
    private Race r = new Race();

    @FXML public void initialize() throws SQLException {
        //ResultSet rs = r.getNames();
        List<String> names = RaceDao.getNames();
        for(String name: names)
            race.getItems().add(name);
        for(int i = 0; i < 12; i++) {
            cheer.getItems().add(i);
            if(i <= 6)
                assi.getItems().add(i);
            if(i <= 8)
                reroll.getItems().add(i);
            if(i >= 1 && i <= 6)
                df.getItems().add(i);
        }
        cheer.setValue(0);
        assi.setValue(0);
        reroll.setValue(0);
        df.setValue(0);
        treasury.setText(Integer.toString(App.getLeague().getTreasury()));
    }

    @FXML public void calcValue() {
        int tmp = App.getLeague().getTreasury();
        t.setApothecary(false);
        if(apo.isVisible()) {
            if(apo.isSelected()) {
                tmp -= 50;
                t.setApothecary(true);
            }
        }
        tmp -= cheer.getValue() * 10 + assi.getValue() * 10 + df.getValue() * 10 - 10;
        tmp -= reroll.getValue() * r.getReroll();
        t.setNreroll(reroll.getValue());
        t.setAssistant(assi.getValue());
        t.setCheerleader(cheer.getValue());
        t.setDf(df.getValue());
        treasury.setText(Integer.toString(tmp));
    }

    @FXML public void changeOptions() throws SQLException {
        Race r = RaceDao.getRace(race.getValue());
        apo.setVisible(false);
        noApo.setVisible(false);
        if(r.isApothecary())
            apo.setVisible(true);
        else
            noApo.setVisible(true);
        rules.setText(r.getSpecial1() + " " + r.getSpecial2() + " " + r.getSpecial3());
        cheer.getSelectionModel().select(0);
        assi.getSelectionModel().select(0);
        reroll.getSelectionModel().select(0);
        df.getSelectionModel().select(0);
        apo.setSelected(false);
        treasury.setText(Integer.toString(App.getLeague().getTreasury()));
        rrValue.setText(r.getReroll() + "k");
        if(race.getValue().equals("Shambling Undead")) {
            journeyman.setVisible(true);
            zombie.setVisible(true);
            skeleton.setVisible(true);
        }
        else {
            journeyman.setVisible(false);
            zombie.setVisible(false);
            skeleton.setVisible(false);
        }
    }

    @FXML public void purchasePlayer() throws IOException, SQLException {
        if(teamName.getText().equals("")) {
            error.setVisible(true);
            error.setText("Error: Team name cannot be empty");
            return;
        }
        t.setCoach(coach.getText());
        t.setName(teamName.getText());
        t.setTreasury(Integer.valueOf(treasury.getText()));
        t.setValue(App.getLeague().getTreasury() - Integer.valueOf(treasury.getText()));
        t.setLeague(App.getLeague().getId());
        t.setRound(0);
        t.setRace(r.getId());
        if(t.getRace() == 4) {
            if(journey.getSelectedToggle() == zombie)
                t.setJourneyman(16);
            else
                t.setJourneyman(15);
        }
        else {
            PlayerTemplate pt = new PlayerTemplate();
            t.setJourneyman(PlayerTemplateDao.getLineman(t.getRace()));
        }
        TeamDao.addTeam(t);
        App.setTeam(t);
        App.setNewTeam(true);
        App.setNaming(false);
        App.setRoot("player/player_purchase");
    }
}
