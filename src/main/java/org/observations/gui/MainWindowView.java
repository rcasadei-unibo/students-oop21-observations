package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.observations.controllers.MainWindowController;

public class MainWindowView implements View<Boolean> {

    MainWindowController controller;

    private final String[] CHARTS_BUTTON_TEXTS= {"Mostra grafici", "Nascondi grafici"};
    BorderPane view = new BorderPane();
    HBox listsBox = new HBox();
    Button chartsWindowButton = new Button();

    public MainWindowView(Node studentPanel, Node momentsPanel, Node observationsPanel) {
        this.listsBox.getChildren().addAll(studentPanel, momentsPanel, observationsPanel);
        this.view.setCenter(listsBox);
        this.chartsWindowButton.setText("Mostra grafici");
        //chartsWindowButton.setOnAction(event -> this.onShowChartsButton());

        this.view.setBottom(chartsWindowButton);
    }

    /*private void onShowChartsButton() {
        this.controller.showChartsWindow();
    }*/

    public void update(Boolean input) {
    }

    public Node getView() {
        return view;
    }

    public void setVisible(Boolean value) {
        view.setVisible(value);
    }

}
