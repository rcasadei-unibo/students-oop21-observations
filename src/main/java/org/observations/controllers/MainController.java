package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MainView;
import org.observations.model.ModelAdapter;
import org.observations.model.core.ModelAdapterImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainController {
    private final ModelAdapter adapter;
    private final MainView view;
    private final SubController<String, List<String>, String> studentsViewController;
    private final SubController<String, List<String>, String> momentsViewController;
    private final SubController<String, Map<String, Map<String, Integer>>, List<String>> observationsViewController;

    private String lastStudentSelected;
    private String lastMomentSelected;

    public MainController() {
        List<String> momentsList;
        List<String> observationTypesList;
        try {
            adapter = new ModelAdapterImpl();
            momentsList = adapter.getMomentsListFromFile();
            observationTypesList = adapter.getTypesListFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        studentsViewController = new StudentsViewController(this);

        momentsViewController = new MomentsViewController(this, momentsList);
        observationsViewController = new ObservatonsViewController(this, observationTypesList);

        momentsViewController.setViewVisible(false);
        observationsViewController.setViewVisible(false);

        updateStudentsPanel();
        view = new MainView(studentsViewController.getView(), momentsViewController.getView(), observationsViewController.getView());
    }

    public Node getView() {
        return view.getView();
    }

    public void setViewVisible(Boolean value) {
    }

    public void updateStudentsPanel() {
        try {
            studentsViewController.updateView(adapter.getStudentsList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMomentsPanel(String student) {
        this.lastStudentSelected = student;
        try {
            momentsViewController.updateView((adapter.getMomentsList(student).get(student)));
            this.momentsViewController.setViewVisible(true);
            this.observationsViewController.setViewVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateObservationsPanel(String moment) {
        this.lastMomentSelected = moment;
        try {
            observationsViewController.updateView(adapter.getDatesAndObservations(moment));
            this.observationsViewController.setViewVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementObservationCount(String observationType){
        try {
            adapter.clickObservation(observationType);
            this.updateObservationsPanel(this.lastMomentSelected);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decrementObservationCount(String observationType){

    }

    public void insertNewStudent(String student){
        try {
            adapter.createStudent(student);
            this.updateStudentsPanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNewMoment(String moment){
        try {
            adapter.createMoment(moment);
            this.updateMomentsPanel(this.lastStudentSelected);
            this.observationsViewController.setViewVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNewObservation(String date, String observationType){
        try {
            adapter.createDate(date);
            adapter.clickObservation(observationType);
            this.updateObservationsPanel(this.lastMomentSelected);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNewObservationType(String observationType){
        try {
            adapter.createObservationsType(observationType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
