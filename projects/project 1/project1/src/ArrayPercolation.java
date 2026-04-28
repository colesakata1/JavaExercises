import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class    ArrayPercolation implements Percolation {
    // n is the size of the array (n^2 total sites)
    private final int n;
    // 2D array keeping track of which sites are open
    private final boolean[][] open;
    // Number of open sites (counter increased when a site opened)
    private int openSites;

    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        // Throw exception if n is too small(less than 1)
        if (n < 1) {
            throw new IllegalArgumentException("Illegal n");
        } else {
            this.n = n;
            open = new boolean[n][n];
            openSites = 0;
        }
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || j < 0 || i >= this.n || j >= this.n) {
            // If i or j are out of bounds throw exception
            throw new IndexOutOfBoundsException("Illegal i or j");
        } else if (open[i][j]) {
            return;
        } else {
            // Otherwise, open this site and increment the openSites counter.
            open[i][j] = true;
            openSites++;
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || j < 0 || i >= this.n || j >= this.n) {
            // Again, out of bounds exception thrown if i or j are out of bounds
            throw new IndexOutOfBoundsException("Illegal i or j");
        } else {
            // Otherwise return the boolean value of the site
            return (open[i][j]);
        }
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || j < 0 || i >= this.n || j >= this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        } else {
            /* Create new 2D Array to track full sites.
             * floodFill with each of the top row sites as a seed
             */
            boolean[][] full = new boolean[this.n][this.n];
            for (int k = 0; k < this.n; k++) {
                floodFill(full, 0, k);
            }
            return full[i][j];
        }
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        // Return the openSites counter
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // For the entire row
        for (int j = 0; j < this.n; j++) {
            // If the bottom row is filled return true (this means it percolates)
            if (isFull(this.n - 1, j)) {
                return true;
            }
        }
        // Otherwise return false
        return false;
    }

    // Recursively flood fills full[][] using depth-first exploration, starting at (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        // Return if indices are out of bounds

        boolean isIValid = (i >= 0) && (i < this.n);
        boolean isJValid = (j >= 0) && (j < this.n);

        if (!(isIValid && isJValid)) {
            return;
        } else if (!open[i][j]) {
            // Return if site is not open
            return;
        } else if (full[i][j]) {
            // Return if site is already full
            return;
        } else {
            // Set site to full
            full[i][j] = true;
            /* Recursively flood fill neighbors in a crossing pattern, first looking at the band
            * of sites one above, through the one below
            * Note: it is not a problem we call flood fill on the original site twice again, as our
            * flood fill simply returns if the site is already full
            */
            for (int k = i - 1; k < i + 2; k++) {
                if (k >= 0 && k < this.n) {
                    floodFill(full, k, j);
                }
            }
            // Then looking at the left and right site
            for (int l = j - 1; l < j + 2; l++) {
                if (l >= 0 && l < this.n) {
                    floodFill(full, i, l);
                }
            }
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
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
