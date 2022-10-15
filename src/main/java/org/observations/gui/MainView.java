package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class MainView implements View<Boolean> {

    HBox view = new HBox();

    public MainView(Node studentPanel, Node momentsPanel, Node observationsPanel) {
        this.view.getChildren().addAll(studentPanel, momentsPanel, observationsPanel);
    }

    public void update(Boolean input) {
    }

    public Node getView() {
        return view;
    }

    public void setVisible(Boolean value) {
        view.setVisible(value);
    }

}
