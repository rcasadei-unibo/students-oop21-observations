package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.HoursViewController;

import java.util.*;

public class HoursView implements View<List<String>> {

    private final HoursViewController controller;
    private final BorderPane view = new BorderPane();
    private final VBox listBox = new VBox();
    private final HBox bottomBox = new HBox();

    private boolean editButtonsVisible = false;

    public HoursView(HoursViewController controller) {
        this.controller = controller;

        view.setMinWidth(150);
        view.setMaxWidth(300);

        this.createEditButton();
        this.createInsertButton();

        view.setBottom(bottomBox);
        view.getBottom().setVisible(false);
    }

    public void update(List<String> input) {
        if (!view.getBottom().isVisible()) {
            view.getBottom().setVisible(true);
        }
        if (!input.isEmpty()) {
            listBox.getChildren().clear();
            input.forEach(hour -> {
                Button button = new Button(hour);
                button.setOnAction(e -> {
                    this.onHourButtonClick(button.getText());
                });
                listBox.getChildren().add(button);
            });
            view.setCenter(listBox);
        } else {
            view.setCenter(new Label("No data for selected student"));
        }
    }

    public Node getView() {
        return view;
    }

    public void setVisible(Boolean value) {
        view.setVisible(value);
    }

    private void createEditButton() {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            this.onEditButtonClick();
        });
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(editButton);
    }

    private void createInsertButton() {
        Button insertButton = new Button("+");
        insertButton.setOnAction(event -> {
            this.onInsertButtonClick();
        });
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    private void onEditButtonClick() {
        if(isEditButtonsVisible()){
            //TODO
        } else {
            //TODO
        }
    }

    private void onInsertButtonClick() {
        new HourInsertionPopup(this.controller);
    }

    private void onHourButtonClick(final String text) {
        controller.getData(text);
    }

    public boolean isEditButtonsVisible() {
        return editButtonsVisible;
    }

    public void setEditButtonsVisible(boolean editButtonsVisible) {
        this.editButtonsVisible = editButtonsVisible;
    }
}
