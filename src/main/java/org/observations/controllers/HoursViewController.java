package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.HoursView;
import org.observations.gui.View;

import java.util.List;

public class HoursViewController implements SubController<String, List<String>, String> {

    private final MainController parentController;
    private final View<List<String>> view;

    public HoursViewController(MainController mainController) {
        parentController = mainController;
        view = new HoursView(this);
    }

    public void updateView(List<String> input) {
        view.update(input);
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
        view.setVisible(value);
    }

    public void switchOnOffEditButtons(Boolean value) {
        //TODO
    }

    public void getData(final String text) {
        System.out.println("get data of: " + text);
        parentController.updateObservationsPanel(text);
    }

    public void updateModel(String hour) {
        System.out.println("Add to model: " + hour);
        //TODO
    }
}
