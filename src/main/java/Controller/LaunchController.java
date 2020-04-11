package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LaunchController {

    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private Label errorLabel1;

    public void startAction(ActionEvent actionEvent) {

        if (player1TextField.getText().isEmpty()) {
            errorLabel1.setText("Player1 is empty!");
        } else if (player2TextField.getText().isEmpty()) {
            errorLabel1.setText("Player2 is empty!");
        } else {

        }
    }

}
