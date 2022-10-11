package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MomentsView;
import org.observations.gui.View;

import java.util.List;

public class MomentsViewController implements SubController<String, List<String>, String> {

    private final MainController parentController;
    private final View<List<String>> view;
    private List<String> momentList;

    public MomentsViewController(MainController mainController, List<String> momentsList) {
        parentController = mainController;
        this.momentList = momentsList;
        view = new MomentsView(this);
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
        this.parentController.updateObservationsPanel(text);
    }

    public void updateModel(String moment) {
        this.parentController.insertNewMoment(moment);
    }

    public List<String> getMomentList() {
        return momentList;
    }

    public void setMomentList(List<String> momentList) {
        this.momentList = momentList;
    }
}