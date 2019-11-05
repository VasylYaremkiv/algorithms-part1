import java.util.Arrays;

public class Board {
    private final int n;
    private final int[][] tiles;
    private final int FREE = 0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;

        if (this.n < 2 || this.n >= 128) {
            throw new IllegalArgumentException();
        }

        this.tiles = new int[this.n][this.n];

        for (int i = 0; i < this.n; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], this.n);
        }
    }
                                           
    // string representation of this board
    public String toString() {
        int numDigits = (int) Math.log10(this.n * this.n - 1) + 2;
        if (numDigits <= 1) {
            numDigits = 2;
        }
        StringBuffer result = new StringBuffer();
        result.append(this.n);
        result.append("\n");

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                result.append(String.format("%" + numDigits + "d", this.tiles[i][j]));
            }    
            result.append("\n");
        }

        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        int position = 1;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (i == this.n - 1 && j == this.n - 1) {
                    continue;
                }

                if (this.tiles[i][j] != position++) {
                    result++;
                }
            }    
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        int position = 1;
        int tile;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (i == this.n - 1 && j == this.n - 1) {
                    continue;
                }

                if (this.tiles[i][j] != position) {
                    tile = findTile(position);
                    result += Math.abs((tile / this.n) - i);
                    result += Math.abs((tile % this.n) - j);
                }
                position++;
            }    
        }

        return result;
    }

    private int findTile(int tile) {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] == tile) {
                    return i * this.n + j;
                }
            }    
        }

        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return this.toString().equals(y.toString());
    }

    // all neighboring boards
    // public Iterable<Board> neighbors() {

    // }

    // a board that is obtained by exchanging any pair of tiles
    // public Board twin() {

    // }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][]{
            // {0, 1, 2},
            // {3, 4, 5},
            // {6, 7, 8}

            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board b = new Board(tiles);
        System.out.println(b);       

        System.out.println(b.hamming());       
        System.out.println(b.manhattan());       
        System.out.println(b.isGoal());       

        int[][] tiles2 = new int[][]{
            // {0, 1, 2},
            // {3, 4, 5},
            // {6, 7, 8}

            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board b2 = new Board(tiles2);
        System.out.println(b.equals(b2));      
        
        
        
    }
}