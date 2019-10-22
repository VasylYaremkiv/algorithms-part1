import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int countOpenSites;
    private boolean[] grid;
    private final int length;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF qu;
    

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        countOpenSites = 0;
        length = n;
        grid = new boolean[n * n];
        qu = new WeightedQuickUnionUF(n * n + 2); // n * n + top_item + bottom_item 
        top = n * n;
        bottom = top + 1;

        for (int i = 0; i < grid.length; i++) {
            grid[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > length || row <= 0) {
            throw new IllegalArgumentException();
        }
        if (col > length || col <= 0) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            int p = convertToItem(row, col);
            grid[p] = true;

            if (row > 1) {
                union(p, p - length);
            } else {
                union(p, top);
            }

            if (row < length) {
                union(p, p + length);
            } else {
                union(p, bottom);
            }

            if (col > 1) {
                union(p, p - 1);
            }
            if (col < length) {
                union(p, p + 1);
            }

            countOpenSites += 1;
        }
    }

    private void union(int p, int q) {
        if (q == top || q == bottom || grid[q]) {
            qu.union(p, q);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > length || row <= 0) {
            throw new IllegalArgumentException();
        }
        if (col > length || col <= 0) {
            throw new IllegalArgumentException();
        }

        return grid[convertToItem(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > length || row <= 0) {
            throw new IllegalArgumentException();
        }
        if (col > length || col <= 0) {
            throw new IllegalArgumentException();
        }

        return qu.connected(top, convertToItem(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }


    // does the system percolate?
    public boolean percolates() {
        return qu.connected(top, bottom);
    }

    private int convertToItem(int row, int col) {
        return (row - 1) * length + col - 1;
    }
    

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        System.out.println(p.isOpen(1, 1));
        System.out.println(p.isFull(1, 1));
        p.open(1, 2);
        System.out.println(p.isOpen(1, 2));
        p.open(1, 3);
        p.open(1, 4);

        p.open(2, 1);
        p.open(2, 3);
        p.open(2, 4);
        p.open(2, 5);

        p.open(3, 1);
        p.open(3, 2);
        p.open(3, 5);

        p.open(4, 1);
        p.open(4, 4);
        p.open(4, 5);

        p.open(5, 1);
        p.open(5, 2);
        p.open(5, 3);
        p.open(5, 4);

        // p.print();
        System.out.println(p.percolates());
    }
}