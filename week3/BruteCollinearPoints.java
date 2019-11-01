import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {
    private final Point[] points;
    private final int length;
    private LineSegment[] segments;
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

        Arrays.sort(points);
        Point previousPoint = points[0];
        for (int i = 1; i < points.length; i++) {
            if (previousPoint.compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
 
        this.points = points;
        this.length = points.length;

        this.segments = new LineSegment[4];
        this.lengthSegments = 0;
        this.findLineSegements();
    } 


    private void findLineSegements() {
        double slopePQ, slopePR, slopePS;
        Point[][] allSegements = new Point[this.length][2];
        int i = 0;

        for (int p = 0; p < this.length; p++) {
            // endPoints = new Point[this.length - 3];
            for (int q = p + 1; q < this.length; q++) {
                slopePQ = this.points[p].slopeTo(this.points[q]);
                for (int r = q + 1; r < this.length; r++) {
                    slopePR = this.points[p].slopeTo(this.points[r]);
                    if (slopePQ == slopePR) {
                        for (int s = r + 1; s < this.length; s++) {
                            slopePS = this.points[p].slopeTo(this.points[s]);
                            if (slopePQ == slopePS) {

                                // if (Arrays.contains()) 
                                System.out.println(" segement (" + slopePS + "): " + this.points[p].toString() + " -> " + this.points[s].toString());

                                allSegements[i][0] = this.points[p];
                                allSegements[i++][1] = this.points[s];
                                // this.segments[this.lengthSegments++] = new LineSegment(this.points[p], this.points[s]);
                            }        
                        }    
                    }
                }
            }
        }

        if (i > 0) {
            Point pp = allSegements[0][0];
            Point ps = allSegements[0][1];
            this.segments[this.lengthSegments++] = new LineSegment(pp, ps);
            System.out.println(" add segement : " + pp.toString() + " -> " + ps.toString());

            for (int j = 1; j < i; j++) {
                if (pp != allSegements[j][0] || ps != allSegements[j][1]) {
                    pp = allSegements[j][0];
                    ps = allSegements[j][1];
                    this.segments[this.lengthSegments++] = new LineSegment(pp, ps);
                    System.out.println(" add segement : " + pp.toString() + " -> " + ps.toString());
                }
            }    
        }
    }

    public void print() {
        for (int i = 0; i < this.length; i++) {
            System.out.println(this.points[i].toString());
        }
    }
    public int numberOfSegments() {       // the number of line segments
        return this.lengthSegments;
    }
 
    public LineSegment[] segments() {
        return Arrays.copyOfRange(this.segments, 0, this.lengthSegments);
    }

    public static void main(String[] args) {
        // Point[] points = new Point[6];
        // points[0] = new Point(190, 100);
        // points[1] = new Point(180, 100);
        // points[2] = new Point(320, 100);
        // points[3] = new Point(210, 100);
        // points[4] = new Point(123, 56);
        // points[5] = new Point(140, 100);
        // BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        // bcp.print();
        // System.out.println("numberOfSegments:  " + bcp.numberOfSegments());


        // // read the n points from a file       
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
        collinear.print();
        System.out.println("numberOfSegments:  " + collinear.numberOfSegments());


        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
 }
