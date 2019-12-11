package essentials.modules.FlyThroughBlocks;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;

public class FTB implements Listener {
	public static boolean toogle(Player player) {
		PlayerConfig playerConfig = PlayerManager.getConfig(player);

		boolean newValue = !playerConfig.getBoolean(PlayerConfigKey.tWallGhost);
		playerConfig.set(PlayerConfigKey.tWallGhost, newValue);

		return newValue;
	}

	@EventHandler
	public void Move(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		//Check to use fly through block
		if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) return;
		if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ()) return;
		
		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.getBoolean(PlayerConfigKey.tWallGhost))
			return;
		
		double xRand = e.getTo().getX() - e.getTo().getBlockX();
		double yRand = e.getTo().getZ() - e.getTo().getBlockY();
		
		if ((xRand > 0.7 || xRand < 0.3 || (xRand < 0.6 && xRand > 0.4)) && (yRand > 0.7 || yRand < 0.3 || (yRand < 0.6 && yRand > 0.4)))
			return;
		
		String locPos = e.getTo().getBlockX() + "-" + e.getTo().getBlockY() + "-" + e.getTo().getBlockZ();
		if(config.containsLoadedKey("ftbLastPosition") && config.getString("ftbLastPosition").equals(locPos))
			return;
		config.setTmp("ftbLastPosition", locPos);

		Location location = e.getTo().clone();
		if(cantWalk(location) && cantWalk(location.add(0, 1, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		location.add(0, -1, 0);
		
		if(cantWalk(location.add(1, 0, 0)) && cantWalk(location.add(0, 1, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		location.add(-1, -1, 0);
		
		if(cantWalk(location.add(-1, 0, 0)) && cantWalk(location.add(0, 1, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		location.add(1, -1, 0);
		
		if(cantWalk(location.add(0, 0, 1)) && cantWalk(location.add(0, 1, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		location.add(0, -1, -1);
		
		if(cantWalk(location.add(0, 0, -1)) && cantWalk(location.add(0, 1, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		location.add(0, -1, 1);
		
		if(cantWalk(location.add(0, 2, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		location.add(0, -2, 0);
		
		if(player.isSneaking() && cantWalk(location.add(0, -1, 0))) {
			ftpHelper(player, true);
			return;
		}
		
		ftpHelper(player, false);
	}
	
	private void ftpHelper(Player player, boolean spectator) {
		if(spectator) {
			if (player.getGameMode().equals(GameMode.CREATIVE))
				player.setGameMode(GameMode.SPECTATOR);
		} else {
			if (player.getGameMode().equals(GameMode.SPECTATOR))
				player.setGameMode(GameMode.CREATIVE);
		}
	}

	private boolean cantWalk(Location l) {
		Block block = l.getBlock();

		return !(block.isLiquid() || block.isPassable() || block.isEmpty());
	}
}
