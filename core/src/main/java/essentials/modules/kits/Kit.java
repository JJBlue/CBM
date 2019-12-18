package essentials.modules.kits;

import java.time.LocalDateTime;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.modules.kits.player.KitPlayerConfig;
import essentials.modules.kits.player.KitPlayerManager;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.PlayerUtilities;
import essentials.utilities.permissions.PermissionHelper;

public class Kit {
	public final String ID;
	
	// Show in inventory
	String name;
	ItemStack showItemStack;
	
	KitSettings settings = new KitSettings();
	
	List<ItemStack> items;
	
	//TMP
	boolean saved = false;
	
	public Kit(String ID) {
		this.ID = ID;
	}
	
	//Not thread safe
	public boolean giveKit(Player player) {
		if(settings.isPermission() && !player.hasPermission(PermissionHelper.getPermission("kit." + ID))) {
			return false;
		}
		
		KitPlayerConfig config = null;

		if(settings.isClaimOneTime() || settings.getCooldown() > 0 || settings.isClaimOneTimeAfterDieing()) {
			config = KitPlayerManager.getConfig(player, ID);
			LocalDateTime claimTime = config.getLocalDateTime("claim"); //Last time claimed
			
			if(claimTime != null) {
				if(settings.isClaimOneTime()) {
					return false;
				}
				
				if(settings.isClaimOneTimeAfterDieing() && claimTime.isAfter(getDeathTime(player))) {
					return false;
				}
				
				claimTime = LocalDateTime.from(claimTime);
				claimTime.plusSeconds(settings.getCooldown());
				
				if(claimTime.isAfter(LocalDateTime.now()))
					return false;
			}
		}
		
		if(settings.getCondition() != null && !settings.getCondition().checkAndExecute(player)) {
			return false;
		}
		
		PlayerUtilities.addItems(player, items);
		
		if(settings.isClaimOneTime() || settings.getCooldown() > 0) {
			if(config != null)
				config.set("claim", LocalDateTime.now());
		}
		
		return true;
	}
	
	protected LocalDateTime getDeathTime(Player player) {
		PlayerConfig config = PlayerManager.getConfig(player);
		return config.getLocalDateTime("deathTime");
	}
}
