package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.SkillDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.Skill;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PlayerController {
    @FXML
    private Label playerName, ma, st, ag, pa, av, skill, spp, unspentSPP, mng, nig, position, team, nr;
    @FXML private Button edit, levelUp;
    @FXML private ImageView playerPortrait;

    @FXML private Button select, skills;
    @FXML private ComboBox cb;
    @FXML private Label text, error, maxLevel;

    @FXML private Spinner<Integer> number;
    @FXML private TextField newName;
    @FXML private Button saveButton;

    private static boolean lUp = false;
    private static boolean naming = false;

    private int[] nrs = new int[App.getTeam().ngiocatori];
    private String[] names = new String[App.getTeam().ngiocatori];

    public Stage sta = new Stage();
    public Scene sc;
    public int spent = 0, sppRequired = 0, value;

    @FXML
    public void initialize() throws SQLException {
        //Se levelUp è true effettua l'inizializzazione della scena per i level up
        if(islUp()) {
            error.setVisible(false);
            text.setText("Select mode:");
            //setta sppRequired in base al livello del giocatore
            switch(App.getPlayer().getLev()) {
                case 0:
                    sppRequired = 6;
                    break;
                case 1:
                    sppRequired = 8;
                    break;
                case 2:
                    sppRequired = 12;
                    break;
                case 3:
                    sppRequired = 16;
                    break;
                case 4:
                    sppRequired = 20;
                    break;
                case 5:
                    sppRequired = 30;
                    break;
            }
            //Inserisce nella ComboBox le possibilità di level up a seconda degli unspent spp del giocatore
            cb.getItems().add("Random Primary (" + sppRequired / 2 + ")");
            if(App.getPlayer().unspentSPP >= sppRequired) {
                cb.getItems().add("Chosen Primary (" + sppRequired + ")");
                cb.getItems().add("Random Secondary (" + sppRequired + ")");
            }
            if((App.getPlayer().getLev() < 5 && App.getPlayer().unspentSPP >= (sppRequired + 6)) || (App.getPlayer().getLev() == 5 && App.getPlayer().unspentSPP >= (sppRequired + 10))) {
                if(App.getPlayer().getLev() < 5)
                    cb.getItems().add("Chosen Secondary (" + (sppRequired + 6) + ")");
                else
                    cb.getItems().add("Chosen Secondary (" + (sppRequired + 10) + ")");
            }
            if((App.getPlayer().getLev() < 5 && App.getPlayer().unspentSPP >= (sppRequired + 12)) || (App.getPlayer().getLev() == 5 && App.getPlayer().unspentSPP >= (sppRequired + 20))) {
                if(App.getPlayer().getLev() < 5)
                    cb.getItems().add("Characteristic Improvement (" + (sppRequired + 12) + ")");
                else
                    cb.getItems().add("Characteristic Improvement (" + (sppRequired + 20) + ")");
            }
            select.setVisible(true);
            skills.setVisible(false);
            return;
        }
        else if(isNaming()) {
            nrs = PlayerDao.getPlayerNumbers(App.getTeam());
            names = PlayerDao.getPlayerNames(App.getTeam());
            SpinnerValueFactory<Integer> numbs = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 1);
            numbs.setValue(App.getPlayer().number);
            number.setValueFactory(numbs);
            newName.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode() == KeyCode.TAB)
                        checkDuplicate();
                }
            });
            number.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(keyEvent.getCode() == KeyCode.TAB)
                        checkNumber();
                }
            });
            return;
        }
        else {
            maxLevel.setVisible(false);
            //Se il giocatore non ha un nome viene visualizzato il text field e il bottone per assegnarlo, altrimenti viene visualizzata una Label che mostra il nome
            if (App.getPlayer().getName() == null)
                playerName.setVisible(false);
            else {
                playerName.setVisible(true);
                playerName.setText(App.getPlayer().getName());
            }
            //Riempe le informazioni relative al giocatore
            position.setText(App.getPlayer().getPosition());
            team.setText(App.getTeam().getName());
            mng.setText(Boolean.toString(App.getPlayer().isMNG()));
            nig.setText(Integer.toString(App.getPlayer().getNIG()));
            playerPortrait.setImage(App.getPlayer().getImg().getImage());
            nr.setText("#" + Integer.toString(App.getPlayer().number));
            setStats();
        }
    }

    /**
     * Imposta le caratteristiche del giocatore
     */
    public void setStats() {
        ma.setText(Integer.toString(App.getPlayer().getMA()));
        st.setText(Integer.toString(App.getPlayer().getST()));
        ag.setText(Integer.toString(App.getPlayer().getAG()) + "+");
        if(App.getPlayer().getPA() > 0)
            pa.setText(Integer.toString(App.getPlayer().getPA()) + "+");
        else
            pa.setText("-");
        av.setText(Integer.toString(App.getPlayer().getAV()) + "+");
        spp.setText(Integer.toString(App.getPlayer().getSPP()));
        unspentSPP.setText(Integer.toString(App.getPlayer().getUnspentSPP()));
        skill.setText(App.getPlayer().skill);
        switch(App.getPlayer().getLev()) {
            case 0:
                if(App.getPlayer().getUnspentSPP() < 3)
                    levelUp.setVisible(false);
                break;
            case 1:
                if(App.getPlayer().getUnspentSPP() < 4)
                    levelUp.setVisible(false);
                break;
            case 2:
                if(App.getPlayer().getUnspentSPP() < 6)
                    levelUp.setVisible(false);
                break;
            case 3:
                if(App.getPlayer().getUnspentSPP() < 8)
                    levelUp.setVisible(false);
                break;
            case 4:
                if(App.getPlayer().getUnspentSPP() < 10)
                    levelUp.setVisible(false);
                break;
            case 5:
                if(App.getPlayer().getUnspentSPP() < 15)
                    levelUp.setVisible(false);
                break;
        }
    }

    //Salva il nome del giocatore
    @FXML
    public void setName() throws IOException{
        setNaming(true);
        sta.setTitle("Edit Name");
        sc = new Scene(App.load("player/edit_name"), 320, 200);
        sta.setScene(sc);
        sta.show();
    }

    //Torna alla schermata generale del team
    @FXML
    public void switchToTeam() throws IOException, SQLException {
        Player p = new Player(App.getPlayer());
        PlayerDao.updatePlayer(p, true);
        Stage stage = (Stage) ma.getScene().getWindow();
        stage.close();
        App.setRoot("team/team_management");
    }

    //Cambia la scena del level up
    @FXML
    public void switchToLevelUp() throws IOException {
        if(App.getPlayer().getLev() == 6) {
            maxLevel.setVisible(true);
            return;
        }
        setlUp(true);
        sta.setTitle("Choose Skill");
        sc = new Scene(App.load("player/skill"), 250, 200);
        sta.setScene(sc);
        sta.show();
    }


    @FXML
    public void select() throws IOException, SQLException {
        //Cambia il valore di spp spesi in base alla scelta di level up
        switch(cb.getSelectionModel().getSelectedIndex()) {
            case 0:
                spent = sppRequired / 2;
                getSkill(true);
                value = 10;
                break;
            case 1:
                spent = sppRequired;
                getSkill(true);
                value = 20;
                break;
            case 2:
                spent = sppRequired;
                getSkill(false);
                value = 20;
                break;
            case 3:
                if(App.getPlayer().getLev() == 5)
                    spent = sppRequired + 10;
                else
                    spent = sppRequired + 6;
                getSkill(false);
                value = 40;
                break;
            case 4:
                if(App.getPlayer().getLev() == 5)
                    spent = sppRequired + 20;
                else
                    spent = sppRequired + 12;
                getCharacteristics();
                break;
        }
        //Nascondo il bottone di scelta di level up con quello della selezione di skill
        select.setVisible(false);
        skills.setVisible(true);
        text.setText("Select Advancement:");
    }

    /**
     * Funzione che riempe la ComboBox delle skill in base alla scelta
     * @param pri se è true, allora riempe le ComboBox con le primarie, altrimenti con le skill secondarie
     * @throws IOException
     */
    public void getSkill(boolean pri) throws SQLException {
        cb.getItems().removeAll(cb.getItems());
        int i = 0;
        Skill s = new Skill();
        String[] skills;
        if(pri) {
            String[] primary;
            if("Farblast & Sons".equals(App.getTeam().getSponsor()) && TeamDao.isLineman(App.getTeam().getId(), App.getPlayer().getTemplateId()) && App.getPlayer().getNewSkills().contains("Bombardier")) {
                primary = new String[App.getPlayer().getPrimary().length() + 1];
                for(; i < App.getPlayer().getPrimary().length() - 1; i++)
                    primary[i] = Character.toString(App.getPlayer().getPrimary().charAt(i));
                primary[primary.length - 1] = "P";
            } else {
                primary = new String[App.getPlayer().getPrimary().length()];
                for(; i < App.getPlayer().getPrimary().length(); i++)
                    primary[i] = Character.toString(App.getPlayer().getPrimary().charAt(i));
            }

            skills = new String[App.getPlayer().getPrimary().length()];
            skills = SkillDao.getSkill(primary);
        }
        else {
            String[] secondary = new String[App.getPlayer().getSecondary().length()];
            for(; i < App.getPlayer().getSecondary().length(); i++)
                secondary[i] = Character.toString(App.getPlayer().getSecondary().charAt(i));
            skills = new String[App.getPlayer().getPrimary().length()];
            skills = SkillDao.getSkill(secondary);
        }
        for(int j = 0; j < skills.length; j++) {
            cb.getItems().add(skills[j]);
            if(i % 12 == 11)
                cb.getItems().add(new Separator());
        }
    }

    /**
     * Riempe la ComboBox con gli aumenti di caratteristica ne caso sia stato scelto l'avanzamento di caratteristica come level up
     */
    public void getCharacteristics() {
        cb.getItems().removeAll(cb.getItems());
        cb.getItems().add("MA+");
        cb.getItems().add("ST+");
        cb.getItems().add("AG+");
        cb.getItems().add("PA+");
        cb.getItems().add("AV+");
    }

    @FXML
    public void levelUp() throws IOException {
        String s = cb.getSelectionModel().getSelectedItem().toString();
        //Controllo il caso in cui venga selezionata la skill Frenzy/Grab e verifico che non sia presente Grab/Frenzy
        if((s.equals("Grab") && App.getPlayer().skill.contains("Frenzy")) || (s.equals("Frenzy") && App.getPlayer().skill.contains("Grab"))) {
            error.setText("You can't take that skill!");
            error.setVisible(true);
            return;
        }
        //Controlla se la skill selezionata è già stata presa
        if(App.getPlayer().skill.contains(s)) {
            error.setText("Skill already taken!");
            //Controllo in caso di nomi simili (Il metodo contains non basta)
            if((s.equals("Block") || s.equals("Catch") || s.equals("Tackle") || s.equals("Pass"))) {
                String tmp = App.getPlayer().skill.replace(s, "");
                int occ = (App.getPlayer().skill.length() - tmp.length()) / s.length();
                if(occ > 1) {
                    error.setVisible(true);
                }
                else {
                    if(s.equals("Block") && !App.getPlayer().skill.contains("Blocks") && !App.getPlayer().skill.contains("Blocker") )
                        error.setVisible(true);
                    else if(s.equals("Catch") && !App.getPlayer().skill.contains("Diving Catch"))
                        error.setVisible(true);
                    else if(s.equals("Tackle") && !App.getPlayer().skill.contains("Diving Tackle") && !App.getPlayer().skill.contains("Break Tackle"))
                        error.setVisible(true);
                    else if(s.equals("Pass") && (!App.getPlayer().skill.contains("Running Pass") || !App.getPlayer().skill.contains("Safe Pass")))
                        error.setVisible(true);
                        //In questo caso viene aggiunta la skill e vengono tolti gli spp
                    else {
                        App.getPlayer().unspentSPP -= spent;
                        App.getPlayer().skill = App.getPlayer().skill.replace(App.getPlayer().getNewSkills(), "");
                        App.getPlayer().setNewSkills(App.getPlayer().getNewSkills() + s + ",");
                        App.getPlayer().skill += App.getPlayer().getNewSkills();
                        App.getPlayer().setNewVal(App.getPlayer().getNewVal() + value);
                        App.getPlayer().val += value;
                        if(!App.getPlayer().MNG)
                            App.getTeam().value += value;
                        App.getPlayer().setLev(App.getPlayer().getLev() + 1);
                        setlUp(false);
                        Stage stage = (Stage) skills.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(App.load("player/player_preview"), 600, 400);
                        ManagementController.getStage().setScene(scene);
                        //App.setRoot("player/player_preview");
                    }
                }
            }
            else
                error.setVisible(true);
        }
        //In caso di aumento di caratteristica, vengono fatti i controlli relativi ad esse
        else if(s.charAt(s.length() - 1) == '+') {
            switch(s) {
                case "MA+":
                    if(App.getPlayer().MA == 9 || App.getPlayer().getNewMA() == 2) {
                        error.setText("Max MA reached!");
                        return;
                    }
                    else {
                        App.getPlayer().MA++;
                        App.getPlayer().setNewMA(App.getPlayer().getNewMA() + 1);
                        App.getPlayer().setLev(App.getPlayer().getLev() + 1);
                        App.getPlayer().val += 20;
                        if(!App.getPlayer().MNG)
                            App.getTeam().value += 20;
                        App.getPlayer().setNewVal(App.getPlayer().getNewVal() + 20);
                    }
                    break;
                case "ST+":
                    if(App.getPlayer().ST == 8 || App.getPlayer().getNewST() == 2) {
                        error.setText("Max ST reached!");
                        return;
                    }
                    else {
                        App.getPlayer().ST++;
                        App.getPlayer().setNewST(App.getPlayer().getNewST() + 1);
                        App.getPlayer().setLev(App.getPlayer().getLev() + 1);
                        if(!App.getPlayer().MNG)
                            App.getTeam().value += 80;
                        App.getPlayer().val += 80;
                        App.getPlayer().setNewVal(App.getPlayer().getNewVal() + 80);
                    }
                    break;
                case "AG+":
                    if(App.getPlayer().AG == 1 || App.getPlayer().getNewST() == -2) {
                        error.setText("Max AG reached!");
                        return;
                    }
                    else {
                        App.getPlayer().AG--;
                        App.getPlayer().setNewAG(App.getPlayer().getNewAG() - 1);
                        App.getPlayer().setLev(App.getPlayer().getLev() + 1);
                        if(!App.getPlayer().MNG)
                            App.getTeam().value += 40;
                        App.getPlayer().val += 40;
                        App.getPlayer().setNewVal(App.getPlayer().getNewVal() + 40);
                    }
                    break;
                case "PA+":
                    if(App.getPlayer().PA == 1 || App.getPlayer().getNewPA() == -2) {
                        error.setText("Max PA reached!");
                        return;
                    }
                    else {
                        App.getPlayer().PA--;
                        App.getPlayer().setNewPA(App.getPlayer().getNewPA() - 1);
                        App.getPlayer().setLev(App.getPlayer().getLev() + 1);
                        if(!App.getPlayer().MNG)
                            App.getTeam().value += 20;
                        App.getPlayer().val += 20;
                        App.getPlayer().setNewVal(App.getPlayer().getNewVal() + 20);
                    }
                    break;
                case "AV+":
                    if(App.getPlayer().AV == 11 || App.getPlayer().getNewAV() == 2) {
                        error.setText("Max AV reached!");
                        return;
                    }
                    else {
                        App.getPlayer().AV++;
                        App.getPlayer().setNewAV(App.getPlayer().getNewAV() + 1);
                        App.getPlayer().setLev(App.getPlayer().getLev() + 1);
                        if(!App.getPlayer().MNG)
                            App.getTeam().value += 10;
                        App.getPlayer().val += 10;
                        App.getPlayer().setNewVal(App.getPlayer().getNewVal() + 10);
                    }
                    break;
            }
            setlUp(false);
            App.getPlayer().unspentSPP -= spent;
            Stage stage = (Stage) skills.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(App.load("player/player_preview"), 600, 400);
            ManagementController.getStage().setScene(scene);
            //App.setRoot("player/player_preview");
        }
        else {
            App.getPlayer().unspentSPP -= spent;
            App.getPlayer().skill = App.getPlayer().skill.replace(App.getPlayer().getNewSkills(), "");
            App.getPlayer().setNewSkills(App.getPlayer().getNewSkills() + s + ",");
            App.getPlayer().skill += App.getPlayer().getNewSkills();
            App.getPlayer().setNewVal(App.getPlayer().getNewVal() + value);
            App.getPlayer().val += value;
            if(!App.getPlayer().MNG)
                App.getTeam().value += value;
            App.getPlayer().setLev(App.getPlayer().getLev() + 1);
            setlUp(false);
            Stage stage = (Stage) skills.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(App.load("player/player_preview"), 600, 400);
            ManagementController.getStage().setScene(scene);
            //App.setRoot("player/player_preview");
        }

    }

    @FXML public void checkDuplicate() {
        error.setVisible(false);
        for(String name : names) {
            if(name.equals(newName.getText())) {
                error.setText("Number Duplicate!");
                error.setVisible(true);
                return;
            }
        }

    }

    @FXML public void checkNumber() {
        error.setVisible(false);
        for(int nr : nrs) {
            if(nr == number.getValue() && nr != App.getPlayer().number) {
                error.setText("Number Duplicate!");
                error.setVisible(true);
                return;
            }
        }
    }

    @FXML public void save() throws IOException, SQLException {
        App.getPlayer().number = number.getValue();
        App.getPlayer().name = newName.getText();
        //Player p = new Player(App.getPlayer());
        //p.updateAnagrafic();
        setNaming(false);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        //App.setRoot("player/player_preview");
        Scene scene = new Scene(App.load("player/player_preview"), 600, 400);
        ManagementController.getStage().setScene(scene);
    }

    @FXML public void takeJourney() throws IOException, SQLException {
        App.getTeam().treasury -= App.getPlayer().val;
        App.getTeam().ngiocatori++;
        App.getPlayer().setJourney(false);
        App.getPlayer().setNewSkills("");
        setNaming(true);
        sta.setTitle("Edit Name");
        sc = new Scene(App.load("player/edit_name"), 320, 200);
        sta.setScene(sc);
        sta.show();
    }

    public static boolean islUp() {
        return lUp;
    }

    public static void setlUp(boolean lUp) {
        PlayerController.lUp = lUp;
    }

    public static boolean isNaming() {
        return naming;
    }

    public static void setNaming(boolean naming) {
        PlayerController.naming = naming;
    }
}
