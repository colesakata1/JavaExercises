import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<>();

        if (!mode.equals('+') && !mode.equals('-')) {
            throw new IllegalArgumentException("illegal mode");
        } else if (mode.equals('+')) {
            for (int i = lo; i<=hi; i++) {
                q.enqueue(i);
            }

            for (int i = 0; i < k; i++) {
                StdOut.println(q.sample());
            }
        } else {
            for (int i = lo; i <= hi; i++) {
                q.enqueue(i);
            }
            for (int i = 0; i < k; i++) {
                StdOut.println(q.dequeue());
            }
        }
    }
}
