package algorithms;

import java.io.*;
import java.util.*;

import org.apache.commons.collections15.map.HashedMap;

public class ColoringAlgorithm {
	private int V; // No. of vertices
	private LinkedList<Integer> adj[]; // Adjacency List
	
	public ColoringAlgorithm(int v) {
		V = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();
	}

	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v); 
	}

	public String greedyColoring() {
		String colors = "";
		int result[] = new int[V];

		Arrays.fill(result, -1);

		result[0] = 0;

		boolean available[] = new boolean[V];

		Arrays.fill(available, true);

		for (int u = 1; u < V; u++) {
			Iterator<Integer> it = adj[u].iterator();
			while (it.hasNext()) {
				int i = it.next();
				if (result[i] != -1)
					available[result[i]] = false;
			}
			int cr;
			for (cr = 0; cr < V; cr++) {
				if (available[cr])
					break;
			}

			result[u] = cr; // Assign the found color

			Arrays.fill(available, true);
		}

		for (int u = 0; u < V; u++)
			colors += (u + " " + result[u]) + "\n";
		return colors;
	}

}
