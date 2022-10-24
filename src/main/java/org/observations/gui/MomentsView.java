package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.MomentsViewController;
import org.observations.gui.popup.MomentsInsertionPopup;
import org.observations.gui.popup.MomentsTypeInsertionPopup;

import java.util.List;

/**
 * View class which create and updates a scrollable button list of moments.
 */
public class MomentsView implements View<List<String>> {

    private static final String LABEL_TEXT = "Momenti";
    private static final String NO_DATA_FOUND_MESSAGE = "Nessun momento trovato";
    private static final String INSERT_BUTTON_TEXT = "+";
    private static final String NEW_TYPE_BUTTON_TEXT = "Aggiungi nuovo tipo di momento";

    private final MomentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private MomentsInsertionPopup popup;
    private MomentsTypeInsertionPopup typePopup;

    /**
     * Initialize a new moments view.
     *
     * @param controller the view controller.
     */
    public MomentsView(MomentsViewController controller) {
        this.controller = controller;
        view.setMinWidth(100);
        createTypeInsertButton();
        createInsertButton();
        view.setTop(new Label(LABEL_TEXT));
        view.setCenter(listPane);
        view.setBottom(bottomBox);
    }

    /**
     * Update the view with the inputted list of moments.
     *
     * @param input list of moment.
     */
    public void update(List<String> input) {
        if (!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            input.forEach(hour -> {
                Button button = new Button(hour);
                button.setOnAction(event -> onMomentButtonClick(button.getText()));
                listBox.getChildren().add(button);
            });
            listPane.setContent(listBox);
            view.setCenter(listPane);
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
     * Update the popup selector
     */
    public void updateObservationSelectorList() {
        if (popup != null) {
            popup.updateObservationSelector();
        }
    }

    /**
     * Create an insert button, for inputting a new moment, on the user interface.
     */
    private void createInsertButton() {
        Button insertButton = new Button(INSERT_BUTTON_TEXT);
        insertButton.setOnAction(event -> onInsertButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    /**
     * Create an insert button, for inputting a new type of moment, on the user interface.
     */
    private void createTypeInsertButton() {
        Button insertButton = new Button(NEW_TYPE_BUTTON_TEXT);
        insertButton.setOnAction(event -> onNewTypeButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    /**
     * On the insert new type button click, initialize if not done already, and show on screen a popup for the user to insert a moment.
     */
    private void onInsertButtonClick() {
        if (popup == null) {
            popup = new MomentsInsertionPopup(controller);
        }
        if (!popup.isShowing()) {
            popup.show();
        }
    }

    /**
     * On the insert new type button click, initialize if not done already, and show on screen a popup for the user to insert a new type of moment.
     */
    private void onNewTypeButtonClick() {
        if (typePopup == null) {
            typePopup = new MomentsTypeInsertionPopup(controller, this);
        }
        if (!typePopup.isShowing()) {
            typePopup.show();
        }
    }

    /**
     * On a moment button click notify controller of the action.
     *
     * @param text name of moment.
     */
    private void onMomentButtonClick(final String text) {
        controller.getData(text);
    }
}
