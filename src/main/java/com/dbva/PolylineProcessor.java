package com.dbva;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PolylineProcessor {

  private Map<Point, List<Point>> graph = new HashMap<>();

  public List<LineSegment> readLines(InputStream is) throws IOException {
    return LineReader.readLines(is);
  }

  public void buildGraph(List<LineSegment> lines) {
    graph.clear();
    for (LineSegment line : lines) {
      graph.computeIfAbsent(line.start, k -> new ArrayList<>()).add(line.end);
      graph.computeIfAbsent(line.end, k -> new ArrayList<>()).add(line.start);
    }
  }

  public Map<Point, List<Point>> getGraph() {
    return graph;
  }

  public List<List<Point>> findPolylines() {
    Set<Point> visited = new HashSet<>();
    List<List<Point>> polylines = new ArrayList<>();

    for (Point point : graph.keySet()) {
      if (!visited.contains(point)) {
        List<Point> polyline = new ArrayList<>();
        dfs(point, polyline, visited);
        if (polyline.size() > 1) {
          polylines.add(polyline);
        }
      }
    }

    return polylines;
  }

  private void dfs(Point point, List<Point> polyline, Set<Point> visited) {
    visited.add(point);
    polyline.add(point);
    if (graph.get(point).size() == 2) {
      for (Point neighbor : graph.get(point)) {
        if (!visited.contains(neighbor)) {
          dfs(neighbor, polyline, visited);
        }
      }
    }
  }

  public double calculateLength(List<Point> polyline) {
    double length = 0.0;
    for (int i = 1; i < polyline.size(); i++) {
      Point start = polyline.get(i - 1);
      Point end = polyline.get(i);
      length += Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
    }
    return length;
  }

  public XYSeriesCollection createDataset(List<List<Point>> polylines) {
    XYSeriesCollection dataset = new XYSeriesCollection();
    int seriesIndex = 1;
    for (List<Point> polyline : polylines) {
      XYSeries series = new XYSeries("Polyline " + seriesIndex);
      for (Point point : polyline) {
        series.add(point.x, point.y);
      }
      dataset.addSeries(series);
      seriesIndex++;
    }
    return dataset;
  }

  public JFreeChart createChart(XYSeriesCollection dataset) {
    JFreeChart chart = ChartFactory.createXYLineChart(
        "Polyline Plot",
        "X Coordinate", "Y Coordinate",
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false
    );

    XYPlot plot = chart.getXYPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setRangeGridlinePaint(Color.gray);
    plot.setDomainGridlinePaint(Color.gray);
    return chart;
  }
}
