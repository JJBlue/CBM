package essentials.modules.kits;

import java.time.LocalDateTime;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.modules.kits.player.KitPlayerConfig;
import essentials.modules.kits.player.KitPlayerManager;
import essentials.utilities.conditions.Condition;
import essentials.utilities.permissions.PermissionHelper;

public class Kit {
	public final String ID;
	
	// Show in inventory
	String name;
	ItemStack showItemStack;
	
	boolean permission = false;
	boolean claimOneTime = false;
	int cooldown = -1;
	List<ItemStack> items;
	
	//Bedingungen
	Condition condition;
	
	//TMP
	boolean saved = false;
	
	public Kit(String ID) {
		this.ID = ID;
	}
	
	public boolean giveKit(Player player) {
		if(permission && !player.hasPermission(PermissionHelper.getPermission("kit." + ID))) {
			return false;
		}
		
		KitPlayerConfig config = null;

		if(claimOneTime || cooldown > 0) {
			config = KitPlayerManager.getConfig(player);
			LocalDateTime claimTime = config.getLocalDateTime(ID); //Last time claimed
			
			if(claimTime != null) {
				if(claimOneTime)
					return false;
				
				
				claimTime = LocalDateTime.from(claimTime);
				claimTime.plusSeconds(cooldown);
				
				if(claimTime.isAfter(LocalDateTime.now()))
					return false;
			}
		}
		
		if(!condition.checkAndExecute(player)) {
			return false;
		}
		
		for(ItemStack itemStack : items) {
			player.getInventory().addItem(itemStack); //TODO if full
		}
		
		if(claimOneTime || cooldown > 0) {
			config.set(ID, LocalDateTime.now());
		}
		
		return true;
	}
}
