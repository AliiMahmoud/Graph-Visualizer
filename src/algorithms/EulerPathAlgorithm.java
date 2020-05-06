package algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.collections15.map.HashedMap;

import edu.uci.ics.jung.graph.util.EdgeType;

public class EulerPathAlgorithm {

	private int V = 0;
	private int E = 0;
	String output = "";
	HashedMap<String, Integer> map = new HashedMap<String, Integer>();
	private ArrayList<String>[] adj;
	MyGraph g;
	private Vector<String> path;

	public Vector<String> getPath() {
		return path;
	}

	public String getOutput(){
		return output;
	}
	public EulerPathAlgorithm(MyGraph in) {
		g = new MyGraph(in);
		g.et = in.et;
		path = new Vector<String>();
		output = "";
	}

	// 0 not eulerian
	// 1 euler cycle
	// 2 euler path
	public int Euler() {

		if (g.et == EdgeType.UNDIRECTED) {
			prepareForEuler();
			int oddCounter = 0;
			boolean hasEdges = false;
			boolean connctd = true;
			String key = "";
			Iterator<String> itr = map.mapIterator();
			while (itr.hasNext()) {
				key = itr.next();
				int numOfEdges = g.getNeighborCount(key);
				if (numOfEdges % 2 != 0)
					oddCounter++;
				if (numOfEdges > 0)
					hasEdges = true;
			}
//			System.out.println(hasEdges);
			if (hasEdges)
				connctd = isConnected();

			if (!connctd)
				return 0;

			if (oddCounter > 2)
				return 0;

			itr = map.mapIterator();
			while (itr.hasNext()) {
				key = itr.next();
				if (adj[map.get(key)].size() % 2 == 1) {
					break;
				}
			}

			getEulerRoute(key);
			return (oddCounter == 2) ? 2 : 1;
		}

		return 0;
	}

	void DFSUtil(String key, boolean visited[]) {
		// Mark the current node as visited
		visited[map.get(key)] = true;

		// Recur for all the vertices adjacent to this vertex
		Object[] adj = g.getNeighbors(key).toArray();
		for (int i = 0; i < adj.length; i++) {
			if (!visited[map.get((String) adj[i])])
				DFSUtil((String) adj[i], visited);
		}

	}

	boolean isConnected() {
		// Mark all the vertices as not visited
		boolean visited[] = new boolean[V];
		int i;
		for (i = 0; i < V; i++)
			visited[i] = false;

		Iterator<String> itr = map.mapIterator();
		String key = "";
		while (itr.hasNext()) {
			key = itr.next();
			Object[] adj = g.getNeighbors(key).toArray();
			if (adj.length != 0)
				break;
		}

		// Start DFS traversal from a vertex with non-zero degree
		DFSUtil(key, visited);

		// Check if all non-zero degree vertices are visited
		itr = map.mapIterator();
		while (itr.hasNext()) {
			key = itr.next();
			Object[] adj = g.getNeighbors(key).toArray();
			if (visited[map.get(key)] == false && adj.length > 0)
				return false;

		}
		return true;
	}

	void prepareForEuler() {
		V = g.getVertexCount();
		E = g.getEdgeCount();
		map.clear();
		int i = 0;
		for (Object o : g.getVertices().toArray()) {
			map.put(o.toString(), i++);
		}

		adj = new ArrayList[V];
		for (i = 0; i < V; i++) {
			adj[i] = new ArrayList();
		}
		for (Object o : g.getEdges().toArray()) {

			String src = g.getEndpoints(o.toString()).getFirst();
			String dest = g.getEndpoints(o.toString()).getSecond();

			adj[map.get(src)].add(dest);
			adj[map.get(dest)].add(src);
		}

	}

	private void getEulerRoute(String u) {
		// Recur for all the vertices adjacent to this vertex

		if (E <= 0)
			return;

		for (int i = 0; i < adj[map.get(u)].size(); i++) {
			String v = adj[map.get(u)].get(i);
			// If edge u-v is a valid next edge

			if (E > 0) {
				if (isValidNextEdge(u, v)) {

					output += u + " " + v;
					output += "\n";
//					System.out.println(u + "-" + v + " ");
					// System.out.println("Edge Rem: " + E);
					path.add(u);
					path.add(v);
					E--;

					// This edge is used so remove it now
					adj[map.get(u)].remove(v);
					adj[map.get(v)].remove(u);
					
					getEulerRoute(v);

				}

			}

		}
	}

	// The function to check if edge u-v can be
	// considered as next edge in Euler Tout
	private boolean isValidNextEdge(String u, String v) {
		// The edge u-v is valid in one of the
		// following two cases:

		// 1) If v is the only adjacent vertex of u
		// ie size of adjacent vertex list is 1
		if (adj[map.get(u)].size() == 1) {
			return true;
		}

		// 2) If there are multiple adjacents, then
		// u-v is not a bridge Do following steps
		// to check if u-v is a bridge
		// 2.a) count of vertices reachable from u
		boolean[] isVisited = new boolean[V];
		int count1 = dfsCount(u, isVisited);

		// 2.b) Remove edge (u, v) and after removing
		// the edge, count vertices reachable from u
		adj[map.get(u)].remove(v);
		adj[map.get(u)].remove(u);
		isVisited = new boolean[V];
		int count2 = dfsCount(u, isVisited);

		// 2.c) Add the edge back to the graph
		adj[map.get(u)].add(v);
		adj[map.get(v)].add(u);
		return (count1 > count2) ? false : true;
	}

	// A DFS based function to count reachable
	// vertices from v
	private int dfsCount(String v, boolean[] isVisited) {
		// Mark the current node as visited
		isVisited[map.get(v)] = true;
		int count = 1;
		// Recur for all vertices adjacent to this vertex
		for (String ad : adj[map.get(v)]) {
			if (!isVisited[map.get(ad)]) {
				count = count + dfsCount(ad, isVisited);
			}
		}
		return count;
	}
}
