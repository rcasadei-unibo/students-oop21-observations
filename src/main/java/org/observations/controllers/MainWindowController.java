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
 * Main controller which manage the main window and all the SubControllers attached
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

    /**
     * Initialize the main window.
     */
    public MainWindowController() {

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
        observationsViewController = new ObservationsViewController(this, observationTypesList);

        momentsViewController.setViewVisible(false);
        observationsViewController.setViewVisible(false);

        //initialize the student list with a list of students saved on local (if present).
        updateStudentsPanel();
        view = new MainWindowView(this, studentsViewController.getView(), momentsViewController.getView(), observationsViewController.getView());
    }

    /**
     * Initialize the charts window.
     */
    public void initializeChartsWindowController() {
        if (chartsWindowController == null) {
            chartsWindowController = new ChartsWindowController(this, view.getView());
        }
    }

    /**
     * Get main window node.
     *
     * @return a node containing the MainWindowView.
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
            return adapter.getStudentsList();
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
            return adapter.getMomentsList(student);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a map of all dates, with all the observations and relative count, of a given moment.
     *
     * @param moment the moment at which observations are related to.
     * @return a map of every date, each date having a map observations and their relative counters.
     */
    public Map<String, Map<String, Integer>> getDateAndObservationsList(String moment) {
        if (moment.isEmpty()) {
            return Map.of();
        }
        try {
            return adapter.getDatesAndObservations(moment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the student view with a new list of all the students.
     */
    private void updateStudentsPanel() {
        studentsViewController.updateView(getStudentsList());
    }

    /**
     * Update the moment view with a list of all the moment of a given student.
     *
     * @param student name of student to search moments of.
     */
    void updateMomentsPanel(String student) {
        lastStudentSelected = student;
        view.clearTextSelectedMoment();
        view.setTextSelectedStudent(student);
        momentsViewController.updateView(getMomentList(student));
        momentsViewController.setViewVisible(true);
        observationsViewController.setViewVisible(false);
    }

    /**
     * Update the observation view with a list of all the observation of a given moment.
     *
     * @param moment moment to search observations of.
     */
    void updateObservationsPanel(String moment) {
        lastMomentSelected = moment;
        view.setTextSelectedMoment(moment);
        observationsViewController.updateView(getDateAndObservationsList(moment));
        observationsViewController.setViewVisible(true);
    }

    /**
     * Create observation if missing or increment the count of a given observation.
     *
     * @param date            a date of dd-mm-yyyy format.
     * @param observationType name of observation.
     */
    void incrementObservationCount(String date, String observationType) {
        try {
            adapter.createDate(date);
            adapter.clickObservation(observationType);
            updateObservationsPanel(lastMomentSelected);
            chartsWindowController.updateChartsWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert a new student and refresh the student view.
     *
     * @param student full name of student
     */
    public void insertNewStudent(String student) {
        try {
            adapter.createStudent(student);
            updateStudentsPanel();
            chartsWindowController.updateChartsWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Insert a new moment or create a new one if it doesn't exist.
     * It also adds it to the last selected student's list of moments. 
     *
     * @param moment name of moment.
     */
    public List<String> insertNewMoment(String moment) {
        try {
            adapter.createMoment(moment);
            updateMomentsPanel(lastStudentSelected);
            observationsViewController.setViewVisible(false);
            chartsWindowController.updateChartsWindow();
            return adapter.getMomentsListFromFile();
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
            view.setShowChartsButtonTextToHide();
            chartsWindowController.showWindow();
        } else {
            view.setShowChartsButtonTextToShow();
            chartsWindowController.hideWindow();
        }
    }

    /**
     * Get all the student with relative moments, dates, observations and observations' count.
     *
     * @return a map containing all students, a map of their saved moments, each containing a map of saved dates, every date having a map of observations and relative counters.
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
            getMomentList(lastStudentSelected);
            getDateAndObservationsList(lastMomentSelected);
            //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Collections.unmodifiableMap(data);
    }

    /**
     * Create (or substitute if already present) a pdf file containing all the saved data of students, their moments, dates and observations
     */
    public void exportPdf() {
        PdfExporter pdfExporter = new PdfExporter();
        pdfExporter.exportPdf(getAllData());
    }
}
