package controller;
import gui.HomeFrame;
import gui.PrintingFrame;
import gui.SingleFrame;

public class Home {

	public static SingleFrame home = new SingleFrame();
	public static int choice = 0;

	/**
	 * static methods [get minimum spanning tree and Etc] Another static frames
	 * with Mouse Listener
	 */

	public static void main(String[] args) {
		home.copy(new HomeFrame("Home"));
	}

	public static void updateFrame() {

		if (choice >= 1 && choice <= 4) {
			home.copy(new PrintingFrame(choice));
		} else if (choice == 5) {
//			System.out.println("Euler's path/Circuit");
			home.copy(new PrintingFrame(choice));
		}

		else if (choice == 7) {
//			System.out.println("Hamiltonian path/Circuit");
			home.copy(new PrintingFrame(choice));
			

		} else if (choice == 6) {
//			System.out.println("Graph Coloring Problem.");

			home.copy(new PrintingFrame(choice));
		}

		else if (choice == 9) {
//			System.out.println("Fleury Algorithm");
			home.copy(new PrintingFrame(choice));

		}

		else if (choice == 10) {
//			System.out.println("Minimun Hamiltonian Circuit");
			home.copy(new PrintingFrame(choice));

			

		} else {

//			System.out.println("Minimun Spanning Tree");
			home.copy(new PrintingFrame(choice));

		}

	}

}