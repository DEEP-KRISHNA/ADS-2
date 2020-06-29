import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.BufferedReader;
// import edu.princeton.cs.algs4.TrieDST;
import java.lang.IllegalArgumentException;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    private TrieDST<Integer> dictST = new TrieDST<Integer>();
    private BoggleBoard solverBoard = null;
    private boolean[][] trav = new boolean[1][1];
    private ArrayList<String> collectStr = null;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < dictionary.length; i++) {
            dictST.put(dictionary[i], scoreOf(dictionary[i]));
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }
        solverBoard = board;
        collectStr = new ArrayList<String>();
        int brow = solverBoard.rows();
        int bcol = solverBoard.cols();
        StringBuffer sb;
        trav = new boolean[brow][bcol];
        for (int i = 0; i < brow; i++) {
            for (int j = 0; j < bcol; j++) {
                sb = new StringBuffer();
                // System.out.println(solverBoard.getLetter(i, j));
                recur(solverBoard, i, j, sb);
            }
        }
        // System.out.println(collectStr.toString());
        return collectStr;
    }

    private void recur(BoggleBoard board, int i, int j, StringBuffer sb) {
        if (trav[i][j] == false) {
            trav[i][j] = true;
            if (board.getLetter(i, j) == 'Q') {
                sb.append(Character.toString(board.getLetter(i, j)));
                sb.append(Character.toString('U'));
                // System.out.println(sb.toString());
                // // } else if (board.getLetter(i, j) == 'U' && sb.length() >= 2) {
                // // if (!((sb.charAt(sb.length() - 1) == 'U') && (sb.charAt(sb.length() - 2)
                // ==
                // // 'Q'))) {
                // // sb = sb.append("" + board.getLetter(i, j));
                // // // System.out.println(sb.toString());
                // // }
            } else {
                sb = sb.append("" + board.getLetter(i, j));
                // System.out.println(sb.toString());
            }
            if (dictST.get(dictST.root, sb.toString(), 0) != null) {
                if (dictST.contains(sb.toString()) && scoreOf(sb.toString()) != 0) {
                    if (!collectStr.contains(sb.toString())) {
                        collectStr.add(sb.toString());
                        // System.out.println(sb.toString());
                    }
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    recur(board, i - 1, j - 1, sb);
                }
                if (i + 1 < board.rows() && j + 1 < board.cols()) {
                    recur(board, i + 1, j + 1, sb);
                }
                if (i - 1 >= 0 && j + 1 < board.cols()) {
                    recur(board, i - 1, j + 1, sb);
                }
                if (i + 1 < board.rows() && j - 1 >= 0) {
                    recur(board, i + 1, j - 1, sb);
                }
                if (i - 1 >= 0) {
                    recur(board, i - 1, j, sb);
                }
                if (i + 1 < board.rows()) {
                    recur(board, i + 1, j, sb);
                }
                if (j - 1 >= 0) {
                    recur(board, i, j - 1, sb);
                }
                if (j + 1 < board.cols()) {
                    recur(board, i, j + 1, sb);
                }
                // trav[i][j] = false;
                trav[i][j] = false;
                // if (sb.length() >= 2) {
                // if (sb.charAt(sb.length() - 2) == 'Q') {
                if (board.getLetter(i, j) == 'Q') {
                    sb = sb.delete(sb.length() - 2, sb.length());
                    // }
                } else {
                    sb = sb.delete(sb.length() - 1, sb.length());
                }
                // return;
            } else {
                trav[i][j] = false;
                // if (sb.length() >= 2) {
                // if (sb.charAt(sb.length() - 2) == 'Q') {
                // sb = sb.delete(sb.length() - 2, sb.length());
                // }
                // } else {
                if (board.getLetter(i, j) == 'Q') {
                    sb = sb.delete(sb.length() - 2, sb.length());
                    // }
                } else {
                    sb = sb.delete(sb.length() - 1, sb.length());
                }
                // sb.delete(sb.length() - 1, sb.length());
                return;
            }

        }
        return;

    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        if (!dictST.contains(word)) {
            return 0;
        }
        int len = word.length();
        if (len < 3) {
            return 0;
        } else if (len == 3 || len == 4) {
            return 1;
        } else if (len == 5) {
            return 2;
        } else if (len == 6) {
            return 3;
        } else if (len == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void main(String[] args) {
        // try {
        // String fileName = "C:\\Users\\PINAKA\\Desktop\\boggle\\Boggle\\dict.txt";
        // File file = new File(fileName);
        // FileReader fr = new FileReader(file);
        // BufferedReader br = new BufferedReader(fr);
        // String line;
        // String concat = "";
        // while ((line = br.readLine()) != null) {
        // // process the line
        // concat = concat + line + "696";
        // }
        // String[] inptarr = concat.split("696");
        // BoggleSolver bs = new BoggleSolver(inptarr);
        // BoggleBoard bo = new
        // BoggleBoard("C:\\Users\\PINAKA\\Desktop\\boggle\\board-16q.txt");
        // bs.getAllValidWords(bo);
        // } catch (Exception e) {

        // }

    }
}
