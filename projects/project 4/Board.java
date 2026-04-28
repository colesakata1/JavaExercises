import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

import java.util.Arrays;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    // number of tiles out of place
    public int hamming;
    // sum of differences in row and column indices between tiles and where they should be
    public int manhattan;
    // keeps track of where the tiles are in a 2D array
    private int[][] tiles;
    // size of the board (n-by-n)
    private int n;
    // position of the blank spot in row major order
    private int blankPos;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.n = tiles.length;
        this.blankPos = 0;
        this.hamming = hamming();
        this.manhattan = manhattan();

    }

    // Returns the size of this board.
    public int size() {
        return this.n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return this.tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        hamming = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (tileAt(i, j) == 0) {
                    blankPos = i * this.n + j + 1;
                } else if (((i * this.n) + j + 1) != (tileAt(i, j))) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (tileAt(i, j) == 0) {
                    blankPos = i * this.n + j+1;
                }
                if (tileAt(i, j) != 0) {
                    int thisi = (tileAt(i, j)-1)/this.n;
                    int thisj = (int) (tileAt(i, j)-1) % this.n;
                    manhattan += (Math.abs(thisi - i) + Math.abs(thisj - j));
                }
            }
        }
        return manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return hamming() == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int[] rowmajor = new int[this.n*this.n-1];
        int count = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (count < this.n*this.n-1) {
                    if (tileAt(i, j) != 0) {
                        rowmajor[count] = tileAt(i, j);
                        count++;
                    }
                }
            }
        }
       int inv =(int) Inversions.count(rowmajor);
       if ((this.n % 2 != 0)) {
           return inv % 2 == 0;
       } else {
           int blankrow = Math.floorDiv(blankPos - 1, this.n);
           int sum = inv + blankrow;
           boolean solvable = sum % 2 != 0;
           return (sum % 2 != 0);
       }
    }


    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        dsa.LinkedQueue<Board> q = new LinkedQueue<>();
        int blankPosi = Math.floorDiv(this.blankPos -1, this.n);
        int blankPosj = ((this.blankPos - 1)%this.n);
        int n1;
        int n2;
        int n3;
        int n4;

        if ((blankPosi + 1) < this.n) {
            int[][] b = cloneTiles();
            n2 = b[(blankPosi + 1)][blankPosj];
            b[blankPosi][blankPosj] = n2;
            b[(blankPosi + 1)][blankPosj] = 0;
            q.enqueue(new Board(b));
        }
        if ((blankPosi - 1) >= 0) {
            int[][] b = cloneTiles();
            n1 = b[(blankPosi - 1)][blankPosj];
            b[blankPosi][blankPosj] = n1;
            b[(blankPosi - 1)][blankPosj] = 0;
            q.enqueue(new Board(b));
        }

        if ((blankPosj + 1) < this.n) {
            int[][] b = cloneTiles();
            n4 = b[blankPosi][blankPosj+1];
            b[blankPosi][blankPosj] = n4;
            b[blankPosi][blankPosj+1] = 0;
            q.enqueue(new Board(b));
        }
        if ((blankPosj - 1) >= 0) {
            int[][] b = cloneTiles();
            n3 = b[blankPosi][(blankPosj-1)];
            b[blankPosi][blankPosj] = n3;
            b[blankPosi][blankPosj-1] = 0;
            q.enqueue(new Board(b));
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        if (Arrays.deepEquals(((Board) other).tiles, this.tiles)) {
            return true;
        } else {
            return false;
        }
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < this.n - 1) {
                    s.append(" ");
                }
            }
            if (i < this.n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                clone[i][j] = tileAt(i,j);
            }
        }
        return clone;
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
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
