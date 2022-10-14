package org.observations.gui.chart;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public abstract class ChartFactory {

    public static PieChart createPieChart(ObservableList<PieChart.Data> pcd) {
        pcd.forEach(data ->
                data.nameProperty()
                        .bind(Bindings
                                .concat(data.getName(), " ", data.pieValueProperty(), "")));
        return new PieChart(pcd);
    }


}
