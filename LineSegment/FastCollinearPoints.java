import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {

    private final Point[] points;

    private final LineSegment[] lineSegments;

    private int getLength() {
        return points.length;
    }

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
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

    public int numberOfSegments()        // the number of line segments
    {
        return segments().length;
    }

    private void helper(int end, int start,Point[] loop_list,Point origin,List<LineSegment> lineSegments){
        if (end - start >= 3) {
            Point[] collinePoints = new Point[end - start + 1];
            int q = 0;
            for (int m = start; m < end; m++) {
                collinePoints[q] = loop_list[m];
                q++;
            }
            collinePoints[q] = origin;
            Point maxPoint = getMaxPoint(collinePoints);
            Point minPoint = getMinPoint(collinePoints);

            if (origin.compareTo(minPoint) <= 0) {
                LineSegment lineSegment = new LineSegment(maxPoint, minPoint);
                lineSegments.add(lineSegment);
            }

        }
    }

    private LineSegment[] findSegments(){
        List<LineSegment> lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            List<Double> slopes = new ArrayList<>();
            Point[] loop_list = array_copy(points, i);
            for (int j = 0; j < loop_list.length; j++) {
                slopes.add(points[i].slopeTo(loop_list[j]));
            }

            Comparator<Point> comparators = points[i].slopeOrder();
            Arrays.sort(loop_list,comparators);

            //int[] index = Merge.indexSort(slopes.toArray(new Comparable[slopes.size()]));
            Double[] slopes_arr = new Double[slopes.size()];
            for (int l = 0; l < slopes.size(); l++) {
                slopes_arr[l] = slopes.get(l);                // java 1.5+ style (outboxing)
            }
            Arrays.sort(slopes_arr);
            int k = 1;
            int start = 0;
            while (k < slopes_arr.length) {
                if (slopes_arr[k].equals(slopes_arr[k - 1])) {
                    k++;
                    if (k == slopes_arr.length){
                        helper(k,start,loop_list,points[i],lineSegments);
                    }
                } else {
                    helper(k,start,loop_list,points[i],lineSegments);
                    start = k++;
                }
            }

        }
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public LineSegment[] segments()                // the line segments
    {
        return this.lineSegments.clone();
    }

    private Point[] array_copy(Point[] points,int index){
        Point[] new_array = new Point[points.length-1];
        int p= 0;
        for (int i= 0;i<points.length;i++){
            if (i != index) {
                new_array[p] = points[i];
                p++;
            }

        }
        return new_array;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}