package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.StudentsView;
import org.observations.gui.View;

import java.util.List;

public class StudentsViewController implements SubController<String, List<String>, String> {

    private final MainWindowController parentController;
    private final View<List<String>> view;

    public StudentsViewController(MainWindowController parent) {
        parentController = parent;
        view = new StudentsView(this);
    }

    public void updateView(List<String> input) {
        System.out.println("\nStudenti: " + input);
        view.update(input);
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
        view.setVisible(value);
    }

    public void getData(final String text) {
        this.parentController.updateMomentsPanel(text);
    }

    public void updateModel(final String output) {
        this.parentController.insertNewStudent(output);
    }
}
