
package org.example;
import org.example.graphplot.analysis.DataAnalysis;
import org.example.graphplot.data.DataCleaner;
import org.example.graphplot.data.DataHandler;
import org.example.graphplot.plot.DataPlotter;
import org.example.graphplot.plot.JFreeChartPlotter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\HP\\Downloads\\corona.csv";

        // Load and clean data
        DataHandler dataHandler = new DataHandler();
        List<String[]> rawData = dataHandler.loadData(filePath);

        DataCleaner dataCleaner = new DataCleaner();
        List<String[]> cleanedData = dataCleaner.cleanAndSelectTopCountries(rawData, 20);
        // Calculer les moyennes globales pour les 20 premiers pays
        double[] globalAverages = DataAnalysis.calculateGlobalAverages(cleanedData);

        // Afficher les statistiques globales
        DataAnalysis.printGlobalStatistics(globalAverages);

        // Afficher le tableau de bord XChart avec les comparaisons
        DataPlotter dataPlotter = new DataPlotter();
        dataPlotter.displayDashboard(cleanedData, globalAverages);


        // Display JFreeChart Dashboard
        JFreeChartPlotter jFreeChartPlotter = new JFreeChartPlotter();
        jFreeChartPlotter.displayDashboard(cleanedData);
    }
}
