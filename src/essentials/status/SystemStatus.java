package essentials.status;

import java.io.File;

import essentials.Image.staticImage;

public class SystemStatus {
	private SystemStatus() {}
	
	public static String getPlatform() {
		return null;//TODO
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
		return -1; //TODO
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
	
	private static File myFile() {
		return new File(".");
	}
}
