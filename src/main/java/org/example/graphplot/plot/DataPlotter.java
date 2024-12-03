package org.example.graphplot.plot;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DataPlotter {

    public static JPanel createXChartDashboard(List<XYChart> xyCharts, List<CategoryChart> histograms) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (XYChart xyChart : xyCharts) {
            panel.add(new XChartPanel<>(xyChart));
        }
        for (CategoryChart histogram : histograms) {
            panel.add(new XChartPanel<>(histogram));
        }

        return panel;
    }

    public static XYChart plotCombinedTimeSeries(double[] yData1, double[] yData2, String title1, String title2) {
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Comparaison de " + title1 + " et " + title2)
                .xAxisTitle("Temps")
                .yAxisTitle("Valeurs")
                .build();

        double[] xData = new double[yData1.length];
        for (int i = 0; i < yData1.length; i++) {
            xData[i] = i;
        }

        chart.addSeries(title1, xData, yData1);
        chart.addSeries(title2, xData, yData2);

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        return chart;
    }

    public static CategoryChart plotHistogram(double[] yData, String title) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title(title)
                .xAxisTitle("Intervalles")
                .yAxisTitle("Fréquence")
                .build();

        // Réduire les données en regroupant par intervalles
        List<String> intervals = new ArrayList<>();
        List<Integer> frequencies = new ArrayList<>();
        int intervalSize = 10; // Taille des intervalles

        for (double value : yData) {
            double intervalStart = Math.floor(value / intervalSize) * intervalSize;
            double intervalEnd = intervalStart + intervalSize;
            String intervalLabel = String.format("%.0f-%.0f", intervalStart, intervalEnd);
            int index = intervals.indexOf(intervalLabel);
            if (index == -1) {
                intervals.add(intervalLabel);
                frequencies.add(1);
            } else {
                frequencies.set(index, frequencies.get(index) + 1);
            }
        }

        chart.addSeries("Valeurs", intervals, frequencies);

        // Personnaliser le style pour améliorer la lisibilité
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAxisTickLabelsFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        chart.getStyler().setXAxisLabelRotation(45); // Rotation des étiquettes pour éviter le chevauchement
        chart.getStyler().setHasAnnotations(true);

        return chart;
    }
}
