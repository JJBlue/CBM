package essentials.modules.kits;

import java.io.File;
import java.io.IOException;
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
	static boolean saved = true;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder() + "/kits.yml");
		configuration = YamlConfiguration.loadConfiguration(file);
		saved = true;
	}
	
	public static void unload() {
		if(!saved) {
			save();
		}
		
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
	
	public static void saveKit(Kit kit) {
		if(!kit.saved) return;
		saved = false;
		
		ConfigurationSection kitsSection = getKitsSection();
		ConfigurationSection kitSection = kitsSection.getConfigurationSection(kit.ID);
		
		kitSection.set("name", kit.name);
		kit.showItemStack = ConfigUtilities.readItemStack(kitSection.getConfigurationSection("showItem"));
		
		kitSection.set("claimOnlyOne", kit.claimOneTime);
		kitSection.set("command-run", kit.commandrun);
		kitSection.set("cooldown", kit.cooldown);
		kitSection.set("exp", kit.exp);
		kitSection.set("money", kit.money);
		kitSection.getBoolean("permission", kit.permission);
		
		//kit.items = getItemStacks(kitSection);
		//TODO
		
		kit.saved = true;
	}
	
	public static void removeKit(Kit kit) {
		saved = false;
		getKitsSection().set(kit.ID, null);
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
	
	public static void save() {
		try {
			configuration.save(file);
			saved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
