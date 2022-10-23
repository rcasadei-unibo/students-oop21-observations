package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MomentsView;
import org.observations.gui.View;

import java.util.List;

public class MomentsViewController implements SubController<String, List<String>, String> {

    private final MainWindowController parentController;
    private final View<List<String>> view;
    private List<String> momentList;

    public MomentsViewController(MainWindowController mainWindowController, List<String> momentsList) {
        parentController = mainWindowController;
        this.momentList = momentsList;
        view = new MomentsView(this);
    }

    public void updateView(List<String> input) {
        System.out.println("\nMomenti: " + input);
        view.update(input);
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
        view.setVisible(value);
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

    public void insertNewMomentType(String typeName) {
        this.momentList = this.parentController.insertNewMoment(typeName);
    }
}
