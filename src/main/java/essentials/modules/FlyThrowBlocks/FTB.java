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
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		if (!playerConfig.getBoolean(PlayerConfigKey.tWallGhost)) return;
		
		double xRand = e.getTo().getX() - e.getTo().getBlockX();
		if(xRand > 0.8 && xRand < 0.2) return;
		
		double yRand = e.getTo().getZ() - e.getTo().getBlockY();
		if(yRand > 0.8 && yRand < 0.2) return;

		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		World w = p.getWorld();

		if (cantWalk(new Location(w, x + 0.8, y, z)) && cantWalk(new Location(w, x + 0.8, y + 1, z)) ||
				cantWalk(new Location(w, x - 0.8, y, z)) && cantWalk(new Location(w, x - 0.8, y + 1, z)) ||
				cantWalk(new Location(w, x, y, z + 0.8)) && cantWalk(new Location(w, x, y + 1, z + 0.8)) ||
				cantWalk(new Location(w, x, y, z - 0.8)) && cantWalk(new Location(w, x, y + 1, z - 0.8)) ||
				cantWalk(new Location(w, x, y + 1.9, z)) ||
				cantWalk(new Location(w, x, y - 0.8, z)) && p.isSneaking() ||
				cantWalk(p.getLocation()) && cantWalk(new Location(w, x, y + 1, z))) {

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
