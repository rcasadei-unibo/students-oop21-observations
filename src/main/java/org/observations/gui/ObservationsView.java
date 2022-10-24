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

/**
 * View class which create and updates a scrollable list of observations and their relative increment buttons.
 */
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

    /**
     * Initialize a new observations view.
     *
     * @param controller the view controller.
     */
    public ObservationsView(ObservationsViewController controller) {
        this.controller = controller;
        view.setMinWidth(150);
        createNewTypeButton();
        createInsertButton();

        dateSelector.valueProperty().addListener((observable, oldValue, newValue) -> setDateSelector());
        dateLabel.setText(DATE_LABEL_TEXT);

        HBox topBox = new HBox(new Label(LABEL_TEXT), dateSelector, dateLabel);
        topBox.setSpacing(TOP_BOX_SPACING);
        view.setTop(topBox);
        view.setBottom(bottomBox);
    }

    /**
     * Update the view with the inputted list of dates and their relative observations and counters.
     *
     * @param input value tho be inputted.
     */
    public void update(Map<String, Map<String, Integer>> input) {
        dateSelector.getItems().clear();
        if (!input.isEmpty()) {
            temporalData = input;
            dateSelector.getItems().addAll(
                    temporalData.keySet().stream()
                            .sorted(new DateComparator())
                            .collect(Collectors.toUnmodifiableList()));

            //If a date has precedently selected update the scroll list with the said date.
            //Otherwise, prompt the user to select a date.
            if (!lastDateSelected.isEmpty()) {
                //If there has been an increment
                if (controller.isPrecedentOperationIsCounter()) {
                    controller.setPrecedentOperationIsCounter(false);
                }
                setListPane(lastDateSelected);
            } else {
                view.setCenter(new Label(DATE_SELECTION_MESSAGE));
            }
        } else {
            view.setCenter(new Label(NO_DATA_FOUND_MESSAGE));
        }
    }

    /**
     * Returns the view root node.
     *
     * @return node of root.
     */
    public Node getView() {
        return view;
    }

    /**
     * Show/hide the view.
     */
    public void setVisible(Boolean value) {
        view.setVisible(value);
    }

    /**
     * Calls the popup to update its list of dates with a new set of dates from the view controller.
     */
    public void updateObservationSelectorList() {
        if (popup != null) {
            popup.updateObservationSelector();
        }
    }

    /**
     * Manages the current selected date for the selected moment.
     * If the user select a date it gets temporally saved, so it doesn't have to reselect the date after incrementing od decrementing an observation.
     * If the user select a new moment the date gets deleted.
     */
    private void setDateSelector() {
        if (!dateSelector.getSelectionModel().isEmpty()) {
            String date = dateSelector.getSelectionModel().getSelectedItem();
            lastDateSelected = date;
            dateLabel.setText(DATE_LABEL_TEXT + " " + date);
            setListPane(date);
        } else if (dateSelector.getItems().contains(lastDateSelected)) {
            dateSelector.getSelectionModel().select(lastDateSelected);

            //Check done to make sure the saved date don't get deleted after an observation increment nut it does if user select a different moment
        } else if (!controller.isPrecedentOperationIsCounter()) {
            lastDateSelected = "";
            dateLabel.setText(DATE_LABEL_TEXT);
        }
    }

    /**
     * Reset the scrollable list with all the observation contained after the given date.
     *
     * @param date a date to which find observation of.
     */
    private void setListPane(String date) {
        if (date != null && !date.isEmpty() && temporalData.containsKey(date)) {
            VBox listBox = new VBox();
            listBox.setSpacing(LIST_BOX_SPACING);
            for (String activity : temporalData.get(date).keySet()) {
                Integer observations = temporalData.get(date).get(activity);
                listBox.getChildren().add(new ObservationLine(controller, date, activity, observations).getView());
            }
            listPane.setContent(listBox);
            view.setCenter(listPane);
        }
    }

    /**
     * Create an insert button, for inputting a new type of observation, on the user interface.
     */
    private void createNewTypeButton() {
        Button insertButton = new Button(NEW_TYPE_BUTTON_TEXT);
        insertButton.setOnAction(event -> onNewTypeButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    /**
     * Create an insert button, for inputting a new observation, on the user interface.
     */
    private void createInsertButton() {
        Button insertButton = new Button(INSERT_BUTTON_TEXT);
        insertButton.setOnAction(event -> onInsertButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    /**
     * On the insert new type button click, initialize if not done already, and show on screen a popup for the user to insert an observation and the date to where has to be saved.
     */
    private void onInsertButtonClick() {
        if (popup == null) {
            popup = new ObservationInsertionPopup(controller);
        }
        if (!popup.isShowing()) {
            popup.show();
        }
    }

    /**
     * On the insert new type button click, initialize if not done already, and show on screen a popup for the user to insert a new type of observation.
     */
    private void onNewTypeButtonClick() {
        if (typePopup == null) {
            typePopup = new ObservationTypeInsertionPopup(controller, this);
        }
        if (!typePopup.isShowing()) {
            typePopup.show();
        }
    }

    /**
     * Simple container for the observation name its counter and a button to increment the said counter.
     */
    private static class ObservationLine extends HBox {

        final Integer SPACING = 5;
        private final ObservationsViewController controller;
        private final String date;
        private final Label observation;
        private final Label counter;

        /**
         * Create a new HBox container containing a label and a button.
         * It saves an observations, its counter and its date of insertion.
         *
         * @param controller the view controller.
         * @param date the observation's date.
         * @param observation the observation name.
         * @param counter the observation's counter.
         */
        public ObservationLine(ObservationsViewController controller, String date, String observation, Integer counter) {
            this.controller = controller;
            this.date = date;
            this.observation = new Label(observation);
            this.counter = new Label(counter.toString());

            Button incrementButton = new Button("+");

            getChildren().addAll(this.observation, this.counter, incrementButton);
            setSpacing(SPACING);

            incrementButton.setOnAction(event -> {
                System.out.println("add observations button hit");
                incrementObservation();
            });
        }

        /**
         * Call the controller to increment the counter for the contained observation and date.
         */
        private void incrementObservation() {
            controller.updateObservationsCount(date, observation.getText(), true);
        }

        /**
         * Call the controller to decrement the counter for the contained observation and date.
         */
        private void reduceObservations() {
            controller.updateObservationsCount(date, observation.getText(), false);
        }

        /**
         * return this element node.
         *
         * @return a node of the element.
         */
        private Node getView() {
            return this;
        }
    }
}

