/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }

        if (this.y == that.y) {
            return 0.0;
        }
        if (this.x == that.x && this.y > that.y) {
            return Double.POSITIVE_INFINITY;
        }
        if (this.x == that.x && this.y < that.y) {
            return Double.POSITIVE_INFINITY; 
        }
        return (double) (that.y - this.y) / (that.x - this.x);    
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    @Override
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        }
        if (this.x > that.x) {
            return 1;
        }
        return 0;        
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new BySlope(this.x, this.y);
    }

    private static class BySlope implements Comparator<Point> { 
        private final Point p;

        public BySlope(int x, int y) {
            p = new Point(x, y);
        }
        @Override
        public int compare(Point v, Point w) {
            double slopeV = p.slopeTo(v);
            double slopeW = p.slopeTo(w);

            if (slopeV == Double.NEGATIVE_INFINITY) {
                if (slopeW == Double.NEGATIVE_INFINITY) {
                    return 0;
                } else {
                    return -1;
                }
            }
            if (slopeW == Double.NEGATIVE_INFINITY) {
                return 1;
            }

            if (slopeV == Double.POSITIVE_INFINITY) {
                if (slopeW == Double.POSITIVE_INFINITY) {
                    return 0;
                } else {
                    return 1;
                }
            }
            if (slopeW == Double.POSITIVE_INFINITY) {
                return -1;
            }

            if (slopeV > slopeW) {
                return 1;
            } 
            if (slopeV < slopeW) {
                return -1;
            }

            return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        
        Point[] points = new Point[2];
        points[0] = new Point(33, 79);
        points[1] = new Point(33, 433);

        System.out.println(points[0].toString() + ".compareTo(" +  points[1].toString() +") : " + points[0].compareTo(points[1]));
        System.out.println(points[0].toString() + ".slopeTo(" +  points[1].toString() +") : " + points[0].slopeTo(points[1]));


        // p1.drawTo(p2);
        // StdDraw.show();

        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 5);
        // StdDraw.setYscale(0, 5);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

    }
}