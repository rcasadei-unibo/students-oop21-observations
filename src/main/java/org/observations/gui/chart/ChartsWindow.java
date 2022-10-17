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

public class ChartsWindow extends Stage {

    private final ChartsWindowController controller;
    private final BorderPane view = new BorderPane();
    private Chart chart;

    private final ComboBox<String> studentSelector = new ComboBox<>();
    private final ComboBox<String> momentSelector = new ComboBox<>();
    private final ComboBox<String> chartSelector = new ComboBox<>();
    private final Button refreshButton = new Button("Aggiorna grafico");

    public ChartsWindow(ChartsWindowController controller, List<String> studentList) {
        this.controller = controller;
        this.initOwner(controller.getMainWindow());
        this.view.setTop(new HBox(
                new Label("Studente:"),
                studentSelector,
                new Label("Momento:"),
                momentSelector,
                new Label("Grafico:"),
                chartSelector));
        this.view.setBottom(refreshButton);

        this.studentSelector.getItems().addAll(studentList);
        this.momentSelector.getItems().add("Tutti");
        this.chartSelector.getItems().addAll("Torta", "Barre");

        this.studentSelector.valueProperty().addListener((observable, oldValue, newValue) ->{
                this.controller.updateMomentSelector(this.studentSelector.getSelectionModel().getSelectedItem());
                this.controller.updateChart();
        });

        this.momentSelector.valueProperty().addListener((observable, oldValue, newValue) -> this.controller.updateChart());

        this.chartSelector.valueProperty().addListener((observable, oldValue, newValue) -> this.controller.updateChart());

        this.refreshButton.setOnAction(event -> this.controller.updateChart());
        this.setScene(new Scene(view, 500, 400));
    }

    public ComboBox<String> getStudentSelector() {
        return this.studentSelector;
    }

    public ComboBox<String> getMomentSelector() {
        return this.momentSelector;
    }

    public ComboBox<String> getChartSelector() {
        return this.chartSelector;
    }

    public void setStudentSelector(List<String> studentList) {
        this.studentSelector.getItems().clear();
        this.studentSelector.getItems().addAll(studentList);
    }

    public void setMomentSelector(List<String> momentList) {
        this.momentSelector.getItems().clear();
        this.momentSelector.getItems().addAll("Tutti");
        this.momentSelector.getItems().addAll(momentList);
    }

    public Optional<String> getSelectedStudent() {
        return Optional.ofNullable(this.studentSelector.getSelectionModel().getSelectedItem());
    }

    public Optional<String> getSelectedMoment() {
        return Optional.ofNullable(this.momentSelector.getSelectionModel().getSelectedItem());
    }

    public void setChart(Chart chart) {
        this.chart = chart;
        this.view.setCenter(chart);
    }


}
