package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.ObservatonsViewController;
import org.observations.controllers.SubController;
import org.observations.gui.popup.ObservationInsertionPopup;

import java.util.Map;

public class ObservatonsView implements View<Map<String, Map<String, Integer>>> {

    private final SubController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private ObservationInsertionPopup popup;


    public ObservatonsView(ObservatonsViewController controller) {
        this.controller = controller;
        this.setView();
    }

    private void setView() {
        this.view.setMinWidth(150);
        this.createEditButton();
        this.createInsertButton();

        this.view.setCenter(listPane);
        this.view.setBottom(bottomBox);
        this.view.getBottom().setVisible(false);
    }

    public void update(Map<String, Map<String, Integer>> input) {
        if(!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            for (String hour : input.keySet()) {
                Map<String, Integer> activities = input.get(hour);
                for (String activity : activities.keySet()) {
                    Integer observations = activities.get(activity);
                    listBox.getChildren().add(new ObservationLine((ObservatonsViewController) this.controller, hour, activity, observations).getView());
                }
            }
            this.listPane.setContent(listBox);
        } else {
            this.view.setCenter(new Label("Nessuna osservazione trovata"));
        }
        this.view.getBottom().setVisible(true);
    }

    public Node getView() {
        return this.view;
    }

    public void setVisible(Boolean value) {
        this.view.setVisible(value);
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
        if(this.popup == null){
            this.popup = new ObservationInsertionPopup(this.controller);
        }
        if(!this.popup.isShowing()){
            this.popup.show();
        }
    }
}
