package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.ObservationsViewController;
import org.observations.gui.popup.ObservationInsertionPopup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ObservationsView implements View<Map<String, Map<String, Integer>>> {

    private final ObservationsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private ObservationInsertionPopup popup;

    public ObservationsView(ObservationsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(150);
        this.createInsertButton();
        this.view.setTop(new Label("Osservazioni"));
        this.view.setBottom(bottomBox);
    }

    public void update(Map<String, Map<String, Integer>> input) {
        if(!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            List<String> reversedList = new ArrayList<>(input.keySet());
            //Reverse list order so we have recent dates as first
            Collections.reverse(reversedList);
            for (String date : reversedList) {
                Map<String, Integer> activities = input.get(date);
                for (String activity : activities.keySet()) {
                    Integer observations = activities.get(activity);
                    listBox.getChildren().add(new ObservationLine(this.controller, date, activity, observations).getView());
                }
            }
            this.listPane.setContent(listBox);
            this.view.setCenter(listPane);
        } else {
            this.view.setCenter(new Label("Nessuna osservazione trovata"));
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
        if(this.popup == null){
            this.popup = new ObservationInsertionPopup(this.controller);
        }
        if(!this.popup.isShowing()){
            this.popup.show();
        }
    }
}
