package it.unipi.utility;

import it.unipi.dataset.PlayerTemplate;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class TemplateImage extends PlayerTemplate {
    @FXML public ImageView img;
    @FXML public ComboBox<Integer> cb;

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
}
