package gui;
import java.awt.Component;
import javax.swing.*;

public class SingleFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public SingleFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(350, 150);
		setSize(600, 500);
		setLayout(null);

	}

	public void copy(JFrame f) {
		setVisible(false);
		clear();
		for (Component ca : f.getContentPane().getComponents()) {
			this.add(ca);
		}
		this.setTitle(f.getTitle());
		this.setLayout(f.getContentPane().getLayout());
		paintAll(this.getGraphics());
		setVisible(true);
	}

	public void clear() {
		getContentPane().removeAll();
	}
}