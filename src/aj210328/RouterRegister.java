package aj210328;

public class RouterRegister {
	public static final int NUM_OF_ROUTERS = 3;
	private static Router[] routers = new Router[NUM_OF_ROUTERS];
	private static Router selectedRouter;

	static {
		routers[0] = new Router(1, "192.168.10.1");
		routers[1] = new Router(2, "192.168.20.1");
		routers[2] = new Router(3, "192.168.30.1");
		selectedRouter = routers[0];
	}

	private RouterRegister() {
	}

	public static Router getRouter(int Rn) {
		if (Rn < 1 || Rn > NUM_OF_ROUTERS) {
			System.out.println("Nepostojeci ruter!");
			return null;
		}
		return routers[Rn - 1];
	}

	public static Router[] getRouters() {
		return routers;
	}

	public static Router getSelectedRouter() {
		return selectedRouter;
	}

	public static void setSelectedRouter(Router r) {
		selectedRouter = r;
	}
	
	public static void setSelectedRouter(int r) {
		selectedRouter = routers[r-1];
	}
}
