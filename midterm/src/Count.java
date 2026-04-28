
import stdlib.StdIn;
import stdlib.StdOut;

public class Count {
    // Entry point.
    public static void main(String[] args) {
        String s = args[0];
        int l = Integer.parseInt(args[1]);
        String[] a = StdIn.readAllStrings();
        StdOut.println(stringsOfLength(a, l));
        StdOut.println(frequencyOf(a, s));
    }

    private static int stringsOfLength(String[] a, int l) {
        int count = 0;
        for (String string : a) {
            int strcount = 0;
            for (char c : string) {
                strcount++;
            }
            if (strcount >= l) {
                count++;
            }
        }
        return count;
    }

    private static int frequencyOf(String[] a, String s) {
        int freq = 0;
        for (String string : a) {
            if (string.equals(s)) {
                freq++;
            }
        }
        return freq;
    }
}
