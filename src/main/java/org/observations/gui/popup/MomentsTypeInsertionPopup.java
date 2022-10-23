package org.observations.gui.popup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.observations.controllers.MomentsViewController;
import org.observations.gui.MomentsView;

/**
 * Custom popup which create a window for the user to input a new type of observation to save.
 */
public class MomentsTypeInsertionPopup extends Stage {

    private static final String ALERT_MESSAGE = "Il campo deve essere riempito";
    private final MomentsViewController controller;
    private final MomentsView view;

    public MomentsTypeInsertionPopup(MomentsViewController controller, MomentsView view) {
        this.controller = controller;
        this.view = view;
        this.initOwner(controller.getView().getScene().getWindow());
        this.setWidth(250);
        this.setHeight(150);

        Label popupMessage = new Label("Inserisci nome nuovo momento");
        Button confirmButton = new Button("Conferma");
        Button cancelButton = new Button("Cancella");
        TextField name = new TextField();
        name.setPromptText("Inserisci nome");

        confirmButton.setOnAction(event -> {
            if (!name.getText().isEmpty()) {
                controller.insertNewMomentType(name.getText());
                this.view.updateObservationSelectorList();
                this.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, ALERT_MESSAGE);
                alert.show();
            }
        });

        cancelButton.setOnAction(event -> this.close());

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(popupMessage, name, new HBox(confirmButton, cancelButton));

        Scene scene = new Scene(box);
        this.setScene(scene);
    }
}