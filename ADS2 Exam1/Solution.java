import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

public class Solution {

    private File file1;
    private File file2;
    private String synsets_pre;
    private Scanner sc1;
    // private String[] synsets_post;
    ArrayList<String> synsets_raw = new ArrayList<String>();
    // private String[] synsets_raw;
    private Hashtable<Integer, ArrayList<Integer>> hypernyms_ht;
    // private String hypernyms_pre;
    private Scanner sc2;
    // private String[] hypernyms_post;
    // private Digraph grp;
    // private SAP sap;

    // constructor takes the name of the two input files
    public Solution(String synsets, String hypernyms) {
        file1 = new File(synsets);
        file2 = new File(hypernyms);

        // System.out.println("Breakpoint1");

        synsets_pre = "";

        try {
            sc1 = new Scanner(file1);
            // int i = 0;
            while (sc1.hasNextLine()) {
                synsets_pre = sc1.nextLine();
                synsets_raw.add(Integer.parseInt(synsets_pre.split(";")[0]), synsets_pre.split(";")[1]);
                // synsets_pre = synsets_pre + sc1.nextLine() + "69'69";
                // System.out.println("Breakpoint1-2" + "------" + i);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error " + ex);
        } catch (NoSuchElementException ex) {
            System.out.println("Error " + ex);
        }

        // synsets_post = synsets_pre.split("69'69");

        // synsets_raw = new String[synsets_post.length];

        // for (int i = 0; i < synsets_post.length; i++) {
        // // synsets_raw[Integer.parseInt(synsets_post[i].split(";")[0])] =
        // // synsets_post[i].split(";")[1];
        // }

        // System.out.println("Breakpoint2");

        // grp = new Digraph(synsets_raw.length);

        hypernyms_ht = new Hashtable<Integer, ArrayList<Integer>>();

        // hypernyms_pre = "";

        String temp = "";
        String hypernyms_post_temp = "";
        try {
            sc2 = new Scanner(file2);

            while (sc2.hasNextLine()) {
                temp = sc2.nextLine();
                hypernyms_post_temp = temp.split("From ")[1];
                String[] hyper_temp = hypernyms_post_temp.split(", to ");

                if (hypernyms_ht.get(Integer.parseInt(hyper_temp[1])) == null)
                    hypernyms_ht.put(Integer.parseInt(hyper_temp[1]), new ArrayList<Integer>());
                // if (hyper_temp.length > 2) {
                // System.out.println("Emergency");
                // }
                // grp.addEdge((Integer.parseInt(hyper_temp[0])),
                // (Integer.parseInt(hyper_temp[1])));
                hypernyms_ht.get(Integer.parseInt(hyper_temp[1])).add(Integer.parseInt(hyper_temp[0]));
                // hypernyms_pre = hypernyms_pre + sc2.nextLine() + "69'69";
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error " + ex);
        } catch (NoSuchElementException ex) {
            System.out.println("Error " + ex);
        }

        // System.out.println("Breakpoint3");

        // hypernyms_post = hypernyms_pre.split("69'69");

        // for (int i = 0; i < hypernyms_post.length; i++) {

        // String hypernyms_post_temp = hypernyms_post[i].split("From ")[1];

        // String[] hyper_temp = hypernyms_post_temp.split(", to ");

        // if (hypernyms_ht.get(Integer.parseInt(hyper_temp[1])) == null)
        // hypernyms_ht.put(Integer.parseInt(hyper_temp[1]), new ArrayList<Integer>());
        // if (hyper_temp.length > 2) {
        // System.out.println("Emergency");
        // }
        // // grp.addEdge((Integer.parseInt(hyper_temp[0])),
        // // (Integer.parseInt(hyper_temp[1])));
        // hypernyms_ht.get(Integer.parseInt(hyper_temp[1])).add(Integer.parseInt(hyper_temp[0]));
        // }

        // System.out.println("Breakpoint4");

        // sap = new SAP(grp);

        HashMap<Integer, Integer> final_hyp = new HashMap<Integer, Integer>();
        for (Integer i : hypernyms_ht.keySet()) {
            final_hyp.put(i, hypernyms_ht.get(i).size());
            // System.out.println(i);
            // System.out.println(hypernyms_ht.get(i).size());
        }

        // System.out.println("Breakpoint5");

        Integer[] finalkeys = final_hyp.keySet().toArray(new Integer[0]);
        Integer[] finalvalues = final_hyp.values().toArray(new Integer[0]);
        int tempp = 0;
        for (int i = 0; i < finalkeys.length; i++) {
            for (int j = i + 1; j < finalkeys.length; j++) {
                if (finalvalues[i] < finalvalues[j]) {
                    tempp = finalvalues[i];
                    finalvalues[i] = finalvalues[j];
                    finalvalues[j] = tempp;

                    tempp = finalkeys[i];
                    finalkeys[i] = finalkeys[j];
                    finalkeys[j] = tempp;
                }
            }
        }

        // System.out.println("Breakpoint6");

        for (int i = 0; i < 8; i++) {
            // System.out.println(finalkeys[i]);
            System.out.println(synsets_raw.get(finalkeys[i]));
            System.out.println(finalvalues[i]);
        }

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

    public static void main(String[] args) {
        Solution w = new Solution("C:\\Users\\PINAKA\\Desktop\\ADS2Exam1\\emails.txt",
                "C:\\Users\\PINAKA\\Desktop\\ADS2Exam1\\email-logs.txt");
    }
}
