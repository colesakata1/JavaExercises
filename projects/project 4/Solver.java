import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

import java.util.Iterator;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    // shortest path to solution
    LinkedStack<Board> solution;
    // length of path
    private int moves;

    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {
        if (board == null) {
            throw new NullPointerException("board is null");
        }
        if (!board.isSolvable()) {
            throw new IllegalArgumentException("board is unsolvable");
        }
        this.solution = new LinkedStack<>();
        Board initial = board;
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        int count = 0;
        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            if (node.board.isGoal()) {
                StdOut.println("moves: " + node.moves);
                this.moves = node.moves;
                LinkedStack<Board> temp = new LinkedStack<>();
                while (node.previous != null) {
                    temp.push(node.board);
                    node = node.previous;
                }
                solution = temp;
                break;

            } else {
                count++;
                Iterator<Board> it = node.board.neighbors().iterator();
                while (it.hasNext()) {
                    Board movedboard = it.next();
                    if (!(movedboard.equals(node.board))) {
                        pq.insert(new SearchNode(movedboard, count, node));
                    }
                }
            }
        }
    }


    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        if (this.solution == null) {
            return 0;
        } else {
            return this.solution.size();
        }

    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        Iterable<Board> iter = this.solution;
        return iter;
    }

    // A data type that represents a search node in the grame tree. Each node includes a
    // reference to a board, the number of moves to the node from the initial node, and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {
        // board state this node represents
        private Board board;
        // moves it took from initial to get to this state
        private int moves;
        // board state this came from
        private SearchNode previous;


        // Constructs a new search node.
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        //   Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            int thissum = this.board.manhattan + this.moves;
            int othersum = other.board.manhattan + other.moves;
            if (thissum - othersum == 0) {
                return 0;
            } else {
                return thissum - othersum;
            }
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
