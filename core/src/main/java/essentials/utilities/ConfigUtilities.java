package essentials.utilities;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import essentials.utilitiesvr.nbt.NBTUtilities;

public class ConfigUtilities {
	public static ItemStack readItemStack(ConfigurationSection section, String key) {
		if(section == null) return null;
		
		if(section.isItemStack(key)) {
			return section.getItemStack(key);
		}
		
		if(section.isString(key)) {
			return (ItemStack) ItemStackJSONUtilities.toObject(section.getString(key));
		}
		
		if(section.isConfigurationSection(key)) {
			section = section.getConfigurationSection(key);
			
			Material material = Material.valueOf(section.getString("material").toUpperCase());
			ItemStack result = new ItemStack(material);
			
			if(section.contains("nbt"))
				NBTUtilities.setNBTTagCompound(result, NBTUtilities.parse(section.getString("nbt")));
			if(section.contains("amount"))
				result.setAmount(section.getInt("amount"));
			
			ItemMeta meta = result.getItemMeta();
			if(section.contains("displayname"))
				meta.setDisplayName(section.getString("displayname"));
			if(section.contains("lore"))
				meta.setLore(section.getStringList("lore"));
			if(section.contains("unbreakable"))
				meta.setUnbreakable(section.getBoolean("unbreakable"));
			result.setItemMeta(meta);
			
			return result;
		}
		
		return null;
	}
	
	public static int getItemStackSaveType(ConfigurationSection section, String key) {
		if(section == null) return 2;
		
		if(section.isItemStack(key))
			return 0;
		
		if(section.isString(key))
			return 1;
		
		return 2;
	}
	
	public static void setItemStack(ItemStack itemStack, ConfigurationSection section, String key) {
		if(section == null) return;
		
		if(section.isItemStack(key)) {
			setItemStack(itemStack, section, key, 0);
			return;
		}
		
		if(section.isString(key)) {
			setItemStack(itemStack, section, key, 1);
			return;
		}
		
		setItemStack(itemStack, section, key, 2);
	}
	
	public static void setItemStack(ItemStack itemStack, ConfigurationSection section, String key, int type) {
		if(section == null) return;
		
		if(type == 0) {
			section.set(key, itemStack);
			return;
		}
		
		if(type == 1) {
			section.set(key, ItemStackJSONUtilities.toString(itemStack));
			return;
		}
		
		{
			ConfigurationSection itemSection = section.getConfigurationSection(key);
			if(itemSection == null) {
				section = section.createSection(key);
			} else {
				section = itemSection;
			}
		}
		
		section.set("material", itemStack.getType().name());
		section.set("nbt", NBTUtilities.nbtToJson(NBTUtilities.getNBTTag(itemStack)));
		section.set("amount", itemStack.getAmount());
		
		ItemMeta meta = itemStack.getItemMeta();
		
		section.set("displayname", meta.getDisplayName());
		section.set("lore", meta.getLore());
		section.set("unbreakable", meta.isUnbreakable());
	}
}
