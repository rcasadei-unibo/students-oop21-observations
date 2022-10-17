package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.observations.controllers.ObservationsViewController;

public class ObservationLine extends HBox implements View<Integer> {

    final Integer SPACING = 5;
    private final ObservationsViewController controller;
    private final Label hour;
    private final Label activity;
    private final Label observations;

    public ObservationLine(ObservationsViewController controller, String hour, String activity, Integer observations) {
        this.controller = controller;
        this.hour = new Label(hour);
        this.activity = new Label(activity);
        this.observations = new Label(observations.toString());

        Button add = new Button("+");
        Button reduce = new Button("-");

        this.getChildren().addAll(this.hour, this.activity, this.observations, add, reduce);
        this.setSpacing(SPACING);


        add.setOnAction(event -> {
            System.out.println("add observations button hit");
            this.incrementObservation();
        });

        reduce.setOnAction(event -> {
            System.out.println("reduce observations button hit");
            this.reduceObservations();
        });
    }

    public void update(Integer input) {
        this.observations.setText(input.toString());
    }

    public Node getView() {
        return this;
    }

    public void setVisible(Boolean value) {}

    private void incrementObservation() {
        controller.updateObservationsCount(this.activity.getText(), true);
    }

    private void reduceObservations() {
        controller.updateObservationsCount(this.activity.getText(), false);
    }
}
