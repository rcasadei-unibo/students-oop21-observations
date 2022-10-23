package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.ObservationsView;
import org.observations.gui.View;

import java.util.List;
import java.util.Map;

public class ObservationsViewController implements SubController<String, Map<String, Map<String, Integer>>, List<String>> {

    private final MainWindowController parentController;
    private final View<Map<String, Map<String, Integer>>> view;
    private List<String> observationTypes;



    private Boolean precedentOperationIsCounter = false;


    public ObservationsViewController(MainWindowController mainWindowController, List<String> observationTypesList) {
        this.parentController = mainWindowController;
        this.observationTypes = observationTypesList;
        this.view = new ObservationsView(this);
    }

    public void updateView(Map<String, Map<String, Integer>> input) {
        System.out.println("\nOsservazioni: " + input);
        view.update(input);
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
        view.setVisible(value);
    }

    public void getData(String text) {
    }

    public void updateModel(List<String> input) {
        this.parentController.incrementObservationCount(input.get(0), input.get(1));
    }

    /**
     * Calls the parent controller to increment counter of an observation done at a given date.
     *
     * @param date date of observation
     * @param activity name of observation
     * @param isIncrement
     */
    public void updateObservationsCount(String date, String activity, Boolean isIncrement) {
        if(isIncrement){
            this.precedentOperationIsCounter = true;
            this.parentController.incrementObservationCount(date, activity);
        } else {
            this.parentController.decrementObservationCount(date, activity);
        }
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
     * @param value
     */
    public void setPrecedentOperationIsCounter(Boolean value) {
        this.precedentOperationIsCounter = value;
    }

    /**
     * Get current temporal list of name of observations types.
     *
     * @return list of types.
     */
    public List<String> getObservationsTypesNames() {
        return this.observationTypes;
    }

    /**
     * Call parent controller to insert a new type of observation.
     *
     * @param typeName name of type.
     */
    public void insertNewObservationType(String typeName){
        this.observationTypes = this.parentController.insertNewObservationType(typeName);
    }
}
