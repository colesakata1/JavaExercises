import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    // Size of array/WQUUF
    private int n;
    // Array to keep track of whether the site is open or not
    private final boolean[][] open;
    // Int counter to track how many sites are open
    private int openSites;
    // Our main WQUUF with a source and sink to keep track of whether the system percolates
    private final WeightedQuickUnionUF uf;
    /* Another WQUUF (without source or sink to check if the site is connected to
    * the top row, to avoid the backwash issue
     */
    private final WeightedQuickUnionUF backwash;
    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Illegal n");
        } else {
            // Create new WQUUF with size n^2 + 2
            this.uf = new WeightedQuickUnionUF((int) (Math.pow(n, 2)+2));
            this.n = n;
            // All sites set to closed (false) by default
            this.open = new boolean[n][n];
            this.openSites = 0;
            this.backwash = new WeightedQuickUnionUF((int) (Math.pow(n, 2)));
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || j < 0 || i >= this.n || j >= this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        } if (!isOpen(i, j)) {
            // Open this site and increase the open site counter
            this.open[i][j] = true;
            this.openSites++;
            // If in first row connect to source
            if (i == 0) {
                this.uf.union(0, encode(i, j));
            }
            // Else if in last row, connect to sink
            if (i == this.n-1) {
                uf.union(encode(i, j), (int) (Math.pow(this.n, 2) + 1));
            }
            /* Then, loop through all direct neighbors (first up and down, then left and right)
            * and connect them if it is open
            */
            for (int k = i-1; k < i+2; k++) {
                // Make sure your indices are in bounds
                if (k >= 0 && k < this.n) {
                    if (this.open[k][j] && k != i) {
                        this.backwash.union(k*this.n+j, i*this.n+j);
                        this.uf.union(encode(k, j), encode(i, j));
                    }
                }
            }
            for (int l = j-1; l < j+2; l++) {
                // Make sure your indices are in bounds
                if (l >= 0 && l < this.n) {
                    if (this.open[i][l] && l != j) {
                        this.backwash.union(i*n+l, i*n+j);
                        this.uf.union(encode(i, l), encode(i, j));
                    }
                }
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= this.n || j < 0 || j >= this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        } else {
            return this.open[i][j];
        }
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        boolean connected = false;
        if (i < 0 || i >= this.n || j < 0 || j >= this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        } else {
            for (int k = 0; k < this.n; k++) {
                if (this.backwash.connected(i*this.n+j, k)) {
                    connected = true;
                    break;
                }
            }
        }
        return (this.uf.connected(encode(i, j), 0) && connected);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return this.uf.connected(0, (int) Math.pow(this.n, 2)+1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return (i*this.n+j+1);
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}