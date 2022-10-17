package org.observations.gui.chart;

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

    public static BarChart<String, Integer> createBarChart(XYChart.Series<String, Integer> series) {
        //return new BarChart<String, Integer>();
        return null;
    }


}
