package aj210328;

import gui.GraphMenu;
import gui.StartMenu;

public class Main {

	static StartMenu startMenu;
	static GraphMenu gm;

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				startMenu = new StartMenu();
				startMenu.setVisible(true);
				gm = new GraphMenu();
				gm.setVisible(false);
			}
		});
	}

	public static void switchFrame() {
		startMenu.setVisible(false);
		gm.setVisible(true);
	}

}