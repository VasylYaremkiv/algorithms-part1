import java.util.Arrays;
import java.util.ArrayList;
// import java.util.Iterator;

public class Board {
    private static final int FREE = 0;

    private final int n;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;

        if (this.n < 2 || this.n >= 128) {
            throw new IllegalArgumentException();
        }

        this.tiles = copyTiles(tiles, this.n);
    }
                                           
    // string representation of this board
    public String toString() {
        int numDigits = (int) Math.log10(this.n * this.n - 1) + 2;
        if (numDigits <= 1) {
            numDigits = 2;
        }
        StringBuilder result = new StringBuilder();
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

                if (this.tiles[i][j] != position) {
                    result++;
                }
                position++;
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
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board b = (Board) y;
        return this.n == b.n && Arrays.deepEquals(this.tiles, b.tiles);

        // if (this.n != b.n) {
        //     return false;
        // }
        // for (int i = 0; i < this.n; i++) {
        //     if (!Arrays.equals(this.tiles[i], b.tiles[i])) {
        //         return false;
        //     }    
        // }
 
        // return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> result = new ArrayList<Board>();

        int freeTile = findTile(FREE);
        int i = freeTile / this.n;
        int j = freeTile % this.n;
        int[][] tmpTiles;

        if (i > 0) {
            tmpTiles = copyTiles(this.tiles, this.n);
            tmpTiles[i][j] = tmpTiles[i - 1][j];
            tmpTiles[i - 1][j] = FREE;
            result.add(new Board(tmpTiles));
        }
        if (i < this.n - 1) {
            tmpTiles = copyTiles(this.tiles, this.n);
            tmpTiles[i][j] = tmpTiles[i + 1][j];
            tmpTiles[i + 1][j] = FREE;
            result.add(new Board(tmpTiles));
        }

        if (j > 0) {
            tmpTiles = copyTiles(this.tiles, this.n);
            tmpTiles[i][j] = tmpTiles[i][j - 1];
            tmpTiles[i][j - 1] = FREE;
            result.add(new Board(tmpTiles));
        }
        if (j < this.n - 1) {
            tmpTiles = copyTiles(this.tiles, this.n);
            tmpTiles[i][j] = tmpTiles[i][j + 1];
            tmpTiles[i][j + 1] = FREE;
            result.add(new Board(tmpTiles));
        }

        return result;
    }

    private int[][] copyTiles(int[][] sourceTiles, int length) {
        int[][] result = new int[length][length];

        for (int i = 0; i < length; i++) {
            result[i] = Arrays.copyOf(sourceTiles[i], length);
        }
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return new Board(this.tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
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
        for (Board board : b.neighbors()) {
            System.out.println(board);
        }

        int[][] tiles2 = {
            // {0, 1, 2},
            // {3, 4, 5},
            // {6, 7, 8}

            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board b2 = new Board(tiles2);
        System.out.println(b.equals(b2));      
        System.out.println(b.equals(b.twin()));      
        
        

    }
}