import dsa.BasicST;
import dsa.LinkedQueue;
import stdlib.StdIn;
import stdlib.StdOut;

import java.util.Arrays;
import java.util.List;

public class ArrayST<Key, Value> implements BasicST<Key, Value> {
    private Key[] keys;     // keys in the symbol table
    private Value[] values; // the corresponding values
    private int n;          // number of key-value pairs

    // Constructs an empty symbol table.
    public ArrayST() {
        this.keys = (Key[]) new Object[2];
        this.values = (Value[]) new Object[2];
        this.n = 0;
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return this.n == 0;
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return this.n;
    }

    // Inserts the key and value pair into this symbol table.
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        if (this.contains(key)) {
            delete(key);
        }
        this.n++;
        resize(n);
        this.keys[n-1] = key;
        this.values[n-1] = value;
    }

    // Returns the value associated with key in this symbol table, or null.
    public Value get(Key key) {
        int index;
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        } else {
            index = Arrays.binarySearch(this.keys, key);
            if (index == -1) {
                return null;
            } else {
                return this.values[index];
            }
        }
    }


    // Returns true if this symbol table contains key, and false otherwise.
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return Arrays.binarySearch(this.keys, key) >= 0;
    }

    // Deletes key and the associated value from this symbol table.
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        int index = Arrays.binarySearch(this.keys, key);
        n--;
        for (int i = index; i < this.n - 1; i++) {
            this.keys[i] = this.keys[i + 1];
            this.values[i] = this.values[i + 1];
            this.keys[this.n - 1] = null;
            this.values[this.n - 1] = null;
        }
        resize(n);
    }

    // Returns all the keys in this symbol table.
    public Iterable<Key> keys() {
        Iterable<Key> keylist = Arrays.asList(this.keys);
        return keylist;
    }

    // Resizes the underlying arrays to capacity.
    private void resize(int capacity) {
        Key[] tempk = (Key[]) new Object[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        values = tempv;
        keys = tempk;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        ArrayST<String, Integer> st = new ArrayST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}