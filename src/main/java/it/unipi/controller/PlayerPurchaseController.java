package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.Player;
import it.unipi.dataset.PlayerTemplate;
import it.unipi.dataset.Race;
import it.unipi.utility.TemplateImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerPurchaseController {
    @FXML TableView<TemplateImage> selectPlayers = new TableView<TemplateImage>();
    private ObservableList<TemplateImage> pt;

    @FXML TableView<TemplateImage>  player = new TableView<TemplateImage>();
    private ObservableList<TemplateImage> pt2;

    @FXML private Label treasuryPlayer, error;

    @FXML private Button purchase;

    private static Player[] p = new Player[16];
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

    }

    private void getTable() throws SQLException {
        PlayerTemplate p = new PlayerTemplate();
        ResultSet rs = p.getTemplate(App.getTeam().getRace());
        while(rs.next()) {
            p = new PlayerTemplate(rs.getInt("id"), rs.getString("position"), rs.getInt("race"), rs.getInt("MA"), rs.getInt("ST"), rs.getInt("AG"), rs.getInt("PA"), rs.getInt("AV"), rs.getString("skill"), rs.getInt("max_qty"), rs.getString("primary"), rs.getString("secondary"), rs.getInt("cost"), rs.getString("url"), rs.getBoolean("big_guy"));
            TemplateImage ti = new TemplateImage(p);
            ti.img = new ImageView();
            ti.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
            ti.cb = new ComboBox<Integer>();
            int j = 0;
            if (!App.isNewTeam()) {
                //Qualcosa per settare j
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
        Race race = new Race();
        ResultSet rs = race.getRules(App.getTeam().getRace());
        String rules = "";
        while(rs.next()) {
            rules = rs.getString("special_1") + " " + rs.getString("special_2") + " " + rs.getString("special_3");
        }
        int value = 0, cont = 0;
        Player[] players = new Player[16];
        for(int i = 0; i < pt.size(); i++) {
            if(App.isNewTeam()) {
                for(int j = 0; j < pt.get(i).cb.getValue(); j++) {
                    players[cont++] = new Player(pt.get(i).getId(), App.getTeam().getId(), 0, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 0, 0, 0, 0, 0, 0, true);
                    if(pt.get(i).getMaxQty() > 10 && rules.contains("Low Cost Lineman"))
                        value += 0;
                    else
                        value += pt.get(i).getCost();
                }
            }
            else{
                //Fai cose quando compri da team già creato
            }
        }
        App.getTeam().setNgiocatori(App.getTeam().getNgiocatori() + cont);
        App.getTeam().setTreasury(Integer.valueOf(treasuryPlayer.getText()));
        App.getTeam().setValue(App.getTeam().getValue() + value);
        App.getTeam().updatePlayer();
        setP(players);
        App.setNaming(true);
        App.setRoot("player/list_name");
        //App.setRoot("dashboard");
    }

    @FXML public void checkBounds() {
        int tmp = App.getTeam().getTreasury(), bg = 0;
        purchase.setDisable(false);
        error.setVisible(false);
        for(int i = 0; i < pt.size(); i++) {
            if(App.isNewTeam())
                tmp -= pt.get(i).cb.getValue() * pt.get(i).getCost();
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
        PlayerTemplate plte = new PlayerTemplate();
        ResultSet rs = plte.getTemplate(App.getTeam().getRace());
        while(rs.next()) {
            plte = new PlayerTemplate(rs.getInt("id"), rs.getString("position"), rs.getInt("race"), rs.getInt("MA"), rs.getInt("ST"), rs.getInt("AG"), rs.getInt("PA"), rs.getInt("AV"), rs.getString("skill"), rs.getInt("max_qty"), rs.getString("primary"), rs.getString("secondary"), rs.getInt("cost"), rs.getString("url"), rs.getBoolean("big_guy"));

            for(int i = 0; i <= 16; i++) {
                if(getP()[i] == null)
                    break;
                if(getP()[i].getTemplate() == plte.getId()) {
                    TemplateImage ti = new TemplateImage(plte);
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
        if(App.isNewTeam()) {
            for(int i = 0; i < pt2.size(); i++) {
                for(int j = 0; j < pt2.size(); j++)
                    if(pt2.get(i).namePlayer.getText().equals(pt2.get(j).namePlayer.getText()) && i != j && !pt2.get(i).namePlayer.getText().equals("")) {
                        error.setText("Name Duplicate!");
                        error.setVisible(true);
                        pt2.get(j).namePlayer.requestFocus();
                        return;
                    }
            }
        }
    }

    @FXML public void checkNumber() {
        error.setVisible(false);
        if(App.isNewTeam()) {
            for(int i = 0; i < pt2.size(); i++) {
                for(int j= 0; j < pt2.size(); j++) {
                    if(pt2.get(i).number.getValue() == pt2.get(j).number.getValue() && i != j) {
                        error.setText("Number Duplicate!");
                        error.setVisible(true);
                        pt2.get(j).number.requestFocus();
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
        getP()[0].addPlayer(getP());
        setP(null);
        App.setRoot("dashboard");
    }

    public static Player[] getP() {
        return p;
    }

    public static void setP(Player[] p) {
        PlayerPurchaseController.p = p;
    }
}
