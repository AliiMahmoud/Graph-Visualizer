package gui;

import javax.swing.*;

import org.apache.commons.collections15.MapIterator;
import org.apache.commons.collections15.Transformer;
import algorithms.MyGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.VertexLabelRenderer;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int windowH, windowW;
	private static MyGraph graph = null;
	VisualizationViewer<String, String> vv;

	public GraphPanel(MyGraph a) {
		graph = a;
		updateView();
	}

	public void changeColor(String colors, int max) {

		final String[] lines = colors.split("\n");

		final Vector<Color> col = new Vector<Color>();
		for (int i = 0; i < max; ++i) {
			Color color = new Color((int) (Math.random() * 0x1000000));
			while (col.contains(color)) {
				color = new Color((int) (Math.random() * 0x1000000));

			}
			col.add(color);
		}

		Transformer<String, Paint> painter = new Transformer<String, Paint>() {

			public Paint transform(String arg0) {

				for (int i = 0; i < lines.length; ++i) {
					String[] data = lines[i].split(" ");
					String node = data[0];
					int color = Integer.parseInt(data[1]);
					if (node.equals(arg0)) {
						return col.get(color);
					}
				}
				return null;
			}
		};

		Transformer<String, String> t = new Transformer<String, String>() {

			public String transform(String arg0) {
				for (int i = 0; i < lines.length; ++i) {
					String[] data = lines[i].split(" ");
					String node = data[0];
					int color = Integer.parseInt(data[1]) + 1;

					if (node.equals(arg0)) {
						return node + " color[" + color + "]";
					}
				}
				return arg0;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(painter);
		vv.getRenderContext().setVertexLabelTransformer(t);
	}

	public void coloredMinTree(String tree) {

		final String[] lines = tree.split("\n");		
		Transformer<String, Paint> t = new Transformer<String, Paint>() {

			public Paint transform(String cost) {
				for (int i = 0; i < lines.length; ++i) {
					
					String from = graph.getEndpoints(cost).getFirst();
					String to = graph.getEndpoints(cost).getSecond();

					String[] data = lines[i].split(" ");
					if ((from.equals(data[0]) && to.equals(data[1]))
							|| (from.equals(data[1]) && to.equals(data[0]))) {
						
						if((cost.trim().equals(data[2])) || cost.trim().equals("") && data[2].equals("0")){
							return Color.GREEN;	
						}						
					}

				}
				return Color.black;
			}
		};
		vv.getRenderContext().setEdgeDrawPaintTransformer(t);
	}

	public void updateView() {
		this.setLayout(null);
		windowW = 580 - 240;
		windowH = 460 - 20;
		setBounds(230, 10, windowW, windowH);
		setBackground(Color.darkGray);
		initialize();
		vv.setBounds(10, 10, windowW - 20, windowH - 20);
		repaint();
	}

	public static MyGraph getGraph() {
		return graph;
	}

	public void setGraph(MyGraph graph) {
		GraphPanel.graph = graph;
		updateView();
	}

	public void initialize() {

		vv = new VisualizationViewer<String, String>(
				new FRLayout<String, String>(graph), new Dimension(280, 280));

		Transformer<String, String> transformer = new Transformer<String, String>() {
			public String transform(String arg0) {
				return arg0;
			}
		};
		vv.getRenderContext().setVertexLabelTransformer(transformer);
		transformer = new Transformer<String, String>() {
			public String transform(String arg0) {
				return arg0;
			}
		};
		vv.getRenderContext().setEdgeLabelTransformer(transformer);
		final DefaultModalGraphMouse<String, Number> graphMouse = new DefaultModalGraphMouse<String, Number>();
		vv.setGraphMouse(graphMouse);

		graphMouse.setMode(DefaultModalGraphMouse.Mode.PICKING);
		add(vv);
	}

	void addNode(String name) {
		graph.addVertex(name);
		repaint();

	}

	void deleteNode(String name) {
		graph.removeVertex(name);
		repaint();
	}

	void deleteEdge(String from, String to) {
		graph.removeEdge(graph.findEdge(from, to));
		repaint();
	}

	void addEdge(String from, String to, String cost) {
		graph.addEdge(from, to, cost);
		repaint();
	}

	void clear() {
		graph.removeAll();
		repaint();

	}
}
