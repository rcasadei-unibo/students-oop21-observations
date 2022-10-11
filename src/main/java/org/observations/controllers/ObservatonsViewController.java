package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.ObservatonsView;
import org.observations.gui.View;

import java.util.List;
import java.util.Map;

public class ObservatonsViewController implements SubController<String, Map<String, Map<String, Integer>>, List<String>> {

    private final MainController parentController;
    private final View<Map<String, Map<String, Integer>>> view;
    private List<String> observationTypes;


    public ObservatonsViewController(MainController mainController, List<String> observationTypesList) {
        this.parentController = mainController;
        this.observationTypes = observationTypesList;
        this.view = new ObservatonsView(this);

    }

    public void updateView(Map<String, Map<String, Integer>> input) {
        view.update(input);
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
        view.setVisible(value);
    }

    @Override
    public void switchOnOffEditButtons(Boolean value) {
        //TODO
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
