import java.util.ArrayList;

// import edu.princeton.cs.algs4.BlackFilter;
// import java.util.Iterator;
// import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {
    private final Board board;
    private final ArrayList<Board> boards = new ArrayList<Board>();
    private final boolean solved;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.board = initial;
        this.solved = this.findSolution();
    }

    private boolean findSolution() {
        int length = this.board.dimension();

        Board unsolvableBoard;
        int[][] unsolvableTiles = new int[length][length];
        int position = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                unsolvableTiles[i][j] = position++;
            }
        }

        unsolvableTiles[length - 1][length - 1] = 0;
        if (length > 2) {
            unsolvableTiles[length - 1][length - 2] = length * length - 2;
            unsolvableTiles[length - 1][length - 3] = length * length - 1;    
        } else {
            unsolvableTiles[length - 1][length - 2] = length * length - 2;
            unsolvableTiles[length - 2][length - 1] = length * length - 1;    
        }

        unsolvableBoard = new Board(unsolvableTiles);

        if (unsolvableBoard.equals(this.board)) {
            return false;
        }


        if (this.board.isGoal()) {
            this.boards.add(this.board);
            return true;
        }

        MinPQ<BoardNode> pq;

        Board checkBoard = this.board;
        Board previousBoard = null;

        int moves = 1;
        BoardNode bn;
        this.boards.add(this.board);

        do {
            pq = new MinPQ<BoardNode>();
            for (Board b : checkBoard.neighbors()) {
                if (b.equals(unsolvableBoard)) {
                    return false;
                }
                if (b.equals(previousBoard)) {
                    continue;
                }
                pq.insert(new BoardNode(b, moves));
                // System.out.println(board);
            }
            bn = pq.delMin();
            previousBoard = checkBoard;
            checkBoard = bn.board;
            this.boards.add(checkBoard);
    
            moves++;
        } while (!checkBoard.isGoal());
        
        return true;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solved;
    }

    private class BoardNode implements Comparable<BoardNode> {
        final int priority;
        // final int moves;
        final Board board;

        public BoardNode(Board b, int m) {
            this.board = b;
            // this.moves = m;
            this.priority = m + b.manhattan();
        }

        // public int compare(Student a, Student b) { 
        //     return a.rollno - b.rollno; 
        // }
        public int compareTo(BoardNode that) {
            return this.priority - that.priority;
        }

    }

    // min number of moves to solve initial board
    public int moves() {
        if (this.solved) {
            return this.boards.size() - 1;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        // ArrayList<Board> result = new ArrayList<Board>();

        return this.boards;
    }

    // private checkIfSolvable(Board b) {
    //     if (b.manhattan() == 2 && b.hamming() == 2) {
    //         return false;
    //     }

    //     return true;
    // }

    // test client (see below) 
    public static void main(String[] args) {
        int[][] tiles = {
            // {0, 1, 2},
            // {3, 4, 5},
            // {6, 7, 8}

            // {1, 2, 3}, 
            // {0, 7, 6},
            // {5, 4, 8}


            {1, 0, 5}, 
            {2, 7, 4},
            {3, 6, 8}

            // {0, 1, 2, 3},
            // {5, 6, 7, 4}, 
            // {9, 10, 11, 8},
            // {13, 14, 15, 12}
        };
        Board b = new Board(tiles);
        System.out.println(b);   

        Solver solver = new Solver(b);
        // System.out.println("Minimum number of moves = " + solver.moves());

        // print solution to standard output
        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
        System.out.println("Minimum number of moves = " + solver.moves());




        // // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         tiles[i][j] = in.readInt();
        //     }
        // }
        // Board initial = new Board(tiles);

        // // solve the puzzle
        // Solver solver = new Solver(initial);

        // // print solution to standard output
        // if (!solver.isSolvable()) {
        //     System.out.println("No solution possible");
        // } else {
        //     System.out.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution()) {
        //         System.out.println(board);
        //     }
        // }
    }
}