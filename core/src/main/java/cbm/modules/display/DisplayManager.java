package cbm.modules.display;

import org.bukkit.configuration.ConfigurationSection;

import cbm.player.PlayersYMLConfig;

public class DisplayManager {
	private DisplayManager() {}
	
	public static void load() {
		ConfigurationSection section = getConfigurationSection();
		
		if(section == null)
			section = PlayersYMLConfig.getConfiguration().createSection("display");
		
		section.addDefault("showElytraSpeed", true);
		section.addDefault("showDamageOnEntity", true);
	}
	
	public static ConfigurationSection getConfigurationSection() {
		return PlayersYMLConfig.getConfigurationSection("display");
	}
}
