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
    private final MainWindowController controller;
    private final BorderPane view = new BorderPane();
    private final Label selectedStudent = new Label(SELECTED_STUDENT_LABEL);
    private final Label selectedMoment = new Label(SELECTED_MOMENT_LABEL);

    public MainWindowView(MainWindowController controller, Node studentPanel, Node momentsPanel, Node observationsPanel) {
        this.controller = controller;

        HBox listsBox = new HBox(studentPanel, momentsPanel, observationsPanel);
        HBox topBox = new HBox(selectedStudent, selectedMoment);
        listsBox.setSpacing(LIST_BOX_SPACING);
        topBox.setSpacing(TOP_BOX_SPACING);

        this.view.setTop(topBox);
        this.view.setCenter(listsBox);

        Button chartsWindowButton = new Button("Mostra grafico");
        chartsWindowButton.setOnAction(event -> this.onShowChartsButton());

        Button pdfExportButton = new Button("Esporta data in pdf");
        pdfExportButton.setOnAction(event -> this.onPdfExportButton());

        HBox bottomBox = new HBox(chartsWindowButton, pdfExportButton);
        bottomBox.setSpacing(LIST_BOX_SPACING);
        this.view.setBottom(bottomBox);
    }

    private void onPdfExportButton() {
        this.controller.exportPdf();
    }

    private void onShowChartsButton() {
        this.controller.showChartsWindow();
    }

    public void setTextSelectedStudent(String student) {
        this.selectedStudent.setText(SELECTED_STUDENT_LABEL + student);
    }

    public void setTextSelectedMoment(String moment) {
        this.selectedMoment.setText(SELECTED_MOMENT_LABEL + moment);
    }

    public void clearTextSelectedMoment() {
        this.selectedMoment.setText(SELECTED_MOMENT_LABEL);
    }

    public Node getView() {
        return view;
    }
}
