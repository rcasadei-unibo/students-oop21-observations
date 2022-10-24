package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.StudentsView;
import org.observations.gui.View;

import java.util.List;

/**
 * Controller class which define how the view interact with the outside.
 */
public class StudentsViewController implements SubController<String, List<String>, String> {

    private final MainWindowController parentController;
    private final View<List<String>> view;

    /**
     * Initialize a students view.
     * 
     * @param mainWindowController the main controller.
     */
    public StudentsViewController(MainWindowController mainWindowController) {
        parentController = mainWindowController;
        view = new StudentsView(this);
    }

    /**
     * Update the view with the inputted list of students.
     *
     * @param input list of students.
     */
    public void updateView(List<String> input) {
        System.out.println("\nStudenti: " + input);
        view.update(input);
    }

    /**
     * Return a node containing the view.
     *
     * @return a node containing the view
     */
    public Node getView() {
        return view.getView();
    }

    /**
     * Hide or show its controlled view.
     *
     * @param value boolean value.
     */
    public void setViewVisible(Boolean value) {
        view.setVisible(value);
    }

    /**
     * Call the MainWindowController to update the user interface.
     *
     * @param text name of student.
     */
    public void getData(final String text) {
        parentController.updateMomentsPanel(text);
    }

    /**
     * Call ht MainWindowController to save a new student.
     *
     * @param output name of student.
     */
    public void updateModel(final String output) {
        parentController.insertNewStudent(output);
    }
}
