public class BruteCollinearPoints {

    private Point[] points;

    public BruteCollinearPoints(Point[] points) {
        int length = points.length;
        for (int i = 0; i< length;i++){
            if (points[i]==null) {
                throw new NullPointerException("argument is null");
            }
            this.points.add(points[i]);
    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        return 0;
    }      // the number of line segments

    public LineSegment[] segments() {
        return null;
    }               // the line segments
}
