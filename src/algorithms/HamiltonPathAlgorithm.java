package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class HamiltonPathAlgorithm {

	public static class Edge {
		int source, dest;
		public String toString(){
			return source + " " + dest;
		}

		public Edge(int source, int dest) {
			this.source = source;
			this.dest = dest;
		}
	}

	public static class Graph {

		List<List<Integer>> adjList = null;

		public Graph(List<Edge> edges, int N) {
			adjList = new ArrayList(N);

			for (int i = 0; i < N; i++) {
				adjList.add(i, new ArrayList());
			}

			// add edges to the undirected graph
			for (int i = 0; i < edges.size(); i++) {
				int src = edges.get(i).source;
				int dest = edges.get(i).dest;

				adjList.get(src).add(dest);
				adjList.get(dest).add(src);
			}
		}
	}

	Vector<Integer[]> paths;

	public Vector<Integer[]> getHamiltonianPaths(Graph g, int start, int N){
		paths = new Vector();
		List<Integer> path = new ArrayList();
		path.add(start);
		boolean[] visited = new boolean[N];
		visited[start] = true;
		allHamiltonianPaths(g, start, visited, path, N);
		return paths;
	}

	public void allHamiltonianPaths(Graph g, int v, boolean[] visited,
			List<Integer> path, int N) {
		if (path.size() == N) {
			Integer[] arr = new Integer[path.size()];
			int i = 0;
			for (Integer e : path)
				arr[i++] = e;
			paths.add(arr);
			return;
		}
		for (int w : g.adjList.get(v)) {
			if (!visited[w]) {
				visited[w] = true;
				path.add(w);
				allHamiltonianPaths(g, w, visited, path, N);
				visited[w] = false;
				path.remove(path.size() - 1);
			}
		}
	}
}