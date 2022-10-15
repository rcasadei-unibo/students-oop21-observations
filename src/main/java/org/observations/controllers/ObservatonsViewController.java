package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.ObservationsView;
import org.observations.gui.View;

import java.util.List;
import java.util.Map;

public class ObservatonsViewController implements SubController<String, Map<String, Map<String, Integer>>, List<String>> {

    private final MainWindowController parentController;
    private final View<Map<String, Map<String, Integer>>> view;
    private final List<String> observationTypes;


    public ObservatonsViewController(MainWindowController mainWindowController, List<String> observationTypesList) {
        this.parentController = mainWindowController;
        this.observationTypes = observationTypesList;
        this.view = new ObservationsView(this);
    }

    public void updateView(Map<String, Map<String, Integer>> input) {
        System.out.println(input);
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
        this.parentController.insertNewObservation(input.get(0), input.get(1));
    }

    public void updateObservationsCount(String activity, Boolean isIncrement) {
        if(isIncrement){
            this.parentController.incrementObservationCount(activity);
        } else {
            this.parentController.decrementObservationCount(activity);
        }
    }

    public List<String> getObservationsTypesNames() {
        return this.observationTypes;
    }
}
