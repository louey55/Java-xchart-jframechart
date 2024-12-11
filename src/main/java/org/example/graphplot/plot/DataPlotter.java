package org.example.graphplot.plot;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DataPlotter {

    public JPanel createXChartDashboard(List<String[]> data, double[] globalAverages) {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        // Pie Chart
        PieChart pieChart = createPieChart(data);
        panel.add(new XChartPanel<>(pieChart));

        // Line Chart
        XYChart lineChart = createLineChart(data);
        panel.add(new XChartPanel<>(lineChart));

        // Bar Chart
        CategoryChart barChart = createBarChart(data);
        panel.add(new XChartPanel<>(barChart));

        // Comparison Table
        JTable comparisonTable = createComparisonTable(data, globalAverages);
        JScrollPane scrollPane = new JScrollPane(comparisonTable);
        panel.add(scrollPane);

        return panel;
    }

    private PieChart createPieChart(List<String[]> data) {
        PieChart chart = new PieChartBuilder().width(400).height(300).title("Recovered Cases").build();

        for (String[] row : data) {
            chart.addSeries(row[0], Integer.parseInt(row[3])); // Add country and recovered cases
        }
        return chart;
    }

    private XYChart createLineChart(List<String[]> data) {
        XYChart chart = new XYChartBuilder()
                .width(400)
                .height(300)
                .title("Confirmed vs Deaths")
                .xAxisTitle("Country Index")
                .yAxisTitle("Cases")
                .build();

        // Disable scientific notation for Y-axis
        chart.getStyler().setYAxisDecimalPattern("###,###");

        double[] xData = new double[data.size()];
        double[] confirmed = new double[data.size()];
        double[] deaths = new double[data.size()];

        for (int i = 0; i < data.size(); i++) {
            xData[i] = i; // Index of the country
            confirmed[i] = Double.parseDouble(data.get(i)[1]); // Confirmed cases
            deaths[i] = Double.parseDouble(data.get(i)[2]); // Deaths
        }

        chart.addSeries("Confirmed", xData, confirmed);
        chart.addSeries("Deaths", xData, deaths);

        return chart;
    }

    private CategoryChart createBarChart(List<String[]> data) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(400)
                .height(300)
                .title("Confirmed Cases by Country")
                .xAxisTitle("Country")
                .yAxisTitle("Confirmed Cases")
                .build();

        // Style adjustments
        chart.getStyler().setYAxisDecimalPattern("###,###");
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        for (String[] row : data) {
            chart.addSeries(row[0], new double[]{1}, new double[]{Double.parseDouble(row[1])}); // Add country and confirmed cases
        }

        return chart;
    }

    private JTable createComparisonTable(List<String[]> data, double[] globalAverages) {
        String[] columnNames = {"Country", "Confirmed", "Recovered", "Deaths",
                "Confirmed (Above/Below)", "Recovered (Above/Below)", "Deaths (Above/Below)"};
        Object[][] tableData = new Object[data.size()][7];

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            tableData[i][0] = row[0]; // Country
            double confirmed = Double.parseDouble(row[1]);
            double recovered = Double.parseDouble(row[3]);
            double deaths = Double.parseDouble(row[2]);

            tableData[i][1] = confirmed;
            tableData[i][2] = recovered;
            tableData[i][3] = deaths;

            tableData[i][4] = confirmed >= globalAverages[0] ? "Above" : "Below";
            tableData[i][5] = recovered >= globalAverages[1] ? "Above" : "Below";
            tableData[i][6] = deaths >= globalAverages[2] ? "Above" : "Below";
        }

        return new JTable(tableData, columnNames);
    }

    public void displayDashboard(List<String[]> data, double[] globalAverages) {
        JFrame frame = new JFrame("XChart Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createXChartDashboard(data, globalAverages));
        frame.pack();
        frame.setVisible(true);
    }
}
