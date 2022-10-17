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

import java.util.List;

public class MomentsView implements View<List<String>> {

    private final MomentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private MomentsInsertionPopup popup;

    public MomentsView(MomentsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(100);
        this.createInsertButton();
        this.view.setTop(new Label("Momenti"));
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
            this.view.setCenter(new Label("Nessun momento trovato"));
        }
    }

    public Node getView() {
        return this.view;
    }

    public void setVisible(Boolean value) {
        this.view.setVisible(value);
    }

    private void createInsertButton() {
        Button insertButton = new Button("+");
        insertButton.setOnAction(event -> this.onInsertButtonClick());
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

    private void onMomentButtonClick(final String text) {
        this.controller.getData(text);
    }
}
