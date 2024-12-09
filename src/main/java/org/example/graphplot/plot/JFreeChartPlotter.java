package org.example.graphplot.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JFreeChartPlotter {

    public JPanel createJFreeDashboard(List<String[]> data) {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        // Bar Chart
        JFreeChart barChart = createBarChart(data);
        panel.add(new ChartPanel(barChart));

        // Line Chart
        JFreeChart lineChart = createLineChart(data);
        panel.add(new ChartPanel(lineChart));

        // Combined Chart
        JFreeChart combinedChart = createCombinedChart(data);
        panel.add(new ChartPanel(combinedChart));

        return panel;
    }

    private JFreeChart createBarChart(List<String[]> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] row : data) {
            dataset.addValue(Double.parseDouble(row[1]), "Confirmed", row[0]);
        }
        return ChartFactory.createBarChart("Confirmed Cases", "Country", "Cases", dataset);
    }

    private JFreeChart createLineChart(List<String[]> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] row : data) {
            dataset.addValue(Double.parseDouble(row[1]), "Confirmed", row[0]);
            dataset.addValue(Double.parseDouble(row[2]), "Deaths", row[0]);
        }
        return ChartFactory.createLineChart("Confirmed vs Deaths", "Country", "Cases", dataset);
    }

    private JFreeChart createCombinedChart(List<String[]> data) {
        // Datasets
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();

        for (String[] row : data) {
            barDataset.addValue(Double.parseDouble(row[1]), "Confirmed", row[0]);
            lineDataset.addValue(Double.parseDouble(row[2]), "Deaths", row[0]);
        }

        // Bar Chart
        JFreeChart chart = ChartFactory.createBarChart("Combined Chart", "Country", "Cases", barDataset);
        CategoryPlot plot = chart.getCategoryPlot();

        // Configure Bar Renderer
        BarRenderer barRenderer = new BarRenderer();
        plot.setRenderer(0, barRenderer);

        // Add Line Chart to the plot
        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        plot.setDataset(1, lineDataset);
        plot.setRenderer(1, lineRenderer);
        plot.mapDatasetToRangeAxis(1, 0);

        // Set Rendering Order
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // Adjust Axis
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    public void displayDashboard(List<String[]> data) {
        JFrame frame = new JFrame("JFreeChart Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(createJFreeDashboard(data));
        frame.pack();
        frame.setVisible(true);
    }
}
