package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.Home;

public class HomeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	Label welcomeMsg = new Label("Graph Visualizer");
	Button adjList = new Button("Adjacency List");
	Button adjMat = new Button("Adjacency Matrix");
	Button incMat = new Button("Incidence Matrix");
	Button repMat = new Button("Representation Matrix");
	Button eulerPath = new Button("Euler's Path/Circuit");
	Button coloring = new Button("Graph Coloring Problem");
	Button hamiltonPath = new Button("Hamilton's Path/Circuit");
	Button FA = new Button("Fluery's  Algorithm");
	Button MHC = new Button("Min Hamilton Circuit");
	Button MST = new Button("Minimum Spanning Tree");
	Button maxFlowButton = new Button("Maximum Flow");
	Button dijkstra = new Button("Dijkstra Algorithm");
	Label newText = new Label("NEW");

	public HomeFrame(String name) {
		super(name);

		welcomeMsg.setBounds(160, 50, 370, 30);
		welcomeMsg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

		adjList.setBounds(260, 130, 140, 34);
		adjMat.setBounds(420, 130, 140, 34);
		incMat.setBounds(260, 180, 140, 34);
		repMat.setBounds(420, 180, 140, 34);
		eulerPath.setBounds(260, 230, 140, 34);
		FA.setBounds(420, 230, 140, 34);
		coloring.setBounds(260, 330, 140, 34);
		MST.setBounds(420, 330, 140, 34);
		hamiltonPath.setBounds(260, 280, 140, 34);
		MHC.setBounds(420, 280, 140, 34);
		dijkstra.setBounds(420, 380, 140, 34);
		maxFlowButton.setBounds(260, 380, 140, 34);
		newText.setBounds(210, 380, 40, 34);
		
		newText.setForeground(Color.RED);

		adjList.setName("adjList");
		adjMat.setName("adjMat");
		incMat.setName("incMat");
		repMat.setName("repMat");
		eulerPath.setName("eulerPath");
		coloring.setName("coloring");
		hamiltonPath.setName("hamiltonPath");
		// hamiltonCircuit.setName("hamiltonCircuit");
		FA.setName("FA");
		MHC.setName("MHC");
		MST.setName("MST");
		maxFlowButton.setName("maxflow");
		dijkstra.setName("dijkstra");

		adjList.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		adjMat.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		incMat.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		repMat.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		eulerPath.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		coloring.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		hamiltonPath.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		// hamiltonCircuit.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		FA.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		MHC.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		MST.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		maxFlowButton.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		dijkstra.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		
		newText.setFont(new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, 15));

		ImageIcon i = new ImageIcon("imgs/graph.png");
		JLabel l = new JLabel(i);
		l.setBounds(20, 165, 207, 207);

		ImageIcon ii = new ImageIcon("imgs/view.png");
		JLabel ll = new JLabel(ii);
		ll.setBounds(25 + 207, 150, 11, 230);
		this.add(ll);
		this.add(newText);
		this.add(welcomeMsg);
		this.add(l);
		this.add(adjList);
		this.add(adjMat);
		this.add(incMat);
		this.add(repMat);

		// this.add(hamiltonCircuit);
		this.add(hamiltonPath);
		this.add(coloring);
		this.add(eulerPath);

		this.add(FA);
		this.add(MHC);
		this.add(MST);

		this.add(maxFlowButton);
		this.add(dijkstra);

		adjList.addMouseListener(new mouse());
		adjMat.addMouseListener(new mouse());
		incMat.addMouseListener(new mouse());
		repMat.addMouseListener(new mouse());
		// hamiltonCircuit.addMouseListener(a);
		hamiltonPath.addMouseListener(new mouse());
		coloring.addMouseListener(new mouse());
		eulerPath.addMouseListener(new mouse());
		FA.addMouseListener(new mouse());
		MST.addMouseListener(new mouse());
		MHC.addMouseListener(new mouse());
		maxFlowButton.addMouseListener(new mouse());
		dijkstra.addMouseListener(new mouse());
		setLayout(null);
	}

	private static class mouse implements MouseListener {
		public void mouseClicked(MouseEvent event) {
			if (event.getComponent().getName().equals("adjList")) {
				Home.choice = 1;
				Home.home.copy(new TypeFrame("Select"));

			}
			if (event.getComponent().getName().equals("adjMat")) {
				Home.choice = 2;
				Home.home.copy(new TypeFrame("Select"));
			}
			if (event.getComponent().getName().equals("incMat")) {
				Home.choice = 3;
				Home.home.copy(new TypeFrame("Select"));

			}
			if (event.getComponent().getName().equals("repMat")) {
				Home.choice = 4;
				Home.home.copy(new TypeFrame("Select"));

			}
			if (event.getComponent().getName().equals("eulerPath")) {
				Home.choice = 5;
				Home.home.copy(new InputFrame("Graph", false));

			}
			if (event.getComponent().getName().equals("coloring")) {
				Home.choice = 6;
				Home.home.copy(new InputFrame("Graph", false));

			}
			if (event.getComponent().getName().equals("hamiltonPath")) {
				Home.choice = 7;
				Home.home.copy(new InputFrame("Graph", false));

			}
			if (event.getComponent().getName().equals("FA")) {
				Home.choice = 9;
				Home.home.copy(new InputFrame("Graph", false));
			}
			if (event.getComponent().getName().equals("MHC")) {
				Home.choice = 10;
				Home.home.copy(new InputFrame("Graph", false));

			}
			if (event.getComponent().getName().equals("MST")) {
				Home.choice = 11;
				Home.home.copy(new InputFrame("Input", false));
			}

			if (event.getComponent().getName().equals("maxflow")) {
				Home.choice = 12;
				Home.home.copy(new InputFrame("Input", true));
			}

			if (event.getComponent().getName().equals("dijkstra")) {
				Home.choice = 13;
				Home.home.copy(new InputFrame("Input", false));
			}

		}

		public void mousePressed(MouseEvent event) {
		}

		public void mouseReleased(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}
	}
}