module it.unipi.bloodbowlmanager {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires mysql.connector.j;
    requires java.desktop;

    requires kernel;
    requires layout;
    requires io;
    requires com.google.gson;
    requires java.sql;
    //requires styled.xml.parser;

    opens it.unipi.controller to javafx.fxml, com.google.gson;
    opens it.unipi.bloodbowlmanager to javafx.fxml;
    opens it.unipi.dataset.Dao to javafx.base;
    opens it.unipi.utility to javafx.base, com.google.gson;
    opens it.unipi.dataset.Model to javafx.base, com.google.gson;
    exports it.unipi.bloodbowlmanager;

}