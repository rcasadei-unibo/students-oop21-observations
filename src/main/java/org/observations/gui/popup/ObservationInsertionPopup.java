package org.observations.gui.popup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.observations.controllers.ObservationsViewController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Custom popup which create a window for the user to input a new observation to the model to save.
 */
public class ObservationInsertionPopup extends Stage {
    private final ObservationsViewController controller;

    public ObservationInsertionPopup(ObservationsViewController controller) {
        this.controller = controller;
        this.initOwner(controller.getView().getScene().getWindow());
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Seleziona un'osservazione");
        Button confirmButton = new Button("Inserisci osservazione");
        Button cancelButton = new Button("Cancella azione");
        ComboBox<String> comboBox = new ComboBox<>();
        DatePicker picker = new DatePicker(LocalDate.now());
        comboBox.getItems().addAll(this.controller.getObservationsTypesNames());

        confirmButton.setOnAction(event -> {
            if(!comboBox.getSelectionModel().getSelectedItem().isEmpty()) {

                controller.updateModel(
                        List.of(
                                picker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                comboBox.getSelectionModel().getSelectedItem()));

                comboBox.getSelectionModel().clearSelection();
                this.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Deve essere scelto un tipo di osservazione.");
                alert.show();
            }
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setSpacing(6);
        box.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(confirmButton, cancelButton);
        hbox.setSpacing(8);
        box.getChildren().addAll(popupMessage, comboBox, picker, hbox);

        Scene scene = new Scene(box);
        this.setScene(scene);
    }
}
