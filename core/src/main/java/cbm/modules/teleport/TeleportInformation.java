package cbm.modules.teleport;

import org.bukkit.Location;

public class TeleportInformation {
	public Location location;
	public int cooldown;
	
	public TeleportInformation(Location location, int cooldown) {
		this.location = location;
		this.cooldown = cooldown;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
}
