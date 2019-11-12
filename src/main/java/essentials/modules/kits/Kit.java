package essentials.modules.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import components.json.JSONObject;
import essentials.utilities.commands.CommandAusfuehren;
import essentials.utilities.conditions.ConditionUtilities;
import essentials.utilities.permissions.PermissionHelper;

public class Kit {
	public final String ID;
	
	// Show in inventory
	String name;
	ItemStack showItemStack;
	
	boolean permission = false;
	boolean claimOneTime = false;
	int cooldown = -1;
	List<String> commandrun;
	List<ItemStack> items;
	
	//Bedingungen
	String json_condition;
	JSONObject condition;
	String json_execute;
	JSONObject execute;
	
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
		
		if(!ConditionUtilities.checkCondition(player, condition)) {
			return false;
		}
		
		ConditionUtilities.execute(player, execute);
		
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
