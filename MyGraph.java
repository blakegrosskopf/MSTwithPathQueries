import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class MyGraph {
    private int n; // Number of vertices
    private ArrayList<Link> edges; // List of all edges
    private HashSet<String> edgeSet; // To check for duplicate edges

    // Constructor: Creates an empty graph with n vertices
    public MyGraph(int n) {
        this.n = n;
        edges = new ArrayList<>();
        edgeSet = new HashSet<>();
    }

    // Copy constructor: Creates a deep copy of another graph
    public MyGraph(MyGraph g) {
        this.n = g.n;
        this.edges = new ArrayList<>(g.edges);
        this.edgeSet = new HashSet<>(g.edgeSet);
    }

    // Adds an edge between vertices a and b with weight w
    // Returns false if edge exists or vertices are invalid
    public boolean addEdge(int a, int b, float w) {
        if (a < 1 || a > n || b < 1 || b > n) {
            return false;
        }
        // Ensure consistent key for undirected edge (smaller vertex first)
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        String key = min + "_" + max;
        if (edgeSet.contains(key)) {
            return false;
        }
        edges.add(new Link(a, b, w));
        edgeSet.add(key);
        return true;
    }

    // Outputs the graph in the specified format
    public void output() {
        System.out.println(n);
        for (Link edge : edges) {
            int a = Math.min(edge.v1, edge.v2);
            int b = Math.max(edge.v1, edge.v2);
            System.out.println(a + " " + b + " " + edge.w);
        }
    }

    // Returns the MST using Kruskal's algorithm
    public ArrayList<Link> MST() {
        ArrayList<Link> mst = new ArrayList<>();
        // Sort edges by weight
        Collections.sort(edges, new Comparator<Link>() {
            public int compare(Link l1, Link l2) {
                return Float.compare(l1.w, l2.w);
            }
        });

        UnionFind uf = new UnionFind(n);
        for (Link edge : edges) {
            int a = edge.v1;
            int b = edge.v2;
            if (uf.union(a, b)) {
                mst.add(edge);
                if (mst.size() == n - 1) { // MST has n-1 edges
                    break;
                }
            }
        }
        return mst;
    }

    // Union-Find data structure for Kruskal's algorithm
    private class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int size) {
            parent = new int[size + 1];
            rank = new int[size + 1];
            for (int i = 1; i <= size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) {
                return false;
            }
            if (rank[px] > rank[py]) {
                parent[py] = px;
            } else if (rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }
}
