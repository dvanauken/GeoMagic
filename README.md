# GeoMagic Application

## Overview
GeoMagic is a Java application designed to read geometric line data and create visualizations of connected line segments (polylines). It simplifies graph theory concepts to determine connections and uses Depth-First Search (DFS) to explore these connections.

## Technical Approach
- **Graph Representation**: Each point is a vertex, and each line segment is an edge. We construct the graph by mapping each vertex to its connected vertices.
- **Joining Lines**: We join lines to form polylines by traversing the graph. A connection is made if a line's end point matches another line's start point.
- **Exclusion of Points**: We avoid using a point to form a polyline if it becomes too crowded, meaning more than two lines meet there.
- **Depth-First Search (DFS)**: DFS helps explore possible paths from each vertex, detecting all possible polylines starting from any unvisited vertex.
- **Length Calculation**: We calculate the length of each polyline as the sum of its constituent line segments. Polylines are then sorted based on their lengths, from longest to shortest.

## Assumptions
The primary rule for creating polylines stated: "If more than two lines share a common start or end point, then no polyline is created from these lines." This rule led to several possible interpretations:
- **Simple Rule**: We avoid making a line if a point is too crowded, meaning more than two lines meet there.
- **Flexible Connections**: If a point is crowded, lines using that point can still connect from their other ends where it’s less crowded.
- **Separate Uses**: Lines that meet at a busy spot can still be useful. We try to use them in different places where they fit better.
- **Using Parts of Lines**: Sometimes, we can use just the part of a line that doesn’t go into the crowded area to connect it somewhere else.

The chosen assumption for this implementation is Flexible Connections.

## Project Structure
- `App.java`: Main application class that initializes the GUI and handles polyline visualization.
- `PolylineProcessor.java`: Contains the logic for reading lines, building the graph, finding polylines, and creating the dataset.
- `LineSegment.java`: Represents a line segment with a start and end point.
- `Point.java`: Represents a 2D space point.
- `LineReader.java`: Utility class to read line segments from an input file.

## Compilation and Build Instructions
Ensure you have Java 18 or above and Maven 3.9.2 or above installed. Navigate to the project directory and run the following commands:

### Clone the Repository:
Use the following command to clone the repository to your local machine:
```bash
git clone https://github.com/dvanauken/GeoMagic.git
```
### Build the project
```bash
mvn clean install
mvn dependency:copy-dependencies
```

### Run the Application:
```bash
java -cp "target/classes;target/dependency/*" com.dbva.App
```
### Running Tests
```bash
mvn test
