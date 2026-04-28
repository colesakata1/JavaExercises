import stdlib.StdOut;

public class GreatCircle {
    // Entry point.
    public static void main(String[] args) {
        // Accept x1 (double), y1 (double), x2 (double), and y2 (double) as command-line arguments.
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);

        // Convert the angles to radians.
        x1 = x1*Math.PI/180;
        y1 = y1*Math.PI/180;
        x2 = x2*Math.PI/180;
        y2 = y2*Math.PI/180;

        // Calculate great-circle distance d.
        double trig = Math.sin(x1)*Math.sin(x2)+Math.cos(x1)*Math.cos(x2)*Math.cos(y1-y2);
        double d = 6359.83*Math.acos(trig);


        // Write d to standard output.
        StdOut.println(d);
    }
}