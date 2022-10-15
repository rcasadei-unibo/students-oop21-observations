package org.observations.gui.popup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.observations.controllers.ObservatonsViewController;

import java.time.LocalDate;
import java.util.List;

public class ObservationInsertionPopup extends Stage {
    private final ObservatonsViewController controller;

    public ObservationInsertionPopup(ObservatonsViewController controller) {
        this.controller = controller;
        this.initOwner(controller.getView().getScene().getWindow());
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Seleziona un momento");
        Button confirmButton = new Button("Inserisci");
        Button cancelButton = new Button("Cancella");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(this.controller.getObservationsTypesNames());

        confirmButton.setOnAction(event -> {
            controller.updateModel(List.of(LocalDate.now().toString(), comboBox.getSelectionModel().getSelectedItem()));
            this.close();
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(popupMessage, comboBox, new HBox(confirmButton, cancelButton));

        Scene scene = new Scene(box);
        this.setScene(scene);
    }
}
