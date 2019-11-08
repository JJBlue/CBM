package essentials.modules.kits;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import essentials.config.MainConfig;
import essentials.utilities.ConfigUtilities;

public class KitsConfig {
	
	static File file;
	static FileConfiguration configuration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder() + "/kits.yml");
		configuration = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void unload() {
		file = null;
		configuration = null;
	}
	
	public static List<Kit> loadKit() {
		List<Kit> kits = new LinkedList<>();
		
		ConfigurationSection kitsSection = getKitsSection();
		
		for(String key : kitsSection.getKeys(false)) {
			ConfigurationSection kitSection = kitsSection.getConfigurationSection(key);
			
			Kit kit = new Kit(key);
			
			kit.saved = true;
			
			kit.name = kitSection.getString("name");
			kit.showItemStack = ConfigUtilities.readItemStack(kitSection.getConfigurationSection("showItem"));
			
			kit.claimOneTime = kitSection.getBoolean("claimOnlyOne");
			kit.commandrun = kitSection.getStringList("command-run");
			kit.cooldown = kitSection.getInt("cooldown");
			kit.exp = kitSection.getInt("exp");
			kit.money = kitSection.getInt("money");
			kit.items = getItemStacks(kitSection);
			kit.permission = kitSection.getBoolean("permission");
			
			kits.add(kit);
		}
		
		return kits;
	}
	
	public static ConfigurationSection getKitsSection() {
		return configuration.getConfigurationSection("kits");
	}

	public static List<ItemStack> getItemStacks(ConfigurationSection section) {
		List<ItemStack> itemStacks = new LinkedList<>();
		ConfigurationSection items = section.getConfigurationSection("items");
		
		for(String key : items.getKeys(false)) {
			ItemStack itemStack = ConfigUtilities.readItemStack(items.getConfigurationSection(key));
			itemStacks.add(itemStack);
		}
		
		return itemStacks;
	}
}
