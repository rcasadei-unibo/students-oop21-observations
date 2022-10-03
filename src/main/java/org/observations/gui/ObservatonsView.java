package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.ObservatonsViewController;
import org.observations.controllers.SubController;

import java.util.Map;

public class ObservatonsView implements View<Map<String, Map<String, Integer>>> {

    private final SubController controller;
    private final BorderPane view = new BorderPane();
    private final VBox listBox = new VBox();
    private final HBox bottomBox = new HBox();


    public ObservatonsView(ObservatonsViewController controller) {
        this.controller = controller;
        this.setView();
    }

    public void update(Map<String, Map<String, Integer>> input) {
        this.listBox.getChildren().clear();
        for (String hour : input.keySet()) {
            Map<String, Integer> activities = input.get(hour);
            for (String activity : activities.keySet()) {
                Integer observations = activities.get(activity);
                this.listBox.getChildren().add(new ObservationLine((ObservatonsViewController) this.controller, hour, activity, observations).getView());
            }
        }
    }

    public Node getView() {
        return this.view;
    }

    public void setVisible(Boolean value) {
        this.view.setVisible(value);
    }

    private void setView() {
        view.setMinWidth(150);
        view.setMaxWidth(300);

        this.createEditButton();
        this.createInsertButton();

        view.setBottom(bottomBox);
        view.getBottom().setVisible(false);
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
        controller.switchOnOffEditButtons(null);
    }

    private void onInsertButtonClick() {
        //TODO
    }
}
