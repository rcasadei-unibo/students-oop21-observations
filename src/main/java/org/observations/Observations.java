package org.observations;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.observations.controllers.MainController;

public class Observations extends Application {
    @Override
    public void start(Stage stage) {

        stage.setTitle("Observations");
        MainController mainController = new MainController();
        Scene scene = new Scene((Parent) mainController.getView(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}