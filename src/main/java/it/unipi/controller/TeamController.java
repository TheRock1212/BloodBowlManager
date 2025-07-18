package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Race;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.connection.Connection;
import it.unipi.utility.json.JsonExploiter;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TeamController {
    @FXML private ComboBox<String> race;
    @FXML private ComboBox<Integer> cheer, assi, df, reroll;
    @FXML private TextField teamName, coach;
    @FXML private Label rules, noApo, rrValue, error, treasury, journeyman;
    @FXML private ToggleGroup journey;
    @FXML private CheckBox apo;
    @FXML private RadioButton zombie, skeleton;

    private final Stage stage = new Stage();

    private final Team t = new Team();
    private Race r = new Race();

    private String data;

    @FXML public void initialize() throws Exception {
        //ResultSet rs = r.getNames();
        Connection.params.put("secret", false);
        data = Connection.getConnection("/api/v1/race/name", Connection.GET, null);
        List<String> names = JsonExploiter.getListFromJson(String.class, data);
        Collections.sort(names);
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
        tmp -= reroll.getValue() * r.getCostreroll();
        t.setNreroll(reroll.getValue());
        t.setAssistant(assi.getValue());
        t.setCheerleader(cheer.getValue());
        t.setDf(df.getValue());
        treasury.setText(Integer.toString(tmp));
    }

    @FXML public void changeOptions() throws Exception {
        Connection.params.put("name", race.getValue().replace(" ", "+"));
        data = Connection.getConnection("/api/v1/race/raceName", Connection.GET, null);
        r = JsonExploiter.getObjectFromJson(Race.class, data);
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
        rrValue.setText(r.getCostreroll() + "k");
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
        if(teamName.getText().isEmpty()) {
            error.setVisible(true);
            error.setText("Error: Team name cannot be empty");
            return;
        }
        t.setCoach(coach.getText());
        t.setName(teamName.getText());
        t.setTreasury(Integer.parseInt(treasury.getText()));
        t.value = App.getLeague().getTreasury() - Integer.parseInt(treasury.getText()) - (t.df - 1) * 10;
        t.setLeague(App.getLeague().getId());
        t.setRound(0);
        t.setRace(r.getId());
        t.setId(0);
        t.setActive(true);
        if(t.getRace() == 4) {
            if(journey.getSelectedToggle() == zombie)
                t.setJourneyman(16);
            else
                t.setJourneyman(15);
        }
        else {
            Connection.params.put("race", t.getRace());
            data = Connection.getConnection("/api/v1/playerTemplate/lineman", Connection.GET, null);
            t.setJourneyman(JsonExploiter.getObjectFromJson(PlayerTemplate.class, data).getId());
        }
        t.setReady(true);
        int id = Integer.parseInt(Connection.getConnection("/api/v1/team/add", Connection.GET, JsonExploiter.toJson(t)));
        t.setId(id);
        App.setTeam(t);
        App.setNewTeam(true);
        App.setNaming(false);
        Stage old = (Stage)df.getScene().getWindow();
        old.close();
        Scene scene = new Scene(App.load("player/player_purchase"), 600, 400);
        stage.setScene(scene);
        stage.setTitle("Purchase Players");
        stage.setResizable(false);
        stage.show();
        //App.setRoot("player/player_purchase");
    }
}
