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

public class StudentsView implements View<List<String>> {

    private static final String LABEL_TEXT = "Studenti";
    private static final String NO_DATA_FOUND_MESSAGE = "Nessuno studente trovato";
    private static final String INSERT_BUTTON_TEXT = "+";

    private final StudentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane listPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private StudentInsertionPopup popup;

    public StudentsView(StudentsViewController controller) {
        this.controller = controller;
        this.view.setMinWidth(150);
        this.createInsertButton();
        this.view.setTop(new Label(LABEL_TEXT));
        this.view.setBottom(bottomBox);
    }

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

    public Node getView() {
        return view;
    }

    public void setVisible(Boolean value) {
        view.setVisible(value);
    }

    private void createInsertButton() {
        Button insertButton = new Button(INSERT_BUTTON_TEXT);
        insertButton.setOnAction(event -> this.onInsertButtonClick());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().add(insertButton);
    }

    private void onInsertButtonClick() {
        if (this.popup == null) {
            this.popup = new StudentInsertionPopup(this.controller);
        }
        if (!this.popup.isShowing()) {
            this.popup.show();
        }
    }

    private void onStudentButtonClick(final String text) {
        controller.getData(text);
    }
}
