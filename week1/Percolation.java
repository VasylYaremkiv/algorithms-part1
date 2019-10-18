import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {
    int countOpenSites;
    int sites[];
    int sz[];
    int grid[];

    int length;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        countOpenSites = 0;
        length = n;
        sites = new int[n * n];
        sz = new int[n * n];
        grid = new int[n * n];
        for (int i = 0; i < sites.length; i++) {
            grid[i] = 1;
            sz[i] = 1;
            sites[i] = i;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row * length + col] = 0;

            int p = row * length + col;
            if (row > 0) {
                union(p, p - length);
            }
            if (row < length - 1) {
                union(p, p + length);
            }
            if (col > 0) {
                union(p, p - 1);
            }
            if (col < length - 1) {
                union(p, p + 1);
            }

            countOpenSites += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row * length + col] == 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return !isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int top[] = new int[length];
        int rootBottom;

        for (int i = 0; i < length; i++) {
            if (isOpen(0, i)) {
                top[i] = root(i);
            }
        }
        for (int i = 0; i < length; i++) {
            if (isOpen(length - 1, i)) {
                rootBottom = root((length - 1) * length + i);
                if (contains(top, rootBottom)) {
                   return true;
                }
            }
        }

        return false;
    }


    private boolean contains(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return true;
            }
        }

        return false;
    }

    public void print() {
        for (int i = 0; i < length; i++) {
            System.out.print("|");           
            for (int j = 0; j < length; j++) {
                if (grid[i * length + j] == 0) {
                    System.out.print(" ");
                }  else {
                    System.out.print("X");
                }
            }
            System.out.println("|");
        }

        for (int i = 0; i < sites.length; i++) {
            System.out.print("|" +  sites[i]); 
        }
        System.out.println("|");
    }

    private int root(int i) {
        while (i != sites[i]) {
            sites[i] = sites[sites[i]];
            i = sites[i];
        }
        return i;
    }

    private boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private void union(int p, int q) {
        if (grid[q] != 0) {
           return;
        }

        int i = root(p);
        int j = root(q);

        if (i == j) {
           return;
        }
        
        if (sz[i] < sz[j]) {
            sites[i] = j; 
            sz[j] += sz[i]; 
        } else { 
            sites[j] = i; 
            sz[i] += sz[j]; 
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        // System.out.println(p.isOpen(0, 1));
        p.open(0, 1);
        // System.out.println(p.isOpen(0, 1));
        p.open(0, 2);
        p.open(0, 3);

        p.open(1, 0);
        p.open(1, 2);
        p.open(1, 3);
        p.open(1, 4);

        p.open(2, 0);
        p.open(2, 1);
        p.open(2, 4);

        p.open(3, 0);
        p.open(3, 3);
        // p.open(3, 4);

        p.open(4, 0);
        p.open(4, 1);
        p.open(4, 2);
        p.open(4, 3);

        p.print();
        System.out.println(p.percolates());
    }
}