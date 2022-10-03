package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.ObservatonsView;
import org.observations.gui.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObservatonsViewController implements SubController<String, Map<String, Map<String, Integer>>, Integer> {

    private final MainController parentController;
    private final View<Map<String, Map<String, Integer>>> view;

    public ObservatonsViewController(MainController mainController) {
        this.parentController = mainController;
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
        System.out.println(text);
        Map<String, Map<String, Integer>> data;
        if(!text.isEmpty()){
            //TODO
        }
        this.updateView(data = Map.of());
    }

    public void updateModel(Integer output) {
        System.out.println(output);
        //TODO
    }

    public void updateObservationsCount(String hour, String activity, Boolean isIncrement) {
        //TODO
    }
}
