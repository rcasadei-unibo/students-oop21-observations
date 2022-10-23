package org.observations.controllers;

import javafx.scene.Node;
import org.observations.gui.MainWindowView;
import org.observations.model.ModelAdapter;
import org.observations.model.core.ModelAdapterImpl;
import org.observations.utility.PdfExporter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main controller which manage the main window and all the subcontrollers attached
 * and act as mediator between controllers classes and model classes.
 */
public class MainWindowController {
    private final ModelAdapter adapter;
    private final MainWindowView view;
    private final SubController<String, List<String>, String> studentsViewController;
    private final SubController<String, List<String>, String> momentsViewController;
    private final SubController<String, Map<String, Map<String, Integer>>, List<String>> observationsViewController;

    private ChartsWindowController chartsWindowController;
    private String lastStudentSelected = "";
    private String lastMomentSelected = "";

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
        this.observationsViewController = new ObservationsViewController(this, observationTypesList);

        this.momentsViewController.setViewVisible(false);
        this.observationsViewController.setViewVisible(false);

        this.updateStudentsPanel();
        this.view = new MainWindowView(this, this.studentsViewController.getView(), this.momentsViewController.getView(), this.observationsViewController.getView());
    }

    /**
     * Initialize the charts window.
     */
    public void initializeChartsWindowController() {
        if (chartsWindowController == null) {
            this.chartsWindowController = new ChartsWindowController(this, this.view.getView());
        }
    }

    /**
     * Get main window node.
     *
     * @return
     */
    public Node getView() {
        return view.getView();
    }

    /**
     * Get list of all the students.
     *
     * @return a list of all the students' names.
     */
    public List<String> getStudentsList() {
        try {
            return this.adapter.getStudentsList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get list of moments of a student.
     *
     * @param student name of student to search moments of.
     * @return a list of all the moments of a student.
     */
    public List<String> getMomentList(String student) {
        if (student.isEmpty()) {
            return List.of();
        }
        try {
            return this.adapter.getMomentsList(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a map of all dates, with all the observations and relative count, of a given moment.
     *
     * @param moment the moment at which observations are related to.
     * @return a map of every date and all observations of that given date.
     */
    public Map<String, Map<String, Integer>> getDateAndObservationsList(String moment) {
        if (moment.isEmpty()) {
            return Map.of();
        }
        try {
            return this.adapter.getDatesAndObservations(moment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the student view with a new list of all the students.
     */
    private void updateStudentsPanel() {
        studentsViewController.updateView(this.getStudentsList());
    }

    /**
     * Update the moment view with a list of all the moment of a given student.
     *
     * @param student name of student to search moments of.
     */
    void updateMomentsPanel(String student) {
        this.lastStudentSelected = student;
        this.view.clearTextSelectedMoment();
        this.view.setTextSelectedStudent(student);
        this.momentsViewController.updateView(this.getMomentList(student));
        this.momentsViewController.setViewVisible(true);
        this.observationsViewController.setViewVisible(false);
    }

    /**
     * Update the observation view with a list of all the observation of a given moment.
     *
     * @param moment moment to search observations of.
     */
    void updateObservationsPanel(String moment) {
        this.lastMomentSelected = moment;
        this.view.setTextSelectedMoment(moment);
        this.observationsViewController.updateView(this.getDateAndObservationsList(moment));
        this.observationsViewController.setViewVisible(true);
    }

    /**
     * Create observation if missing or increment the count of a given observation.
     *
     * @param date            dd-mm-yyyy format.
     * @param observationType name of observation.
     */
    void incrementObservationCount(String date, String observationType) {
        try {
            adapter.createDate(date);
            adapter.clickObservation(observationType);
            this.updateObservationsPanel(this.lastMomentSelected);
            this.chartsWindowController.updateChartsWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decrement the count of a given observation.
     *
     * @param observationType name of observation.
     * @param activity
     */
    void decrementObservationCount(String observationType, String activity) {

    }

    /**
     * Insert a new student
     *
     * @param student full name of student
     */
    public void insertNewStudent(String student) {
        try {
            adapter.createStudent(student);
            this.updateStudentsPanel();
            this.chartsWindowController.updateChartsWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert a new moment or create a new one if it doesn't exist.
     *
     * @param moment name of moment.
     */
    public List<String> insertNewMoment(String moment) {
        try {
            this.adapter.createMoment(moment);
            this.updateMomentsPanel(this.lastStudentSelected);
            this.observationsViewController.setViewVisible(false);
            this.chartsWindowController.updateChartsWindow();
            return this.adapter.getMomentsListFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert a new observation type.
     *
     * @param observationType name of new type.
     */
    public List<String> insertNewObservationType(String observationType) {
        try {
            adapter.createObservationsType(observationType);
            return adapter.getTypesListFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show or hide the charts window.
     */
    public void showChartsWindow() {
        if (!chartsWindowController.isShowing()) {
            this.chartsWindowController.showWindow();
        } else {
            this.chartsWindowController.hideWindow();
        }
    }

    /**
     * Get all the student with relative moments, dates, observations and observations' count.
     *
     * @return map of all data.
     */
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

            //Necessary to not brick model.saved class
            this.getMomentList(this.lastStudentSelected);
            this.getDateAndObservationsList(this.lastMomentSelected);
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Collections.unmodifiableMap(data);
    }

    public void exportPdf(){
        PdfExporter pdfExporter = new PdfExporter();
        pdfExporter.exportPdf(this.getAllData());
    }
}
