package org.observations.controllers;

import javafx.stage.Window;
import org.observations.chartFactory.ChartFactory;
import org.observations.gui.chart.ChartsWindow;
import org.observations.utility.ChartDataFilter;

import java.util.List;
import java.util.Map;

public class ChartsWindowController {

    private final MainWindowController controller;
    private final ChartsWindow chartsWindow;
    private Map<String, Map<String, Map<String, Map<String, Integer>>>> data;


    public ChartsWindowController(MainWindowController controller) {
        this.controller = controller;
        this.chartsWindow = new ChartsWindow(this, controller.getStudentsList());
        this.data = this.controller.getAllData();
        this.updateChartsWindow();
    }

    public void showWindow() {
        chartsWindow.show();
    }

    public void hideWindow() {
        chartsWindow.hide();
    }

    public boolean isShowing() {
        return chartsWindow.isShowing();
    }

    public void updateChartsWindow() {
        this.chartsWindow.setStudentSelector(this.controller.getStudentsList());
        this.chartsWindow.setMomentSelector(List.of());
        this.setNewChart();
    }

    public void updateChart() {
        this.setNewChart();
    }

    private void setNewChart() {
        if (!this.chartsWindow.getChartSelector().getSelectionModel().isEmpty()) {
            switch (this.chartsWindow.getChartSelector().getSelectionModel().getSelectedItem()) {
                case "Torta":
                    this.setChartToPie();
                    break;
                case "Barre":
                    this.setChartToBar();
                    break;
            }
        } else {
            this.setChartToPie();
        }
    }

    private void setChartToPie() {
        this.chartsWindow.setChart(
                ChartFactory.createPieChart(
                        ChartDataFilter.getPieData(
                                this.data,
                                this.chartsWindow.getSelectedStudent(),
                                this.chartsWindow.getSelectedMoment()
                        )));
    }

    private void setChartToBar() {
        this.chartsWindow.setChart(
                ChartFactory.createBarChart(
                        ChartDataFilter.getBarData(
                                this.data,
                                this.chartsWindow.getSelectedStudent(),
                                this.chartsWindow.getSelectedMoment()
                        )));
    }

    public void updateMomentSelector(String student) {
        this.chartsWindow.setMomentSelector(this.controller.getMomentList(student));
    }

    public Window getMainWindow() {
        return this.controller.getView().getScene().getWindow();
    }
}
