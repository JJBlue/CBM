package essentials.status;

import java.io.File;

public class SystemStatus {
	private SystemStatus() {}
	
	public static String getPlatform() {
		return System.getProperty("os.name");
	}
	
	public static long getAllProcesses() {
		return ProcessHandle.allProcesses().count();
	}
	
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}
	
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}
	
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}
	
	public static long getUsedMemory() {
		return getTotalMemory() - getFreeMemory();
	}
	
	public static int getRunningThreads() {
		return Thread.getAllStackTraces().size();
	}
	
	public static int getCores() {
		return -1; //TODO
	}
	
	public static String getJavaVersion() {
		return Runtime.version().toString();
	}
	
	public static long getMaxDisk() {
		return myFile().getTotalSpace();
	}
	
	public static long getFreeDisk() {
		return myFile().getFreeSpace();
	}
	
	public static long getTotalDisk() {
		return myFile().getTotalSpace();
	}
	
	public static long getUsedDisk() {
		return myFile().getTotalSpace() - myFile().getFreeSpace();
	}
		
	public static double BytestoMB(long bytes) {
		return bytes / 1000000d;
	}
	
	public static double BytestoGB(long bytes) {
		return bytes / 1000000000d;
	}
	
	public static double BytestoGB(long bytes, int amount) {
		return round(bytes / 1000000000d, amount);
	}
	
	private static File myFile() {
		return new File(".");
	}
	
	public static double round(double value, int amount) {
		long roundV = (long) Math.pow(10, amount);
		long tmp = (long) (value * roundV);
		return (double) tmp / (double) roundV;
	}
}
