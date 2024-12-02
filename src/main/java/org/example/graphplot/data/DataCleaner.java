package org.example.graphplot.data;

import java.util.*;

public class DataCleaner {

    // Nettoyage des données CSV : suppression des lignes vides, suppression des doublons et normalisation
    public static List<String[]> cleanCSVData(List<String[]> data) {
        List<String[]> cleanedData = new ArrayList<>();
        Set<String> seenRows = new HashSet<>(); // Utiliser un Set pour détecter les doublons

        for (String[] row : data) {
            // Vérifier si la ligne est valide (non vide)
            boolean validRow = true;
            for (String cell : row) {
                if (cell == null || cell.trim().isEmpty()) {
                    validRow = false;
                    break;
                }
            }
            if (!validRow) {
                continue; // Ignorer les lignes vides
            }

            // Nettoyer et normaliser la ligne
            for (int i = 0; i < row.length; i++) {
                row[i] = row[i].trim(); // Supprimer les espaces superflus
                row[i] = capitalize(row[i]); // Normaliser les données (ex. : capitalisation)
            }

            // Vérifier les doublons
            String rowKey = String.join(",", row); // Utiliser une chaîne unique pour comparer les lignes
            if (!seenRows.contains(rowKey)) {
                seenRows.add(rowKey);
                cleanedData.add(row);
            }
        }

        return cleanedData;
    }

    // Méthode de normalisation : capitaliser les mots (première lettre en majuscule)
    private static String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
}
