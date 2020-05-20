package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.Home;
import algorithms.MyGraph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.util.EdgeType;

class InputFrame2 extends JFrame {

    private static final long serialVersionUID = 1L;

    static Boolean finish = null;
    GraphPanel p;
    Label addNodeLabel = new Label("Add Vertex");
    TextField addNodeTextField = new TextField();
    Button addNodeButton = new Button("Add");

    Label addEdge = new Label("Add Edge");
    Label fromLabel = new Label("From :");
    Label toLabel = new Label("To :");
    TextField from = new TextField();

    Label costLabel = new Label("Cost :");
    TextField cost = new TextField();

    TextField to = new TextField();
    Button addEdgeButton = new Button("Add");

    Label deleteVertex = new Label("Delete Vertex");
    Button deleteBtn = new Button("Delete");
    TextField delete = new TextField();

    Label deleteEdge = new Label("Delete Edge");
    Button deleteEBtn = new Button("Delete");
    Button back = new Button("Back");
    TextField deleteEFrom = new TextField();
    TextField deleteETo = new TextField();

    Label fromE = new Label("From :");
    Label toE = new Label("To :");
    Button submit = new Button("Done");

    Label srcLabel = new Label("Source");
    Label destLabel = new Label("Destination");
    TextField src = new TextField();
    TextField dest = new TextField();

    public InputFrame2(String name, boolean directed) {

        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(350, 150);
        setSize(600, 500);
        if (directed == true)
            p = new GraphPanel(new MyGraph(EdgeType.DIRECTED));
        else
            p = new GraphPanel(new MyGraph(EdgeType.UNDIRECTED));
        init();
    }

    public InputFrame2(MyGraph g) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(350, 150);
        setSize(600, 500);
        if (g.et.equals(EdgeType.DIRECTED))
            setTitle("Directed Graph");
        else
            setTitle("Undirected Graph");
        p = new GraphPanel(g);
        init();
    }

    public void init() {
        addNodeLabel.setBounds(70, 25, 100, 10);
        addNodeTextField.setBounds(20, 40, 140, 25);
        addNodeButton.setBounds(170, 40, 43, 25);

        deleteVertex.setBounds(60, 72, 100, 10);
        delete.setBounds(20, 90, 140, 25);
        deleteBtn.setBounds(170, 90, 43, 25);

        addEdge.setBounds(80, 160, 100, 10);
        fromLabel.setBounds(20, 183, 40, 10);
        from.setBounds(65, 180, 95, 20);
        toLabel.setBounds(35, 213, 25, 10);
        to.setBounds(65, 210, 95, 20);
        costLabel.setBounds(23, 243, 35, 10);
        cost.setBounds(65, 240, 95, 20);
        addEdgeButton.setBounds(170, 240, 45, 20);

        deleteEdge.setBounds(80, 289, 100, 10);
        fromE.setBounds(20, 313, 40, 10);
        deleteEFrom.setBounds(65, 310, 95, 20);
        toE.setBounds(35, 343, 25, 10);
        deleteETo.setBounds(65, 340, 95, 20);
        deleteEBtn.setBounds(170, 340, 45, 20);

        srcLabel.setBounds(50, 369, 70, 20);
        src.setBounds(25, 390, 80, 25);
        destLabel.setBounds(130, 369, 65, 25);
        dest.setBounds(124, 390, 80, 25);

        back.setBounds(25, 420, 70, 25);
        back.setName("back");

        submit.setBounds(140, 420, 60, 25);
        submit.setName("submit");

        submit.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        back.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));

        addNodeButton.setName("addVertex");
        deleteBtn.setName("deleteVertex");
        addEdgeButton.setName("addEdge");
        deleteEBtn.setName("deleteEdge");

        addNodeButton.addMouseListener(new mouse());
        submit.addMouseListener(new mouse());
        back.addMouseListener(new mouse());
        deleteBtn.addMouseListener(new mouse());
        addEdgeButton.addMouseListener(new mouse());
        deleteEBtn.addMouseListener(new mouse());

        add(submit);
        add(addNodeLabel);
        add(addNodeButton);
        add(addNodeTextField);
        add(deleteEdge);
        add(deleteEFrom);
        add(fromE);
        add(deleteETo);
        add(toE);
        add(deleteEBtn);
        add(deleteVertex);
        add(delete);
        add(deleteBtn);
        add(addEdge);
        add(fromLabel);
        add(toLabel);
        add(from);
        add(to);
        add(addEdgeButton);
        add(costLabel);
        add(cost);
        add(back);
        add(p);
        add(src);
        add(srcLabel);
        add(dest);
        add(destLabel);
    }

    private class mouse implements MouseListener, MouseMotionListener {
        public void mouseClicked(MouseEvent event) {

            if (event.getComponent().getName().equals("addVertex")) {
                if (addNodeTextField.getText().trim().equals(""))
                    return;
                p.addNode(addNodeTextField.getText().trim());
                addNodeTextField.setText("");
            }

            else if (event.getComponent().getName().equals("addEdge")) {
                String fromName = from.getText().trim();
                String toName = to.getText().trim();
                String costStr = cost.getText().trim();
                p.addEdge(fromName, toName, costStr);
                to.setText("");
                cost.setText("");
                from.setText("");

            } else if (event.getComponent().getName().equals("deleteVertex")) {
                p.deleteNode(delete.getText().trim());
                delete.setText("");
            }

            else if (event.getComponent().getName().equals("deleteEdge")) {
                String fromName = deleteEFrom.getText().trim();
                String toName = deleteETo.getText().trim();
                p.deleteEdge(fromName, toName);
                deleteEFrom.setText("");
                deleteETo.setText("");
            } else if (event.getComponent().getName().equals("submit")) {
                Home.updateFrame();
                Home.updateFrameWithSrcAndDest(src.getText().trim(), dest.getText().trim());
                
            } else {
                if (Home.choice >= 1 && Home.choice <= 4) {
                    Home.home.copy(new TypeFrame("Select"));
                } else
                    Home.home.copy(new HomeFrame("Home"));
            }
        }

        public void mousePressed(MouseEvent event) {

        }

        public void mouseDragged(MouseEvent event) {

        }

        public void mouseReleased(MouseEvent event) {

        }

        // Empty definitions for unused event methods.
        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {
        }
    }

}
