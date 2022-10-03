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

import java.util.List;

public class StudentsView implements View<List<String>> {

    private final StudentsViewController controller;
    private final BorderPane view = new BorderPane();
    private final ScrollPane studentsPane = new ScrollPane();
    private final HBox bottomBox = new HBox();
    private boolean editButtonsVisible;

    public StudentsView(StudentsViewController controller) {
        this.controller = controller;

        view.setMinWidth(150);
        view.setMaxWidth(300);

        this.createEditButton();
        this.createInsertButton();

        view.setBottom(bottomBox);
        view.getBottom().setVisible(false);
    }

    public void update(final List<String> input) {
        if (!input.isEmpty()) {
            VBox listBox = new VBox();
            listBox.setSpacing(8);
            input.forEach(student -> {
                Button button = new Button(student);
                button.setOnAction(event -> {
                    System.out.println(button.getText());
                    this.onStudentButtonClick(button.getText());
                });
                this.resize(button.getWidth());
                listBox.getChildren().add(button);
            });
            studentsPane.setContent(listBox);
            view.getBottom().setVisible(true);
            view.setCenter(studentsPane);
        } else {
            view.setCenter(new Label("No students found"));
            view.getBottom().setVisible(false);
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

    private void resize(final double width) {
        if (width > view.getWidth() && width <= view.getMaxWidth()) {
            view.prefWidth(width);
        } else {
            view.setPrefWidth(view.getMinWidth());
        }
    }

    private void onInsertButtonClick() {
        System.out.println("insert student button has been hit");
        new StudentInsertionPopup(this.controller);
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
