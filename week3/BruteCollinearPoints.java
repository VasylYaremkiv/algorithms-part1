import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {
    private final Point[] points;
    private final int length;
    private final ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private int lengthSegments;

 
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        double slopePQ, slopePR, slopePS;
        Point[][] allSegements = new Point[this.length][2];
        int i = 0;

        for (int p = 0; p < this.length; p++) {
            for (int q = p + 1; q < this.length; q++) {
                slopePQ = this.points[p].slopeTo(this.points[q]);
                for (int r = q + 1; r < this.length; r++) {
                    slopePR = this.points[p].slopeTo(this.points[r]);
                    if (slopePQ == slopePR) {
                        for (int s = r + 1; s < this.length; s++) {
                            slopePS = this.points[p].slopeTo(this.points[s]);
                            if (slopePQ == slopePS) {
                                allSegements[i][0] = this.points[p];
                                allSegements[i++][1] = this.points[s];
                            }        
                        }    
                    }
                }
            }
        }

        if (i > 0) {
            Point pp = allSegements[0][0];
            Point ps = allSegements[0][1];
            this.segments.add(new LineSegment(pp, ps));
            this.lengthSegments++;

            for (int j = 1; j < i; j++) {
                if (pp != allSegements[j][0] || ps != allSegements[j][1]) {
                    pp = allSegements[j][0];
                    ps = allSegements[j][1];
                    this.segments.add(new LineSegment(pp, ps));
                    this.lengthSegments++;        
                }
            }    
        }
    }

    public int numberOfSegments() {       // the number of line segments
        return this.lengthSegments;
    }
 
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.lengthSegments]);
    }

    public static void main(String[] args) {
        // Point[] points = new Point[6];
        // points[0] = new Point(190, 100);
        // points[1] = new Point(180, 100);
        // points[2] = new Point(15643, 100);
        // points[3] = new Point(15643, 160);
        // points[4] = new Point(123, 56);
        // points[5] = new Point(140, 100);
        // BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        // System.out.println("numberOfSegments:  " + bcp.numberOfSegments());


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
        // collinear.print();
        System.out.println("numberOfSegments:  " + collinear.numberOfSegments());


        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
 }
