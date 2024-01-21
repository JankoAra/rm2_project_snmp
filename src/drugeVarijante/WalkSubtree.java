package drugeVarijante;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import aj210328.RouterRegister;
import aj210328.SnmpQuery;

public class WalkSubtree {

	public static void main(String[] args) {
		List<String> routeDest = SnmpQuery.getSubtree(".1.3.6.1.2.1.4.21.1.1", RouterRegister.getRouter(1));
		List<String> routeMask = SnmpQuery.getSubtree(".1.3.6.1.2.1.4.21.1.11", RouterRegister.getRouter(1));
		List<String> routeNextHop = SnmpQuery.getSubtree(".1.3.6.1.2.1.4.21.1.7", RouterRegister.getRouter(1));
		List<String> routeProto = SnmpQuery.getSubtree(".1.3.6.1.2.1.4.21.1.9", RouterRegister.getRouter(1));
		if(new HashSet<Integer>(Arrays.asList(routeDest.size(), routeMask.size(), routeNextHop.size(), routeProto.size())).size()!=1) {
			System.out.println("Nejednaki skupovi");
			return;
		}
		int size = routeDest.size();
		for(int i=0;i<size;i++) {
			System.out.println(routeDest.get(i)+"\t"+routeMask.get(i)+"\t"+routeNextHop.get(i)+"\t"+routeProto.get(i));
		}
	}

}
