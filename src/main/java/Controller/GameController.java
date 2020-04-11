package Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {

    @FXML
    private ImageView background;

    @FXML
    public void initialize() {
        background.setImage(new Image(getClass().getResource("/Pictures/board.png").toExternalForm()));

    }
}
