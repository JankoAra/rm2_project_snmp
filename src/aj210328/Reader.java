package aj210328;

import gui.BarGraph;
import gui.BarGraphController;

public class Reader extends Thread {

	private int period;

	public Reader(int T) {
		setDaemon(true);
		period = T;
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
				// read all relevant MIBs for all routers and store results in corresponding
				// router info lists
				for (Router r : RouterRegister.getRouters()) {
					r.add5secUsage(Integer.parseInt(SnmpQuery.getOID(OID_cmpCPUTotal5secRev, r)));
					r.add1minUsage(Integer.parseInt(SnmpQuery.getOID(OID_cmpCPUTotal1minRev, r)));
					r.add5minUsage(Integer.parseInt(SnmpQuery.getOID(OID_cmpCPUTotal5minRev, r)));
					r.addMemPool1Free(Integer.parseInt(SnmpQuery.getOID(OID_memoryPoolFree + ".1", r)));
					r.addMemPool1Used(Integer.parseInt(SnmpQuery.getOID(OID_memoryPoolUsed + ".1", r)));
					r.addMemPool2Free(Integer.parseInt(SnmpQuery.getOID(OID_memoryPoolFree + ".2", r)));
					r.addMemPool2Used(Integer.parseInt(SnmpQuery.getOID(OID_memoryPoolUsed + ".2", r)));
				}
				
				Main.gm.updateBarGraph();
				System.out.println(BarGraphController.extractSelectedData());
				

				// sleep
				Thread.sleep(period * 1000);

				System.out.println("Citanje");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
