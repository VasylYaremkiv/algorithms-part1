import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double RATE = 1.96;
    private final int count;
    private final double[] fractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        if (trials <= 0) {
            throw new IllegalArgumentException();
        }

        count = trials;
        fractions = new double[count];
        Percolation pr;

        for (int i = 0; i < count; i++) {
            pr = new Percolation(n);
            while (!pr.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!pr.isOpen(row, col)) {
                    pr.open(row, col);
                }
            }
            fractions[i] = (double) pr.numberOfOpenSites() / (n * n);
        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((RATE * stddev()) / Math.sqrt(count));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((RATE * stddev()) / Math.sqrt(count));
    }

   // test client (see below)
   public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + confidence);
   }

}
