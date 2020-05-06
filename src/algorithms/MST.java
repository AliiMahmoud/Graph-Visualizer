package algorithms;
// Java program for Kruskal's algorithm to find Minimum 
// Spanning Tree of a given connected, undirected and 
// weighted graph 
import java.util.*;
import java.lang.*;
import java.io.*;

public class MST {
	public static class ConnectionChek {
		int N = 100000;
		Vector<Integer>[] gr1 = new Vector[N];
		Vector<Integer>[] gr2 = new Vector[N];
		boolean[] vis1 = new boolean[N];
		boolean[] vis2 = new boolean[N];

		public ConnectionChek() {

			for (int i = 0; i < N; i++) {
				gr1[i] = new Vector();
				gr2[i] = new Vector();
			}
		}

		public void Add_edge(int u, int v) {
			gr1[u].add(v);
			gr2[v].add(u);
		}

		void dfs1(int x) {
			vis1[x] = true;
			for (int i : gr1[x])
				if (!vis1[i])
					dfs1(i);
		}

		void dfs2(int x) {
			vis2[x] = true;
			for (int i : gr2[x])
				if (!vis2[i])
					dfs2(i);
		}

		public boolean Is_connected(int n) {
			Arrays.fill(vis1, false);
			dfs1(1);

			Arrays.fill(vis2, false);
			dfs2(1);

			for (int i = 1; i <= n; i++) {
				if (!vis1[i] && !vis2[i])
					return false;
			}
			return true;
		}
	}

	// A class to represent a graph edge
	public class Edge implements Comparable<Edge> {
		public int src, dest, weight;

		public int compareTo(Edge compareEdge) {
			return this.weight - compareEdge.weight;
		}
	};

	class subset {
		int parent, rank;
	};

	public int V, E;
	public Edge edge[];

	public MST(int v, int e) {
		V = v;
		E = e;
		edge = new Edge[E];
		for (int i = 0; i < e; ++i)
			edge[i] = new Edge();
	}

	int find(subset subsets[], int i) {
		if (subsets[i].parent != i)
			subsets[i].parent = find(subsets, subsets[i].parent);

		return subsets[i].parent;
	}

	void Union(subset subsets[], int x, int y) {
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);
		if (subsets[xroot].rank < subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank > subsets[yroot].rank)
			subsets[yroot].parent = xroot;
		else {
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
	}

	public Vector<Integer[]> KruskalMST() {
		Vector<Integer[]> vec = new Vector<Integer[]>();
		Edge result[] = new Edge[V]; // Tnis will store the resultant MST
		int e = 0; // An index variable, used for result[]
		int i = 0; // An index variable, used for sorted edges
		for (i = 0; i < V; ++i)
			result[i] = new Edge();

		Arrays.sort(edge);
		subset subsets[] = new subset[V];
		for (i = 0; i < V; ++i)
			subsets[i] = new subset();

		for (int v = 0; v < V; ++v) {
			subsets[v].parent = v;
			subsets[v].rank = 0;
		}
		i = 0; // Index used to pick next edge
		while (e < V - 1) {
			Edge next_edge = new Edge();
			next_edge = edge[i++];
			int x = find(subsets, next_edge.src);
			int y = find(subsets, next_edge.dest);
			if (x != y) {
				result[e++] = next_edge;
				Union(subsets, x, y);
			}
		}
		for (i = 0; i < e; ++i) {
			Integer[] arr = new Integer[3];
			arr[0] = result[i].src;
			arr[1] = result[i].dest;
			arr[2] = result[i].weight;
			vec.add(arr);
		}
		return vec;
	}

	public static ConnectionChek createChecker() {
		return new ConnectionChek();
	}

}
