import java.util.Arrays;
import java.util.HashMap;
import edu.princeton.cs.algs4.HexDump;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.lang.IllegalArgumentException;

public class CircularSuffixArray {

    // circular suffix array of s

    private String[] arr = new String[1];
    private HashMap<String, Integer> map = null;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        arr = new String[s.length()];
        map = new HashMap<String, Integer>();
        String temp = null;
        for (int i = 0; i < s.length(); i++) {
            temp = (s.substring(i, s.length()) + s.substring(0, i));
            arr[i] = temp;
            map.put(temp, i);
        }
        Arrays.sort(arr);
        // for (int i = 0; i < s.length(); i++) {
        // System.out.println(arr[i]);
        // }
    }

    // length of s
    public int length() {
        return arr.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException();
        }
        if (arr[0].equals(arr[arr.length - 1])) {
            return i;
        }
        return map.get(arr[i]);
    }

    // unit testing (required)
    public static void main(String[] args) {
        // // BinaryStdIn.readString();
        // CircularSuffixArray s = new CircularSuffixArray("ARD!RCAAAABB");
        // String temp = "ARD!RCAAAABB";
        // for (int i = 0; i < temp.length(); i++) {
        // System.out.println(s.index(i));
        // }
    }

}
