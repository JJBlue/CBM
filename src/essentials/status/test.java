package essentials.status;

public class test {

	public static void main(String[] args) {
		System.out.println(SystemStatus.BytestoGB(SystemStatus.getMaxMemory()) + " " + SystemStatus.BytestoGB(SystemStatus.getTotalDisk()) + " " + SystemStatus.BytestoGB(SystemStatus.getFreeDisk()));
		
		System.out.println("§e--------------------------------------------------");
		System.out.println("§e Platform: §6" + SystemStatus.getPlatform() + " §eRunning threads: §6" + SystemStatus.getRunningThreads());
		System.out.println("§e CPU usage: % ( cores)");
		System.out.println("§e Uptime: ");
		System.out.println("§e TPS: ()");
		System.out.println("§e Memory usage: " + (SystemStatus.BytestoMB(SystemStatus.getTotalMemory()) / SystemStatus.BytestoMB(SystemStatus.getUsedMemory())) + "% (" + SystemStatus.BytestoMB(SystemStatus.getUsedMemory()) + "/ " + SystemStatus.BytestoMB(SystemStatus.getTotalMemory()) + " MB)");
		System.out.println("§e Java version: " + SystemStatus.getJavaVersion());
		System.out.println("§e Disk usage: " + (SystemStatus.BytestoGB(SystemStatus.getTotalDisk()) / SystemStatus.BytestoGB(SystemStatus.getUsedDisk())) + "% (" + SystemStatus.BytestoGB(SystemStatus.getUsedDisk()) + "/ " + SystemStatus.BytestoGB(SystemStatus.getMaxDisk()) + " GB)");
		
		System.out.println("§e--------------------------------------------------");
	}

}
