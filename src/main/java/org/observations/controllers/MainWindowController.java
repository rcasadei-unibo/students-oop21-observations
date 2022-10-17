package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MainWindowView;
import org.observations.model.ModelAdapter;
import org.observations.model.core.ModelAdapterImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindowController {
    private final ModelAdapter adapter;
    private final MainWindowView view;
    private final SubController<String, List<String>, String> studentsViewController;
    private final SubController<String, List<String>, String> momentsViewController;
    private final SubController<String, Map<String, Map<String, Integer>>, List<String>> observationsViewController;

    private ChartsWindowController chartsWindowController;
    private String lastStudentSelected;
    private String lastMomentSelected;

    public MainWindowController() {

        List<String> momentsList;
        List<String> observationTypesList;
        try {
            this.adapter = new ModelAdapterImpl();
            momentsList = adapter.getMomentsListFromFile();
            observationTypesList = adapter.getTypesListFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.studentsViewController = new StudentsViewController(this);
        this.momentsViewController = new MomentsViewController(this, momentsList);
        this.observationsViewController = new ObservatonsViewController(this, observationTypesList);
        this.chartsWindowController = new ChartsWindowController(this);

        this.momentsViewController.setViewVisible(false);
        this.observationsViewController.setViewVisible(false);

        this.updateStudentsPanel();
        this.view = new MainWindowView(this.studentsViewController.getView(), this.momentsViewController.getView(), this.observationsViewController.getView());
    }

    public Node getView() {
        return view.getView();
    }

    public List<String> getStudentsList() {
        try {
            return this.adapter.getStudentsList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getMomentList(String student) {
        try {
            return this.adapter.getMomentsList(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Map<String, Integer>> getDateAndObservationsList(String moment) {
        try {
            return this.adapter.getDatesAndObservations(moment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStudentsPanel() {
        studentsViewController.updateView(this.getStudentsList());
    }

    void updateMomentsPanel(String student) {
        this.lastStudentSelected = student;
        this.momentsViewController.updateView(this.getMomentList(student));
        this.momentsViewController.setViewVisible(true);
        this.observationsViewController.setViewVisible(false);
    }

    void updateObservationsPanel(String moment) {
        this.lastMomentSelected = moment;
        this.observationsViewController.updateView(this.getDateAndObservationsList(moment));
        this.observationsViewController.setViewVisible(true);
    }

    void incrementObservationCount(String observationType) {
        try {
            adapter.clickObservation(observationType);
            this.updateObservationsPanel(this.lastMomentSelected);
            if (this.chartsWindowController.isShowing()) {
                this.chartsWindowController.updateChartsWindow();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void decrementObservationCount(String observationType) {

    }

    public void insertNewStudent(String student) {
        try {
            adapter.createStudent(student);
            this.updateStudentsPanel();
            if (this.chartsWindowController.isShowing()) {
                this.chartsWindowController.updateChartsWindow();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNewMoment(String moment) {
        try {
            adapter.createMoment(moment);
            this.updateMomentsPanel(this.lastStudentSelected);
            this.observationsViewController.setViewVisible(false);
            if (this.chartsWindowController.isShowing()) {
                this.chartsWindowController.updateChartsWindow();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNewObservation(String date, String observationType) {
        try {
            adapter.createDate(date);
            adapter.clickObservation(observationType);
            this.updateObservationsPanel(this.lastMomentSelected);
            if (this.chartsWindowController.isShowing()) {
                this.chartsWindowController.updateChartsWindow();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNewObservationType(String observationType) {
        try {
            adapter.createObservationsType(observationType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showChartsWindow() {
        if (this.chartsWindowController == null) {
            this.chartsWindowController = new ChartsWindowController(this);
        }
        if (!chartsWindowController.isShowing()) {
            this.chartsWindowController.showWindow();
        } else {
            this.chartsWindowController.hideWindow();
        }
    }

    public Map<String, Map<String, Map<String, Map<String, Integer>>>> getAllData() {
        Map<String, Map<String, Map<String, Map<String, Integer>>>> data = new HashMap<>();
        try {
            adapter.getStudentsList().forEach(student -> data.put(student, new HashMap<>()));
            for (String student : data.keySet()) {
                data.put(student, new HashMap<>());
                List<String> moments = adapter.getMomentsList(student);
                for (String moment : moments) {
                    data.get(student).put(moment, new HashMap<>());
                    Map<String, Map<String, Integer>> observations = adapter.getDatesAndObservations(moment);
                    data.get(student).get(moment).putAll(observations);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Collections.unmodifiableMap(data);
    }
}
