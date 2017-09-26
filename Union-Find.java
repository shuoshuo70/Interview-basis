import java.util.Arrays;

/**
 * Created by shuoshuo on 2017/9/25.
 */
public class UnionFind {
	public static void main(String[] args) {
		int v = 3, e = 3;
		Graph graph = new Graph(v, e);

		graph.edges[0].src = 0;
		graph.edges[0].dest = 1;

		graph.edges[1].src = 1;
		graph.edges[1].dest = 2;

		graph.edges[2].src = 0;
		graph.edges[2].dest = 2;

		if (graph.isCircle2(graph)) {
			System.out.println("hahah");
		}

		Graph friends = new Graph(5, 5);
		friends.union2(friends.rank, friends.parent, 0, 2);
		friends.union2(friends.rank, friends.parent, 4, 2);
		friends.union2(friends.rank, friends.parent, 3, 1);

		if (friends.find2(friends.parent, 0) == friends.find2(friends.parent, 4)) {
			System.out.println("yes");
		}
	}
}

class Graph {
	int v, e;
	Edge[] edges;
	int[] parent;
	int[] rank;

	Graph (int v, int e) {
		this.v = v;
		this.e = e;
		edges = new Edge[v];
		parent = new int[v];
		rank = new int[v];

		for (int i=0; i<v; i++) {
			edges[i] = new Edge();
		}
		Arrays.fill(parent, -1);
	}

	int find (int[] parent, int i) {
		if (parent[i] == -1) {
			return i;
		}

		return find(parent, parent[i]);
	}

	void union(int[] parent, int x, int y) {
		int xset = find(parent, x);
		int yset = find(parent, y);

		parent[xset] = yset;
	}

	boolean isCircle(Graph graph) {
		for (int i=0; i<v; i++) {
			int x = find(graph.parent, graph.edges[i].src);
			int y = find(graph.parent, graph.edges[i].dest);

			if (x == y) {
				return true;
			}

			union(graph.parent, x, y);
		}

		return false;
	}

	int find2(int[] parent, int i) {
		if (parent[i] == -1) {
			return i;
		}

		int res = find2(parent, parent[i]);
		parent[i] = res;
		return res;
	}

	void union2(int[] rank, int[] parent, int x, int y) {
		int xset = find2(parent, x);
		int yset = find2(parent, y);

		if (xset == yset) {
			return;
		}

		int xrank = rank[xset];
		int yrank = rank[yset];

		if (xrank < yrank) {
			parent[xset] = yset;
		} else if (xrank > yrank) {
			parent[yset] = xset;
		} else {
			parent[xset] = yset;
			rank[yset]++;
		}
	}

	boolean isCircle2(Graph graph) {
		for (int i=0; i<v; i++) {
			int x = find2(graph.parent, graph.edges[i].src);
			int y = find2(graph.parent, graph.edges[i].dest);

			if (x == y) {
				return true;
			}

			union2(graph.rank, graph.parent, x, y);
		}

		return false;
	}
}

class Edge {
	int src, dest;
}
