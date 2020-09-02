import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {

    private final Point[] points;

    private final LineSegment[] lineSegments;

    private int getLength() {
        return points.length;
    }

    public BruteCollinearPoints(Point[] points) {
        ArrayList<Point> backup = new ArrayList<>();
        if (points == null) throw new IllegalArgumentException("argument is null");
        int length = points.length;
        this.points = new Point[length];
        for (int i = 0; i < length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("argument is null");
            }else if (containsValue(backup, points[i])) throw new IllegalArgumentException("duplicated point");
            this.points[i] = points[i];
            backup.add(points[i]);
        }   // finds all line segments containing 4 points

        this.lineSegments = findSegments();
    }

    private boolean containsValue(List<Point> points, Point point) {
        return points.stream().anyMatch(p -> p.compareTo(point) == 0);
    }

    public int numberOfSegments() {
        return segments().length;
    }      // the number of line segments

    private LineSegment[] findSegments(){
        List<LineSegment> lineSegments = new ArrayList<>();
        for (int i = 0; i < getLength() - 3; i++) {
            for (int j = i + 1; j < getLength() - 2; j++) {
                for (int k = j + 1; k < getLength() - 1; k++) {
                    for (int l = k + 1; l < getLength(); l++) {
                        double slopeij = points[i].slopeTo(points[j]);
                        double slopeik = points[i].slopeTo(points[k]);
                        double slopeil = points[i].slopeTo(points[l]);
                        if (slopeij == slopeik && slopeij == slopeil) {
                            Point[] fourPoints = new Point[]{points[i], points[j], points[k], points[l]};
                            Point maxPoint = getMaxPoint(fourPoints);
                            Point minPoint = getMinPoint(fourPoints);
                            LineSegment lineSegment = new LineSegment(maxPoint, minPoint);
                            lineSegments.add(lineSegment);
                        }

                    }
                }
            }
        }
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }               // the line segments

    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }


    private Point getMaxPoint(Point[] points) {
        Point currentMax = points[0];
        for (int i = 1; i < points.length; i++) {
            if(points[i].compareTo(currentMax) > 0 ) {
                currentMax = points[i];
            }
        }
        return currentMax;
    }

    private Point getMinPoint(Point[] points) {
        Point currentMin = points[0];
        for (int i = 1; i < points.length; i++) {
            if(points[i].compareTo(currentMin) < 0 ) {
                currentMin = points[i];
            }
        }
        return currentMin;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            StdOut.println(segment);
        }
        Point p1 = new Point(10000, 50000);
        Point p2 = new Point(4000, 2948);
        segments[0] = new LineSegment(p1, p2);
        points[0] = p1;
        LineSegment[] segments2 = collinear.segments();
        for (LineSegment segment : segments2) {
            StdOut.println(segment);
            //segment.draw();
        }
        StdDraw.show();
    }
}

