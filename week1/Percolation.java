// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {
    private int countOpenSites;
    private int[] sites;
    private int[] sz;
    private boolean[] grid;
    private final int length;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        countOpenSites = 0;
        length = n;
        sites = new int[n * n];
        sz = new int[n * n];
        grid = new boolean[n * n];
        for (int i = 0; i < sites.length; i++) {
            grid[i] = false;
            sz[i] = 1;
            sites[i] = i;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isFull(row, col)) {
            row = row - 1;
            col = col - 1;

            grid[row * length + col] = true;

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
        return grid[(row -1) * length + col -1];
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
        int[] top = new int[length];
        int rootBottom;

        for (int i = 0; i < length; i++) {
            if (isOpen(1, i + 1)) {
                top[i] = root(i);
            }
        }
        for (int i = 0; i < length; i++) {
            if (isOpen(length, i + 1)) {
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

    // private void print() {
    //     for (int i = 0; i < length; i++) {
    //         System.out.print("|");           
    //         for (int j = 0; j < length; j++) {
    //             if (grid[i * length + j]) {
    //                 System.out.print(" ");
    //             }  else {
    //                 System.out.print("X");
    //             }
    //         }
    //         System.out.println("|");
    //     }

    //     for (int i = 0; i < sites.length; i++) {
    //         System.out.print("|" +  sites[i]); 
    //     }
    //     System.out.println("|");
    // }

    private int root(int i) {
        while (i != sites[i]) {
            sites[i] = sites[sites[i]];
            i = sites[i];
        }
        return i;
    }

    // private boolean connected(int p, int q) {
    //     return root(p) == root(q);
    // }

    private void union(int p, int q) {
        if (!grid[q]) {
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
        Percolation p = new Percolation(50);
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