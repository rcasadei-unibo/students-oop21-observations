package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.MomentsViewController;
import org.observations.gui.popup.MomentsInsertionPopup;
import org.observations.gui.popup.MomentsTypeInsertionPopup;

import java.util.List;

public class MomentsView implements View<List<String>> {

    private static final String LABEL_TEXT = "Momenti";
    private static final String NO_DATA_FOUND_MESSAGE = "Nessun momento trovato";
    private static final String INSERT_BUTTON_TEXT = "+";
    private static final String NEW_TYPE_BUTTON_TEXT = "Aggiungi nuovo tipo di momento";

    private final MomentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private MomentsInsertionPopup popup;
    private MomentsTypeInsertionPopup typePopup;

    public MomentsView(MomentsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(100);
        this.createTypeInsertButton();
        this.createInsertButton();
        this.view.setTop(new Label(LABEL_TEXT));
        this.view.setCenter(listPane);
        this.view.setBottom(bottomBox);
    }

    public void update(List<String> input) {
        if (!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            input.forEach(hour -> {
                Button button = new Button(hour);
                button.setOnAction(event -> this.onMomentButtonClick(button.getText()));
                listBox.getChildren().add(button);
            });
            this.listPane.setContent(listBox);
            this.view.setCenter(this.listPane);
        } else {
            this.view.setCenter(new Label(NO_DATA_FOUND_MESSAGE));
        }
    }

    public Node getView() {
        return this.view;
    }

    public void setVisible(Boolean value) {
        this.view.setVisible(value);
    }

    public void updateObservationSelectorList() {
        this.popup.updateObservationSelector();
    }

    private void createInsertButton() {
        Button insertButton = new Button(INSERT_BUTTON_TEXT);
        insertButton.setOnAction(event -> this.onInsertButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    private void createTypeInsertButton() {
        Button insertButton = new Button(NEW_TYPE_BUTTON_TEXT);
        insertButton.setOnAction(event -> this.onNewTypeButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    private void onInsertButtonClick() {
        if (this.popup == null) {
            this.popup = new MomentsInsertionPopup(this.controller);
        }
        if (!this.popup.isShowing()) {
            this.popup.show();
        }
    }

    private void onNewTypeButtonClick() {
        if (this.typePopup == null) {
            this.typePopup = new MomentsTypeInsertionPopup(this.controller, this);
        }
        if (!this.typePopup.isShowing()) {
            this.typePopup.show();
        }
    }

    private void onMomentButtonClick(final String text) {
        this.controller.getData(text);
    }
}
