package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.StudentInsertionPopup;
import org.observations.gui.StudentsView;
import org.observations.gui.View;

import java.util.List;

public class StudentsViewController implements SubController<String, List<String>, String> {

    private final MainController parentController;
    private final View<List<String>> view;

    public StudentsViewController(MainController parent) {
        parentController = parent;
        view = new StudentsView(this);
        updateView(List.of());
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
        System.out.println(text);
        //TODO
        parentController.updateHoursPanel(text);
    }

    public void updateModel(final String output) {
        System.out.println(output);
        //TODO
    }
}
