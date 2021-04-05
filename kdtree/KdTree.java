/* *****************************************************************************
 *  Name: Xiaoxiao
 *  Date: 01/04/2021
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;             // root of BST

    private static class Node {
        private final Point2D p;           // sorted by key
        private RectHV rect;         // associated data
        private Node left;
        private Node right;  // left and right subtrees
        private int size;

        public Node(Point2D p, int size) {
            this.p = p;
            this.size = size;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        root = insert(root, p, null, "TOP");
    }

    private Node insert(Node x, Point2D p, Node parent, String orientation) {
        if (x == null) {
            Node newNode = new Node(p, 1);
            if (parent == null) {
                newNode.rect = new RectHV(0, 0, 1, 1);
            }
            else if (orientation.equals("LEFT")) {
                newNode.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(),
                                          parent.rect.ymax());
            }
            else if (orientation.equals("RIGHT")) {
                newNode.rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(),
                                          parent.rect.ymax());
            }
            else if (orientation.equals("TOP")) {
                newNode.rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(),
                                          parent.rect.ymax());
            }
            else {
                newNode.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                          parent.rect.xmax(), parent.p.y());
            }
            return newNode;
        }
        if (orientation.equals("TOP") || orientation.equals("BOTTOM")) {
            if (p.x() < x.p.x()) x.left = insert(x.left, p, x, "LEFT");
            else x.right = insert(x.right, p, x, "RIGHT");
        }
        else {
            if (p.y() < x.p.y()) x.left = insert(x.left, p, x, "BOTTOM");
            else x.right = insert(x.right, p, x, "TOP");
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return get(root, p, 1) != null;
    }

    private Point2D get(Node x, Point2D p, int level) {
        if (p == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        if (x.p.equals(p)) return x.p;
        if (level % 2 != 0) {
            if (p.x() < x.p.x()) return get(x.left, p, level + 1);
            else return get(x.right, p, level + 1);
        }
        else {
            if (p.y() < x.p.y()) return get(x.left, p, level + 1);
            else return get(x.right, p, level + 1);
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 1);
    }

    private void draw(Node x, int level) {
        if (x == null) return;
        if (level % 2 != 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.006);
            Point2D start = new Point2D(x.p.x(), x.rect.ymin());
            Point2D end = new Point2D(x.p.x(), x.rect.ymax());
            start.drawTo(end);
            StdDraw.show();
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.006);
            Point2D start = new Point2D(x.rect.xmin(), x.p.y());
            Point2D end = new Point2D(x.rect.xmax(), x.p.y());
            start.drawTo(end);
            StdDraw.show();
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        x.p.draw();
        StdDraw.show();
        draw(x.left, level + 1);
        draw(x.right, level + 1);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("calls range() with a null key");
        Queue<Point2D> queue = new Queue<>();
        rangeSearch(root, rect, queue);
        return queue;
    }

    private void rangeSearch(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null || !x.rect.intersects(rect)) return;
        if (rect.contains(x.p)) queue.enqueue(x.p);
        rangeSearch(x.left, rect, queue);
        rangeSearch(x.right, rect, queue);
    }

    // a nearest neighbor in the set to point p; null if the set is empty

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls range() with a null key");
        Point2D candidate;
        double minDistance = Double.POSITIVE_INFINITY;
        candidate = nearest(root, p, 1, minDistance, root.p);
        return candidate;
    }

    private Point2D nearest(Node x, Point2D p, int level, double minDistance, Point2D candidate) {
        if (x == null || x.rect.distanceSquaredTo(p) >= minDistance) return null;
        if (x.p.distanceSquaredTo(p) < minDistance) {
            minDistance = x.p.distanceSquaredTo(p);
            candidate = x.p;
        }
        if (level % 2 != 0) {
            if (p.x() <= x.p.x()) {
                Point2D candidateLeft = nearest(x.left, p, level + 1, minDistance, candidate);
                if (candidateLeft != null) {
                    minDistance = candidateLeft.distanceSquaredTo(p);
                    candidate = candidateLeft;
                }
                Point2D candidateRight = nearest(x.right, p, level + 1, minDistance, candidate);
                if (candidateRight != null) {
                    //minDistance = candidateRight.distanceTo(p);
                    candidate = candidateRight;
                }
            }
            else {
                Point2D candidateRight = nearest(x.right, p, level + 1, minDistance, candidate);
                if (candidateRight != null) {
                    minDistance = candidateRight.distanceSquaredTo(p);
                    candidate = candidateRight;
                }
                Point2D candidateLeft = nearest(x.left, p, level + 1, minDistance, candidate);
                if (candidateLeft != null) {
                    //minDistance = candidateLeft.distanceTo(p);
                    candidate = candidateLeft;
                }
            }
        }
        else {
            if (p.y() <= x.p.y()) {
                Point2D candidateLeft = nearest(x.left, p, level + 1, minDistance, candidate);
                if (candidateLeft != null) {
                    minDistance = candidateLeft.distanceSquaredTo(p);
                    candidate = candidateLeft;
                }
                Point2D candidateRight = nearest(x.right, p, level + 1, minDistance, candidate);
                if (candidateRight != null) {
                    //minDistance = candidateRight.distanceTo(p);
                    candidate = candidateRight;
                }
            }
            else {
                Point2D candidateRight = nearest(x.right, p, level + 1, minDistance, candidate);
                if (candidateRight != null) {
                    minDistance = candidateRight.distanceSquaredTo(p);
                    candidate = candidateRight;
                }
                Point2D candidateLeft = nearest(x.left, p, level + 1, minDistance, candidate);
                if (candidateLeft != null) {
                    //minDistance = candidateLeft.distanceTo(p);
                    candidate = candidateLeft;
                }
            }
        }
        return candidate;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree testTree = new KdTree();
        System.out.println(testTree.size());
        testTree.insert(new Point2D(0.700000, 0.200000));
        System.out.println(testTree.size());
        testTree.insert(new Point2D(0.500000, 0.400000));
        System.out.println(testTree.size());
        testTree.insert(new Point2D(0.200000, 0.300000));
        testTree.insert(new Point2D(0.400000, 0.700000));
        testTree.insert(new Point2D(0.900000, 0.600000));
        testTree.draw();
        System.out.println(testTree.contains(new Point2D(0.700000, 0.200000)));
        System.out.println(testTree.contains(new Point2D(0.700000, 0.300000)));
        Iterable<Point2D> aa = testTree.range(new RectHV(0.1, 0.1, 0.3, 0.5));
        for (Point2D p : aa) {
            System.out.println(p.toString());
        }
        Point2D res = testTree.nearest(new Point2D(0.6, 0.8));
        System.out.println(res);
        System.out.println(testTree.size());
    }
}
