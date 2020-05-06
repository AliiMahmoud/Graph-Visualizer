package gui;

import controller.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class TypeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	Label welcomeMsg = new Label("Select The Graph Type");
	Button directed = new Button("Directed Graph");
	Button undirected = new Button("Undirected Graph");
	Button back = new Button("Back");

	public TypeFrame(String name) {
		super(name);

		welcomeMsg.setBounds(180, 110, 370, 30);
		welcomeMsg.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		directed.setBounds(150, 200, 120, 35);
		undirected.setBounds(350, 200, 120, 35);
		back.setBounds(10, 420, 90, 35);
		back.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		ImageIcon i = new ImageIcon("imgs/directed.png");
		JLabel l = new JLabel(i);
		l.setBounds(140, 270, 120, 140);
		this.add(l);
		ImageIcon j = new ImageIcon("imgs/undirected.png");
		JLabel la = new JLabel(j);
		la.setBounds(340, 270, 120, 155);
		this.add(la);

		directed.setName("dir");
		undirected.setName("undir");
		back.setName("back");

		directed.addMouseListener(new mouse());
		undirected.addMouseListener(new mouse());
		back.addMouseListener(new mouse());

		this.add(welcomeMsg);
		this.add(directed);
		this.add(back);
		this.add(undirected);

		setLayout(null);

	}

	private class mouse implements MouseListener {
		public void mouseClicked(MouseEvent event) {
			String name = event.getComponent().getName();
			if (name.equals("dir")) {
				InputFrame in = new InputFrame("Directed Graph", true);
				Home.home.copy(in);

			} else if (name.equals("back")) {
				Home.home.copy(new HomeFrame("Home"));

			} else
				Home.home.copy(new InputFrame("Undirected Graph", false));

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