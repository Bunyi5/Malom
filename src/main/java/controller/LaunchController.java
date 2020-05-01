package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Controller class of the launch screen.
 */
@Slf4j
public class LaunchController {

    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private Label errorLabel1;

    @FXML
    private Label errorLabel2;

    /**
     * Loads the game when the player clicks on the play button.
     *
     * @param actionEvent a click by the player
     * @throws IOException if {@code fxmlLoader} can't load fxml file
     */
    public void startAction(ActionEvent actionEvent) throws IOException {

        if (player1TextField.getText().isEmpty()) {
            errorLabel1.setText("Player1 name is empty!");
        } else if (player2TextField.getText().isEmpty()) {
            errorLabel2.setText("Player2 name is empty!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().initializeData(player1TextField.getText(), player2TextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setX((Screen.getPrimary().getBounds().getWidth()/2)-400);
            stage.setY(0);
            stage.show();
            log.info("Player1 is set to {}, Player2 is set to {}, loading game scene.",
                    player1TextField.getText(), player2TextField.getText());
        }
    }

}
