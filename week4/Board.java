import java.util.Arrays;

public class Board {
    private final int n;
    private final int[][] tiles;

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
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
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
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
        };
        Board b = new Board(tiles);
        System.out.println(b);       
    }
}