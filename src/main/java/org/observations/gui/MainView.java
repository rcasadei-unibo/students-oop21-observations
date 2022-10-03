package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class MainView implements View<Boolean> {

    BorderPane view = new BorderPane();

    public MainView(Node menuBar, Node students, Node dates, Node observations) {
        this.view.setTop(menuBar);
        this.view.setLeft(students);
        this.view.setCenter(dates);
        this.view.setRight(observations);
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
