package gui;

import java.util.ArrayList;

import aj210328.Router;
import aj210328.RouterRegister;

public class GraphController {
	private static Router selectedRouter;
	private static String selectedMIB;
	private static String[] possibleMIBs = { "CPU usage last 5s", "CPU usage last 1min", "CPU usage last 5min",
			"Amount of used memory", "Amount of free memory" };
	private static int numberOfGraphsNeeded = 1;

	static {
		selectedRouter = RouterRegister.getRouter(1);
		selectedMIB = possibleMIBs[0];
	}

	private GraphController() {
	}

	public static int getNumberOfGraphsNeeded() {
		return numberOfGraphsNeeded;
	}

	public static Router getSelectedRouter() {
		return selectedRouter;
	}

	public static void setSelectedRouter(Router selectedRouter) {
		GraphController.selectedRouter = selectedRouter;
	}

	public static void setSelectedRouter(int Rn) {
		setSelectedRouter(RouterRegister.getRouter(Rn));
	}

	public static String getSelectedMIB() {
		return selectedMIB;
	}

	public static void setSelectedMIB(String selectedMIB) {
		GraphController.selectedMIB = selectedMIB;
		if (selectedMIB.equals("Amount of used memory") || selectedMIB.equals("Amount of free memory")) {
			numberOfGraphsNeeded = 2;
		} else {
			numberOfGraphsNeeded = 1;
		}
	}

	public static ArrayList<Integer> extractSelectedDataOneGraph() {
		switch (selectedMIB) {
		case "CPU usage last 5s":
			return selectedRouter.getCpu5secUsage();
		case "CPU usage last 1min":
			return selectedRouter.getCpu1minUsage();
		case "CPU usage last 5min":
			return selectedRouter.getCpu5minUsage();
		default:
			return null;
		}
	}

	public static ArrayList<ArrayList<Integer>> extractSelectedDataTwoGraphs() {
		ArrayList<ArrayList<Integer>> res;
		switch (selectedMIB) {
		case "Amount of free memory":
			res = new ArrayList<>();
			res.add(selectedRouter.getMemPool1free());
			res.add(selectedRouter.getMemPool2free());
			return res;
		case "Amount of used memory":
			res = new ArrayList<>();
			res.add(selectedRouter.getMemPool1used());
			res.add(selectedRouter.getMemPool2used());
			return res;
		default:
			return null;
		}
	}

}
