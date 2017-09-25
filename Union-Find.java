import java.util.Arrays;

/**
 * Created by shuoshuo on 2017/9/25.
 */
public class Union-Find {
	public static void main(String[] args) {
		int v = 3, e = 3;
		Graph graph = new Graph(v, e);

		graph.edge[0].src = 0;
		graph.edge[0].dest = 1;

		graph.edge[1].src = 1;
		graph.edge[1].dest = 2;

		graph.edge[2].src = 0;
		graph.edge[2].dest = 2;

		if (graph.isCycle(graph) == 1) {
			System.out.println("hahah");
		}
	}
}

class Graph {
	int v, e; //count of vertices and edges
	Edge edge[];

	//边的起点与终点
	class Edge {
		int src, dest;
	}

	Graph(int v, int e) {
		this.v = v;
		this.e = e;
		edge = new Edge[e];
		for (int i=0; i<v; i++) {
			edge[i] = new Edge();
		}
	}

	//find parent node
	int find(int parent[], int i) {
		if (parent[i] == -1) {
			return i;
		}

		return find(parent, parent[i]);
	}

	//join two sets
	void union(int parent[], int x, int y) {
		int xset = find(parent, x);
		int yset = find(parent, y);

		parent[xset] = yset;
	}

	int isCycle(Graph graph) {
		int[] parent = new int[graph.v];
		Arrays.fill(parent, -1);

		for (int i=0; i<graph.v; i++) {
			int x = graph.find(parent, graph.edge[i].src);
			int y = graph.find(parent, graph.edge[i].dest);

			if (x == y) {
				return 1;
			}

			graph.union(parent, x, y);
		}

		return 0;
	}
}
