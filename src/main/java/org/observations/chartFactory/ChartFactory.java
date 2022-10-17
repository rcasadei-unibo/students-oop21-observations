package org.observations.chartFactory;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

public abstract class ChartFactory {

    public static PieChart createPieChart(ObservableList<PieChart.Data> pcd) {
        pcd.forEach(data ->
                data.nameProperty()
                        .bind(Bindings
                                .concat(data.getName(), " ", data.pieValueProperty(), "")));
        return new PieChart(pcd);
    }

    public static BarChart<String, Number> createBarChart(XYChart.Series<String, Number> series) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Osservazioni");
        yAxis.setLabel("Numero di volte osservato");
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.getData().add(series);
        return chart;
    }
}
