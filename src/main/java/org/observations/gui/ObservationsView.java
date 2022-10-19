package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.ObservationsViewController;
import org.observations.gui.popup.ObservationInsertionPopup;
import org.observations.utility.DateComparator;

import java.util.Map;
import java.util.stream.Collectors;

public class ObservationsView implements View<Map<String, Map<String, Integer>>> {

    private static final double TOP_BOX_SPACING = 10;
    private static final double LIST_BOX_SPACING = 8;
    private final ObservationsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private final ComboBox<String> dateSelector = new ComboBox<>();
    private String lastDateSelected = "";
    private ObservationInsertionPopup popup;
    private Map<String, Map<String, Integer>> temporalData;

    public ObservationsView(ObservationsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(150);
        this.createInsertButton();

        this.dateSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.setDateSelector();
        });

        HBox topBox = new HBox(new Label("Osservazioni"), new Label("Data: "), dateSelector);
        topBox.setSpacing(TOP_BOX_SPACING);
        this.view.setTop(topBox);
        this.view.setBottom(bottomBox);
    }

    private void setDateSelector() {
        if (!dateSelector.getSelectionModel().isEmpty()) {
            this.lastDateSelected = dateSelector.getSelectionModel().getSelectedItem();
            this.setListPane(this.lastDateSelected);
        } else if (dateSelector.getItems().contains(this.lastDateSelected)) {
            this.dateSelector.getSelectionModel().select(this.lastDateSelected);
        }
    }

    public void update(Map<String, Map<String, Integer>> input) {
        this.dateSelector.getItems().clear();
        if (!input.isEmpty()) {
            this.temporalData = input;
            this.dateSelector.getItems().addAll(
                    this.temporalData.keySet().stream()
                            .sorted(new DateComparator())
                            .collect(Collectors.toUnmodifiableList()));
            
            if (this.lastDateSelected != null) {
                this.setListPane(this.lastDateSelected);
            } else {
                this.view.setCenter(new Label("Seleziona una data"));
            }
        } else {
            this.view.setCenter(new Label("Nessuna osservazione trovata"));
        }
    }

    private void setListPane(String date) {
        if (date != null && !date.isEmpty() && this.temporalData.containsKey(date)) {
            VBox listBox = new VBox();
            listBox.setSpacing(LIST_BOX_SPACING);
            for (String activity : this.temporalData.get(date).keySet()) {
                Integer observations = this.temporalData.get(date).get(activity);
                listBox.getChildren().add(new ObservationLine(this.controller, date, activity, observations).getView());
            }
            this.listPane.setContent(listBox);
            this.view.setCenter(listPane);
        } else {
            this.view.setCenter(new Label("Data invalida"));
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
        if (this.popup == null) {
            this.popup = new ObservationInsertionPopup(this.controller);
        }
        if (!this.popup.isShowing()) {
            this.popup.show();
        }
    }

}
