package org.example.graphplot.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class JFreeChartPlotter {

    // Méthode pour créer un graphique de séries temporelles
    public static JFreeChart createTimeSeriesChart(String title, String xAxisLabel, String yAxisLabel,
                                                   double[] xData, double[] yData, String seriesName) {
        XYSeries series = new XYSeries(seriesName);
        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i], yData[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset);
    }

    // Méthode pour créer un histogramme
    public static JFreeChart createHistogram(String title, String categoryAxisLabel, String valueAxisLabel,
                                             double[] data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (double value : data) {
            dataset.addValue(value, "Fréquence", String.valueOf(value));
        }

        return ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset);
    }

    // Méthode pour afficher un graphique dans une fenêtre Swing
    public static void displayChart(JFreeChart chart) {
        JFrame frame = new JFrame(chart.getTitle().getText());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
