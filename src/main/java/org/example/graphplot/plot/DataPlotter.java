package org.example.graphplot.plot;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import javax.swing.*;
import java.util.*;

public class DataPlotter {

    // Tracer un graphique combiné (time series) pour plusieurs séries de données
    public static XYChart plotCombinedTimeSeries(double[] yData1, double[] yData2, String title1, String title2) {
        // Créer un graphique
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Comparaison de " + title1 + " et " + title2)
                .xAxisTitle("Temps")
                .yAxisTitle("Valeurs")
                .build();

        // Générer un axe X (simple index pour le temps)
        double[] xData = new double[yData1.length];
        for (int i = 0; i < yData1.length; i++) {
            xData[i] = i;
        }

        // Ajouter les séries de données au graphique
        chart.addSeries(title1, xData, yData1);
        chart.addSeries(title2, xData, yData2);

        // Personnaliser le graphique
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setMarkerSize(6);

        return chart;
    }

    // Tracer un graphique en histogramme des données
    public static CategoryChart plotHistogram(double[] yData, String title) {
        // Calculer la fréquence des valeurs
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : yData) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        // Extraire les clés et valeurs pour l'affichage
        List<Double> values = new ArrayList<>(frequencyMap.keySet());
        List<Integer> frequencies = new ArrayList<>(frequencyMap.values());

        // Créer un graphique
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(title).xAxisTitle("Valeur").yAxisTitle("Fréquence").build();

        // Ajouter les données au graphique
        chart.addSeries("Fréquence", values, frequencies);

        return chart;
    }

    // Afficher les graphiques XYChart dans une fenêtre
    public static void displayXYCharts(List<XYChart> timeSeriesCharts) {
        for (XYChart chart : timeSeriesCharts) {
            // Créer un wrapper Swing pour afficher chaque graphique dans une nouvelle fenêtre
            new SwingWrapper<>(chart).displayChart();
        }
    }

    // Afficher les graphiques CategoryChart dans une fenêtre
    public static void displayCategoryCharts(List<CategoryChart> histograms) {
        for (CategoryChart histogram : histograms) {
            // Créer un wrapper Swing pour afficher chaque histogramme dans une nouvelle fenêtre
            new SwingWrapper<>(histogram).displayChart();
        }
    }
}
