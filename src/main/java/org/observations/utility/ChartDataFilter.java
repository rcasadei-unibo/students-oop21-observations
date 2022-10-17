package org.observations.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChartDataFilter {
    private static final String ALL = "Tutti";

    public static ObservableList<PieChart.Data> getPieData(
            Map<String, Map<String, Map<String, Map<String, Integer>>>> data,
            Optional<String> student,
            Optional<String> moment) {

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        if (student.isPresent() && moment.isPresent()) {
            Map<String, Integer> filteredData;
            if (moment.get().equals(ALL)) {
                filteredData = filterDataByStudent(data, student.get());
            } else {
                filteredData = filterDataByStudentAndMoment(data, student.get(), moment.get());
            }

            for (String key : filteredData.keySet()) {
                pieData.add(new PieChart.Data(key, filteredData.get(key)));
            }
        }

        System.out.println(pieData);
        return pieData;
    }

    public static XYChart.Series<String, Number> getBarData(
            Map<String, Map<String, Map<String, Map<String, Integer>>>> data,
            Optional<String> student,
            Optional<String> moment) {

        XYChart.Series<String, Number> barData = new XYChart.Series<>();

        if (student.isPresent() && moment.isPresent()) {
            Map<String, Integer> filteredData;
            if (moment.get().equals(ALL)) {
                filteredData = filterDataByStudent(data, student.get());
            } else {
                filteredData = filterDataByStudentAndMoment(data, student.get(), moment.get());
            }

            for (String key : filteredData.keySet()) {
                barData.getData().add(new XYChart.Data<>(key, filteredData.get(key)));
            }
        }

        System.out.println(barData);
        return barData;
    }

    public static Map<String, Integer> filterDataByStudent(
            Map<String, Map<String, Map<String, Map<String, Integer>>>> data,
            String studentFilter) {

        if (data.isEmpty()) {
            return Map.of();
        } else {
            Map<String, Integer> observations = new HashMap<>();
            for (String moment : data.get(studentFilter).keySet()) {
                for (String date : data.get(studentFilter).get(moment).keySet()) {
                    Map<String, Integer> map = data.get(studentFilter).get(moment).get(date);
                    for (String observation : map.keySet()) {
                        if (observations.containsKey(observation)) {
                            observations.put(observation, map.get(observation) + observations.get(observation));
                        } else {
                            observations.put(observation, map.get(observation));
                        }
                    }
                }
            }
            return observations;
        }
    }

    public static Map<String, Integer> filterDataByStudentAndMoment(
            Map<String, Map<String, Map<String, Map<String, Integer>>>> data,
            String studentFilter,
            String momentFilter) {

        if (data.isEmpty()) {
            return Map.of();
        } else {
            Map<String, Integer> observations = new HashMap<>();
            for (String date : data.get(studentFilter).get(momentFilter).keySet()) {
                Map<String, Integer> map = data.get(studentFilter).get(momentFilter).get(date);
                for (String observation : map.keySet()) {
                    if (observations.containsKey(observation)) {
                        observations.put(observation, map.get(observation) + observations.get(observation));
                    } else {
                        observations.put(observation, map.get(observation));
                    }
                }
            }
            return observations;
        }
    }
}
