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
import org.observations.gui.popup.ObservationTypeInsertionPopup;
import org.observations.utility.DateComparator;

import java.util.Map;
import java.util.stream.Collectors;

public class ObservationsView implements View<Map<String, Map<String, Integer>>> {


    private static final String LABEL_TEXT = "Osservazioni";
    private static final String NO_DATA_FOUND_MESSAGE = "Nessuna osservazione trovata";
    private static final String DATE_LABEL_TEXT = "Data: ";
    private static final String DATE_SELECTION_MESSAGE = "Seleziona una data";
    private static final String INSERT_BUTTON_TEXT = "+";
    private static final String NEW_TYPE_BUTTON_TEXT = "Aggiungi nuovo tipo di osservazione";
    private static final double TOP_BOX_SPACING = 16;
    private static final double LIST_BOX_SPACING = 8;

    private final ObservationsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private final Label dateLabel = new Label();
    private final ComboBox<String> dateSelector = new ComboBox<>();
    private String lastDateSelected = "";
    private ObservationInsertionPopup popup;
    private Map<String, Map<String, Integer>> temporalData;
    private ObservationTypeInsertionPopup typePopup;

    public ObservationsView(ObservationsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(150);
        this.createNewTypeButton();
        this.createInsertButton();

        this.dateSelector.valueProperty().addListener((observable, oldValue, newValue) -> this.setDateSelector());
        this.dateLabel.setText(DATE_LABEL_TEXT);

        HBox topBox = new HBox(new Label(LABEL_TEXT), dateSelector, this.dateLabel);
        topBox.setSpacing(TOP_BOX_SPACING);
        this.view.setTop(topBox);
        this.view.setBottom(bottomBox);
    }

    /**
     * Update the view with the new input.
     *
     * @param input value tho be inputted.
     */
    public void update(Map<String, Map<String, Integer>> input) {
        this.dateSelector.getItems().clear();
        if (!input.isEmpty()) {
            this.temporalData = input;
            this.dateSelector.getItems().addAll(
                    this.temporalData.keySet().stream()
                            .sorted(new DateComparator())
                            .collect(Collectors.toUnmodifiableList()));

            if (this.lastDateSelected != null && !lastDateSelected.isEmpty()) {
                if (this.controller.isPrecedentOperationIsCounter()) {
                    this.controller.setPrecedentOperationIsCounter(false);
                }
                this.setListPane(this.lastDateSelected);
            } else {
                this.view.setCenter(new Label(DATE_SELECTION_MESSAGE));
            }
        } else {
            this.view.setCenter(new Label(NO_DATA_FOUND_MESSAGE));
        }
    }

    /**
     * Returns the view root node.
     *
     * @return node of root.
     */
    public Node getView() {
        return this.view;
    }

    /**
     * Show/hide the view.
     */
    public void setVisible(Boolean value) {
        this.view.setVisible(value);
    }

    /**
     * Update the popup selector
     */
    public void updateObservationSelectorList() {
        if (this.popup != null) {
            this.popup.updateObservationSelector();
        }
    }

    private void setDateSelector() {
        if (!dateSelector.getSelectionModel().isEmpty()) {
            String date = dateSelector.getSelectionModel().getSelectedItem();
            this.lastDateSelected = date;
            this.dateLabel.setText(DATE_LABEL_TEXT + " " + date);
            this.setListPane(date);
        } else if (dateSelector.getItems().contains(this.lastDateSelected)) {
            this.dateSelector.getSelectionModel().select(this.lastDateSelected);
        } else if (!this.controller.isPrecedentOperationIsCounter()) {
            this.lastDateSelected = "";
            this.dateLabel.setText(DATE_LABEL_TEXT);
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
        }
    }

    private void createNewTypeButton() {
        Button insertButton = new Button(NEW_TYPE_BUTTON_TEXT);
        insertButton.setOnAction(event -> this.onNewTypeButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    private void createInsertButton() {
        Button insertButton = new Button(INSERT_BUTTON_TEXT);
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

    private void onNewTypeButtonClick() {
        if (this.typePopup == null) {
            this.typePopup = new ObservationTypeInsertionPopup(this.controller, this);
        }
        if (!this.typePopup.isShowing()) {
            this.typePopup.show();
        }
    }

    /**
     * Simple container for the observation name its counter
     * and a button to increment the said counter
     */
    private static class ObservationLine extends HBox {

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

        //call controller to increment the counter
        private void incrementObservation() {
            controller.updateObservationsCount(this.date, this.activity.getText(), true);
        }

        //call the controller to decrement the counter
        private void reduceObservations() {
            controller.updateObservationsCount(this.date, this.activity.getText(), false);
        }

        private Node getView() {
            return this;
        }
    }
}

