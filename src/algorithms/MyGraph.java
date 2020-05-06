package algorithms;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class MyGraph extends SparseMultigraph<String, String> {

	private static final long serialVersionUID = 1L;
	public EdgeType et = null;

	
	public MyGraph(EdgeType type) {
		super();
		et = type;
	}
	
	public MyGraph(MyGraph another) {
		super();
		vertices.putAll(another.vertices);
		edges.putAll(another.edges);
		
	}
	
	@Override
	public String findEdge(String arg0, String arg1) {
		if(super.findEdge(arg0, arg1) == null)
			return null;
		Object[] edgat = findEdgeSet(arg0, arg1).toArray();
		return edgat[edgat.length - 1].toString();
	}

	
	boolean findVertex(String name) {
		Object[] vertex = getVertices().toArray();
		for (int i = 0; i < vertex.length; ++i)
			if (vertex[i].equals(name))
				return true;
		return false;
	}

	public void removeAll() {

		Object[] vertex = getVertices().toArray();
		Object[] edges = getEdges().toArray();
		for (int i = 0; i < vertex.length; ++i)
			removeVertex(vertex[i].toString());
		for (int i = 0; i < edges.length; ++i)
			removeEdge(edges[i].toString());
	}

	public boolean addEdge(String from, String to, String cost) {

		if (from.equals("") || to.equals(""))
			return false;

		if (!findVertex(from) || !findVertex(to))
			return false;

		String c = "";
		try {
			double test = Double.parseDouble(cost);
			c = String.valueOf(cost);
		} catch (Exception e) {
			c = "";
		}
		if (cost.equals("")) {
			c = "";
		}
		while (containsEdge(c))
			c += ' ';

		super.addEdge(c, from, to, et);
		return true;
	}

	

}