module it.unipi.bloodbowlmanager {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    requires kernel;
    requires layout;
    requires io;
    requires styled.xml.parser;

    opens it.unipi.controller to javafx.fxml;
    opens it.unipi.bloodbowlmanager to javafx.fxml;
    opens it.unipi.dataset.Dao to javafx.base;
    opens it.unipi.utility to javafx.base;
    opens it.unipi.dataset.Model to javafx.base;
    exports it.unipi.bloodbowlmanager;

}