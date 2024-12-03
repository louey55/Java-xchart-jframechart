package org.example.graphplot.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    public static List<String[]> loadCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        }
        return data;
    }

    public static List<String[]> cleanCSVData(List<String[]> rawData) {
        List<String[]> cleanedData = new ArrayList<>();
        for (String[] row : rawData) {
            String[] cleanedRow = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                cleanedRow[i] = row[i].trim();
            }
            cleanedData.add(cleanedRow);
        }
        return cleanedData;
    }

    public static double[] extractColumnAsDouble(List<String[]> data, String columnName) {
        columnName = columnName.trim().toLowerCase();
        int columnIndex = -1;
        String[] header = data.get(0);

        for (int i = 0; i < header.length; i++) {
            if (header[i].trim().toLowerCase().equals(columnName)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex == -1) {
            throw new IllegalArgumentException("La colonne " + columnName + " n'a pas été trouvée.");
        }

        double[] columnData = new double[data.size() - 1];
        for (int i = 1; i < data.size(); i++) {
            String value = data.get(i)[columnIndex].trim();
            try {
                columnData[i - 1] = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                columnData[i - 1] = 0.0;
            }
        }
        return columnData;
    }

    public static double[] normalizeData(double[] data, double divisor) {
        double[] normalizedData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalizedData[i] = data[i] / divisor;
        }
        return normalizedData;
    }
}
