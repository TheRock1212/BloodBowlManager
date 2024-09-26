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

    opens it.unipi.controller to javafx.fxml;
    opens it.unipi.bloodbowlmanager to javafx.fxml;
    exports it.unipi.bloodbowlmanager;
}