
import java.lang.IllegalArgumentException;

public class Outcast {
    // constructor takes a WordNet object

    private final WordNet wn;

    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null)
            throw new IllegalArgumentException();
        int max = Integer.MIN_VALUE;
        int max_index = 0;
        for (int i = 0; i < nouns.length; i++) {
            int su = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (nouns[0] == null || nouns[i] == null)
                    throw new IllegalArgumentException();
                su = su + wn.distance(nouns[0], nouns[i]);
            }
            if (su > max) {
                max = su;
                max_index = i;
            }
        }
        return nouns[max_index];
    }

    // see test client below
    public static void main(String[] args) {

    }
}