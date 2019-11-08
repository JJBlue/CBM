package essentials.utilities;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfigUtilities {
	public static ItemStack readItemStack(ConfigurationSection section) {
		if(section == null) return null;
		
		Material material = Material.valueOf(section.getString("material").toUpperCase());
		ItemStack result = new ItemStack(material);
		
		if(section.contains("nbt"))
			NBTUtilities.setNBTTagCompound(result, NBTUtilities.parse(section.getString("nbt")));
		
		ItemMeta meta = result.getItemMeta();
		if(section.contains("displayname"))
			meta.setDisplayName(section.getString("displayname"));
		if(section.contains("lore"))
			meta.setLore(section.getStringList("lore"));
		if(section.contains("unbreakable"))
			meta.setUnbreakable(section.getBoolean("unbreakable"));
		result.setItemMeta(meta);
		
		if(section.contains("amount"))
			result.setAmount(section.getInt("amount"));
		
		return result;
	}
}
