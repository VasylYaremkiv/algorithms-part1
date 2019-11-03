import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;


public class FastCollinearPoints {
    private final Point[] points;
    private final int length;
    // private LineSegment[] segments;
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

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
 
        // this.points = points;
        this.length = points.length;
        // this.print();

        this.lengthSegments = 0;
        if (this.length >= 4) {
            // this.segments = new LineSegment[this.length - 3];
            this.findLineSegements();
        } else {
            // this.segments = new LineSegment[0];
        }

    } 


    private void findLineSegements() {
        // double slopePQ, slopePR, slopePS;
        // Point[][] allSegements = new Point[this.length][2];
        // Point[][] allSegementsStart = new Point[this.length][2];
        ArrayList<Point> allSegementsStart = new ArrayList<Point>();
        ArrayList<Point> allSegementsEnd = new ArrayList<Point>();
        // private ArrayList<Point[2]> allSegements = new ArrayList<Point[2]>();

        int i = 0;

        for (int p = 0; p < this.length - 3; p++) {
            Point[] nextPoints = Arrays.copyOfRange(this.points, p + 1, points.length);
            Point po = this.points[p];
            Arrays.sort(nextPoints, this.points[p].slopeOrder());
            int count = 1;
            // int previousPointIndex = 0;
            double previousSlope = po.slopeTo(nextPoints[0]);

            for (int s = 1; s < nextPoints.length; s++) {
                if (po.slopeTo(nextPoints[s]) == previousSlope) {
                    count++;
                    // previousPointIndex = s;
                    // System.out.println(" segement (" + po.slopeTo(nextPoints[s]) + "): " + po.toString() + " -> " + nextPoints[s].toString());
                } else {
                    if (count >= 3) {
                        // System.out.println(" END segement (" + po.slopeTo(nextPoints[s-1]) + "): " + po.toString() + " -> " + nextPoints[s-1].toString());
                        // previousSlope = po.slopeTo(nextPoints[s - 1]);
                        if (checkIfSegmentPresent(po, nextPoints[s - 1], allSegementsStart, allSegementsEnd, i)) {
                            allSegementsStart.add(po);
                            allSegementsEnd.add(nextPoints[s - 1]);
                            i++;    
                        }
                        // this.segments[this.lengthSegments++] = new LineSegment(po, nextPoints[s - 1]);
                    }
                    previousSlope = po.slopeTo(nextPoints[s]);
                    count = 1;
                }

            }
    
            if (count >= 3) {
                // System.out.println(" END segement (" + po.slopeTo(nextPoints[nextPoints.length-1]) + "): " + po.toString() + " -> " + nextPoints[nextPoints.length - 1].toString());
                // this.segments[this.lengthSegments++] = new LineSegment(po, nextPoints[nextPoints.length - 1]);

                if (checkIfSegmentPresent(po, nextPoints[nextPoints.length - 1], allSegementsStart, allSegementsEnd, i)) {
                    allSegementsStart.add(po);
                    allSegementsEnd.add(nextPoints[nextPoints.length - 1]);
                    i++;
                }

            }
        }

        if (i > 0) {
            // Point pp = allSegements[0][0];
            // Point ps = allSegements[0][1];
            for (int a = 0; a < i; a++) {
                this.segments.add(new LineSegment(allSegementsStart.get(a), allSegementsEnd.get(a)));
                this.lengthSegments++;
                // System.out.println(" CREATE segement (" + allSegements[a][0].slopeTo(allSegements[a][1]) + "): " + allSegements[a][0].toString() + " -> " + allSegements[a][1].toString());
            }
        }
    }

    private boolean checkIfSegmentPresent(Point start, Point end, ArrayList<Point> allSegementsStart, ArrayList<Point> allSegementsEnd, int len) {
        double slope = start.slopeTo(end);
        // System.out.println(" CHECK  segement (" + start.toString() + "->" + end.toString() + ")");
        for (int i = 0; i < len; i++) {
            if (allSegementsEnd.get(i) == end && slope == allSegementsStart.get(i).slopeTo(allSegementsEnd.get(i))) {
                // System.out.println(" SAME AS  segement (" + start.toString() + "->" + end.toString() + "): " + allSegements[i][0].toString() + " -> " + allSegements[i][1].toString());

                return false;
            }
        }

        return true;
    }

    // private void print() {
    //     for (int i = 0; i < this.length; i++) {
    //         System.out.println(this.points[i].toString());
    //     }
    // }
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
