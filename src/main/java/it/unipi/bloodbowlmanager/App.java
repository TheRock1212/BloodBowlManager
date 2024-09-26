package it.unipi.bloodbowlmanager;

import it.unipi.dataset.League;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import it.unipi.controller.*;

public class App extends Application {

    private static Connection connection;
    private static Scene scene;
    private static boolean newTeam = false;
    private static League league;

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
       //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/league/league_dashboard.fxml"));
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection co = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bblm", "root", "root");
        setConnection(co);
        scene = new Scene(loadFXML("league/league_dashboard"), 600, 400);
        stage.setTitle("Blood Bowl League Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        App.connection = connection;
    }

    public static boolean isNewTeam() {
        return newTeam;
    }

    public static void setNewTeam(boolean newTeam) {
        App.newTeam = newTeam;
    }

    public static League getLeague() {
        return league;
    }

    public static void setLeague(League league) {
        App.league = league;
    }

    public static void main(String[] args) {
        launch();
    }
}