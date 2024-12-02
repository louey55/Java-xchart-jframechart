package org.example;

import org.example.graphplot.data.DataHandler;
import org.example.graphplot.data.DataCleaner;
import org.example.graphplot.analysis.DataAnalysis;
import org.example.graphplot.plot.DataPlotter;
import org.example.graphplot.plot.JFreeChartPlotter;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XYChart;
import org.jfree.chart.JFreeChart;

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

            // Analyse de base sur les données
            System.out.println("Statistiques sur les cas confirmés :");
            DataAnalysis.printStatistics(confirmedByCountry);

            // ** Tableau de bord avec XChart **
            System.out.println("Affichage des graphiques avec XChart...");
            displayXChartDashboard(confirmedByCountry, deathsByCountry, recoveredByCountry);

            // ** Tableau de bord avec JFreeChart **
            System.out.println("Affichage des graphiques avec JFreeChart...");
            displayJFreeChartDashboard(confirmedByCountry, deathsByCountry, recoveredByCountry);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tableau de bord avec XChart (graphique combiné et histogrammes)
    private static void displayXChartDashboard(double[] confirmed, double[] deaths, double[] recovered) {
        // Courbes combinées
        XYChart chartConfirmedDeaths = DataPlotter.plotCombinedTimeSeries(confirmed, deaths, "Cas Confirmés", "Décès");
        XYChart chartConfirmedRecovered = DataPlotter.plotCombinedTimeSeries(confirmed, recovered, "Cas Confirmés", "Récupérations");

        // Histogrammes
        CategoryChart histogramConfirmed = DataPlotter.plotHistogram(confirmed, "Histogramme des Cas Confirmés");
        CategoryChart histogramDeaths = DataPlotter.plotHistogram(deaths, "Histogramme des Décès");

        // Afficher les graphiques XChart dans un tableau de bord
        List<XYChart> xyCharts = new ArrayList<>();
        xyCharts.add(chartConfirmedDeaths);
        xyCharts.add(chartConfirmedRecovered);
        DataPlotter.displayXYCharts(xyCharts); // Afficher les courbes dans le tableau de bord XChart

        List<CategoryChart> categoryCharts = new ArrayList<>();
        categoryCharts.add(histogramConfirmed);
        categoryCharts.add(histogramDeaths);
        DataPlotter.displayCategoryCharts(categoryCharts); // Afficher les histogrammes dans le tableau de bord XChart
    }

    // Tableau de bord avec JFreeChart (graphique combiné et histogrammes)
    private static void displayJFreeChartDashboard(double[] confirmed, double[] deaths, double[] recovered) {
        // Courbes combinées
        JFreeChart chartConfirmedDeaths = JFreeChartPlotter.createTimeSeriesChart(
                "Cas Confirmés vs Décès", "Temps", "Valeurs", generateXData(confirmed.length), confirmed, "Cas Confirmés");
        JFreeChart chartConfirmedRecovered = JFreeChartPlotter.createTimeSeriesChart(
                "Cas Confirmés vs Récupérations", "Temps", "Valeurs", generateXData(confirmed.length), recovered, "Cas Confirmés");

        // Histogrammes
        JFreeChart histogramConfirmed = JFreeChartPlotter.createHistogram(
                "Histogramme des Cas Confirmés", "Valeur", "Fréquence", confirmed);
        JFreeChart histogramDeaths = JFreeChartPlotter.createHistogram(
                "Histogramme des Décès", "Valeur", "Fréquence", deaths);

        // Afficher les graphiques JFreeChart dans un tableau de bord séparé
        JFreeChartPlotter.displayChart(chartConfirmedDeaths); // Afficher le graphique JFreeChart des cas confirmés vs décès
        JFreeChartPlotter.displayChart(chartConfirmedRecovered); // Afficher le graphique JFreeChart des cas confirmés vs récupérations
        JFreeChartPlotter.displayChart(histogramConfirmed); // Afficher l'histogramme des cas confirmés
        JFreeChartPlotter.displayChart(histogramDeaths); // Afficher l'histogramme des décès
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
