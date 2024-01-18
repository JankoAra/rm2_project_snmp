package aj210328;

import java.util.ArrayList;

public class Router {

	private String ipAddress;
	private int id;

	public static final String COMMUNITY_STRING = "si2019";
	
	private static final int listSize = 300;

	private ArrayList<Integer> cpu5secUsage = new ArrayList<Integer>(listSize);
	private ArrayList<Integer> cpu1minUsage = new ArrayList<Integer>(listSize);
	private ArrayList<Integer> cpu5minUsage = new ArrayList<Integer>(listSize);
	private ArrayList<Integer> memPool1free = new ArrayList<Integer>(listSize);
	private ArrayList<Integer> memPool1used = new ArrayList<Integer>(listSize);
	private ArrayList<Integer> memPool2free = new ArrayList<Integer>(listSize);
	private ArrayList<Integer> memPool2used = new ArrayList<Integer>(listSize);
	
	public static final String memPoolName1 = "Processor";
	public static final String memPoolName2 = "I/O";

	public Router(int id, String ip) {
		ipAddress = ip;
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void add5secUsage(int val) {
		cpu5secUsage.add(val);
	}

	public void add1minUsage(int val) {
		cpu1minUsage.add(val);
	}

	public void add5minUsage(int val) {
		cpu5minUsage.add(val);
	}

	public void addMemPool1Free(int val) {
		memPool1free.add(val);
	}

	public void addMemPool1Used(int val) {
		memPool1used.add(val);
	}

	public void addMemPool2Free(int val) {
		memPool2free.add(val);
	}

	public void addMemPool2Used(int val) {
		memPool2used.add(val);
	}

	@Override
	public String toString() {
		return "Ruter" + id;
	}

	public int getId() {
		return id;
	}

	public static String getCommunityString() {
		return COMMUNITY_STRING;
	}

	public ArrayList<Integer> getCpu5secUsage() {
		return cpu5secUsage;
	}

	public ArrayList<Integer> getCpu1minUsage() {
		return cpu1minUsage;
	}

	public ArrayList<Integer> getCpu5minUsage() {
		return cpu5minUsage;
	}

	public ArrayList<Integer> getMemPool1free() {
		return memPool1free;
	}

	public ArrayList<Integer> getMemPool1used() {
		return memPool1used;
	}

	public ArrayList<Integer> getMemPool2free() {
		return memPool2free;
	}

	public ArrayList<Integer> getMemPool2used() {
		return memPool2used;
	}
}
