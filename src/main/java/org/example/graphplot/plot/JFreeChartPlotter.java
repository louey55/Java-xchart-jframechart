package org.example.graphplot.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JFreeChartPlotter {

    // Créer un tableau de bord contenant plusieurs graphiques JFreeChart
    public static JPanel createJFreeChartDashboard(List<JFreeChart> charts) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); // 2 colonnes pour l'affichage
        for (JFreeChart chart : charts) {
            ChartPanel chartPanel = new ChartPanel(chart);
            panel.add(chartPanel);
        }
        return panel;
    }

    // Créer un graphique de séries temporelles
    public static JFreeChart createTimeSeriesChart(String title, double[] xData, double[] yData, String seriesName) {
        XYSeries series = new XYSeries(seriesName);
        for (int i = 0; i < xData.length; i++) {
            series.add(xData[i], yData[i]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        return ChartFactory.createXYLineChart(title, "Temps", "Valeurs", dataset);
    }

    // Créer un histogramme en limitant les valeurs pour éviter une surcharge
    public static JFreeChart createHistogram(String title, double[] data, int maxEntries) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double[] sampledData = sampleData(data, maxEntries);
        for (double value : sampledData) {
            dataset.addValue(value, "Valeur", String.valueOf(value));
        }
        return ChartFactory.createBarChart(title, "Valeurs", "Fréquence", dataset);
    }

    // Méthode pour échantillonner les données
    private static double[] sampleData(double[] data, int maxEntries) {
        if (data.length <= maxEntries) {
            return data; // Pas besoin d'échantillonnage
        }
        double[] sampledData = new double[maxEntries];
        int step = data.length / maxEntries; // Écart entre les échantillons
        for (int i = 0; i < maxEntries; i++) {
            sampledData[i] = data[i * step];
        }
        return sampledData;
    }
}
