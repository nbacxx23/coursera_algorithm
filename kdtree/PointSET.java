/* *****************************************************************************
 *  Name: Xiaoxiao
 *  Date: 31/03/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private final SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.clear();
        for (Point2D point : pointSet) {
            point.draw();
            StdDraw.show();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("argument is null");
        }
        SET<Point2D> insidePoints = new SET<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                insidePoints.add(point);
            }
        }
        return insidePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        Point2D candidate = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : pointSet) {
            double distance = point.distanceSquaredTo(p);
            if (distance < minDistance) {
                minDistance = distance;
                candidate = point;
            }
        }
        return candidate;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET testSet = new PointSET();
        StdDraw.enableDoubleBuffering();
        testSet.insert(new Point2D(0.700000, 0.500000));
        testSet.insert(new Point2D(0.500000, 0.700000));
        testSet.insert(new Point2D(0.500000, 0.900000));
        testSet.insert(new Point2D(0.900000, 0.500000));
        System.out.println(testSet.size());
        System.out.println(testSet.isEmpty());
        System.out.println(testSet.contains(new Point2D(0.900000, 0.500000)));
        System.out.println(testSet.contains(new Point2D(0.900000, 0.800000)));
        Iterable<Point2D> it = testSet.range(new RectHV(0.2, 0.2, 0.8, 0.8));
        for (Point2D point : it) System.out.println(point.toString());
        Point2D res = testSet.nearest(new Point2D(0.6, 0.9));
        System.out.println(res.toString());
        testSet.draw();
    }
}

