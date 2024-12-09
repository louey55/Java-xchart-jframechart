package org.example.graphplot.data;

import java.util.ArrayList;
import java.util.List;

public class DataCleaner {
    public List<String[]> cleanAndSelectTopCountries(List<String[]> rawData, int topN) {
        List<String[]> cleanedData = new ArrayList<>();
        for (String[] row : rawData) {
            try {
                int confirmed = Integer.parseInt(row[1]); // Cases confirmed
                int deaths = Integer.parseInt(row[2]);
                int recovered = Integer.parseInt(row[3]);
                if (confirmed >= 0 && deaths >= 0 && recovered >= 0) {
                    cleanedData.add(row);
                }
            } catch (NumberFormatException e) {
                // Skip invalid rows
            }
            if (cleanedData.size() == topN) break;
        }
        return cleanedData;
    }
}
