package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Dao.PlayerDao;
import it.unipi.dataset.Dao.PlayerTemplateDao;
import it.unipi.dataset.Dao.RaceDao;
import it.unipi.dataset.Dao.TeamDao;
import it.unipi.dataset.Model.Player;
import it.unipi.dataset.Model.PlayerTemplate;
import it.unipi.dataset.Model.Race;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerPurchaseController {
    @FXML TableView<TemplateImage> selectPlayers = new TableView<TemplateImage>();
    private ObservableList<TemplateImage> pt;

    @FXML TableView<TemplateImage>  player = new TableView<TemplateImage>();
    private ObservableList<TemplateImage> pt2;

    @FXML private Label treasuryPlayer, error;

    @FXML private Button purchase;

    private Scene scene;

    private static Player[] p = new Player[16];
    private int[] nrs = new int[App.getTeam().ngiocatori];
    private String[] names = new String[App.getTeam().ngiocatori];
    //private TemplateImage[] pt2 = new TemplateImage[16];

    @FXML public void initialize() throws SQLException {
        TableColumn pos = new TableColumn("Position");
        pos.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn ma = new TableColumn("MA");
        ma.setCellValueFactory(new PropertyValueFactory<>("ma"));

        TableColumn st = new TableColumn("ST");
        st.setCellValueFactory(new PropertyValueFactory<>("st"));

        TableColumn ag = new TableColumn("AG");
        ag.setCellValueFactory(new PropertyValueFactory<>("ag"));

        TableColumn pa = new TableColumn("PA");
        pa.setCellValueFactory(new PropertyValueFactory<>("pa"));

        TableColumn av = new TableColumn("AV");
        av.setCellValueFactory(new PropertyValueFactory<>("av"));

        TableColumn skills = new TableColumn("skills");
        skills.setCellValueFactory(new PropertyValueFactory<>("skill"));

        TableColumn primary = new TableColumn("primary");
        primary.setCellValueFactory(new PropertyValueFactory<>("primary"));

        TableColumn secondary = new TableColumn("secondary");
        secondary.setCellValueFactory(new PropertyValueFactory<>("secondary"));

        TableColumn Cost = new TableColumn("Cost");
        Cost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn image = new TableColumn(" ");
        image.setCellValueFactory(new PropertyValueFactory<ImageView, TemplateImage>("img"));

        TableColumn selection = new TableColumn("Qty");
        selection.setCellValueFactory(new PropertyValueFactory<ComboBox<Integer>, TemplateImage>("cb"));

        TableColumn text = new TableColumn("Name");
        text.setCellValueFactory(new PropertyValueFactory<TextField, TemplateImage>("namePlayer"));

        TableColumn number = new TableColumn("Number");
        number.setCellValueFactory(new PropertyValueFactory<ComboBox<Integer>, TemplateImage>("number"));

        if(App.isNaming()) {
            player.getColumns().addAll(image, number, text, pos);
            pt2 = FXCollections.observableArrayList();
            player.setItems(pt2);

            getPlayers();
        }
        else {
            selectPlayers.getColumns().addAll(image, pos, ma, st, ag, pa, av, skills, primary, secondary, Cost, selection);
            pt = FXCollections.observableArrayList();
            selectPlayers.setItems(pt);
            treasuryPlayer.setText(Integer.toString(App.getTeam().getTreasury()));
            getTable();
        }

        if(!App.isNewTeam()) {
            nrs = PlayerDao.getPlayerNumbers(App.getTeam());
            names = PlayerDao.getPlayerNames(App.getTeam());
        }

    }

    private void getTable() throws SQLException {
        PlayerTemplate p = new PlayerTemplate();
        List<PlayerTemplate> templates = PlayerTemplateDao.getTemplate(App.getTeam().getRace());
        for(PlayerTemplate t : templates) {
            TemplateImage ti = new TemplateImage(t);
            ti.img = new ImageView();
            ti.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
            ti.cb = new ComboBox<Integer>();
            int j = 0;
            if (!App.isNewTeam()) {
                //Qualcosa per settare j
                j = PlayerDao.getPositional(ti.getId(), App.getTeam().getId());
            }
            for (; j <= ti.getMaxQty(); j++)
                ti.cb.getItems().add(j);
            ti.cb.getSelectionModel().select(0);
            ti.cb.setOnAction(e -> checkBounds());
            pt.add(ti);
        }
    }

    @FXML public void purchase() throws IOException, SQLException {
        int tot = 0;

        for(int i = 0; i < pt.size(); i++)
            tot += pt.get(i).cb.getValue();

        if(App.isNewTeam() && tot < 11) {
            error.setText("Minimum number of players not reached");
            error.setVisible(true);
            return;
        }

        if(tot > 16) {
            error.setText("Maximum number of players reached");
            error.setVisible(true);
            return;
        }
        boolean rules = RaceDao.hasSpecial(App.getTeam().getRace());
        int value = 0, cont = 0;
        Player[] players = new Player[16];
        for(int i = 0; i < pt.size(); i++) {
            if(App.isNewTeam()) {
                for(int j = 0; j < pt.get(i).cb.getValue(); j++) {
                    players[cont++] = new Player(pt.get(i).getId(), App.getTeam().getId(), 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, false);
                    if(pt.get(i).getMaxQty() > 10 && rules)
                        value += 0;
                    else
                        value += pt.get(i).getCost();
                }
            }
            else{
                for(int j = 0; j < (pt.get(i).cb.getValue() - (int)pt.get(i).cb.getItems().get(0)); j++) {
                    players[cont++] = new Player(pt.get(i).getId(), App.getTeam().getId(), 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true, false);
                    if(pt.get(i).getMaxQty() > 10 && rules)
                        value += 0;
                    else
                        value += pt.get(i).getCost();
                }
            }
        }
        App.getTeam().setNgiocatori(App.getTeam().getNgiocatori() + cont);
        App.getTeam().setTreasury(Integer.valueOf(treasuryPlayer.getText()));
        App.getTeam().setValue(App.getTeam().getValue() + value);
        TeamDao.updateTeam(App.getTeam(), true);
        setP(players);
        App.setNaming(true);
        Stage stage = (Stage) purchase.getScene().getWindow();
        stage.close();
        scene = new Scene(App.load("player/list_name"), 600, 400);
        ManagementController.getStage().setScene(scene);
        ManagementController.getStage().setResizable(false);
        ManagementController.getStage().setTitle("Name players");
        ManagementController.getStage().show();
        //App.setRoot("player/list_name");
        //App.setRoot("dashboard");
    }

    @FXML public void checkBounds() {
        int tmp = App.getTeam().getTreasury(), bg = 0;
        purchase.setDisable(false);
        error.setVisible(false);
        for(int i = 0; i < pt.size(); i++) {
            if(App.isNewTeam())
                tmp -= pt.get(i).cb.getValue() * pt.get(i).getCost();
            else {
                tmp -= (pt.get(i).cb.getValue() - (int)pt.get(i).cb.getItems().get(0)) * pt.get(i).cost;
            }
            if(pt.get(i).isBigGuy() && pt.get(i).cb.getValue() > 0)
                bg++;
        }
        if((App.getTeam().getRace() == 2 || App.getTeam().getRace() == 22 || App.getTeam().getRace() == 24) && bg > 1) {
            error.setVisible(true);
            error.setText("Error: You can't buy more than 1 big guy");
            purchase.setDisable(true);
        }
        else if(App.getTeam().getRace() == 19 && bg > 3) {
            error.setVisible(true);
            error.setText("Error: You can't buy more than 3 big guy");
            purchase.setDisable(true);
        }
        if(tmp < 0) {
            error.setText("Not Enough Money!");
            error.setVisible(true);
            purchase.setDisable(true);
        }
        treasuryPlayer.setText(Integer.toString(tmp));
    }

    private void getPlayers() throws SQLException {
        List<PlayerTemplate> templates = PlayerTemplateDao.getTemplate(App.getTeam().getRace());
        for(PlayerTemplate template : templates) {
            for(int i = 0; i <= 16; i++) {
                if(getP()[i] == null)
                    break;
                if(getP()[i].getTemplate() == template.getId()) {
                    TemplateImage ti = new TemplateImage(template);
                    ti.img = new ImageView();
                    ti.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
                    ti.namePlayer = new TextField();
                    ti.namePlayer.setOnAction(e -> checkDuplicate());
                    ti.namePlayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if(keyEvent.getCode() == KeyCode.TAB)
                                checkDuplicate();
                        }
                    });
                    ti.number = new Spinner<Integer>();
                    SpinnerValueFactory<Integer> nr = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 1);
                    ti.number.setValueFactory(nr);
                    ti.number.setPrefWidth(60);
                    //ti.number.setEditable(true);
                    ti.number.setOnInputMethodTextChanged(e -> checkNumber());
                    ti.number.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if(keyEvent.getCode() == KeyCode.TAB)
                                checkNumber();
                        }
                    });
                    pt2.add(ti);
                }
            }
        }
    }

    @FXML public void checkDuplicate() {
        error.setVisible(false);
        for(int i = 0; i < pt2.size(); i++) {
            for(int j = 0; j < pt2.size(); j++)
                if(pt2.get(i).namePlayer.getText().equals(pt2.get(j).namePlayer.getText()) && i != j && !pt2.get(i).namePlayer.getText().equals("")) {
                    error.setText("Name Duplicate!");
                    error.setVisible(true);
                    pt2.get(j).namePlayer.requestFocus();
                    return;
                }
            if(!App.isNewTeam()) {
                for(String name : names) {
                    if(name.equals(pt2.get(i).namePlayer.getText())) {
                        error.setText("Number Duplicate!");
                        error.setVisible(true);
                        pt2.get(i).number.requestFocus();
                        return;
                    }
                }
            }
        }
    }

    @FXML public void checkNumber() {
        error.setVisible(false);
        for(int i = 0; i < pt2.size(); i++) {
            for(int j= 0; j < pt2.size(); j++) {
                if(pt2.get(i).number.getValue() == pt2.get(j).number.getValue() && i != j) {
                    error.setText("Number Duplicate!");
                    error.setVisible(true);
                    pt2.get(j).number.requestFocus();
                    return;
                }
            }
            if(!App.isNewTeam()) {
                for(int nr : nrs) {
                    if(nr == pt2.get(i).number.getValue()) {
                        error.setText("Number Duplicate!");
                        error.setVisible(true);
                        pt2.get(i).number.requestFocus();
                        return;
                    }
                }
            }
        }
    }

    @FXML public void addPlayers() throws SQLException, IOException {
        App.setNaming(false);
        for(int i = 0; i < pt2.size(); i++) {
            getP()[i].setName(pt2.get(i).namePlayer.getText());
            getP()[i].setNumber(pt2.get(i).number.getValue());
        }
        PlayerDao.addPlayer(getP());
        if(!App.isNewTeam() && getP() != null) {
            Player[] jms = PlayerDao.getJourneymans(App.getTeam());
            if(jms != null) {
                for(int i = 0,  j = (jms.length - 1); i < 27 && j >= 0; i++, j--) {
                    if(getP() == null)
                        break;
                    else
                        PlayerDao.deletePlayer(jms[j].getId());
                }
            }
        }
        setP(null);
        Stage stage = (Stage) error.getScene().getWindow();
        stage.close();
        if(App.isNewTeam()) {
            App.setRoot("dashboard");
        }
        else {
            App.setRoot("team/team_management");
        }
    }

    public static Player[] getP() {
        return p;
    }

    public static void setP(Player[] p) {
        PlayerPurchaseController.p = p;
    }
}
