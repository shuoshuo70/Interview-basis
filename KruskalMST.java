import java.util.Arrays;

/**
 * Created by shuoshuo on 2017/10/10.
 */

class Graph
{
    public Edge[] edge;
    public int V, E;

    Graph(int V, int E) {
        this.V = V;
        this.E = E;
        edge = new Edge[E];

        for (int i= 0; i < E; i++) {
            edge[i] = new Edge();
        }
    }

    public int find(int[] parent, int i) {
        if (parent[i] == -1) {
            return i;
        }
        return find(parent, parent[i]);
    }

    public void union(int[] parent, int x, int y) {
        int px = find(parent, x);
        int py = find(parent, y);

        if (px == py) {
            return;
        }

        parent[px] = py;
    }

    public boolean isCircle(Graph graph) {
        int[] parent = new int[graph.V];
        Arrays.fill(parent, -1);
        for (int i=0; i < graph.E; i++) {
            int px = find(parent, graph.edge[i].src);
            int py = find(parent, graph.edge[i].dest);

            if (px == py) {
                return true;
            } else {
                union(parent, px, py);
            }
        }

        return false;
    }

}


class Edge implements Comparable<Edge>{
    int src, dest, weight;

    @Override
    public int compareTo(Edge e) {
        return this.weight - e.weight;
    }
}


public class KruskalMST {

    // Driver Program
    public static void main (String[] args)
    {

        /* Let us create following weighted graph
                 10
            0--------1
            |  \     |
           6|   5\   |15
            |      \ |
            2--------3
                4       */
        int V = 4;  // Number of vertices in graph
        int E = 5;  // Number of edges in graph
        Graph graph = new Graph(V, E);

        // add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 10;

        // add edge 0-2
        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 6;

        // add edge 0-3
        graph.edge[2].src = 0;
        graph.edge[2].dest = 3;
        graph.edge[2].weight = 5;

        // add edge 1-3
        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 15;

        // add edge 2-3
        graph.edge[4].src = 2;
        graph.edge[4].dest = 3;
        graph.edge[4].weight = 4;

        Edge[] res = kruskalMST(graph);
        for (Edge edge : res) {
            System.out.println(edge.src + "->" + edge.dest);
        }
    }

    private static Edge[] kruskalMST(Graph graph) {
        Edge[] res = new Edge[graph.V - 1];
        for (int i=0; i<res.length; i++) {
            res[i] = new Edge();
        }

        Arrays.sort(graph.edge);
        int[] newParent = new int[graph.V];
        Arrays.fill(newParent, -1);

        int v = 0, e = 0, index = 0;
        while (e < graph.V - 1) {
            Edge newEdge = graph.edge[index++];

            int px = graph.find(newParent, newEdge.src);
            int py = graph.find(newParent, newEdge.dest);

            if (px != py) {
                res[e++] = newEdge;
                graph.union(newParent, px, py);
            }
        }

        return res;
    }
}



