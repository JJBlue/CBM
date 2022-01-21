package cbm.modules.teleport;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.DelayQueue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import cbm.player.PlayerConfig;
import cbm.player.PlayerConfigKey;
import cbm.player.PlayerManager;

public class teleportCommand implements TabExecutor {
	// <To, From>
	public static DelayQueue<TeleportRequest> tpa;
	
	static {
		tpa = new DelayQueue<>(); // Better DelayQueue with set
		
		new Thread(() -> {
			while(tpa != null) {
				try {
					var tr = tpa.take();
					
					OfflinePlayer p1 = Bukkit.getOfflinePlayer(tr.getObject1());
					OfflinePlayer p2 = Bukkit.getOfflinePlayer(tr.getObject2());
					
					if(p1.isOnline()) {
						p1.getPlayer().sendMessage("Teleportanfrage von " + p2.getName() + " ist abgelaufen");
					}
					
					if(p2.isOnline()) {
						p2.getPlayer().sendMessage("Teleportanfrage zu " + p1.getName() + " ist abgelaufen");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 0) return false;
		
		Location l = null;
		Player p = (sender instanceof Player ? (Player) sender : null);

		if (sender instanceof Player player) {
			l = player.getLocation();
		} else if (sender instanceof BlockCommandSender) {
			l = ((BlockCommandSender) sender).getBlock().getLocation();
		}
		
		switch (args[0].toLowerCase()) {
			case "tptoggle": {

				if (p == null) break;

				PlayerConfig playerConfig = PlayerManager.getConfig(p);

				if (playerConfig.getBoolean(PlayerConfigKey.tTp)) {
					playerConfig.set(PlayerConfigKey.tTp, false);
					sender.sendMessage("Tptoggle wurde ausgeschaltet!");
				} else {
					playerConfig.set(PlayerConfigKey.tTp, true);
					sender.sendMessage("Tptoggle wurde angeschaltet!");
				}

				break;
			}
			case "tpall": {

				for (Player p1 : Bukkit.getOnlinePlayers()) {
					PlayerConfig playerConfig = PlayerManager.getConfig(p1);

					if (playerConfig.getBoolean(PlayerConfigKey.tTp))
						p1.teleport(l);
				}

				sender.sendMessage("Alle Spieler wurden zu ihnen teleportiert");

				break;
			}
			case "tphere": {

				Player p1 = Bukkit.getPlayer(args[1]);
				if (p1 == null) break;
				PlayerConfig playerConfig = PlayerManager.getConfig(p1);

				if (playerConfig.getBoolean(PlayerConfigKey.tTp)) {
					sender.sendMessage("Er hat Tptoggle aktiv");
				} else {
					p1.teleport(l);
					sender.sendMessage("Er wurde zu ihnen teleportiert");
				}

				break;
			}
			case "tpa": {

				if(p == null) break;
				
				Player p1 = Bukkit.getPlayer(args[1]);
				var tuple = new TeleportRequest(p1.getUniqueId(), p.getUniqueId());
				
				if (tpa.contains(tuple)) {
					sender.sendMessage("Du hast schon eine Anfrage geschickt");
				} else {
					tpa.put(tuple);
					sender.sendMessage("Sie haben eine Teleportsanfrage an " + p1.getName() + " gesendent");
					p1.sendMessage("Sie haben eine Teleportsanfrage von " + p.getName() + " erhalten");
				}

				break;
			}
			case "tpaccept": {
				if(p == null) break;
				
				if (args.length == 1) {
					var tuple = tpa.parallelStream()
						.filter(t -> t.getObject1().equals(p.getUniqueId()))
						.findAny()
						.orElse(null);

					Player p1 = Bukkit.getPlayer(tuple.getObject2());
					
					if (tpa.remove(tuple) && p1 != null) {
						if (p1.isOnline()) {
							p1.teleport(p);
							p1.sendMessage(p.getName() + " hat die Anfrage angenommen");
							sender.sendMessage("Sie haben die Anfrage angenommen");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}
					} else {
						sender.sendMessage("Sie haben keine Anfragen");
					}
				} else if (args.length == 1) {
					Player p1 = Bukkit.getPlayer(args[1]);
					var tuple = (p1 != null ? new TeleportRequest(p.getUniqueId(), p1.getUniqueId()) : null);
					
					if (p1 != null && tpa.remove(tuple)) {
						if (p1.isOnline()) {
							p1.teleport(p);
							p1.sendMessage(p.getName() + " hat die Anfrage angenommen");
							sender.sendMessage("Sie haben die Anfrage angenommen");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}
					} else {
						sender.sendMessage("Sie haben keine Anfrage von diesem Spieler");
					}
				}

				break;
			}
			case "tpdeny": {
				if(p == null) break;
				
				if (args.length == 1) {
					var tuple = tpa.parallelStream()
						.filter(t -> t.getObject1().equals(p.getUniqueId()))
						.findAny()
						.orElse(null);

					Player p1 = Bukkit.getPlayer(tuple.getObject2());
						
					if (tpa.remove(tuple) && p1 != null) {
						if (p1.isOnline()) {
							p1.sendMessage(p.getName() + " hat die Anfrage abgelehnt");
							sender.sendMessage("Sie haben die Anfrage abgelehnt");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}
					} else {
						sender.sendMessage("Sie haben keine Anfragen");
					}
				} else if (args.length == 1) {
					Player p1 = Bukkit.getPlayer(args[1]);
					var tuple = (p1 != null ? new TeleportRequest(p.getUniqueId(), p1.getUniqueId()) : null);
					
					if (p1 != null && tpa.remove(tuple)) {
						if (p1.isOnline()) {
							p1.sendMessage(p.getName() + " hat die Anfrage abgelehnt");
							sender.sendMessage("Sie haben die Anfrage abgelehnt");
						} else {
							sender.sendMessage("Der Spieler " + p1.getName() + " ist nicht mehr online");
						}
					} else {
						sender.sendMessage("Sie haben keine Anfrage von diesem Spieler");
					}
				}

				break;
			}
			case "tpaall": {
				if(p == null) break;
				
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					var tuple = new TeleportRequest(p2.getUniqueId(), p.getUniqueId());
					
					if (!tpa.contains(tuple)) {
						tpa.put(tuple);
						sender.sendMessage("Sie haben eine Teleportsanfrage an " + p2.getName() + " gesendent");
						p2.sendMessage("Sie haben eine Teleportsanfrage von " + p.getName() + " erhalten");
					}
				}

				break;
			}
			case "back": {
				if (p == null) break;
				
				PlayerConfig playerConfig = PlayerManager.getConfig(p);
				Location location = playerConfig.getLocation(PlayerConfigKey.tpLocation);
				if (location != null)
					p.teleport(location);

				break;
			}
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
			returnArguments.add("tpa");
			returnArguments.add("tpaccept");

		} else {
			switch (args[0].toLowerCase()) {
				case "tphere":
				case "tpa":
				case "tpaccept":
				case "tpdeny":

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
