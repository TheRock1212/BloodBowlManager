package it.unipi.bloodbowlmanager;

import it.unipi.dataset.Model.League;
import it.unipi.dataset.Model.Team;
import it.unipi.utility.PlayerPreview;
import it.unipi.utility.ResultGame;
import it.unipi.utility.Tabs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {

    private static Connection connection;
    private static Stage stage;
    private static Scene scene;
    private static boolean newTeam = false;
    private static League league;
    private static Team team;
    private static boolean naming = false;
    private static PlayerPreview player;
    private static ResultGame result;
    private static Tabs returnState;

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
       //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/league/league_dashboard.fxml"));
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection co = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bblm", "root", "root");
        setConnection(co);
        returnState = Tabs.STANDINGS;
        scene = new Scene(loadFXML("league/league_dashboard"), 1000, 700);
        stage.setTitle("Blood Bowl League Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        App.stage = stage;
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

    public static Team getTeam() {
        return team;
    }

    public static void setTeam(Team team) {
        App.team = team;
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean isNaming() {
        return naming;
    }

    public static void setNaming(boolean naming) {
        App.naming = naming;
    }

    public static void changeScene(double width, double height, String fxml) throws IOException{
        App.scene = new Scene(loadFXML(fxml), width, height);
        stage.setScene(scene);
    }

    public static Parent load(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static PlayerPreview getPlayer() {
        return player;
    }

    public static void setPlayer(PlayerPreview player) {
        App.player = player;
    }

    public static ResultGame getResult() {
        return result;
    }

    public static void setResult(ResultGame result) {
        App.result = result;
    }

    public static Tabs getReturnState() {
        return returnState;
    }

    public static void setReturnState(Tabs returnState) {
        App.returnState = returnState;
    }
}