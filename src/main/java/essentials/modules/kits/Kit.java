package essentials.modules.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.economy.EconomyManager;
import essentials.utilities.commands.CommandAusfuehren;
import essentials.utilities.permissions.PermissionHelper;

public class Kit {
	public final String ID;
	
	// Show in inventory
	String name;
	ItemStack showItemStack;
	
	boolean permission = false;
	boolean claimOneTime = false;
	int cooldown = -1;
	int money = -1;
	int exp = -1;
	List<String> commandrun;
	List<ItemStack> items;
	
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
		
		if(exp > 0 && player.getLevel() < exp) {
			return false;
		}
		
		player.setLevel(player.getLevel() - exp);
		
		if(money > 0 && !EconomyManager.removeMoney(player.getUniqueId(), money, true)) {
			player.setLevel(player.getLevel() + exp);
			return false;
		}
		
		for(ItemStack itemStack : items) {
			player.getInventory().addItem(itemStack); //TODO if full
		}
		
		//TODO claimed
		
		for(String command : commandrun) {
			CommandAusfuehren.Command(player, command);
		}
		
		return true;
	}
}
