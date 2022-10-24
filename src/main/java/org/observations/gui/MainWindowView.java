package org.observations.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.observations.controllers.MainWindowController;

public class MainWindowView {

    private static final Double LIST_BOX_SPACING = 8.0;
    private static final Double TOP_BOX_SPACING = 30.0;
    private static final String SELECTED_STUDENT_LABEL = "Studente selezionato: ";
    private static final String SELECTED_MOMENT_LABEL = "Momento selezionato: ";
    private static final String SHOW_CHART_BUTTON_MESSAGE = "Mostra grafico";
    private static final String HIDE_CHART_BUTTON_MESSAGE = "Nascondi grafico";
    private static final String PDF_BUTTON_TEXT = "Esporta data in pdf";

    private final MainWindowController controller;
    private final BorderPane view = new BorderPane();
    private final Label selectedStudent = new Label(SELECTED_STUDENT_LABEL);
    private final Label selectedMoment = new Label(SELECTED_MOMENT_LABEL);
    private final Button chartsWindowButton = new Button();

    /**
     * Creates a new view for the main window.
     *
     * @param controller        a MainWindowController as view controller.
     * @param studentPanel      a node containing the students view.
     * @param momentsPanel      a node containing the moments view.
     * @param observationsPanel a node containing the observations view.
     */
    public MainWindowView(MainWindowController controller, Node studentPanel, Node momentsPanel, Node observationsPanel) {
        this.controller = controller;

        HBox listsBox = new HBox(studentPanel, momentsPanel, observationsPanel);
        HBox topBox = new HBox(selectedStudent, selectedMoment);
        listsBox.setSpacing(LIST_BOX_SPACING);
        topBox.setSpacing(TOP_BOX_SPACING);

        view.setTop(topBox);
        view.setCenter(listsBox);

        setShowChartsButtonTextToShow();
        chartsWindowButton.setOnAction(event -> onShowChartsButton());

        Button pdfExportButton = new Button(PDF_BUTTON_TEXT);
        pdfExportButton.setOnAction(event -> onPdfExportButton());

        HBox bottomBox = new HBox(chartsWindowButton, pdfExportButton);
        bottomBox.setSpacing(LIST_BOX_SPACING);
        view.setBottom(bottomBox);
    }

    /**
     * Call controller method to export a pdf on button clicked.
     */
    private void onPdfExportButton() {
        controller.exportPdf();
    }

    /**
     * Call controller method to show charts window on button clicked.
     */
    private void onShowChartsButton() {
        controller.showChartsWindow();
    }

    /**
     * Set the show chart button to 'show charts'
     */
    public void setShowChartsButtonTextToShow() {
        chartsWindowButton.setText(SHOW_CHART_BUTTON_MESSAGE);
    }

    /**
     * Set the show chart button to 'hide charts'
     */
    public void setShowChartsButtonTextToHide() {
        chartsWindowButton.setText(HIDE_CHART_BUTTON_MESSAGE);
    }

    /**
     * Show on the user interface the student selected.
     *
     * @param student name of student.
     */
    public void setTextSelectedStudent(String student) {
        selectedStudent.setText(SELECTED_STUDENT_LABEL + student);
    }

    /**
     * Show on the user interface the moment selected.
     *
     * @param moment name of moment.
     */
    public void setTextSelectedMoment(String moment) {
        selectedMoment.setText(SELECTED_MOMENT_LABEL + moment);
    }

    /**
     * Clear off on the user interface the moment selected.
     */
    public void clearTextSelectedMoment() {
        selectedMoment.setText(SELECTED_MOMENT_LABEL);
    }

    /**
     * Return a node of the view.
     *
     * @return a node of the view.
     */
    public Node getView() {
        return view;
    }
}
