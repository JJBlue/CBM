package essentials.modules.claim;

import org.bukkit.configuration.ConfigurationSection;

public class ClaimConfigMethods {
	public static double costPerBlock() {
		return getClaimSection().getDouble("costPerBlock");
	}
	
	public static long getDefaultFreeBlocks() {
		return getClaimSection().getLong("freeBlocks");
	}
	
	public static ConfigurationSection getClaimSection() {
		return ClaimConfig.getSection("claim");
	}
}
