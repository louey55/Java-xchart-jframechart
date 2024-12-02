package org.example.graphplot.analysis;

import java.util.Arrays;

public class DataAnalysis {

    // Calcul de la moyenne
    public static double calculateMean(double[] data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.length;
    }

    // Calcul de l'écart-type
    public static double calculateStandardDeviation(double[] data) {
        double mean = calculateMean(data);
        double sum = 0.0;
        for (double value : data) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / data.length);
    }

    // Méthode pour trier les données et obtenir la médiane
    public static double calculateMedian(double[] data) {
        if (data.length == 0) {
            throw new IllegalArgumentException("Le tableau ne peut pas être vide");
        }

        Arrays.sort(data);
        int middle = data.length / 2;
        if (data.length % 2 == 0) {
            return (data[middle - 1] + data[middle]) / 2.0;
        } else {
            return data[middle];
        }
    }

    // Affichage des résultats statistiques
    public static void printStatistics(double[] data) {
        if (data.length == 0) {
            System.err.println("Les données sont vides, impossible de calculer les statistiques.");
            return;
        }
        System.out.println("Moyenne: " + calculateMean(data));
        System.out.println("Médiane: " + calculateMedian(data));
        System.out.println("Ecart-Type: " + calculateStandardDeviation(data));
    }
}
