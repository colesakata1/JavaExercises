import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a random queue, implemented using a resizing array as the underlying
// data structure.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    // Constructs an empty random queue.
    public ResizingArrayRandomQueue() {
        q = (Item[]) new Object[2];
        this.n = q.length;
    }

    // Returns true if this queue is empty, and false otherwise.
    public boolean isEmpty() {
        return this.n < 1;
    }

    // Returns the number of items in this queue.
    public int size() {
        return this.n;
    }

    // Adds item to the end of this queue.
    public void enqueue(Item item) {
        resize(n+1);
        n++;
        q[n-1] = item;
    }

    // Returns a random item from this queue.
    public Item sample() {
        return q[StdRandom.uniform(n)];
    }

    // Removes and returns a random item from this queue.
    public Item dequeue() {
        int i = StdRandom.uniform(n);
        Item returnItem = q[i];
        q[i] = null;
        resize(n-1);
        n--;
        return returnItem;
    }

    // Returns an independent iterator to iterate over the items in this queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // Returns a string representation of this queue.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        private int index;

        // Constructs an iterator.
        public RandomQueueIterator() {
            index = 0;

        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return index < n;
        }

        // Returns the next item.
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                index++;
                return q[index];
            }
        }
    }

    // Resizes the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            int r = StdRandom.uniform(10000);
            q.enqueue(r);
            sum += r;
        }
        int iterSumQ = 0;
        for (int x : q) {
            iterSumQ += x;
        }
        int dequeSumQ = 0;
        while (q.size() > 0) {
            dequeSumQ += q.dequeue();
        }
        StdOut.println("sum       = " + sum);
        StdOut.println("iterSumQ  = " + iterSumQ);
        StdOut.println("dequeSumQ = " + dequeSumQ);
        StdOut.println("iterSumQ + dequeSumQ == 2 * sum? " + (iterSumQ + dequeSumQ == 2 * sum));
    }
}
