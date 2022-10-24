package org.observations.gui.chart;

import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.observations.controllers.ChartsWindowController;

import java.util.List;
import java.util.Optional;

/**
 * Custom window which can contain a chart and controller to filter data to show.
 */
public class ChartsWindow extends Stage {

    private static final String[] CHARTS_TYPES = {"Torta", "Barre"};
    private final ChartsWindowController controller;
    private final BorderPane view = new BorderPane();
    private Chart chart;

    private final ComboBox<String> chartSelector = new ComboBox<>();
    private final ComboBox<String> studentSelector = new ComboBox<>();
    private final ComboBox<String> momentSelector = new ComboBox<>();
    private final Button refreshButton = new Button("Aggiorna grafico");

    /**
     * Creates a new view for the charts window.
     *
     * @param controller the charts window controller.
     * @param studentList a list containing the names of students.
     */
    public ChartsWindow(ChartsWindowController controller, List<String> studentList) {
        this.controller = controller;
        initOwner(controller.getMainWindow());
        view.setTop(new HBox(
                new Label("Grafico:"),
                chartSelector,
                new Label("Studente:"),
                studentSelector,
                new Label("Momento:"),
                momentSelector
        ));
        view.setBottom(refreshButton);

        chartSelector.getItems().addAll(CHARTS_TYPES);
        studentSelector.getItems().addAll(studentList);
        momentSelector.getItems().add("Tutti");

        studentSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
            controller.updateMomentSelector(Optional.ofNullable(studentSelector.getSelectionModel().getSelectedItem()));
            controller.updateChart();
        });

        momentSelector.valueProperty().addListener((observable, oldValue, newValue) -> controller.updateChart());

        chartSelector.valueProperty().addListener((observable, oldValue, newValue) -> controller.updateChart());

        refreshButton.setOnAction(event -> controller.updateChart());
        setScene(new Scene(view, 500, 400));
    }

    /**
     * Set list of student selector
     *
     * @param studentList list of students
     */
    public void setStudentSelector(List<String> studentList) {
        studentSelector.getItems().clear();
        studentSelector.getItems().addAll(studentList);
    }

    /**
     * Set list of moment selector
     *
     * @param momentList list of moments
     */
    public void setMomentSelector(List<String> momentList) {
        momentSelector.getItems().clear();
        momentSelector.getItems().addAll("Tutti");
        momentSelector.getItems().addAll(momentList);
    }

    /**
     * Return an optional of selected chart
     *
     * @return an optional string containing a chart type name.
     */
    public Optional<String> getSelectedChart() {
        return Optional.ofNullable(chartSelector.getSelectionModel().getSelectedItem());
    }

    /**
     * Return an optional of selected student.
     *
     * @return an optional string containing a student name.
     */
    public Optional<String> getSelectedStudent() {
        return Optional.ofNullable(studentSelector.getSelectionModel().getSelectedItem());
    }

    /**
     * Return an optional selected moment.
     *
     * @return an optional string containing a moment name.
     */
    public Optional<String> getSelectedMoment() {
        return Optional.ofNullable(momentSelector.getSelectionModel().getSelectedItem());
    }

    /**
     * Set new chart for the view.
     *
     * @param chart chart to set.
     */
    public void setChart(Chart chart) {
        this.chart = chart;
        view.setCenter(chart);
    }
}
