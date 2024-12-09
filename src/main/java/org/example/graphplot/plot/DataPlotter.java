package org.example.graphplot.plot;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DataPlotter {

    public JPanel createXChartDashboard(List<String[]> data) {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        // Pie Chart
        PieChart pieChart = createPieChart(data);
        panel.add(new XChartPanel<>(pieChart));

        // Line Chart
        XYChart lineChart = createLineChart(data);
        panel.add(new XChartPanel<>(lineChart));

        // Histogram
        CategoryChart barChart = createBarChart(data);
        panel.add(new XChartPanel<>(barChart));

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

    public void displayDashboard(List<String[]> data) {
        JFrame frame = new JFrame("XChart Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createXChartDashboard(data));
        frame.pack();
        frame.setVisible(true);
    }
}
