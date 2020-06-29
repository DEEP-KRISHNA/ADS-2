import edu.princeton.cs.algs4.HexDump;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to
    // standard output
    public static void encode() {

        char[] ascii = new char[256];
        char inpt = ' ';
        int pos = 0;
        for (int i = 0; i <= 255; i++) {
            ascii[i] = (char) i;
        }

        String strinpt = BinaryStdIn.readString();
        int strpos = 0;

        while (strpos < strinpt.length()) {
            inpt = strinpt.charAt(strpos);
            for (int i = 0; i <= 255; i++) {
                if (inpt == ascii[i]) {
                    pos = i;
                    break;
                }
            }
            // System.out.println(inpt);
            BinaryStdOut.write((char) pos);
            // System.out.print((char) pos);
            for (int i = pos; i > 0; i--) {
                ascii[i] = ascii[i - 1];
            }
            ascii[0] = inpt;
            strpos++;
        }
        BinaryStdOut.flush();
        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to
    // standard output
    public static void decode() {
        // String inputstr = BinaryStdIn.readString().trim();
        // System.out.println(inputstr);
        // String postemp[] = inputstr.split(" ");
        // int posn[] = new int[postemp.length];
        // for (int i = 0; i < postemp.length; i++) {
        // posn[i] = Integer.parseInt(postemp[i].trim(), 16);
        // // System.out.println(posn[i]);
        // }

        // sandysandysandysandysandy

        char[] ascii = new char[256];
        char inpt = ' ';
        char inpttemp = ' ';
        // int pos = 0;
        for (int i = 0; i <= 255; i++) {
            ascii[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            // for (int s = 0; s < inputstr.length(); s++) {
            // inpt = (char) ascii[posn[s]];
            // inpt = inputstr.charAt(s);
            inpt = BinaryStdIn.readChar();
            inpttemp = ascii[inpt];
            BinaryStdOut.write(ascii[inpt]);
            // for (int i = 0; i <= 255; i++) {
            // if (inpt == ascii[i]) {
            // pos = i;
            // break;
            // }
            // }
            // System.out.println(inpt);
            // System.out.print((char) pos);
            for (int i = inpt; i > 0; i--) {
                ascii[i] = ascii[i - 1];
            }
            ascii[0] = inpttemp;
        }
        BinaryStdOut.flush();
        BinaryStdIn.close();
        BinaryStdOut.close();
        // sandysandysandysandysandy

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        }

    }

}