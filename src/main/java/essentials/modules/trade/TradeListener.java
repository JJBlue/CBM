package essentials.modules.trade;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TradeListener implements Listener {
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		TradeManager.removePlayer(event.getPlayer());
	}

	@EventHandler
	public void interact(PlayerInteractAtEntityEvent event) {
		if (!(event.getRightClicked() instanceof Player)) return;

		Player player = event.getPlayer();
		Player clickedPlayer = (Player) event.getRightClicked();
		if (player.isSneaking() && player.getInventory().getItemInMainHand().getType().equals(Material.AIR))
			TradeManager.request(player, clickedPlayer);
	}
}
