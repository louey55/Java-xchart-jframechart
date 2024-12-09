package org.example;

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

        // Display XChart Dashboard
        DataPlotter dataPlotter = new DataPlotter();
        dataPlotter.displayDashboard(cleanedData);

        // Display JFreeChart Dashboard
        JFreeChartPlotter jFreeChartPlotter = new JFreeChartPlotter();
        jFreeChartPlotter.displayDashboard(cleanedData);
    }
}
