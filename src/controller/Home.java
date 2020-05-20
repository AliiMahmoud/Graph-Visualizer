package controller;

import gui.HomeFrame;
import gui.PrintingFrame;
import gui.SingleFrame;

public class Home {

	public static SingleFrame home = new SingleFrame();
	public static int choice = 0;

	public static void main(String[] args) {
		home.copy(new HomeFrame("Home"));
	}

	public static void updateFrame() {
		home.copy(new PrintingFrame(choice));
	}

	public static void updateFrameWithSrcAndDest(String src, String dest) {
		home.copy(new PrintingFrame(choice, src, dest));
	}

}