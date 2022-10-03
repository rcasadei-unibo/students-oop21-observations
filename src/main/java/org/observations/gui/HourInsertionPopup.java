package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.observations.controllers.HoursViewController;

public class HourInsertionPopup extends Stage {

    private final HoursViewController controller;

    public HourInsertionPopup(HoursViewController controller) {

        this.controller = controller;
        this.initOwner(controller.getView().getScene().getWindow());
        //this.initStyle(StageStyle.UNDECORATED);
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Select a hour");
        Button confirmButton = new Button("Insert");
        Button cancelButton = new Button("Cancel");
        ComboBox<Integer> comboBox = new ComboBox<>();

        for (int i = 6; i < 20; i++) {
            comboBox.getItems().add(i);
        }

        confirmButton.setOnAction(event -> {
            controller.updateModel(comboBox.getSelectionModel().getSelectedItem().toString());
            this.close();
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(popupMessage, comboBox, new HBox(confirmButton, cancelButton));

        Scene scene = new Scene(box);
        this.setScene(scene);
        this.show();
    }
}
