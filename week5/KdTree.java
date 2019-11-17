// import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;


public class KdTree {
    private class Node {
        private final Point2D point;
        private final boolean vertical;
        private Node left;
        private Node right;
        public double x;
        public double y;

        public Node(Point2D p, boolean v, Node left, Node right) {
            this.point = p;
            this.vertical = v;
            this.left = left;
            this.right = right;
            this.x = p.x();
            this.y = p.y();
        }
    }

    private Node root;
    private int size;
    public KdTree() {
        this.size = 0;
        // this.set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!this.contains(p)) {
            // System.out.println(p);
            if (isEmpty()) {
                this.root = new Node(p, true, null, null);
            } else {
                insert(this.root, p, true);
            }
            this.size++;
        }
    }

    private Node insert(Node node, Point2D p, boolean vertical) {
        if (node == null) {
            return new Node(p, vertical, null, null);
        }

        if (node.x == p.x() && node.y == p.y()) {
            return node;
        } 

        if (compareNodeAndPoint(node, p)) {
            node.left = insert(node.left, p, !node.vertical);
        } else {
            node.right = insert(node.right, p, !node.vertical);
        }

        return node;
    }

    private boolean compareNodeAndPoint(Node node, Point2D p) {
        return node.vertical && p.x() < node.x || !node.vertical && p.y() < node.y;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return contains(this.root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) {
            return false;
        }
        if (node.x == p.x() && node.y == p.y()) {
            return true;
        }

        if (compareNodeAndPoint(node, p)) {
            return contains(node.left, p);
        } else {
            return contains(node.right, p);
        }
    }

    public void draw() {
        draw(this.root);
    }
    private void draw(Node node) {
        if (node == null) {
            return;
        }

        StdDraw.setXscale(0, 4);
        StdDraw.setYscale(0, 4);

        StdDraw.setPenRadius(0.01);

        node.point.draw();

        StdDraw.setPenRadius();
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.BLUE);
        } else {
            StdDraw.setPenColor(StdDraw.RED);
        }
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        return range(root, rect);
    }

    private Iterable<Point2D> range(Node node, RectHV rect) {
        if (node == null) {
            return null;
        }

        ArrayList<Point2D> result = new ArrayList<Point2D>();
        Iterable<Point2D> leftTreePoints = null;
        Iterable<Point2D> rightTreePoints = null;

        if (rect.contains(node.point)) {
            result.add(node.point);
        }

        if (node.vertical) {
            if (node.x >= rect.xmin() && node.x <= rect.xmax()) {
                leftTreePoints = range(node.left, rect);
                rightTreePoints = range(node.right, rect);
            } else {
                if (node.x > rect.xmax()) {
                    leftTreePoints = range(node.left, rect);
                } else {
                    rightTreePoints = range(node.right, rect);
                }
            }
        } else {
            if (node.y >= rect.ymin() && node.y <= rect.ymax()) {
                leftTreePoints = range(node.left, rect);
                rightTreePoints = range(node.right, rect);
            } else {
                if (node.y > rect.ymax()) {
                    leftTreePoints = range(node.left, rect);
                } else {
                    rightTreePoints = range(node.right, rect);
                }
            }
        }

        if (leftTreePoints != null) {
            for (Point2D point : leftTreePoints) {
                result.add(point);
            }
        }

        if (rightTreePoints != null) {
            for (Point2D point : rightTreePoints) {
                result.add(point);
            }
        }

        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (this.isEmpty()) {
            return null;
        }

        return nearest(root, new RectHV(0, 0, 1, 1), p.x(), p.y(), null);
    }

    private Point2D nearest(Node node, RectHV rect, double x, double y, Point2D candidate) {
        if (node == null) {
            return candidate;
        }

        double dqn = 0.0;
        double drq = 0.0;
        RectHV left = null;
        RectHV rigt = null;
        final Point2D query = new Point2D(x, y);
        Point2D nearest = candidate;

        if (nearest != null) {
            dqn = query.distanceSquaredTo(nearest);
            drq = rect.distanceSquaredTo(query);
        }

        if (nearest == null || dqn > drq) {
            final Point2D point = new Point2D(node.x, node.y);
            if (nearest == null || dqn > query.distanceSquaredTo(point))
                nearest = point;

            if (node.vertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
                rigt = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());

                if (x < node.x) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                rigt = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());

                if (y < node.y) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            }
        }

        return nearest;
    }

    public static void main(String[] args) {
        Point2D[] points = {
            new Point2D(1, 2),
            new Point2D(1, 3),
            new Point2D(2, 2),
            new Point2D(0.5, 2),
            new Point2D(1.5, 1.5)
        };

        KdTree ps = new KdTree();
        for (int i = 0; i < points.length; i++) {
            ps.insert(points[i]);
        }

        RectHV rect = new RectHV(1, 1, 3, 2.5);
        for (Point2D p : ps.range(rect)) {
            System.out.println(p);
        }

        System.out.println(ps.nearest(new Point2D(1.02, 1.69)));
        ps.draw();
    }
}
