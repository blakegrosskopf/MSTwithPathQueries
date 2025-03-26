import java.util.Scanner;
import java.util.ArrayList;

public class MyHelper {
    public int[] parent;         // Parent of each vertex in the MST tree
    public int[] depth;          // Depth of each vertex from root
    public float[] weight_to_parent; // Weight of edge to parent
    public int[][] anc;          // 2^k-th ancestor of each vertex
    public float[][] maxw;       // Max weight in path to 2^k-th ancestor
    public int[][] max_vertex;   // Vertex where max weight edge occurs
    public int logn;             // Max power of 2 needed for lifting

    public MyHelper() {
        // Fields are initialized in Task1
    }
}
