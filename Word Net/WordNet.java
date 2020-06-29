import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Bag;

public class WordNet {

    private File file1;
    private File file2;
    private Hashtable<String, ArrayList<Integer>> synsets_ht;
    private String synsets_pre;
    private Scanner sc1;
    // private String[] synsets_post;
    // private String[] synsets_raw;
    private ArrayList<String> synsets_raw = new ArrayList<String>();
    // private Hashtable<Integer, ArrayList<Integer>> hypernyms_ht;
    private String hypernyms_pre;
    private Scanner sc2;
    // private String[] hypernyms_post;
    private Digraph grp;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        file1 = new File(synsets);
        file2 = new File(hypernyms);

        synsets_ht = new Hashtable<String, ArrayList<Integer>>();

        synsets_pre = "";

        try {
            sc1 = new Scanner(file1);

            while (sc1.hasNextLine()) {
                // synsets_pre = synsets_pre + sc1.nextLine() + "69'69";
                synsets_pre = sc1.nextLine();
                // synsets_raw = new String[synsets_pre.length];

                // for (int i = 0; i < synsets_pre.length; i++) {
                synsets_raw.add(Integer.parseInt(synsets_pre.split(",")[0]), synsets_pre.split(",")[1]);
                // synsets_raw[Integer.parseInt(synsets_pre.split(",")[0])] =
                // synsets_pre.split(",")[1];
                for (String j : synsets_pre.split(",")[1].split(" ")) {
                    if (synsets_ht.get(j) == null)
                        synsets_ht.put(j, new ArrayList<Integer>());
                    synsets_ht.get(j).add(Integer.parseInt(synsets_pre.split(",")[0]));
                }
                // }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error " + ex);
        } catch (NoSuchElementException ex) {
            System.out.println("Error " + ex);
        }

        // synsets_post = synsets_pre.split("69'69");

        // synsets_raw = new String[synsets_post.length];

        // for (int i = 0; i < synsets_post.length; i++) {
        // synsets_raw[Integer.parseInt(synsets_post[i].split(",")[0])] =
        // synsets_post[i].split(",")[1];
        // for (String j : synsets_post[i].split(",")[1].split(" ")) {
        // if (synsets_ht.get(j) == null)
        // synsets_ht.put(j, new ArrayList<Integer>());
        // synsets_ht.get(j).add(Integer.parseInt(synsets_post[i].split(",")[0]));
        // }
        // }
        grp = new Digraph(synsets_raw.size());

        // hypernyms_ht = new Hashtable<Integer, ArrayList<Integer>>();

        hypernyms_pre = "";

        try {
            sc2 = new Scanner(file2);

            while (sc2.hasNextLine()) {
                // hypernyms_pre = hypernyms_pre + sc2.nextLine() + "69'69";
                hypernyms_pre = sc2.nextLine();
                String[] hyper_temp = hypernyms_pre.split(",");
                // hypernyms_ht.put(Integer.parseInt(hyper_temp[0]), new ArrayList<Integer>());
                for (int j = 1; j < hyper_temp.length; j++) {
                    grp.addEdge((Integer.parseInt(hyper_temp[0])), (Integer.parseInt(hyper_temp[j])));
                    // hypernyms_ht.get(Integer.parseInt(hyper_temp[0])).add(Integer.parseInt(hyper_temp[j]));
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error " + ex);
        } catch (NoSuchElementException ex) {
            System.out.println("Error " + ex);
        }

        // hypernyms_post = hypernyms_pre.split("69'69");

        // for (int i = 0; i < hypernyms_post.length; i++) {

        // String[] hyper_temp = hypernyms_post[i].split(",");

        // hypernyms_ht.put(Integer.parseInt(hyper_temp[0]), new ArrayList<Integer>());
        // for (int j = 1; j < hyper_temp.length; j++) {
        // grp.addEdge((Integer.parseInt(hyper_temp[0])),
        // (Integer.parseInt(hyper_temp[j])));
        // hypernyms_ht.get(Integer.parseInt(hyper_temp[0])).add(Integer.parseInt(hyper_temp[j]));
        // }
        // }

        sap = new SAP(grp);

        // for (Integer i : hypernyms_ht.keySet()) {
        // System.out.println(i);
        // System.out.println(hypernyms_ht.get(i));
        // }

        // System.out.println();
        // System.out.println("Sandy");
        // System.out.println();

        // for (String i : synsets_ht.keySet()) {
        // System.out.println(i);
        // System.out.println(synsets_ht.get(i));
        // }

        // System.out.println();
        // System.out.println("Sandy");
        // System.out.println();

        // for (int i = 0; i < synsets_raw.length; i++) {
        // System.out.println(synsets_raw[i]);
        // }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsets_ht.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return synsets_ht.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || (!isNoun(nounB)))
            throw new IllegalArgumentException();
        return sap.length(synsets_ht.get(nounA), synsets_ht.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || (!isNoun(nounB)))
            throw new IllegalArgumentException();
        int n = sap.ancestor(synsets_ht.get(nounA), synsets_ht.get(nounB));
        return synsets_raw.get(n);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("C:\\Users\\PINAKA\\Desktop\\Word Net\\wordnet\\SamplePiece1.txt",
                "C:\\Users\\PINAKA\\Desktop\\Word Net\\wordnet\\SamplePiece2.txt");
    }
}