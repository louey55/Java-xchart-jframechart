package org.example.graphplot.analysis;

import java.util.List;

public class DataAnalysis {

    // Calcul de la moyenne globale pour les 20 premiers pays (Confirmed, Recovery, Deaths)
    public static double[] calculateGlobalAverages(List<String[]> data) {
        double totalConfirmed = 0;
        double totalRecovered = 0;
        double totalDeaths = 0;
        int count = Math.min(20, data.size()); // Limiter aux 20 premiers pays

        for (int i = 0; i < count; i++) {
            String[] row = data.get(i);
            try {
                totalConfirmed += Double.parseDouble(row[1]); // Confirmed cases
                totalRecovered += Double.parseDouble(row[3]); // Recovered cases
                totalDeaths += Double.parseDouble(row[2]); // Deaths
            } catch (NumberFormatException e) {
                System.err.println("Erreur dans les données pour le pays : " + row[0]);
            }
        }

        // Calcul des moyennes
        double averageConfirmed = totalConfirmed / count;
        double averageRecovered = totalRecovered / count;
        double averageDeaths = totalDeaths / count;

        return new double[]{averageConfirmed, averageRecovered, averageDeaths};
    }

    // Préparation des données pour les graphiques (Confirmed, Recovery et Death)
    public static double[][] prepareChartData(double[] globalAverages) {
        double[] confirmedAverage = {globalAverages[0]};
        double[] recoveryAverage = {globalAverages[1]};
        double[] deathAverage = {globalAverages[2]};
        return new double[][]{confirmedAverage, recoveryAverage, deathAverage};
    }

    // Affichage des statistiques globales
    public static void printGlobalStatistics(double[] globalAverages) {
        System.out.printf("Statistiques globales pour les 20 premiers pays :%n");
        System.out.printf("Moyenne des cas confirmés : %.2f%n", globalAverages[0]);
        System.out.printf("Moyenne des récupérations : %.2f%n", globalAverages[1]);
        System.out.printf("Moyenne des décès : %.2f%n", globalAverages[2]);
    }
}
