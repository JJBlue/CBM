package cbm.utilities.system;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.bukkit.Bukkit;

import com.sun.management.OperatingSystemMXBean;

import cbm.main.Main;
import cbm.utilities.MathUtilities;
import components.reflection.MethodReflection;
import components.reflection.ObjectReflection;

public class SystemStatus {
	private SystemStatus() {}

	public static String getPlatform() {
		return System.getProperty("os.name");
	}

	public static String getArchitecture() {
		return System.getProperty("os.arch");
	}
	
	public static double getCPUUsage() {
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		return operatingSystemMXBean.getProcessCpuLoad() * 100;
	}

	@SuppressWarnings("deprecation")
	public static double getSystemCPUUsage() {
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		return operatingSystemMXBean.getSystemCpuLoad() * 100;
	}

	public static double[] getRecentTPS() {
		try {
			Object dedictedServer = MethodReflection.callMethod(Bukkit.getServer(), "getServer");
			Object recentTPS = ObjectReflection.getObject("recentTps", dedictedServer);

			if (recentTPS != null)
				return (double[]) recentTPS;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getOnlineSince() {
		LocalDateTime started = Main.getOnline();
		LocalDateTime now = LocalDateTime.now();
		
		long days = started.until(now, ChronoUnit.DAYS);
		started = started.minusDays(days);
		
		long hours = started.until(now, ChronoUnit.HOURS);
		started = started.minusMinutes(hours);
		
		long minutes = started.until(now, ChronoUnit.MINUTES);
		started = started.minusMinutes(minutes);
		
		long seconds = started.until(now, ChronoUnit.SECONDS);
		started = started.minusSeconds(seconds);
		
		return days + " d " + hours + " h " + minutes + " m " + seconds + " s";

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
		return Runtime.getRuntime().availableProcessors();
	}

	public static String getJavaVersion() {
		String version = System.getProperty("java.version");
		
		if(version.startsWith("8") || version.startsWith("7"))
			return version;
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
		return MathUtilities.round(bytes / 1000000000d, amount);
	}

	private static File myFile() {
		return new File(".");
	}
}
