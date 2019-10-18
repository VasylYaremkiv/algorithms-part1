import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int countOpenSites;
    int sites[];
    int grid[];
    int length;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        countOpenSites = 0;
        length = n;
        sites = new int[n * n];
        grid = new int[n * n];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = 1;
        }
        // Arrays.fill(sites, 0);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row * length + col] = 0;
            countOpenSites += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return countOpenSites >= length * length;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    public void print() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (grid[i * length + j] == 0) {
                    System.out.print(" ");
                }  else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation per = new Percolation(5);
        per.open(0,2);
        per.print();
    }
}