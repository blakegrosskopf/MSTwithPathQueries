import java.util.ArrayList;

/**
 * Solves the energy network problem using MST and binary lifting.
 * Task 1: Finds the MST to minimize maintenance costs.
 * Task 2: Determines if a new pipe replaces an existing MST edge.
 */
public class Prog2task {

    /**
     * Task 1: Computes the MST and prepares helper data for Task 2.
     * @param n Number of cities
     * @param pipes List of possible pipes
     * @param helper Object to store MST tree data
     * @return List of pipes in the MST
     */
    public static ArrayList<Link> Task1(int n, ArrayList<Link> pipes, MyHelper helper) {
        // Build graph and find MST
        MyGraph graph = new MyGraph(n);
        for (Link pipe : pipes) {
            graph.addEdge(pipe.v1, pipe.v2, pipe.w);
        }
        ArrayList<Link> mst = graph.MST();

        // Build adjacency list for MST
        ArrayList<ArrayList<Link>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<Link>());
        }
        for (Link edge : mst) {
            int a = edge.v1;
            int b = edge.v2;
            float w = edge.w;
            adj.get(a).add(new Link(a, b, w));
            adj.get(b).add(new Link(b, a, w));
        }

        // DFS to compute tree properties
        int[] parent = new int[n + 1];
        int[] depth = new int[n + 1];
        float[] weight_to_parent = new float[n + 1];
        boolean[] visited = new boolean[n + 1];
        dfs(1, 0, 0, adj, parent, depth, weight_to_parent, visited);

        // Build binary lifting tables
        int logn = (int) (Math.log(n) / Math.log(2)) + 1;
        int[][] anc = new int[logn][n + 1];
        float[][] maxw = new float[logn][n + 1];
        int[][] max_vertex = new int[logn][n + 1];

        // Base case: k=0
        for (int v = 1; v <= n; v++) {
            anc[0][v] = parent[v];
            if (parent[v] != 0) {
                maxw[0][v] = weight_to_parent[v];
                max_vertex[0][v] = v;
            } else {
                maxw[0][v] = -1;
            }
        }

        // Dynamic programming for higher k
        for (int k = 1; k < logn; k++) {
            for (int v = 1; v <= n; v++) {
                if (anc[k - 1][v] != 0) {
                    anc[k][v] = anc[k - 1][anc[k - 1][v]];
                    float mw1 = maxw[k - 1][v];
                    float mw2 = maxw[k - 1][anc[k - 1][v]];
                    if (mw1 >= mw2) {
                        maxw[k][v] = mw1;
                        max_vertex[k][v] = max_vertex[k - 1][v];
                    } else {
                        maxw[k][v] = mw2;
                        max_vertex[k][v] = max_vertex[k - 1][anc[k - 1][v]];
                    }
                } else {
                    anc[k][v] = 0;
                    maxw[k][v] = -1;
                }
            }
        }

        // Store in helper
        helper.parent = parent;
        helper.depth = depth;
        helper.weight_to_parent = weight_to_parent;
        helper.anc = anc;
        helper.maxw = maxw;
        helper.max_vertex = max_vertex;
        helper.logn = logn;

        return mst;
    }

    /**
     * DFS to compute parent, depth, and weight_to_parent arrays.
     */
    private static void dfs(int v, int p, int d, ArrayList<ArrayList<Link>> adj,
                            int[] parent, int[] depth, float[] weight_to_parent, boolean[] visited) {
        visited[v] = true;
        parent[v] = p;
        depth[v] = d;
        if (p != 0) {
            for (Link edge : adj.get(v)) {
                if (edge.v1 == p || edge.v2 == p) {
                    weight_to_parent[v] = edge.w;
                    break;
                }
            }
        }
        for (Link edge : adj.get(v)) {
            int next = (edge.v1 == v ? edge.v2 : edge.v1);
            if (!visited[next]) {
                dfs(next, v, d + 1, adj, parent, depth, weight_to_parent, visited);
            }
        }
    }

    /**
     * Task 2: Checks if newPipe replaces an MST edge.
     * @param n Number of cities
     * @param pipes Original pipes (unused here)
     * @param newPipe New pipe to consider
     * @param helper Precomputed MST data
     * @return Edge to replace, or empty list if no replacement
     */
    public static ArrayList<Link> Task2(int n, ArrayList<Link> pipes, Link newPipe, MyHelper helper) {
        int u = newPipe.v1;
        int v = newPipe.v2;
        float w = newPipe.w;

        // Find maximum weight edge in MST path from u to v
        int a = u;
        int b = v;
        float max_weight_a = -1;
        int max_w_vertex_a = -1;
        float max_weight_b = -1;
        int max_w_vertex_b = -1;

        // Lift deeper vertex to same depth
        if (helper.depth[a] > helper.depth[b]) {
            int diff = helper.depth[a] - helper.depth[b];
            for (int k = 0; k < helper.logn; k++) {
                if ((diff & (1 << k)) != 0) {
                    if (helper.maxw[k][a] > max_weight_a) {
                        max_weight_a = helper.maxw[k][a];
                        max_w_vertex_a = helper.max_vertex[k][a];
                    }
                    a = helper.anc[k][a];
                }
            }
        } else if (helper.depth[b] > helper.depth[a]) {
            int diff = helper.depth[b] - helper.depth[a];
            for (int k = 0; k < helper.logn; k++) {
                if ((diff & (1 << k)) != 0) {
                    if (helper.maxw[k][b] > max_weight_b) {
                        max_weight_b = helper.maxw[k][b];
                        max_w_vertex_b = helper.max_vertex[k][b];
                    }
                    b = helper.anc[k][b];
                }
            }
        }

        // If at same vertex, path is from one to the other
        if (a == b) {
            float max_weight = Math.max(max_weight_a, max_weight_b);
            int max_w_vertex = (max_weight_a >= max_weight_b ? max_w_vertex_a : max_w_vertex_b);
            if (w < max_weight) {
                ArrayList<Link> res = new ArrayList<>();
                int x = max_w_vertex;
                int y = helper.parent[x];
                float weight = helper.weight_to_parent[x];
                res.add(new Link(x, y, weight));
                return res;
            }
            return new ArrayList<>();
        }

        // Lift both to just below LCA
        for (int k = helper.logn - 1; k >= 0; k--) {
            if (helper.anc[k][a] != helper.anc[k][b]) {
                if (helper.maxw[k][a] > max_weight_a) {
                    max_weight_a = helper.maxw[k][a];
                    max_w_vertex_a = helper.max_vertex[k][a];
                }
                a = helper.anc[k][a];
                if (helper.maxw[k][b] > max_weight_b) {
                    max_weight_b = helper.maxw[k][b];
                    max_w_vertex_b = helper.max_vertex[k][b];
                }
                b = helper.anc[k][b];
            }
        }

        // Include edges to LCA
        float weight_a_to_lca = helper.weight_to_parent[a];
        float weight_b_to_lca = helper.weight_to_parent[b];
        if (weight_a_to_lca > max_weight_a) {
            max_weight_a = weight_a_to_lca;
            max_w_vertex_a = a;
        }
        if (weight_b_to_lca > max_weight_b) {
            max_weight_b = weight_b_to_lca;
            max_w_vertex_b = b;
        }

        // Determine max weight and corresponding edge
        float max_weight = Math.max(max_weight_a, max_weight_b);
        int max_w_vertex = (max_weight_a >= max_weight_b ? max_w_vertex_a : max_w_vertex_b);

        if (w < max_weight) {
            ArrayList<Link> res = new ArrayList<>();
            int x = max_w_vertex;
            int y = helper.parent[x];
            float weight = helper.weight_to_parent[x];
            res.add(new Link(x, y, weight));
            return res;
        }
        return new ArrayList<>();
    }
}

