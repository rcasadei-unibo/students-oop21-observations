package org.observations.controllers;

import javafx.scene.Node;
import javafx.stage.Window;
import org.observations.chartFactory.ChartFactory;
import org.observations.gui.chart.ChartsWindow;
import org.observations.utility.ChartDataFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller which will create and control a customized window.
 */
public class ChartsWindowController {

    private final MainWindowController controller;
    private final ChartsWindow chartsWindow;
    private Map<String, Map<String, Map<String, Map<String, Integer>>>> data;

    /**
     * Initialize and control new charts window.
     *
     * @param controller the main controller.
     * @param view the node of the main window.
     */
    public ChartsWindowController(MainWindowController controller, Node view) {
        this.controller = controller;
        chartsWindow = new ChartsWindow(this, controller.getStudentsList());
        data = controller.getAllData();
        updateChartsWindow();
    }

    /**
     * Show the charts window.
     */
    public void showWindow() {
        chartsWindow.show();
    }

    /**
     * Hide the charts window.
     */
    public void hideWindow() {
        chartsWindow.hide();
    }

    /**
     * Return boolean value if window is showed or not.
     *
     * @return
     */
    public boolean isShowing() {
        return chartsWindow.isShowing();
    }

    /**
     * Prompt the controller to get new data.
     */
    public void updateChartsWindow() {
        data = controller.getAllData();
        chartsWindow.setStudentSelector(controller.getStudentsList());
        chartsWindow.setMomentSelector(List.of());
    }

    /**
     * Update the view with an updated chart
     */
    public void updateChart() {
        setNewChart();
    }

    /**
     * Set a new chart
     */
    private void setNewChart() {

        //if user hasn't selected a specific chart type yet then create a pie type chart.
        if (chartsWindow.getSelectedChart().isPresent()) {
            switch (chartsWindow.getSelectedChart().get()) {
                case "Torta":
                    setChartToPie();
                    break;
                case "Barre":
                    setChartToBar();
                    break;
            }
        } else {
            setChartToPie();
        }
    }

    /**
     * Create and pass a new pie chart to the view to substitute the old one.
     */
    private void setChartToPie() {
        chartsWindow.setChart(
                ChartFactory.createPieChart(
                        ChartDataFilter.getPieData(
                                data,
                                chartsWindow.getSelectedStudent(),
                                chartsWindow.getSelectedMoment()
                        )));
    }

    /**
     * Create and pass a new bar chart to the view to substitute the old one.
     */
    private void setChartToBar() {
        chartsWindow.setChart(
                ChartFactory.createBarChart(
                        ChartDataFilter.getBarData(
                                data,
                                chartsWindow.getSelectedStudent(),
                                chartsWindow.getSelectedMoment()
                        )));
    }

    /**
     * Update the moment selector in the view with a new list of moments of the selected student.
     *
     * @param student Student to search moments of.
     */
    public void updateMomentSelector(Optional<String> student) {
        student.ifPresent(s -> {
            if(data.containsKey(s)){
                chartsWindow.setMomentSelector(new ArrayList<>(data.get(s).keySet()));
            }
        });
    }

    /**
     * Pass the window from the scene of the controlled view.
     *
     * @return Window of the controlled view.
     */
    public Window getMainWindow() {
        return controller.getView().getScene().getWindow();
    }
}
