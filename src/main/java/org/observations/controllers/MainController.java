package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MainView;

import java.util.List;
import java.util.Map;

public class MainController {
    private final MainView view;
    private MenuController menuController;
    private SubController<String, List<String>, String> studentsViewController;
    private SubController<String, List<String>, String> hoursViewController;
    private SubController<String, Map<String, Map<String, Integer>>, Integer> observatonsViewController;

    public MainController() {
        menuController = new MenuController(this);
        studentsViewController = new StudentsViewController(this);
        hoursViewController = new HoursViewController(this);
        observatonsViewController = new ObservatonsViewController(this);

        hoursViewController.setViewVisible(false);
        observatonsViewController.setViewVisible(false);

        updateStudentsPanel();
        view = new MainView(menuController.getView(), studentsViewController.getView(), hoursViewController.getView(), observatonsViewController.getView());
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
    }

    public void updateStudentsPanel() {
        //TODO
        studentsViewController.updateView(List.of());
    }

    public void updateHoursPanel(String text) {
        //TODO
        hoursViewController.updateView(List.of());
    }

    public void updateObservationsPanel(String text) {
        //TODO
        observatonsViewController.updateView(Map.of());
    }
}
