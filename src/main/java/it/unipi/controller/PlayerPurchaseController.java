package it.unipi.controller;

import it.unipi.bloodbowlmanager.App;
import it.unipi.dataset.PlayerTemplate;
import it.unipi.utility.TemplateImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerPurchaseController {
    @FXML TableView<TemplateImage> selectPlayers = new TableView<TemplateImage>();
    private ObservableList<TemplateImage> pt;

    @FXML private Label treasuryPlayer, error;

    //private static Player[] p = new Player[16];

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

        selectPlayers.getColumns().addAll(image, pos, ma, st, ag, pa, av, skills, primary, secondary, Cost, selection);
        pt = FXCollections.observableArrayList();
        selectPlayers.setItems(pt);

        getTable();

        treasuryPlayer.setText(Integer.toString(App.getTeam().getTreasury()));
    }

    private void getTable() throws SQLException {
        PlayerTemplate p = new PlayerTemplate();
        ResultSet rs = p.getTemplate(App.getTeam().getRace());
        while(rs.next()) {
            p = new PlayerTemplate(rs.getInt("id"), rs.getString("position"), rs.getInt("race"), rs.getInt("MA"), rs.getInt("ST"), rs.getInt("AG"), rs.getInt("PA"), rs.getInt("AV"), rs.getString("skill"), rs.getInt("max_qty"), rs.getString("primary"), rs.getString("secondary"), rs.getInt("cost"), rs.getString("url"), rs.getBoolean("big_guy"));
            TemplateImage ti = new TemplateImage(p);
            ti.img = new ImageView();
            System.out.println(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png"));
            ti.img.setImage(new Image(getClass().getResource("/it/unipi/bloodbowlmanager/img/" + ti.getUrl() + ".png").toExternalForm()));
            ti.cb = new ComboBox<Integer>();
            int j = 0;
            if(!App.isNewTeam()) {
                //Qualcosa per settare j
            }
            for(; j <= ti.getMaxQty(); j++)
                ti.cb.getItems().add(j);
            ti.cb.getSelectionModel().select(0);
            //ti.cb.setOnAction(e -> checkBounds());
            pt.add(ti);
        }
    }

    @FXML public void purchase() throws IOException {
        App.setRoot("dashboard");
    }
}
