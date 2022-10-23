package org.observations.gui.popup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.observations.controllers.MomentsViewController;

/**
 * Custom popup which create a window for the user to input a new moment to the model to save.
 */
public class MomentsInsertionPopup extends Stage {

    private static final String ALERT_MESSAGE = "Deve essere scelto un momento.";
    private final MomentsViewController controller;
    private final ComboBox<String> momentSelector = new ComboBox<>();

    public MomentsInsertionPopup(MomentsViewController controller) {
        this.controller = controller;
        this.initOwner(controller.getView().getScene().getWindow());
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Seleziona un momento");
        Button confirmButton = new Button("Inserisci momento");
        Button cancelButton = new Button("Cancella azione");
        this.updateObservationSelector();

        confirmButton.setOnAction(event -> {
            if (!momentSelector.getSelectionModel().getSelectedItem().isEmpty()) {
                controller.updateModel(momentSelector.getSelectionModel().getSelectedItem());
                momentSelector.getSelectionModel().clearSelection();
                this.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, ALERT_MESSAGE);
                alert.show();
            }
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setSpacing(8);
        box.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(confirmButton, cancelButton);
        hbox.setSpacing(8);
        box.getChildren().addAll(popupMessage, momentSelector, hbox);

        Scene scene = new Scene(box);
        this.setScene(scene);
    }

    public void updateObservationSelector() {
        this.momentSelector.getItems().clear();
        this.momentSelector.getItems().addAll(this.controller.getMomentList());
    }
}
