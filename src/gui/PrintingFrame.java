package gui;

import controller.*;
import algorithms.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.MapIterator;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.map.HashedMap;

import edu.uci.ics.jung.algorithms.flows.EdmondsKarpMaxFlow;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class PrintingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	static MyGraph graph = GraphPanel.getGraph();
	final Button back = new Button("Back");
	private static Label msg = null;
	final Button anoter = new Button("Path");
	final Button cycle = new Button("Cycle");
	private static GraphPanel panel;
	private static Vector<MyGraph> paths;
	private static Vector<MyGraph> cycles;
	private static String start = "";
	private static boolean check = true;
	String[] starts;
	String[] ends;
	int choice = 0;

	private void setView() {
		graph = GraphPanel.getGraph();
		setLayout(null);

		back.setBounds(10, 432, 90, 25);
		back.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		back.setName("back");
		add(back);

		back.addMouseListener(new listen());
		anoter.addMouseListener(new listen());
		anoter.setBounds(480, 432, 90, 25);
		anoter.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		anoter.setName("another");

		cycle.addMouseListener(new listen());
		cycle.setBounds(380, 432, 90, 25);
		cycle.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		cycle.setName("cycle");

	}

	public PrintingFrame(int choice) {

		setView();
		this.choice = choice;
		if (choice == 1)
			adjacencyList();
		else if (choice == 2)
			adjacencyMatrix();
		else if (choice == 3)
			incidenceMatrix();
		else if (choice == 4)
			representationMatrix();
		else if (choice == 11)
			minimumSpanningTree();
		else if (choice == 7)
			hamiltonianAlgorithm();
		else if (choice == 5)
			EulerAlgorithm();
		else if (choice == 9)
			flueryAlgorithm();
		else if (choice == 10)
			minimumHamiltonian();
		else if (choice == 6)
			coloringProblem();

	}

	public PrintingFrame(int choice, String from, String to) {
		setView();
		this.choice = choice;
		if (choice == 12)
			maxFlow(from, to);
		else if (choice == 13)
			dijkstra(from, to);
	}

	private void dijkstra(String from, String to) {
		msg = new Label("Cost from " + from + " to " + to + " = ");
		this.setTitle("Dijkstra Algorithm");
		msg.setBounds(80, 20, 5000, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		add(msg);

		if (graph.getVertexCount() == 0) {
			msg.setText("The Graph Has No Vertices");
			msg.setBounds(90, 20, 400, 30);
			return;
		}

		if (!graph.containsVertex(from) || !graph.containsVertex(to)) {
			msg.setText("Invalid Source Or Destination");
			return;
		}

		Transformer<String, Number> nev = new Transformer<String, Number>() {
			public Number transform(String arg0) {
				String cost = arg0.trim();
				if (cost == null || cost.equals("")) {
					return 0;
				} else {
					if (arg0.contains("."))
						return Double.valueOf(arg0.trim());
					else
						return Integer.valueOf(arg0.trim());
				}
			}
		};
		DijkstraShortestPath x = new DijkstraShortestPath<String, String>(graph, nev);
		List out = x.getPath(from, to);
		if (out.size() == 0) {
			msg.setText("Can't find a path form Source to Destination");
			msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
			msg.setBounds(50, 20, 500, 30);
			return;
		}
		msg.setText(msg.getText() + " " + x.getDistance(from, to));
		MyGraph path = new MyGraph(graph.et);
		for (int i = 0; i < out.size(); ++i) {
			String f = graph.getEndpoints(out.get(i).toString()).getFirst();
			String s = graph.getEndpoints(out.get(i).toString()).getSecond();
			path.addVertex(f);
			path.addVertex(s);
			path.addEdge(f, s, out.get(i).toString());
		}
		panel = new GraphPanel(path);
		panel.setBounds(10, 50, 560, 360);
		panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
		add(panel);

	}

	private void maxFlow(String from, String to) {
		System.out.println("maxFlow");
		msg = new Label("MaxFlow from " + from + " to " + to + " = ");
		this.setTitle("MaxFlow Algorithm");
		msg.setBounds(80, 20, 5000, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		add(msg);

		if (graph.getVertexCount() == 0) {
			msg.setText("The Graph Has No Vertices");
			msg.setBounds(90, 20, 400, 30);
			return;
		}

		if (!graph.containsVertex(from) || !graph.containsVertex(to)) {
			msg.setText("Invalid Source Or Sink");
			return;
		}
		HashedMap<String, Integer> map = new HashedMap<String, Integer>();
		int i = 0;
		for (Object o : graph.getVertices().toArray()) {
			map.put(o.toString(), i);
			i++;
		}
		int[][] repGraph = new int[graph.getVertexCount()][graph.getVertexCount()];
		for (i = 0; i < graph.getVertexCount(); ++i) {
			for (int j = 0; j < graph.getVertexCount(); ++j) {
				repGraph[i][j] = 0;
			}
		}
		for (Object e : graph.getEdges().toArray()) {
			String f = graph.getEndpoints(e.toString()).getFirst();
			String t = graph.getEndpoints(e.toString()).getSecond();

			i = map.get(f);
			int j = map.get(t);
			int weight = 0;
			if (!e.toString().trim().equals("")) {
				weight = Integer.parseInt(e.toString().trim());
			}
			repGraph[i][j] = weight;
		}
		MaxFlowAlgorithm maxflow = new MaxFlowAlgorithm(graph.getVertexCount());
		int result = maxflow.getMaxFlow(repGraph, map.get(from), map.get(to));
		msg.setText(msg.getText() + result);

	}

	public void adjacencyList() {

		JTextArea textArea = new JTextArea();
		setTitle("The Adjacency List");
		textArea.setEnabled(false);
		// textArea.setBounds(0, 0, 100, 100);
		textArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		JScrollPane scrollableTextArea = new JScrollPane(textArea);
		// scrollableTextArea.setLayout(null);
		textArea.setBackground(Color.DARK_GRAY);
		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setBounds(10, 10, 570, 420);
		getContentPane().add(scrollableTextArea);

		textArea.append("\n");
		textArea.append("\t\t    The Adjacency List\n\n\n");
		Object[] vertex = graph.getVertices().toArray();
		for (int i = 0; i < vertex.length; ++i) {
			textArea.append("\t " + vertex[i]);
			for (int j = 0; j < vertex.length; ++j) {

				Object[] innerEdges = graph.findEdgeSet(vertex[i].toString(), vertex[j].toString()).toArray();

				for (Object e : innerEdges) {
					String edge = e.toString();
					if (edge != null) {
						edge = edge.trim();
						if (edge.equals(""))
							textArea.append(" => " + vertex[j]);
						else
							textArea.append(" => [" + vertex[j] + ", " + edge.trim() + "]");
					}
				}
			}
			textArea.append("\n\n");
		}

	}

	public void adjacencyMatrix() {

		setTitle("The Adjacency Matrix");

		Object[] vertex = graph.getVertices().toArray();
		String[] columns = new String[vertex.length + 1];
		Object[][] data = new Object[vertex.length][vertex.length + 1];
		columns[0] = "\t";

		for (int i = 0; i < vertex.length; ++i) {
			columns[i + 1] = (vertex[i].toString());
		}
		for (int i = 0; i < vertex.length; ++i) {
			data[i][0] = (vertex[i].toString());
			for (int j = 0; j < vertex.length; ++j) {

				Object[] innerEdges = graph.findEdgeSet(vertex[i].toString(), vertex[j].toString()).toArray();
				for (Object e : innerEdges) {
					String edge = e.toString();
					edge = edge.trim();
					if (edge.equals("")) {
						data[i][j + 1] = "1";
					} else {
						data[i][j + 1] = "1 [W = " + (edge) + "]";
					}

				}
				if (innerEdges.length == 0) {
					data[i][j + 1] = "0";
				}
			}
		}
		JTable table = new JTable(data, columns);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 10, 570, 420);
		this.add(js);
		table.setEnabled(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		this.pack();

	}

	public void incidenceMatrix() {

		setTitle("The Incidence Matrix");

		Object[] vertex = graph.getVertices().toArray();
		Object[] edge = graph.getEdges().toArray();
		String[] columns = new String[edge.length + 1];

		Object[][] data = new Object[vertex.length][edge.length + 1];

		columns[0] = "-";
		for (int i = 1; i <= edge.length; ++i) {
			columns[i] = "E" + i;
			String value = edge[i - 1].toString().trim();
			if (!value.equals("")) {
				columns[i] += " [W = " + value + "]";
			}
		}

		for (int i = 0; i < vertex.length; ++i) {
			data[i][0] = vertex[i].toString();
		}

		for (int i = 0; i < vertex.length; ++i) {
			for (int j = 0; j < edge.length; ++j) {
				data[i][j + 1] = "0";
			}
		}

		for (int i = 0; i < edge.length; ++i) {
			String from = graph.getEndpoints(edge[i].toString()).getFirst();
			String to = graph.getEndpoints(edge[i].toString()).getSecond();
			for (int j = 0; j < vertex.length; ++j) {
				if (data[j][0].equals(from)) {
					data[j][i + 1] = "1";
				}
				if (data[j][0].equals(to)) {
					if (graph.et.equals(EdgeType.DIRECTED))
						data[j][i + 1] = "-1";
					else
						data[j][i + 1] = "1";
				}
			}
		}
		JTable table = new JTable(data, columns);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 10, 570, 420);
		this.add(js);
		table.setEnabled(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		this.pack();

	}

	public void minimumSpanningTree() {

		msg = new Label("Graph is not connected");
		msg.setBounds(100, 20, 400, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		add(msg);

		MST.ConnectionChek check = MST.createChecker();

		int V = graph.getVertexCount(); // Number of vertices in graph
		int E = graph.getEdgeCount(); // Number of edges in graph
		MST g = new MST(V, E);
		HashedMap<String, Integer> map = new HashedMap<String, Integer>();
		int i = 0;
		for (Object o : graph.getVertices().toArray()) {
			map.put(o.toString(), i);
			i++;
		}
		i = 0;

		for (Object e : graph.getEdges().toArray()) {
			String from = graph.getEndpoints(e.toString()).getFirst();
			String to = graph.getEndpoints(e.toString()).getSecond();

			check.Add_edge(map.get(from) + 1, map.get(to) + 1);
			check.Add_edge(map.get(to) + 1, map.get(from) + 1);

			g.edge[i].src = map.get(from);
			g.edge[i].dest = map.get(to);
			int weight = 0;
			if (!e.toString().trim().equals("")) {
				weight = Integer.parseInt(e.toString().trim());
			}
			g.edge[i].weight = weight;
			i++;
		}

		int n = graph.getVertexCount();
		if (check.Is_connected(n)) {
			MyGraph minimum = new MyGraph(graph.et);
			Vector<Integer[]> vec = g.KruskalMST();
			String output = "";
			for (Integer[] arr : vec) {
				MapIterator<String, Integer> it = map.mapIterator();
				String from = "", to = "", cost = String.valueOf(arr[2]);
				while (it.hasNext()) {
					String key = it.next();
					int value = map.get(key);
					if (value == arr[0])
						from = key;
					if (value == arr[1])
						to = key;
				}

				output += from + " " + to + " " + cost + "\n";
				while (minimum.containsEdge(cost))
					cost += ' ';
				minimum.addVertex(from);
				minimum.addVertex(to);
				minimum.addEdge(from, to, cost);
			}
			msg.setText("Minimum Spanning Tree");
			if (n == 0)
				return;
			if (n == 1) {
				minimum = graph;
			}
			panel = new GraphPanel(graph);
			panel.setBounds(10, 50, 560, 360);
			panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
			panel.coloredMinTree(output);
			add(panel);

		}

	}

	public void representationMatrix() {

		setTitle("The Representation Matrix");

		Object[] vertex = graph.getVertices().toArray();
		String[] columns = new String[vertex.length + 1];
		Object[][] data = new Object[vertex.length][vertex.length + 1];
		columns[0] = "\t";

		for (int i = 0; i < vertex.length; ++i) {
			columns[i + 1] = (vertex[i].toString());
		}
		for (int i = 0; i < vertex.length; ++i) {
			data[i][0] = (vertex[i].toString());
			for (int j = 0; j < vertex.length; ++j) {

				data[i][j + 1] = graph.findEdgeSet(vertex[i].toString(), vertex[j].toString()).size();
			}
		}
		JTable table = new JTable(data, columns);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(10, 10, 570, 420);
		this.add(js);
		table.setEnabled(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); ++i) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		this.pack();

	}

	public void hamiltonianAlgorithm() {

		msg = new Label("This Graph Has No Hamiltonian Paths");
		msg.setBounds(110, 20, 400, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		add(msg);

		if (hamShit() != 0) {
			msg.setText("Hamiltonian Path Starts from " + start);
			panel = new GraphPanel(paths.get(0));
			panel.setBounds(10, 50, 560, 360);
			panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
			add(panel);
			add(anoter);
			add(cycle);
		}
	}

	int hamShit() {

		HamiltonPathAlgorithm ham = new HamiltonPathAlgorithm();
		int V = graph.getVertexCount(); // Number of vertices in graph

		if (V == 0)
			return 0;

		HashedMap<String, Integer> map = new HashedMap<String, Integer>();
		int i = 0;
		for (Object o : graph.getVertices().toArray()) {
			map.put(o.toString(), i);
			i++;
		}
		i = 0;
		java.util.List<HamiltonPathAlgorithm.Edge> listOfEgdes = new ArrayList();
		for (Object e : graph.getEdges().toArray()) {
			String from = graph.getEndpoints(e.toString()).getFirst();
			String to = graph.getEndpoints(e.toString()).getSecond();
			HamiltonPathAlgorithm.Edge edge = new HamiltonPathAlgorithm.Edge(map.get(from), map.get(to));
			listOfEgdes.add(edge);
		}

		HamiltonPathAlgorithm.Graph tempG = new HamiltonPathAlgorithm.Graph(listOfEgdes, V);
		Vector<Integer[]> vec = new Vector<Integer[]>();
		for (int j = 0; j < V; ++j) {
			Vector<Integer[]> tempVec = ham.getHamiltonianPaths(tempG, j, V);
			if (tempVec.size() > vec.size()) {
				vec = tempVec;
				i = j;
			}
		}

		{
			MapIterator<String, Integer> it = map.mapIterator();
			while (it.hasNext()) {
				String key = it.next();
				int value = map.get(key);
				if (value == i)
					start = key;
			}
		}

		int current = 0;
		if (vec.size() != 0) {
			starts = new String[vec.size()];
			ends = new String[vec.size()];
			paths = new Vector<MyGraph>();
			for (Integer[] arr : vec) {
				MyGraph element = new MyGraph(EdgeType.UNDIRECTED);
				if (arr.length == 1)
					element.addVertex(graph.getVertices().toArray()[0].toString());
				for (i = 0; i < arr.length - 1; ++i) {
					MapIterator<String, Integer> it = map.mapIterator();
					String from = "", to = "";
					while (it.hasNext()) {
						String key = it.next();
						int value = map.get(key);
						if (value == arr[i])
							from = key;
						if (i == 0 && value == arr[i])
							starts[current] = key;
						if (value == arr[i + 1])
							to = key;
					}
					String cost = graph.findEdge(from, to);
					element.addVertex(from);
					element.addVertex(to);
					element.addEdge(from, to, cost);
					if (i == (arr.length - 2))
						ends[current++] = to;
				}
				paths.add(element);
			}

			cycles = new Vector<MyGraph>();
			for (int j = 0; j < current; ++j) {

				String edge = graph.findEdge(starts[j], ends[j]);
				if (edge != null && !paths.get(j).containsEdge(edge)) {
					MyGraph found = new MyGraph(paths.get(j));
					found.addEdge(starts[j], ends[j], edge);
					cycles.add(found);
				}
			}
		}
		return vec.size();
	}

	public void EulerAlgorithm() {

		msg = new Label("This Graph is not Eulerian");

		msg.setBounds(100, 20, 400, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		add(msg);

		EulerPathAlgorithm eul = new EulerPathAlgorithm(graph);
		int type = eul.Euler();
		if (!(type == 0 || graph.getVertexCount() == 0)) {
			if (type == 2)
				msg.setText("It Has Eulerian Paths");
			else
				msg.setText("It Has Eulerian Circuits");

			panel = new GraphPanel(new MyGraph(GraphPanel.getGraph()));
			panel.setBounds(10, 50, 560, 360);
			panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
			add(panel);

			panel.remove(panel.vv);

			Button print = new Button("Print It");
			print.addMouseListener(new listen());
			print.setBounds(480, 432, 90, 25);
			print.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			print.setName("print");
			add(print);

		}
	}

	public void flueryAlgorithm() {

		msg = new Label("This Graph is not Eulerian");
		add(msg);

		msg.setBounds(10, 20, 650, 30);

		EulerPathAlgorithm eul = new EulerPathAlgorithm(graph);
		int type = eul.Euler();
		if (!(type == 0 || graph.getVertexCount() == 0)) {
			if (type == 2)
				msg.setText("Eulerian Path [");
			else
				msg.setText("Eulerian Circuit [");

			panel = new GraphPanel(new MyGraph(GraphPanel.getGraph()));
			panel.setBounds(10, 50, 560, 360);
			panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
			add(panel);

			String[] edgat = eul.getOutput().split("\n");
			for (int i = 0; i < edgat.length; ++i) {
				String[] args = edgat[i].split(" ");

				String sec = "";
				String fir = "";
				fir = edgat[i].split(" ")[0];
				if (args.length == 2)
					sec = edgat[i].split(" ")[1];
				msg.setText(msg.getText() + fir + " => ");
				if (i == edgat.length - 1)
					msg.setText(msg.getText() + sec + "]");

			}
		}
	}

	public void minimumHamiltonian() {
		msg = new Label("No Hamiltonian Circuits");
		msg.setBounds(110, 20, 400, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
		add(msg);

		if (hamShit() != 0) {

			msg.setText("Minimum Hamiltonian Circuit [");
			msg.setBounds(20, 20, 400, 30);
			msg.setFont(new Font(Font.DIALOG, Font.PLAIN, 13));

			int minimum = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int i = 0; i < cycles.size(); ++i) {
				MyGraph current = cycles.get(i);
				Object[] edgat = current.getEdges().toArray();
				int localMin = 0;
				for (Object ed : edgat) {
					if (!ed.toString().trim().equals("")) {
						localMin += Integer.parseInt(ed.toString().trim());
					}
				}
				if (minimum > localMin) {
					minimum = localMin;
					minIndex = i;
				}
			}
			if (minIndex != -1) {
				panel = new GraphPanel(graph);
				panel.setBounds(10, 50, 560, 360);
				panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
				add(panel);

				Object[] to = cycles.get(minIndex).getNeighbors(starts[minIndex]).toArray();
				Vector<String> visited = new Vector<String>();
				visited.add(starts[minIndex]);
				String first = "";
				for (Object o : to) {
					if (o.toString().equals(ends[minIndex]))
						continue;
					first = o.toString();
				}
				visited.add(first);

				while (visited.size() < cycles.get(minIndex).getEdgeCount()) {
					to = cycles.get(minIndex).getNeighbors(first).toArray();
					for (Object o : to) {
						if (visited.contains(o.toString()))
							continue;
						first = o.toString();
					}
					visited.add(first);
				}
				visited.add(starts[minIndex]);

				for (int i = 0; i < visited.size(); ++i) {
					if (i == visited.size() - 1)
						msg.setText(msg.getText() + visited.get(i) + "]");
					else
						msg.setText(msg.getText() + visited.get(i) + " => ");
				}

			} else {
				msg.setText("No Hamiltonian Circuits");
				msg.setBounds(110, 20, 400, 30);
				msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));

			}
		}
	}

	public void coloringProblem() {

		msg = new Label("Color Number Is");
		msg.setBounds(170, 20, 300, 30);
		msg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		add(msg);

		if (graph.getVertexCount() == 0) {
			msg.setText("Graph Is Empty");
			return;
		}
		int V = graph.getVertexCount(); // Number of vertices in graph
		int E = graph.getEdgeCount(); // Number of edges in graph
		ColoringAlgorithm algorithm = new ColoringAlgorithm(V);

		HashedMap<String, Integer> map = new HashedMap<String, Integer>();
		int i = 0;
		for (Object o : graph.getVertices().toArray()) {
			map.put(o.toString(), i);
			i++;
		}
		for (Object e : graph.getEdges().toArray()) {
			String from = graph.getEndpoints(e.toString()).getFirst();
			String to = graph.getEndpoints(e.toString()).getSecond();
			algorithm.addEdge(map.get(from), map.get(to));
		}

		String shit = algorithm.greedyColoring();
		String output = "";
		String[] lines = shit.split("\n");
		int max = Integer.MIN_VALUE;
		for (i = 0; i < lines.length; ++i) {
			String[] data = lines[i].split(" ");
			int mappedValue = Integer.parseInt(data[0]);
			String color = data[1];
			if (max < Integer.parseInt(color)) {
				max = Integer.parseInt(color);
			}
			MapIterator<String, Integer> it = map.mapIterator();
			while (it.hasNext()) {
				String key = it.next();
				int value = map.get(key);
				if (value == mappedValue)
					output += key + " " + color + "\n";
			}
		}

		msg.setText(msg.getText() + " " + (max + 1));
		panel = new GraphPanel(new MyGraph(GraphPanel.getGraph()));
		panel.setBounds(10, 50, 560, 360);
		panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
		add(panel);
		panel.changeColor(output, max + 1);

	}

	private class listen implements MouseListener {

		public void mouseReleased(MouseEvent arg0) {

		}

		public void mousePressed(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {

		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getComponent().getName().equals("back")) {
				if (choice == 12 || choice == 13)
					Home.home.copy(new InputFrame2(graph));
				else
					Home.home.copy(new InputFrame(graph));

			} else if (arg0.getComponent().getName().equals("cycle")) {
				if (cycles.size() == 0)
					return;

				if (check == false)
					return;
				msg.setText("Hamiltonian Circle");
				panel.remove(panel.vv);
				panel.repaint();
				panel.setGraph(cycles.get(0));
				panel.setBounds(10, 50, 560, 360);
				panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);

				check = false;

			} else if (arg0.getComponent().getName().equals("another") && !check) {

				msg.setText("Hamiltonian Path " + "Starts From " + start);
				panel.remove(panel.vv);
				panel.repaint();
				panel.setGraph(paths.get(0));
				panel.setBounds(10, 50, 560, 360);
				panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);
				check = true;

			}
			if (arg0.getComponent().getName().equals("print")) {

				msg.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
				msg.setBounds(10, 20, 650, 30);

				EulerPathAlgorithm eul = new EulerPathAlgorithm(graph);
				int type = eul.Euler();
				if (!(type == 0 || graph.getVertexCount() == 0)) {
					if (type == 2)
						msg.setText("Eulerian Path [");
					else
						msg.setText("Eulerian Circuit [");

					panel.setGraph(GraphPanel.getGraph());
					panel.setBounds(10, 50, 560, 360);
					panel.vv.setBounds(10, 10, 560 - 20, 360 - 20);

					String[] edgat = eul.getOutput().split("\n");
					for (int i = 0; i < edgat.length; ++i) {
						String[] args = edgat[i].split(" ");

						String sec = "";
						String fir = "";
						fir = edgat[i].split(" ")[0];
						if (args.length == 2)
							sec = edgat[i].split(" ")[1];
						msg.setText(msg.getText() + fir + " => ");
						if (i == edgat.length - 1)
							msg.setText(msg.getText() + sec + "]");

					}
				}

			}
		}
	}
}
