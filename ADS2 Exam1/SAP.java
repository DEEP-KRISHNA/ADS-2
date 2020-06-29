
import java.lang.IllegalArgumentException;

public class SAP {

    private final Digraph G;
    private BreadthFirstDirectedPaths vBFS;
    private BreadthFirstDirectedPaths wBFS;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v > G.V() || w > G.V())
            throw new IllegalArgumentException();
        if (v == w)
            return 0;
        vBFS = new BreadthFirstDirectedPaths(G, v);
        wBFS = new BreadthFirstDirectedPaths(G, w);
        int len = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                if ((vBFS.distTo(i) + wBFS.distTo(i)) < len) {
                    len = vBFS.distTo(i) + wBFS.distTo(i);
                }
            }
        }
        if (len == Integer.MAX_VALUE)
            return -1;
        else
            return len;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    // -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v > G.V() || w > G.V())
            throw new IllegalArgumentException();
        if (v == w)
            return v;
        vBFS = new BreadthFirstDirectedPaths(G, v);
        wBFS = new BreadthFirstDirectedPaths(G, w);
        int len = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                if ((vBFS.distTo(i) + wBFS.distTo(i)) < len) {
                    len = vBFS.distTo(i) + wBFS.distTo(i);
                    ans = i;
                }
            }
        }
        if (ans == Integer.MAX_VALUE)
            return -1;
        else
            return ans;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in
    // w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        int len = Integer.MAX_VALUE;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null)
                    throw new IllegalArgumentException();
                if (length(i, j) < len)
                    len = length(i, j);
            }
        }
        if (len == Integer.MAX_VALUE)
            return -1;
        else
            return len;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such
    // path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        int ans = Integer.MAX_VALUE;
        for (Integer i : v) {
            for (Integer j : w) {
                if (i == null || j == null)
                    throw new IllegalArgumentException();
                if (ancestor(i, j) < ans)
                    ans = ancestor(i, j);
            }
        }
        if (ans == Integer.MAX_VALUE)
            return -1;
        else
            return ans;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}