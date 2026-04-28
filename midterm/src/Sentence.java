import java.util.Comparator;
import java.util.Iterator;

import stdlib.StdOut;

public class Sentence implements Comparable<Sentence>, Iterable<String> {
    private String s; // the sentence
    private String[] words; // words in the sentence

    public Sentence(String s) {
        this.s = s;
        this.words = s.split("\\s+");
    }

    public int charCount() {
        int count = 0;
        for (char c : this.s) {
            count++;
        }
        return count;
    }

    public int wordCount() {
        return this.words.length;
    }

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
        return this.compareTo(other);
    }

    public String toString() {
        return charCount() + ":" + s;
    }

    public int compareTo(Sentence other) {
        return this.wordCount() - other.wordCount();
    }

    public static Comparator<Sentence> charCountOrder() {
        return this.CharCountOrder;
    }

    public Iterator<String> iterator() {
        return this.WordIterator;
    }

    private static class CharCountOrder implements Comparator<Sentence> {
        public int compare(Sentence s1, Sentence s2) {
            return s1.charCount() - s2.charCount();
        }
    }

    private class WordIterator implements Iterator<String> {
        private int i; // index of current letter
        public WordIterator() {
            this.i = 0;
        }

        public boolean hasNext() {
            return this.i < this.words.length -2;
        }
        
        public String next() {
            if(this.hasNext()) {
            return this.iteratorwords[i];
            i++;
        }
    }

    // Unit tests the data type.
    public static void main(String[] args) {
        Sentence s1 = new Sentence("abc def ghi jkl mno");
        Sentence s2 = new Sentence("abcdefg hijklmn opqrst");
        Sentence s3 = new Sentence("abc def ghi jkl mno");
        StdOut.println(s1);
        StdOut.println(s2);
        StdOut.println(s3);
        StdOut.println(s1.wordCount());
        StdOut.println(s1.equals(s3));
        StdOut.println(s1.compareTo(s2));
        StdOut.println(Sentence.charCountOrder().compare(s1, s3));
        for (String word : s3) {
            StdOut.print(word + " ");
        }
        StdOut.println();
    }
}
