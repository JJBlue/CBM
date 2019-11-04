package essentials.modules.teleport;

import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class teleportCommand implements TabExecutor {

	public ArrayList<String> tpa = new ArrayList<>(); //TODO better

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 0) return false;

		Location l = null;
		Player p = null;

		if (sender instanceof Player) {
			p = (Player) sender;
			l = p.getLocation();
		} else if (sender instanceof BlockCommandSender)
			l = ((BlockCommandSender) sender).getBlock().getLocation();

		switch (args[0].toLowerCase()) {
			case "tptoggle":

				if (p == null) break;

				PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);

				if (playerConfig.getBoolean(PlayerConfigKey.tTp)) {
					playerConfig.set(PlayerConfigKey.tTp, false);
					sender.sendMessage("Tptoggle wurde ausgeschaltet!");
				} else {
					playerConfig.set(PlayerConfigKey.tTp, true);
					sender.sendMessage("Tptoggle wurde angeschaltet!");
				}

				break;

			case "tpall":

				for (Player p1 : Bukkit.getOnlinePlayers()) {
					playerConfig = PlayerManager.getPlayerConfig(p1);

					if (playerConfig.getBoolean(PlayerConfigKey.tTp))
						p1.teleport(l);
				}

				sender.sendMessage("Alle Spieler wurden zu ihnen teleportiert");

				break;

			case "tphere":

				Player p1 = Bukkit.getPlayer(args[0]);
				if (p1 == null) break;
				playerConfig = PlayerManager.getPlayerConfig(p1);

				if (playerConfig.getBoolean(PlayerConfigKey.tTp))
					sender.sendMessage("Er hat Tptoggle aktiv");
				else {
					p1.teleport(l);
					sender.sendMessage("Er wurde zu ihnen teleportiert");
				}

				break;

			case "tpa": //TODO

				p1 = Bukkit.getPlayer(args[0]);

				if (tpa.contains(p1.getUniqueId().toString() + "," + p.getUniqueId().toString())) {
					sender.sendMessage("Du hast schon eine Anfrage geschickt");
				} else {
					tpa.add(p1.getUniqueId().toString() + "," + p.getUniqueId().toString());
					sender.sendMessage("Sie haben eine Teleportsanfrage an " + p1.getName() + " gesendent");
					p1.sendMessage("Sie haben eine Teleportsanfrage von " + p.getName() + " erhalten");
				}

				break;

			case "tpaccept": //TODO

				if (args.length == 0) {
					p1 = null;

					for (String pl : tpa) {
						String[] s = pl.split(",");
						if (s[0].equalsIgnoreCase(p.getUniqueId().toString())) {
							p1 = (Player) Bukkit.getPlayer(UUID.fromString(s[1]));
							break;
						}
					}

					if (p1 != null) {
						if (p1.isOnline()) {
							p1.teleport(p);
							p1.sendMessage(p.getName() + " hat die Anfrage angenommen");
							sender.sendMessage("Sie haben die Anfrage angenommen");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}

						tpa.remove(p.getUniqueId().toString() + "," + p1.getUniqueId().toString());
					} else
						sender.sendMessage("Sie haben keine Anfragen");
				} else if (args.length == 1) {
					p1 = Bukkit.getPlayer(args[0]);
					if (p1 == null) break;

					if (tpa.contains(p.getUniqueId().toString() + "," + p1.getUniqueId().toString())) {
						if (p1.isOnline()) {
							p1.teleport(p);
							p1.sendMessage(p.getName() + " hat die Anfrage angenommen");
							sender.sendMessage("Sie haben die Anfrage angenommen");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}

						tpa.remove(p.getUniqueId().toString() + "," + p1.getUniqueId().toString());
					} else
						sender.sendMessage("Sie haben keine Anfrage von diesem Spieler");
				}

				break;

			case "tpdeny": //TODO

				if (args.length == 0) {
					p1 = null;

					for (String pl : tpa) {
						String[] s = pl.split(",");
						if (s[0].equalsIgnoreCase(p.getUniqueId().toString())) {
							p1 = (Player) Bukkit.getPlayer(UUID.fromString(s[1]));
							break;
						}
					}

					if (p1 != null) {
						if (p1.isOnline()) {
							p1.sendMessage(p.getName() + " hat die Anfrage abgelehnt");
							sender.sendMessage("Sie haben die Anfrage abgelehnt");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}

						tpa.remove(p.getUniqueId().toString() + "," + p1.getUniqueId().toString());
					} else {
						sender.sendMessage("Sie haben keine Anfragen");
					}
				} else if (args.length == 1) {
					p1 = Bukkit.getPlayer(args[0]);

					if (tpa.contains(p.getUniqueId().toString() + "," + p1.getUniqueId().toString())) {
						if (p1.isOnline()) {
							p1.sendMessage(p.getName() + " hat die Anfrage abgelehnt");
							sender.sendMessage("Sie haben die Anfrage abgelehnt");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}

						tpa.remove(p.getUniqueId().toString() + "," + p1.getUniqueId().toString());
					} else {
						sender.sendMessage("Sie haben keine Anfrage von diesem Spieler");
					}
				}

				break;

			case "tpaall":

				for (Player p2 : Bukkit.getOnlinePlayers()) {
					if (!tpa.contains(p2.getUniqueId().toString() + "," + p.getUniqueId().toString())) {
						tpa.add(p2.getUniqueId().toString() + "," + p.getUniqueId().toString());
						sender.sendMessage("Sie haben eine Teleportsanfrage an " + p2.getName() + " gesendent");
						p2.sendMessage("Sie haben eine Teleportsanfrage von " + p.getName() + " erhalten");
					}
				}

				break;

			case "back":

				if (p == null) break;
				playerConfig = PlayerManager.getPlayerConfig(p);
				Location location = playerConfig.getLocation(PlayerConfigKey.tpLocation);
				if (location != null)
					p.teleport(location);

				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("back");
			returnArguments.add("tptoggle");
			returnArguments.add("tpall");
			returnArguments.add("tphere");

		} else {
			switch (args[0].toLowerCase()) {
				case "tphere":

					for (Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());

					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
