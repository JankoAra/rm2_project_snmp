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
//podesavanje
//sudo ip addr add 192.168.122.1/24 dev virbr0
//sudo ip route add 192.168.10.0/24 via 192.168.122.100 dev virbr0
//sudo ip route add 192.168.20.0/24 via 192.168.122.100 dev virbr0
//sudo ip route add 192.168.30.0/24 via 192.168.122.100 dev virbr0
//sudo ip route add 192.168.12.0/24 via 192.168.122.100 dev virbr0
//sudo ip route add 192.168.13.0/24 via 192.168.122.100 dev virbr0
//sudo ip route add 192.168.23.0/24 via 192.168.122.100 dev virbr0