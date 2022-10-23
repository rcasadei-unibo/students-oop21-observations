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

    private static final String ALERT_MESSAGE = "Deve essere scelto un tipo di osservazione.";
    private final ObservationsViewController controller;
    private final ComboBox<String> observationSelector = new ComboBox<>();

    public ObservationInsertionPopup(ObservationsViewController controller) {
        this.controller = controller;
        this.initOwner(controller.getView().getScene().getWindow());
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Seleziona un'osservazione");
        Button confirmButton = new Button("Inserisci osservazione");
        Button cancelButton = new Button("Cancella azione");
        DatePicker picker = new DatePicker(LocalDate.now());
        this.updateObservationSelector();

        confirmButton.setOnAction(event -> {
            if(!observationSelector.getSelectionModel().getSelectedItem().isEmpty()) {
                controller.updateModel(
                        List.of(
                                picker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                observationSelector.getSelectionModel().getSelectedItem()));

                observationSelector.getSelectionModel().clearSelection();
                this.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, ALERT_MESSAGE);
                alert.show();
            }
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setSpacing(6);
        box.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(confirmButton, cancelButton);
        hbox.setSpacing(8);
        box.getChildren().addAll(popupMessage, observationSelector, picker, hbox);

        Scene scene = new Scene(box);
        this.setScene(scene);
    }

    /**
     * Update the selector with an updated list of observations.
     */
    public void updateObservationSelector() {
        this.observationSelector.getItems().clear();
        this.observationSelector.getItems().addAll(this.controller.getObservationsTypesNames());
    }
}
