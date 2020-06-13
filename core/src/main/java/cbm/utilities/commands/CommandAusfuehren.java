package cbm.utilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandAusfuehren {
	public static void Command(Entity p, String s) {
		String s3 = s;
		s3 = s3.replaceAll("@p", p.getName());
		s3 = s3.replaceAll("@w", p.getWorld().getName());

		if (s3.contains("@a")) {
			String oldstring = s3;

			for (Player players : Bukkit.getOnlinePlayers()) {
				s3 = oldstring;
				String s4 = s3.replace("@a", players.getName());
				commandstart(s4, p);
			}
		} else
			commandstart(s3, p);
	}

	public static void commandstart(String s, Entity p) {
		if (s.startsWith("@c ")) {
			s = s.split("@c ")[1];
			Bukkit.getConsoleSender().getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
		} else if(p instanceof Player) {
			((Player) p).performCommand(s);
		}
	}
}
