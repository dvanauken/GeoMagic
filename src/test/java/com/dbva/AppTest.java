package com.dbva;

import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private PolylineProcessor processor;
    private List<LineSegment> lines;

    @BeforeEach
    public void setUp() {
        processor = new PolylineProcessor();
        // Load the line data from the resource input file
        try (InputStream is = getClass().getResourceAsStream("/input.txt")) {
            if (is == null) {
                throw new IllegalArgumentException("Resource file not found. Check the file path.");
            }
            lines = processor.readLines(is);
            processor.buildGraph(lines); // Build the graph with the lines read from the file
        } catch (Exception e) {
            fail("Failed to read lines: " + e.getMessage());
        }
    }

    @Test
    public void testGraphIsNotEmpty() {
        assertFalse(processor.getGraph().isEmpty(), "Graph should not be empty after processing lines.");
    }

    @Test
    public void testPolylinesCreation() {
        List<List<Point>> polylines = processor.findPolylines();
        assertNotNull(polylines, "Polylines list should not be null.");
        assertFalse(polylines.isEmpty(), "Should create at least one polyline.");
    }

    @Test
    public void testCalculatePolylineLength() {
        List<List<Point>> polylines = processor.findPolylines();
        assertTrue(polylines.stream().allMatch(polyline -> processor.calculateLength(polyline) > 0), "All polylines should have a positive length.");
    }

    @Test
    public void testDatasetCreation() {
        List<List<Point>> polylines = processor.findPolylines();
        XYSeriesCollection dataset = processor.createDataset(polylines);
        assertNotNull(dataset, "Dataset should not be null.");
        assertFalse(dataset.getSeries().isEmpty(), "Dataset should contain series.");
    }

    @Test
    public void testChartCreation() {
        List<List<Point>> polylines = processor.findPolylines();
        XYSeriesCollection dataset = processor.createDataset(polylines);
        assertNotNull(processor.createChart(dataset), "Chart should be successfully created.");
    }
}
