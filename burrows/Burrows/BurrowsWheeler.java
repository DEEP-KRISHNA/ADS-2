import java.util.Arrays;
import edu.princeton.cs.algs4.HexDump;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Hashtable;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String inpt = BinaryStdIn.readString();
        CircularSuffixArray suff = new CircularSuffixArray(inpt);
        for (int i = 0; i < inpt.length(); i++) {
            if (suff.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < inpt.length(); i++) {
            if (suff.index(i) == 0) {
                BinaryStdOut.write(inpt.charAt(inpt.length() - 1));
            } else {
                BinaryStdOut.write(inpt.charAt(suff.index(i) - 1));
            }

        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
        BinaryStdIn.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String postemp = BinaryStdIn.readString();

        char post_char[] = postemp.toCharArray();
        char str_char[] = post_char.clone();

        Arrays.sort(str_char);
        int[] next = new int[post_char.length];

        Hashtable<Character, ArrayList<Integer>> hash = new Hashtable<Character, ArrayList<Integer>>();

        for (int i = 0; i < post_char.length; i++) {
            if (hash.get(post_char[i]) != null) {
                hash.get(post_char[i]).add(i);
            } else {
                ArrayList<Integer> alb = new ArrayList<Integer>();
                alb.add(i);
                hash.put(post_char[i], alb);
            }
        }

        for (int i = 0; i < next.length; i++) {
            next[i] = hash.get(str_char[i]).get(0);
            hash.get(str_char[i]).remove(0);
        }

        int[] result = new int[post_char.length];
        int c = first;
        for (int i = 0; i < result.length; i++) {
            result[i] = str_char[c];
            c = next[c];
            BinaryStdOut.write((char) result[i]);
        }

        BinaryStdOut.flush();
        BinaryStdOut.close();
        BinaryStdIn.close();

        // System.out.println(actualpos);
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        }
    }

}