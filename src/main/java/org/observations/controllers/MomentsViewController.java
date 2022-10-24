package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MomentsView;
import org.observations.gui.View;

import java.util.List;

public class MomentsViewController implements SubController<String, List<String>, String> {

    private final MainWindowController parentController;
    private final View<List<String>> view;
    private List<String> momentList;

    /**
     * Initialize a moments view.
     * 
     * @param mainWindowController the main controller.
     * @param momentsList a list containing all names of types of moments.
     */
    public MomentsViewController(MainWindowController mainWindowController, List<String> momentsList) {
        parentController = mainWindowController;
        momentList = momentsList;
        view = new MomentsView(this);
    }

    /**
     * Update the view with the inputted list of moments.
     *
     * @param input list of moments.
     */
    public void updateView(List<String> input) {
        System.out.println("\nMomenti: " + input);
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
     * @param text name of moment.
     */
    public void getData(final String text) {
        parentController.updateObservationsPanel(text);
    }

    /**
     * Call ht MainWindowController to save a new moment.
     *
     * @param output name of moment.
     */
    public void updateModel(String output) {
        parentController.insertNewMoment(output);
    }

    /**
     * Get current temporal list of name of moments types.
     *
     * @return list of types.
     */
    public List<String> getMomentList() {
        return momentList;
    }

    /**
     * Call MainWindowController to insert a new type of moment.
     *
     * @param typeName name of type.
     */
    public void insertNewMomentType(String typeName) {
        momentList = parentController.insertNewMoment(typeName);
    }
}
