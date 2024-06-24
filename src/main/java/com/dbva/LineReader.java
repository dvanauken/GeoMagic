package com.dbva;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LineReader {

  public static List<LineSegment> readLines(InputStream inputStream) throws IOException {
    List<LineSegment> lines = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        if (parts.length != 4) {
          System.err.println("Invalid line format: " + line);
          continue;
        }
        try {
          double x1 = Double.parseDouble(parts[0]);
          double y1 = Double.parseDouble(parts[1]);
          double x2 = Double.parseDouble(parts[2]);
          double y2 = Double.parseDouble(parts[3]);
          lines.add(new LineSegment(new com.dbva.Point(x1, y1), new Point(x2, y2)));
        } catch (NumberFormatException e) {
          System.err.println("Invalid coordinate value: " + line);
        }
      }
    }
    return lines;
  }
}
