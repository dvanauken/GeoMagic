package com.dbva;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

public class App extends JFrame {

  private PolylineProcessor processor = new PolylineProcessor();

  public App() {
    List<LineSegment> lines = null;
    try (InputStream is = getClass().getResourceAsStream("/input.txt")) {
      lines = processor.readLines(is);
      lines.forEach(line -> System.out.println(line));
    } catch (IOException e) {
      System.err.println("Failed to read lines: " + e.getMessage());
    }

    processor.buildGraph(lines);
    List<List<Point>> polylines = processor.findPolylines();
    polylines.sort(Comparator.comparingDouble(processor::calculateLength).reversed());

    XYSeriesCollection dataset = processor.createDataset(polylines);
    JFreeChart chart = processor.createChart(dataset);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 600));
    setContentPane(chartPanel);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      App frame = new App();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });
  }
}
