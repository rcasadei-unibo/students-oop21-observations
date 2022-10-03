package org.observations.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.observations.controllers.StudentsViewController;

public class StudentInsertionPopup extends Stage {

    private final StudentsViewController controller;
    String name;

    public StudentInsertionPopup(StudentsViewController controller) {
        this.controller = controller;
        this.initOwner(controller.getView().getScene().getWindow());
        //this.initStyle(StageStyle.UNDECORATED);
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Insert a student name");
        Button confirmButton = new Button("Insert student");
        Button cancelButton = new Button("Cancel");
        TextField name = new TextField();
        TextField surname = new TextField();
        name.setPromptText("name");
        surname.setPromptText("surname");

        confirmButton.setOnAction(event -> {
            if (!surname.getText().isEmpty() && !name.getText().isEmpty()) {
                controller.updateModel(surname.getText() + " " + name.getText());
                this.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields must be filled.");
                alert.show();
            }
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(popupMessage, surname, name, new HBox(confirmButton, cancelButton));

        Scene scene = new Scene(box);
        this.setScene(scene);
        this.show();
    }
}
