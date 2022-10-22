package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.observations.controllers.ObservationsViewController;

public class ObservationLine extends HBox implements View<Integer> {

    final Integer SPACING = 5;
    private final ObservationsViewController controller;
    private final String date;
    private final Label activity;
    private final Label observations;

    public ObservationLine(ObservationsViewController controller, String date, String activity, Integer observations) {
        this.controller = controller;
        this.date = date;
        this.activity = new Label(activity);
        this.observations = new Label(observations.toString());

        Button add = new Button("+");

        this.getChildren().addAll(this.activity, this.observations, add);
        this.setSpacing(SPACING);


        add.setOnAction(event -> {
            System.out.println("add observations button hit");
            this.incrementObservation();
        });
    }

    public void update(Integer input) {
        this.observations.setText(input.toString());
    }

    public Node getView() {
        return this;
    }

    public void setVisible(Boolean value) {
    }

    private void incrementObservation() {
        controller.updateObservationsCount(this.date, this.activity.getText(), true);
    }

    private void reduceObservations() {
        controller.updateObservationsCount(this.date, this.activity.getText(), false);
    }
}
