package essentials.modules.FlyThrowBlocks;

import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FTB implements Listener {
	public static boolean toogle(Player player) {
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(player);

		boolean newValue = !playerConfig.getBoolean(PlayerConfigKey.tWallGhost);
		playerConfig.set(PlayerConfigKey.tWallGhost, newValue);

		return newValue;
	}

	@EventHandler
	public void Move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		//Check to use fly through block
		if (!p.getGameMode().equals(GameMode.CREATIVE) && !p.getGameMode().equals(GameMode.SPECTATOR)) return;
		if(e.getFrom().getX() == e.getTo().getX() && e.getFrom().getY() == e.getTo().getY() && e.getFrom().getZ() == e.getTo().getZ()) return;
		
		PlayerConfig config = PlayerManager.getPlayerConfig(p);
		if (!config.getBoolean(PlayerConfigKey.tWallGhost))
			return;
		
		double xRand = e.getTo().getX() - e.getTo().getBlockX();
		double yRand = e.getTo().getZ() - e.getTo().getBlockY();
		
		if ((xRand > 0.7 || xRand < 0.3 || (xRand < 0.6 && xRand > 0.4)) && (yRand > 0.7 || yRand < 0.3 || (yRand < 0.6 && yRand > 0.4)))
			return;

		int x = p.getLocation().getBlockX();
		int y = p.getLocation().getBlockY();
		int z = p.getLocation().getBlockZ();
		World w = p.getWorld();

		if (cantWalk(p.getLocation()) && cantWalk(new Location(w, x, y + 1, z)) ||
				cantWalk(new Location(w, x + 1, y, z)) && cantWalk(new Location(w, x + 1, y + 1, z)) ||
				cantWalk(new Location(w, x - 1, y, z)) && cantWalk(new Location(w, x - 1, y + 1, z)) ||
				cantWalk(new Location(w, x, y, z + 1)) && cantWalk(new Location(w, x, y + 1, z + 1)) ||
				cantWalk(new Location(w, x, y, z - 1)) && cantWalk(new Location(w, x, y + 1, z - 1)) ||
				cantWalk(new Location(w, x, y + 2, z)) ||
				cantWalk(new Location(w, x, y - 1, z)) && p.isSneaking()
		) {

			if (p.getGameMode().equals(GameMode.CREATIVE))
				p.setGameMode(GameMode.SPECTATOR);
		} else if (p.getGameMode().equals(GameMode.SPECTATOR))
			p.setGameMode(GameMode.CREATIVE);
	}

	private boolean cantWalk(Location l) {
		Block block = l.getBlock();

		return !(block.isLiquid() || block.isPassable() || block.isEmpty());
	}
}
