package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.ObservationsView;
import org.observations.gui.View;

import java.util.List;
import java.util.Map;

/**
 * Controller class which define how the view interact with the outside.
 */
public class ObservationsViewController implements SubController<String, Map<String, Map<String, Integer>>, List<String>> {

    private final MainWindowController parentController;
    private final View<Map<String, Map<String, Integer>>> view;
    private List<String> observationTypes;
    private Boolean precedentOperationIsCounter = false;

    /**
     * Initialize an observations view.
     *
     * @param mainWindowController the main controller.
     * @param observationTypesList a list containing all names of types of observations.
     */
    public ObservationsViewController(MainWindowController mainWindowController, List<String> observationTypesList) {
        parentController = mainWindowController;
        observationTypes = observationTypesList;
        view = new ObservationsView(this);
    }

    /**
     * Update the user interface with the input received.
     *
     * @param input a map of dates, each date having a map of observations and observations' counters.
     */
    public void updateView(Map<String, Map<String, Integer>> input) {
        System.out.println("\nOsservazioni: " + input);
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

    //Not utilized since there noting else to select after the observations
    public void getData(String text) {
    }

    /**
     * Call MainWindowController to increment
     *
     * @param input list with first argument the date and second argument the name of the observation.
     */
    public void updateModel(List<String> input) {
        parentController.incrementObservationCount(input.get(0), input.get(1));
    }

    /**
     * Calls the parent controller to increment counter of an observation done at a given date.
     *
     * @param date        date of observation
     * @param activity    name of observation
     */
    public void updateObservationsCount(String date, String activity) {
        precedentOperationIsCounter = true;
        parentController.incrementObservationCount(date, activity);
    }

    /**
     * Return true or false if precedent operation was an incremental or decremental operation
     *
     * @return true if the precedent operation was an incremental or decremental operation
     */
    public Boolean isPrecedentOperationIsCounter() {
        return precedentOperationIsCounter;
    }

    /**
     * Set precedentOperationIsCounter true or false.
     *
     * @param value boolean value.
     */
    public void setPrecedentOperationIsCounter(Boolean value) {
        precedentOperationIsCounter = value;
    }

    /**
     * Get current temporal list of name of observations types.
     *
     * @return list of types.
     */
    public List<String> getObservationsTypesNames() {
        return observationTypes;
    }

    /**
     * Call MainWindowController to insert a new type of observation.
     *
     * @param typeName name of type.
     */
    public void insertNewObservationType(String typeName) {
        observationTypes = parentController.insertNewObservationType(typeName);
    }
}
