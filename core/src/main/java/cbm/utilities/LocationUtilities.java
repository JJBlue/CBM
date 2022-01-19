package cbm.utilities;

import org.bukkit.Location;

public class LocationUtilities {
	public static String toString(Location location) {
		if(location == null) return "?";
		return "(" + round(location.getX()) + " " + round(location.getY()) + " " + round(location.getZ()) + ") (" + round(location.getYaw()) + " " + round(location.getPitch()) + ")";
	}
	
	protected static double round(double d) {
		return Math.round(d * 100) / 100;
	}
}
