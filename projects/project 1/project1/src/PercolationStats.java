import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
        // Array of ratios of open sites to total at percolation
    private final double[]x;
    // Number of times to experiment
    private final int m;
    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        // Throw exception if n or m are too small
        if (n < 1 || m < 1) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        // Instantiate variables
        this.x = new double[m];
        this.m = m;

        // Loop through this m times
        for (int f = 0; f < this.m; f++) {
            // Create a new UFPercolation class
            UFPercolation uf = new UFPercolation(n);
            int i;
            int j;
            // Loops until every single site has been opened
            while(!uf.percolates()) {
                // Randomly select i and j between 0 and n-1
                i = StdRandom.uniform(n);
                j = StdRandom.uniform(n);
                // Open the site at i,j
                uf.open(i, j);
            }
            // This x is the ratio between the open sites and total sites
            this.x[f] = uf.numberOfOpenSites()/((double) (n*n));
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        // Take the mean of the double[] x
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        // Find the standard deviation of the double[] x
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean()-((1.96*this.stddev())/Math.sqrt(this.m));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean()+((1.96*stddev())/Math.sqrt(this.m));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.5f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.5f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.5f, %.5f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}