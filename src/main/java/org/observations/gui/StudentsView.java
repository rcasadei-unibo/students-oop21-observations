package org.observations.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.observations.controllers.StudentsViewController;
import org.observations.gui.popup.StudentInsertionPopup;

import java.util.List;

/**
 * View class which create and updates a scrollable button list of students.
 */
public class StudentsView implements View<List<String>> {

    private static final String LABEL_TEXT = "Studenti";
    private static final String NO_DATA_FOUND_MESSAGE = "Nessuno studente trovato";
    private static final String INSERT_BUTTON_TEXT = "+";

    private final StudentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private StudentInsertionPopup popup;

    /**
     * Initialize a new student view.
     *
     * @param controller the view controller.
     */
    public StudentsView(StudentsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(150);
        this.createInsertButton();
        this.view.setTop(new Label(LABEL_TEXT));
        this.view.setBottom(bottomBox);
    }

    /**
     * Update the view with the inputted list of students.
     *
     * @param input list of students.
     */
    public void update(List<String> input) {
        if (!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            input.forEach(student -> {
                Button button = new Button(student);
                button.setOnAction(event -> this.onStudentButtonClick(button.getText()));
                listBox.getChildren().add(button);
            });
            this.listPane.setContent(listBox);
            this.view.setCenter(listPane);
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
        return view;
    }

    /**
     * Show/hide the view.
     */
    public void setVisible(Boolean value) {
        view.setVisible(value);
    }

    /**
     * Create an insert button on the user interface.
     */
    private void createInsertButton() {
        Button insertButton = new Button(INSERT_BUTTON_TEXT);
        insertButton.setOnAction(event -> this.onInsertButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    /**
     * On the insert button click, initialize if not done already, and show on screen a popup for the user to insert a new student.
     */
    private void onInsertButtonClick() {
        if (this.popup == null) {
            this.popup = new StudentInsertionPopup(this.controller);
        }
        if (!this.popup.isShowing()) {
            this.popup.show();
        }
    }

    /**
     * On a student button click notify controller of the action.
     *
     * @param text name of student.
     */
    private void onStudentButtonClick(final String text) {
        controller.getData(text);
    }
}
