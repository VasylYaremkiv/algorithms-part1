import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;
    public PointSET() {
        this.set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    public int size() {
        return this.set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!this.contains(p)) {
            this.set.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return this.set.contains(p);
    }

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 5);
        StdDraw.setYscale(0, 5);
        for (Point2D p : this.set) {
            p.draw();
        }
        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        SET<Point2D> result = new SET<Point2D>();
        for (Point2D p : this.set) {
            if (rect.contains(p)) {
                result.add(p);
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

        Point2D near = p;
        Double distance;
        Double minDistance = 0.0;
        boolean first = true;

        for (Point2D cp : this.set) {
            distance = cp.distanceTo(p);
            if (first || distance < minDistance) {
                minDistance = distance;
                first = false;
                near = cp;
            }
        }


        return near;
    }

    public static void main(String[] args) {
        Point2D points[] = {
            new Point2D(1, 2),
            new Point2D(1, 3),
            new Point2D(2, 2),
            new Point2D(0.5, 2),
            new Point2D(1.5, 1.5)
        };

        PointSET ps = new PointSET();
        for (int i = 0; i < points.length; i++) {
            ps.insert(points[i]);
        }

        RectHV rect = new RectHV(1, 1, 3, 2.5);
        for (Point2D p : ps.range(rect)) {
            System.out.println(p);
        }

        System.out.println(ps.nearest(new Point2D(1.02, 1.69)));
        // ps.draw();
    }
}
