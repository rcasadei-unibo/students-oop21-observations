package org.observations.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class used for filter data received and/or reorganize it in a compatible format for a PieChart or a BarChart.
 */
public class ChartDataFilter {
    private static final String ALL = "Tutti";

    /**
     * Return filtered data in an ObservableList containing PieChart.Data from raw data
     *
     * @param data    Map containing all or a single student and all their
     *                relative moments and observations.
     * @param student Name of student to search content of.
     * @param moment  Name of moment to search content of or to search
     *                all moments available.
     * @return an ObservableList of PieChart.Data containing all the observations found and the relative number of observations.
     */
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
        return pieData;
    }

    /**
     * Return filtered data in XYChart.Series from raw data
     *
     * @param data    Map containing all or a single student and all their
     *                relative moments and observations.
     * @param student Name of student to search content of.
     * @param moment  Name of moment to search content of or to search
     *                all moments available.
     * @return an XYChart.Series containing all the observations found and the relative number of observations.
     */
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
        return barData;
    }

    /**
     * Search all the observations and their counts of a specific student
     *
     * @param data          Map containing all or a single student and all their
     *                      relative moments and observations.
     * @param studentFilter Student to search content of.
     * @return A map of all observations found and their counts.
     */
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

    /**
     * Search all the observations and their counts of a specific student
     *
     * @param data          Map containing all or a single student and all their
     *                      relative moments and observations.
     * @param studentFilter Student to search content of.
     * @param momentFilter  Moment to search content of.
     * @return A map of all observations found and their counts.
     */
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
