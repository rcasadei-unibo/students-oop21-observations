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

    private final StudentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane studentsPane = new ScrollPane();
    private final HBox bottomBox = new HBox();

    private boolean editButtonsVisible;
    private StudentInsertionPopup popup;

    public StudentsView(StudentsViewController controller) {
        this.controller = controller;

        view.setMinWidth(150);

        this.createEditButton();
        this.createInsertButton();

        view.setBottom(bottomBox);
    }

    public void update(final List<String> input) {
        if (!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            input.forEach(student -> {
                Button button = new Button(student);
                button.setOnAction(event -> {
                    this.onStudentButtonClick(button.getText());
                });
                listBox.getChildren().add(button);
            });
            studentsPane.setContent(listBox);
            view.setCenter(studentsPane);
        } else {
            view.setCenter(new Label("No students found"));
        }
    }

    public Node getView() {
        return view;
    }

    public void setVisible(Boolean value) {
        view.setVisible(value);
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

    private void onEditButtonClick() {
        //TODO
    }

    public boolean isEditButtonsVisible() {
        return editButtonsVisible;
    }

    public void setEditButtonsVisible(boolean editButtonsVisible) {
        this.editButtonsVisible = editButtonsVisible;
    }

}
