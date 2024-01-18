package aj210328;

public class Reader extends Thread {

	private static int period;
	private static int timesRead = 0;

	public Reader(int T) {
		setDaemon(true);
		period = T;
	}

	public static int getPeriod() {
		return period;
	}

	public static int getTimesRead() {
		return timesRead;
	}

	public static final String OID_cmpCPUTotal5secRev = "1.3.6.1.4.1.9.9.109.1.1.1.1.6.1";
	public static final String OID_cmpCPUTotal1minRev = "1.3.6.1.4.1.9.9.109.1.1.1.1.7.1";
	public static final String OID_cmpCPUTotal5minRev = "1.3.6.1.4.1.9.9.109.1.1.1.1.8.1";
	public static final String OID_memoryPoolName = "1.3.6.1.4.1.9.9.48.1.1.1.2"; // .1 and .2
	public static final String OID_memoryPoolUsed = "1.3.6.1.4.1.9.9.48.1.1.1.5"; // .1 and .2
	public static final String OID_memoryPoolFree = "1.3.6.1.4.1.9.9.48.1.1.1.6"; // .1 and .2

	@Override
	public void run() {
		while (true) {
			try {
				// citanje svih MIB-ova za sve rutere i dodavanje u odgovarajuce liste
				System.out.println("Citanje");
				for (Router r : RouterRegister.getRouters()) {
					r.add5secUsage(parseIntOrZero(SnmpQuery.getOID(OID_cmpCPUTotal5secRev, r)));
					r.add1minUsage(parseIntOrZero(SnmpQuery.getOID(OID_cmpCPUTotal1minRev, r)));
					r.add5minUsage(parseIntOrZero(SnmpQuery.getOID(OID_cmpCPUTotal5minRev, r)));
					r.addMemPool1Free(parseIntOrZero(SnmpQuery.getOID(OID_memoryPoolFree + ".1", r)));
					r.addMemPool1Used(parseIntOrZero(SnmpQuery.getOID(OID_memoryPoolUsed + ".1", r)));
					r.addMemPool2Free(parseIntOrZero(SnmpQuery.getOID(OID_memoryPoolFree + ".2", r)));
					r.addMemPool2Used(parseIntOrZero(SnmpQuery.getOID(OID_memoryPoolUsed + ".2", r)));
				}
				timesRead++;
				Main.gm.updateGraphs();

				// sleep
				Thread.sleep(period * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int parseIntOrZero(String string) {
		int res = 0;
		try {
			res = Integer.parseInt(string);
		} catch (Exception e) {

		}
		return res;
	}
}
