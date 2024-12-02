package org.example.graphplot.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    // Charger un fichier CSV et retourner les données sous forme de liste de tableaux de chaînes
    public static List<String[]> loadCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Diviser chaque ligne par la virgule et ajouter les éléments à la liste
                String[] row = line.split(",");
                data.add(row);
            }
        }
        return data;
    }

    // Nettoyer les données de la première ligne et supprimer les espaces autour des valeurs
    public static List<String[]> cleanCSVData(List<String[]> rawData) {
        List<String[]> cleanedData = new ArrayList<>();

        // Nettoyage des colonnes en enlevant les espaces
        for (String[] row : rawData) {
            String[] cleanedRow = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                cleanedRow[i] = row[i].trim();  // Enlever les espaces avant et après
            }
            cleanedData.add(cleanedRow);
        }
        return cleanedData;
    }

    // Extraire une colonne spécifique sous forme de tableau de double
    public static double[] extractColumnAsDouble(List<String[]> data, String columnName) {
        // Nettoyer le nom de la colonne : enlever les espaces et mettre en minuscule
        columnName = columnName.trim().toLowerCase();

        // Trouver l'index de la colonne correspondant au nom nettoyé
        int columnIndex = -1;
        String[] header = data.get(0);

        for (int i = 0; i < header.length; i++) {
            // Nettoyer le nom de chaque colonne dans le fichier
            if (header[i].trim().toLowerCase().equals(columnName)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex == -1) {
            throw new IllegalArgumentException("La colonne " + columnName + " n'a pas été trouvée.");
        }

        // Extraire les données de la colonne
        double[] columnData = new double[data.size() - 1];  // Ignorer la première ligne (entêtes)
        for (int i = 1; i < data.size(); i++) {
            String value = data.get(i)[columnIndex].trim();
            // Vérifier si la valeur peut être convertie en double
            try {
                columnData[i - 1] = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                columnData[i - 1] = 0.0;  // Si la valeur ne peut pas être convertie, mettre 0
            }
        }
        return columnData;
    }
}
