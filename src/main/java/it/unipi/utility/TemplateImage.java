package it.unipi.utility;

import it.unipi.dataset.PlayerTemplate;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class TemplateImage extends PlayerTemplate {
    @FXML public ImageView img;
    @FXML public ComboBox<Integer> cb;
    @FXML public TextField namePlayer;
    @FXML public Spinner<Integer> number;

    public TemplateImage() {

    }

    public TemplateImage(PlayerTemplate pt) {
        super(pt);
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public ComboBox<Integer> getCb() {
        return cb;
    }

    public void setCb(ComboBox<Integer> cb) {
        this.cb = cb;
    }

    public TextField getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(TextField namePlayer) {
        this.namePlayer = namePlayer;
    }

    public Spinner<Integer> getNumber() {
        return number;
    }

    public void setNumber(Spinner<Integer> number) {
        this.number = number;
    }
}
