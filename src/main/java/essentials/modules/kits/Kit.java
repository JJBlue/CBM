package essentials.modules.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		
		//TODO cooldown
		
		if(!condition.checkAndExecute(player)) {
			return false;
		}
		
		for(ItemStack itemStack : items) {
			player.getInventory().addItem(itemStack); //TODO if full
		}
		
		//TODO claimed
		return true;
	}
}
