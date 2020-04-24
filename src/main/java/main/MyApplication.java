package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        primaryStage.setTitle("malom");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.setX((Screen.getPrimary().getBounds().getWidth()/2)-300);
        primaryStage.setY((Screen.getPrimary().getBounds().getHeight()/2)-250);
        primaryStage.show();
    }

}
