package org.example;

import org.example.graphplot.data.DataCleaner;
import org.example.graphplot.data.DataHandler;
import org.example.graphplot.plot.DataPlotter;
import org.example.graphplot.plot.JFreeChartPlotter;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.jfree.chart.JFreeChart;
import org.example.graphplot.analysis.DataAnalysis;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Charger les données brutes du fichier CSV
            String filePath = "C:\\Users\\HP\\Downloads\\corona.csv"; // Remplacer par le chemin correct
            List<String[]> rawData = DataHandler.loadCSV(filePath);

            // Nettoyer les données
            List<String[]> cleanedData = DataCleaner.cleanCSVData(rawData);

            // Extraire les données des colonnes
            double[] confirmedByCountry = DataHandler.extractColumnAsDouble(cleanedData, "Confirmed");
            double[] deathsByCountry = DataHandler.extractColumnAsDouble(cleanedData, "Deaths");
            double[] recoveredByCountry = DataHandler.extractColumnAsDouble(cleanedData, "Recovered");

            // Calculer des statistiques sur les données
            DataAnalysis.printStatistics(confirmedByCountry);
            DataAnalysis.printStatistics(deathsByCountry);
            DataAnalysis.printStatistics(recoveredByCountry);

            // Afficher le tableau de bord XChart
            displayXChartDashboard(confirmedByCountry, deathsByCountry, recoveredByCountry);

            // Afficher le tableau de bord JFreeChart
            displayJFreeChartDashboard(confirmedByCountry, deathsByCountry, recoveredByCountry);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tableau de bord avec XChart
    private static void displayXChartDashboard(double[] confirmed, double[] deaths, double[] recovered) {
        // Courbes combinées
        XYChart chartConfirmedDeaths = DataPlotter.plotCombinedTimeSeries(confirmed, deaths, "Cas Confirmés", "Décès");
        XYChart chartConfirmedRecovered = DataPlotter.plotCombinedTimeSeries(confirmed, recovered, "Cas Confirmés", "Récupérations");

        // Histogrammes
        CategoryChart histogramConfirmed = DataPlotter.plotHistogram(confirmed, "Histogramme des Cas Confirmés");
        CategoryChart histogramDeaths = DataPlotter.plotHistogram(deaths, "Histogramme des Décès");

        // Afficher dans une fenêtre Swing
        JFrame frame = new JFrame("Dashboard XChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));
        frame.add(new XChartPanel<>(chartConfirmedDeaths));
        frame.add(new XChartPanel<>(chartConfirmedRecovered));
        frame.add(new XChartPanel<>(histogramConfirmed));
        frame.add(new XChartPanel<>(histogramDeaths));
        frame.pack();
        frame.setVisible(true);
    }

    // Tableau de bord avec JFreeChart
    private static void displayJFreeChartDashboard(double[] confirmed, double[] deaths, double[] recovered) {
        List<JFreeChart> charts = new ArrayList<>();
        charts.add(JFreeChartPlotter.createTimeSeriesChart("Cas Confirmés", generateXData(confirmed.length), confirmed, "Confirmés"));
        charts.add(JFreeChartPlotter.createTimeSeriesChart("Décès", generateXData(deaths.length), deaths, "Décès"));
        charts.add(JFreeChartPlotter.createHistogram("Histogramme Cas Confirmés", confirmed, 20)); // Limiter à 20 valeurs
        charts.add(JFreeChartPlotter.createHistogram("Histogramme Décès", deaths, 20)); // Limiter à 20 valeurs

        JPanel dashboard = JFreeChartPlotter.createJFreeChartDashboard(charts);

        // Afficher dans une fenêtre Swing
        JFrame frame = new JFrame("Dashboard JFreeChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(dashboard);
        frame.pack();
        frame.setVisible(true);
    }

    // Générer les données X pour les graphiques
    private static double[] generateXData(int size) {
        double[] xData = new double[size];
        for (int i = 0; i < size; i++) {
            xData[i] = i;
        }
        return xData;
    }
}
