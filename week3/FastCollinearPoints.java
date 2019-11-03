import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {
    private final Point[] points;
    private final int length;
    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private int lengthSegments;


    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);
        Point previousPoint = this.points[0];
        for (int i = 1; i < points.length; i++) {
            if (previousPoint.compareTo(this.points[i]) == 0) {
                throw new IllegalArgumentException();
            }
            previousPoint = this.points[i];
        }
 
        this.length = points.length;

        this.lengthSegments = 0;
        if (this.length >= 4) {
            this.findLineSegements();
        }

    } 


    private void findLineSegements() {
        ArrayList<Point> allSegementsStart = new ArrayList<Point>();
        ArrayList<Point> allSegementsEnd = new ArrayList<Point>();

        int i = 0;

        for (int p = 0; p < this.length - 3; p++) {
            Point[] nextPoints = Arrays.copyOfRange(this.points, p + 1, points.length);
            Point po = this.points[p];
            Arrays.sort(nextPoints, this.points[p].slopeOrder());
            int count = 1;
            double previousSlope = po.slopeTo(nextPoints[0]);

            for (int s = 1; s < nextPoints.length; s++) {
                if (po.slopeTo(nextPoints[s]) == previousSlope) {
                    count++;
                } else {
                    if (count >= 3) {
                        if (checkIfSegmentPresent(po, nextPoints[s - 1], allSegementsStart, allSegementsEnd, i)) {
                            allSegementsStart.add(po);
                            allSegementsEnd.add(nextPoints[s - 1]);
                            i++;    
                        }
                    }
                    previousSlope = po.slopeTo(nextPoints[s]);
                    count = 1;
                }

            }
    
            if (count >= 3) {
                if (checkIfSegmentPresent(po, nextPoints[nextPoints.length - 1], allSegementsStart, allSegementsEnd, i)) {
                    allSegementsStart.add(po);
                    allSegementsEnd.add(nextPoints[nextPoints.length - 1]);
                    i++;
                }
            }
        }

        if (i > 0) {
            for (int a = 0; a < i; a++) {
                this.segments.add(new LineSegment(allSegementsStart.get(a), allSegementsEnd.get(a)));
                this.lengthSegments++;
            }
        }
    }

    private boolean checkIfSegmentPresent(Point start, Point end, ArrayList<Point> allSegementsStart, ArrayList<Point> allSegementsEnd, int len) {
        double slope = start.slopeTo(end);
        for (int i = 0; i < len; i++) {
            if (allSegementsEnd.get(i) == end && slope == allSegementsStart.get(i).slopeTo(allSegementsEnd.get(i))) {
                return false;
            }
        }

        return true;
    }
    public int numberOfSegments() {       // the number of line segments
        return this.lengthSegments;
    }
 
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.lengthSegments]);
    }

    public static void main(String[] args) {
        Point[] points = new Point[15];
        points[0] = new Point(10, 0);
        points[1] = new Point(13, 0);
        points[2] = new Point(20, 0);
        points[3] = new Point(30, 0);
        points[4] = new Point(8, 2);
        points[5] = new Point(18, 2);
        points[6] = new Point(11, 3);
        points[7] = new Point(9, 6);
        points[8] = new Point(2, 8);
        points[9] = new Point(0, 10);
        points[10] = new Point(20, 10);
        points[11] = new Point(5, 12);
        points[12] = new Point(2, 18);
        points[13] = new Point(10, 20);
        points[14] = new Point(0, 30);

        // '(30000, 0) -> (20000, 10000) -> (10000, 20000) -> (0, 30000)'

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        // bcp.print();
        System.out.println("numberOfSegments:  " + bcp.numberOfSegments());


        // // read the n points from a file       
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points1 = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points1[i] = new Point(x, y);
        }

        // // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points1) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points1);
        // collinear.print();
        System.out.println("numberOfSegments:  " + collinear.numberOfSegments());


        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
 }
