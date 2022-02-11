package cbm.utilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import cbm.modules.sudo.sudoplayer.SudoPlayerInterface;
import cbm.modules.sudo.sudoplayer.SudoPlayerManager;

public class CommandAusfuehren {
	public static void Command(Entity e, String s) {
		String s3 = s;
		s3 = s3.replaceAll("@p", e.getName());
		s3 = s3.replaceAll("@w", e.getWorld().getName());

		if (s3.contains("@a")) {
			String oldstring = s3;

			for (Player players : Bukkit.getOnlinePlayers()) {
				s3 = oldstring;
				String s4 = s3.replace("@a", players.getName());
				commandstart(s4, e);
			}
		} else {
			commandstart(s3, e);
		}
	}

	public static void commandstart(String s, Entity e) {
		if (s.startsWith("@cp ")) {
			s = s.substring("@cp ".length()).trim();
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
			
			Player sp = SudoPlayerManager.getSudoPlayer(e, (Player) e);
			if(sp == null) return;
			
			if(sp instanceof SudoPlayerInterface spi) {
				spi.setAllPermissions(true);
			}
			
			Bukkit.dispatchCommand(sp, s);
		} else if (s.startsWith("@c ")) {
			s = s.substring("@c ".length()).trim();
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
		} else if(e instanceof Player p) {
			p.performCommand(s);
		}
	}
}
