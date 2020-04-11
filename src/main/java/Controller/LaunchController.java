package Controller;


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

import java.io.IOException;

public class LaunchController {

    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private Label errorLabel1;

    public void startAction(ActionEvent actionEvent) throws IOException {

        if (player1TextField.getText().isEmpty()) {
            errorLabel1.setText("Player1 is empty!");
        } else if (player2TextField.getText().isEmpty()) {
            errorLabel1.setText("Player2 is empty!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setX((Screen.getPrimary().getBounds().getWidth()/2)-300);
            stage.setY(0);
            stage.show();
        }
    }

}
