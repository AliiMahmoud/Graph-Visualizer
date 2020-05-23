package algorithms;

import java.util.*;

public class MaxFlowAlgorithm {
    int V;

    public MaxFlowAlgorithm(int v){
        V = v;
    }

    boolean search(int arrGraph[][], int s, int t, int pare[]) {
        boolean vis[] = new boolean[V];
        for (int i = 0; i < V; ++i)
            vis[i] = false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        vis[s] = true;
        pare[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (vis[v] == false && arrGraph[u][v] > 0) {
                    queue.add(v);
                    pare[v] = u;
                    vis[v] = true;
                }
            }
        }

        return (vis[t] == true);
    }

    public int getMaxFlow(int graph[][], int s, int t) {
        int u, v;

        int arrGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                arrGraph[u][v] = graph[u][v];

        int pare[] = new int[V];

        int max_flow = 0;

        while (search(arrGraph, s, t, pare)) {
            int fPath = Integer.MAX_VALUE;
            for (v = t; v != s; v = pare[v]) {
                u = pare[v];
                fPath = Math.min(fPath, arrGraph[u][v]);
            }

            for (v = t; v != s; v = pare[v]) {
                u = pare[v];
                arrGraph[u][v] -= fPath;
                arrGraph[v][u] += fPath;
            }

            max_flow += fPath;
        }

        return max_flow;
    }
}
