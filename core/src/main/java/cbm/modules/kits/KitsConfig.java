package cbm.modules.kits;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import cbm.config.ConfigHelper;
import cbm.config.MainConfig;
import cbm.utilities.ConfigUtilities;
import cbm.utilities.StringUtilities;
import cbm.utilities.conditions.Condition;

public class KitsConfig {
	
	static File file;
	static FileConfiguration configuration;
	static boolean saved = true;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder() + "/kits.yml");
		if(!file.exists())
			ConfigHelper.extractDefaultConfigs("kits", "kits.yml");
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
			kit.showItemStack = ConfigUtilities.readItemStack(kitSection, "showItem");
			
			kit.settings.setClaimOneTime(kitSection.getBoolean("claimOnlyOne"));
			kit.settings.setCooldown(kitSection.getInt("cooldown"));
			kit.settings.setCondition(new Condition(
				kitSection.getString("condition"),
				kitSection.getString("execute")
			));
			kit.items = getItemStacks(kitSection.getConfigurationSection("items"));
			kit.settings.setPermission(kitSection.getBoolean("permission"));
			
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
		kit.showItemStack = ConfigUtilities.readItemStack(kitSection, "showItem");
		
		kitSection.set("claimOnlyOne", kit.settings.isClaimOneTime());
		kitSection.set("cooldown", kit.settings.getCooldown());
		if(kit.settings.getCondition() != null){
			kitSection.set("condition", kit.settings.getCondition().getConditionToString());
			kitSection.set("execute", kit.settings.getCondition().getExecuteToString());
		}
		kitSection.getBoolean("permission", kit.settings.isPermission());
		setItemStacks(kit.items, kitSection.createSection("items"));
		
		kit.saved = true;
	}
	
	public static void removeKit(Kit kit) {
		saved = false;
		getKitsSection().set(kit.ID, null);
	}
	
	public static ConfigurationSection getKitsSection() {
		return configuration.getConfigurationSection("kits");
	}

	public static List<ItemStack> getItemStacks(ConfigurationSection items) {
		List<ItemStack> itemStacks = new LinkedList<>();
		
		for(String key : items.getKeys(false)) {
			ItemStack itemStack = ConfigUtilities.readItemStack(items, key);
			itemStacks.add(itemStack);
		}
		
		return itemStacks;
	}
	
	public static void setItemStacks(List<ItemStack> items, ConfigurationSection section) {
		int type = -1;
		
		//Clear all old itemstacks
		for(String key : section.getKeys(false)) {
			if(type == -1) {
				type = ConfigUtilities.getItemStackSaveType(section, key);
			}
			section.set(key, null);
		}
		
		//Set new Itemstacks
		String ID = null;
		
		for(ItemStack itemStack : items) {
			ID = StringUtilities.nextLetterString(ID);
			ConfigUtilities.setItemStack(itemStack, section, ID, type);
		}
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
