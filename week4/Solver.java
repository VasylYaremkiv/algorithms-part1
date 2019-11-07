import java.util.ArrayList;
import java.util.Collections;

// import edu.princeton.cs.algs4.BlackFilter;
// import java.util.Iterator;
// import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {
    private final Board board;
    private final ArrayList<Board> boards = new ArrayList<Board>();
    private boolean solved;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.board = initial;
        this.findSolution();
    }

    private BoardNode findNextNode(BoardNode bn, MinPQ<BoardNode> pq) {
        for (Board b : bn.board.neighbors()) {
            if (bn.parent == null || !bn.parent.board.equals(b)) {
                // pq.insert(new Node(b, node));
                pq.insert(new BoardNode(b, bn));
            }
        }

        return pq.delMin();
    }

    private void findSolution() {
        MinPQ<BoardNode> pq = new MinPQ<BoardNode>();
        BoardNode bn = new BoardNode(this.board, null);

        MinPQ<BoardNode> pqTwin = new MinPQ<BoardNode>();
        BoardNode bnTwin = new BoardNode(this.board.twin(), null);

        while (true) {
            if(bn.board.isGoal()){
                break;
            }
            bn = findNextNode(bn, pq);

            if(bnTwin.board.isGoal()){
                break;
            }
            bnTwin = findNextNode(bnTwin, pqTwin);
        }

        if (bnTwin.board.isGoal()) {
            this.solved = false;
        } else {
            this.solved = true;
            while (bn != null) {
                this.boards.add(bn.board);
                bn = bn.parent;
            }
        }


        // ArrayList<Board> checkBoards = new ArrayList<Board>();
        // checkBoards.add(this.board);
        // // Board checkBoard = this.board;

        // ArrayList<BoardNode> BoardNodes = new ArrayList<BoardNode>();
        // BoardNodes.add(bn);


        // while (true) {

        // }


        // do {
        //     // pq = new MinPQ<BoardNode>();
        //     for (Board b : bn.board.neighbors()) {
        //         if (bn.parent == null || !bn.parent.board.equals(b)) {
        //             // pq.insert(new Node(b, node));
        //             pq.insert(new BoardNode(b, bn));
        //         }

        //         // if (b.equals(unsolvableBoard)) {
        //         //     return false;
        //         // }
        //         // if (checkBoards.contains(b)) {
        //         //     System.out.println("board present");
        //         //     continue;
        //         // }
        //         // // if (bn.parent != null && b.equals(previousBoardNode.board)) {
        //         // //     continue;
        //         // // }
        //         // pq.insert(new BoardNode(b, bn));
        //         // checkBoards.add(b);

        //         // System.out.println(board);
        //     }
        //     bn = pq.delMin();
        //     BoardNodes.add(bn);
        //     // moves++;
        //     // previousBoardNode = bn;
        //     // checkBoard = bn.board;
        //     // this.boards.add(checkBoard);
    
        //     // moves++;
        // } while (!bn.board.isGoal() && checkBoards.size() < 100);


        // while (bn != null) {
        //     // this.boards.add(previousBoardNode.board);
        //     System.out.println(bn.board);
        //     System.out.println("moves: " + bn.moves + " priority: " + bn.priority);
        //     bn = bn.parent;
        // }
        // // for (BoardNode boardnode : pq) {
        // //     // System.out.println(boardnode.priority);
        // //     System.out.println("moves: " + boardnode.moves + " priority: " +boardnode.priority);
        // //     // System.out.println(boardnode.board);
        // // }


        // // if (bn.board.isGoal() && true) {
        // //     while (bn != null) {
        // //         // this.boards.add(previousBoardNode.board);
        // //         System.out.println(bn.board);
        // //         bn = bn.parent;
        // //     }    
        // // }


        // return true;



        // MinPQ<BoardNode> pq = new MinPQ<BoardNode>();

        // Board checkBoard = this.board;
        // BoardNode previousBoardNode = new BoardNode(this.board, 0, null);

        // int moves = 1;
        // BoardNode bn;
        // ArrayList<Board> checkBoards = new ArrayList<Board>();
        // checkBoards.add(this.board);

        // do {
        //     // pq = new MinPQ<BoardNode>();
        //     for (Board b : checkBoard.neighbors()) {
        //         if (b.equals(unsolvableBoard)) {
        //             return false;
        //         }
        //         if (checkBoards.contains(b)) {
        //             System.out.println("board present");
        //             continue;
        //         }
        //         if (previousBoardNode != null && b.equals(previousBoardNode.board)) {
        //             continue;
        //         }
        //         pq.insert(new BoardNode(b, moves, previousBoardNode));
        //         checkBoards.add(b);

        //         // System.out.println(board);
        //     }
        //     bn = pq.delMin();
        //     previousBoardNode = bn;
        //     checkBoard = bn.board;
        //     // this.boards.add(checkBoard);
    
        //     moves++;
        // } while (!checkBoard.isGoal() && checkBoards.size() < 20);

        // while (previousBoardNode.parent != null) {
        //     this.boards.add(previousBoardNode.board);
        //     previousBoardNode = previousBoardNode.parent;
        // }

        // System.out.println("boardnode.board:");

        // for (BoardNode boardnode : pq) {
        //     // System.out.println(boardnode.priority);
        //     System.out.println("moves: " + boardnode.moves + " priority: " +boardnode.priority);
        //     // System.out.println(boardnode.board);
        // }

        // return true;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solved;
    }

    private class BoardNode implements Comparable<BoardNode> {
        final int priority;
        final int moves;
        final Board board;
        final BoardNode parent;

        public BoardNode(Board b, BoardNode parent) {
            this.board = b;
            if (parent == null) {
                this.moves = 0;
            } else {
                this.moves = parent.moves + 1;
            }
            this.priority = this.moves + b.manhattan();
            this.parent = parent;
        }

        public int compareTo(BoardNode that) {
            // return this.priority - that.priority;

            int c = (board.manhattan() + moves) - (that.board.manhattan() + that.moves);
            if (c == 0) {
                return board.manhattan() - that.board.manhattan();
            }
            return c;
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
        Collections.reverse(this.boards);
        return this.boards;
    }

    // test client (see below) 
    public static void main(String[] args) {
        int[][] tiles = {
            // {0, 1, 2},
            // {3, 4, 5},
            // {6, 7, 8}

            // {1, 2, 3}, 
            // {0, 7, 6},
            // {5, 4, 8}


            {1, 0, 4}, 
            {2, 7, 5},
            {3, 6, 8}

            // {0, 1, 2, 3},
            // {5, 6, 7, 4}, 
            // {9, 10, 11, 8},
            // {13, 14, 15, 12}
        };
        Board b = new Board(tiles);
        System.out.println(b);   

        Solver solver = new Solver(b);
        System.out.println("Minimum number of moves = " + solver.moves());

        // // print solution to standard output
        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
        // System.out.println("Minimum number of moves = " + solver.moves());




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