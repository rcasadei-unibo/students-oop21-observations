package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.observations.controllers.ObservationsViewController;

/**
 * Simple container for the observation name its counter
 * and a button to increment the said counter
 */
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

    /**
     * Update the view with the new input.
     *
     * @param input value tho be inputted.
     */
    public void update(Integer input) {
        this.observations.setText(input.toString());
    }

    /**
     * Returns the view root node.
     *
     * @return node of root.
     */
    public Node getView() {
        return this;
    }

    /**
     * Show/hide the view.
     *
     * @param value
     */
    public void setVisible(Boolean value) {
    }

    //call controller to increment the counter
    private void incrementObservation() {
        controller.updateObservationsCount(this.date, this.activity.getText(), true);
    }

    //call the controller to decrement the counter
    private void reduceObservations() {
        controller.updateObservationsCount(this.date, this.activity.getText(), false);
    }
}
