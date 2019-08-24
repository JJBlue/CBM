package essentials.commands.armorstand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class ArmorstandListener implements Listener {
	@EventHandler
	public void interactEntity(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		
		if(!config.getBoolean("armorstandEditorListener")) return;
		
		switch(player.getInventory().getHeldItemSlot()) {
			case 1:
				
				break;
			case 2:
				
				break;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		
		if(!config.getBoolean("armorstandEditorListener")) return;
		
		
		
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void exit(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if(!config.getBoolean("armorstandEditorListener")) return;
		
		config.removeBuffer("armorstandEditorListener");
		event.setCancelled(true);
	}
}
