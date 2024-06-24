package com.dbva;

public class LineSegment {
  Point start, end;

  LineSegment(Point start, Point end) {
    this.start = start;
    this.end = end;
  }

  double length() {
    return Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
  }
}
