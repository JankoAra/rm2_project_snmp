package gui;

import java.util.ArrayList;

import aj210328.Router;
import aj210328.RouterRegister;

public class BarGraphController {
	private static Router selectedRouter;
	private static String selectedMIB;
	private static String[] possibleMIBs = { "CPU usage last 5s", "CPU usage last 1min", "CPU usage last 5min",
			"Amount of used memory", "Amount of free memory" };

	static {
		selectedRouter = RouterRegister.getRouter(1);
		selectedMIB = possibleMIBs[0];
	}

	private BarGraphController() {
	}

	public static Router getSelectedRouter() {
		return selectedRouter;
	}

	public static void setSelectedRouter(Router selectedRouter) {
		BarGraphController.selectedRouter = selectedRouter;
	}

	public static void setSelectedRouter(int Rn) {
		setSelectedRouter(RouterRegister.getRouter(Rn));
	}

	public static String getSelectedMIB() {
		return selectedMIB;
	}

	public static void setSelectedMIB(String selectedMIB) {
		BarGraphController.selectedMIB = selectedMIB;
	}

	public static ArrayList<Integer> extractSelectedData() {
		switch (selectedMIB) {
		case "CPU usage last 5s":
			return selectedRouter.getCpu5secUsage();

		case "CPU usage last 1min":
			return selectedRouter.getCpu1minUsage();

		case "CPU usage last 5min":
			return selectedRouter.getCpu5minUsage();

		case "Amount of free memory":
			return selectedRouter.getMemPool1free();

		case "Amount of used memory":
			return selectedRouter.getMemPool1used();

		default:
			return null;
		}
	}

	public static BarGraph makeNewBarGraph() {
		System.out.println(selectedRouter + ":" + selectedMIB);
		return new BarGraph(extractSelectedData());
	}

}
