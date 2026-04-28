import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.

    public static void main(String[] args) {
        LinkedDeque<String> d =  new LinkedDeque<>();
        d.addFirst(args[0]);

        for (int i = 1; i < args.length; i++) {
            String w = args[i];

            if (less(w, d.peekFirst())) {
                d.addFirst(w);
            } else if (less(d.peekLast(), w)) {
                d.addLast(w);
            } else {
                LinkedStack<String> s = new LinkedStack<>();
                while (less(d.peekFirst(), w)) {
                    s.push(d.removeFirst());
                }
                d.addFirst(w);
                while (!s.isEmpty()) {
                    d.addFirst(s.pop());
                }

            }
        }
        LinkedDeque<String> s = new LinkedDeque<>();
        while (!d.isEmpty()) {
            StdOut.println(d.peekFirst());
            s.addLast(d.removeFirst());
        }
        d = s;
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
