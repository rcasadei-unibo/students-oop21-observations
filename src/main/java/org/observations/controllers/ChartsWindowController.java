package org.observations.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.stage.Window;
import org.observations.gui.chart.ChartFactory;
import org.observations.gui.chart.ChartsWindow;

import java.util.*;

public class ChartsWindowController {

    private final MainWindowController controller;
    private final ChartsWindow chartsWindow;


    public ChartsWindowController(MainWindowController controller) {
        this.controller = controller;
        this.chartsWindow = new ChartsWindow(this, controller.getStudentsList());
        this.setChart(null);
    }

    private void setChart(Object o) {


    }

    public void updateChartsWindow() {
        this.chartsWindow.setStudentSelector(this.controller.getStudentsList());
        this.chartsWindow.setMomentSelector(List.of());
        this.setChart(null);
    }

    public void setChartToPie() {
        this.chartsWindow.setChart(ChartFactory.createPieChart(this.getPieData()));
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


    public ObservableList<PieChart.Data> getPieData() {
        Optional<String> selectedStudent = this.chartsWindow.getSelectedStudent();
        Optional<String> selectedMoment = this.chartsWindow.getSelectedMoment();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        if (selectedStudent.isPresent() && selectedMoment.isPresent()) {
            Map<String, Integer> filteredData = this.filterData(this.controller.getAllData(), selectedStudent.get(), selectedMoment.get());
            for (String key : filteredData.keySet()) {
                pieData.add(new PieChart.Data(key, filteredData.get(key)));
            }
        }
        System.out.println(pieData);
        return pieData;
    }

    private Map<String, Integer> filterData(
            Map<String, Map<String, Map<String, Map<String, Integer>>>> data,
            String studentFilter,
            String momentFilter) {

        Map<String, Integer> observations = new HashMap<>();
        System.out.println(studentFilter);
        System.out.println(momentFilter);

        if (Objects.equals(momentFilter, "Tutti")) {
            for (String moment : data.get(studentFilter).keySet()) {
                for (String date : data.get(studentFilter).get(moment).keySet()) {
                    Map<String, Integer> map = data.get(studentFilter).get(moment).get(date);
                    for (String observation : map.keySet()) {
                        if(observations.containsKey(observation)){
                            observations.put(observation, map.get(observation) + observations.get(observation));
                        } else {
                            observations.put(observation, map.get(observation));
                        }
                    }
                }
            }
        } else {
            for (String date : data.get(studentFilter).get(momentFilter).keySet()) {
                Map<String, Integer> map = data.get(studentFilter).get(momentFilter).get(date);
                for (String observation : map.keySet()) {
                    if(observations.containsKey(observation)){
                        observations.put(observation, map.get(observation) + observations.get(observation));
                    } else {
                        observations.put(observation, map.get(observation));
                    }
                }
            }
        }
        return observations;
    }

    public void updateChart() {
        this.getPieData();
    }

    public void updateMomentSelector(String student) {
        this.chartsWindow.setMomentSelector(this.controller.getMomentList(student));
    }

    public Window getMainWindow() {
        return this.controller.getView().getScene().getWindow();
    }
}
