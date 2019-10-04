package essentials.modules.troll;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.modules.collision.CollisionManager;
import essentials.modules.visible.HideState;
import essentials.modules.visible.VisibleManager;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TrollCommands implements TabExecutor, Listener {

	public final static TrollCommands trollCommands;
	private final HashMap<Player, Player> control = new HashMap<>();
	
	static {
		trollCommands = new TrollCommands();
	}

	private TrollCommands() {
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if (args.length < 1) return true;
		
		Player p = null;

		if (sender instanceof Player)
			p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "help":

				sender.sendMessage("wall: only works in creative mode & permission 'troll.item'. Click with a glass_pane on a entity");
				sender.sendMessage("stick: same as wall but with a stick");
				sender.sendMessage("death & immortel: only permissions needed");
				sender.sendMessage("mydeath: nothing is needed");
				sender.sendMessage("§4Stick §4= Beaten opponents will be tp 10 meters up");
				sender.sendMessage("§4Diamand sword with display name: 'Death' §4= opponent's death");
				sender.sendMessage("§4Diamand sword with display name: 'MyDeath' §4= your death");
				sender.sendMessage("§4Diamand sword with display name:  'Immortel' §4= Immortel");
				
				break;

			case "op":

				if (args.length < 2) return true;

				Player p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) return true;
				p2.sendMessage("§7§o[Server]: You were opped by an operator");
				LanguageConfig.sendMessage(sender, "text.commandExecuted");

				break;

			case "deop":

				if (args.length < 2) return true;

				p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) return true;
				p2.sendMessage("§7§o[Server]: You were deopped by an operator");
				LanguageConfig.sendMessage(sender, "text.commandExecuted");

				break;

			case "servertext":

				if (args.length < 2) return true;

				StringBuilder text = new StringBuilder();
				for (int l = 2; l < args.length; l++)
					text.append(" ").append(args[l]);

				if (args[1].compareToIgnoreCase("@a") == 0) {
					for (Player players : Bukkit.getOnlinePlayers())
						players.sendMessage("§7§o[Server]:" + text);

				} else {
					p2 = Bukkit.getPlayer(args[1]);
					if (p2 == null) return true;

					p2.sendMessage("§7§o[Server]:" + text);
					LanguageConfig.sendMessage(sender, "text.commandExecuted");
				}

				break;

			case "wall":

				if(p == null) break;
				
				PlayerConfig config = PlayerManager.getPlayerConfig(p);
				try {
					config.setTmp("trollTrappedMaterial", Material.valueOf(args[1].toUpperCase()));
					LanguageConfig.sendMessage(sender, "text.commandExecuted");
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}
				break;

			case "control":
				if (p == null) break;
				if (control.containsKey(p)) {
					control.remove(p);
					VisibleManager.setVisible(p, HideState.VISIBLE);
					CollisionManager.setCollision(p, true);
					break;
				}
				if (args.length < 2) return true;

				Player toControl = Bukkit.getPlayer(args[1]);
				if (toControl == null) return true;
				if (p.equals(toControl)) return true;

				if (control.containsValue(toControl)) return true;

				control.put(p, toControl);
				p.teleport(toControl.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
				VisibleManager.setVisible(p, HideState.INVISIBLE);
				CollisionManager.setCollision(p, false);
				break;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("help");
			returnArguments.add("op");
			returnArguments.add("deop");
			returnArguments.add("servertext");
			returnArguments.add("wall");
			returnArguments.add("control");

		} else {
			switch (args[0]) {
				case "servertext":
					returnArguments.add("@a");

				default:
				case "op":
				case "deop":

				case "control":
					for (Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());

					break;

				case "wall":
					
					for(Material material : Material.values())
						if(material.isSolid() && material.isBlock())
							returnArguments.add(material.toString());
					
					break;
			}
		}

		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent e) {
		if (control.containsValue(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (control.containsKey(e.getPlayer())) {
			Location to = e.getTo();
			if (to == null) return;
			control.get(e.getPlayer()).teleport(e.getTo());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSneak(PlayerToggleSneakEvent e) {
		if (control.containsValue(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (control.containsKey(e.getPlayer())) {
			control.get(e.getPlayer()).setSneaking(e.getPlayer().isSneaking());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onFly(PlayerToggleFlightEvent e) {
		if (control.containsValue(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}

		if (control.containsKey(e.getPlayer())) {
			control.get(e.getPlayer()).setFlying(e.getPlayer().isFlying());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSwim(EntityToggleSwimEvent e) {
		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();
		if (control.containsValue(p)) {
			e.setCancelled(true);
			return;
		}

		if (control.containsKey(p)) {
			control.get(p).setSwimming(p.isSwimming());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onGlide(EntityToggleGlideEvent e) {
		if (!(e.getEntity() instanceof Player)) return;

		Player p = (Player) e.getEntity();
		if (control.containsValue(p)) {
			e.setCancelled(true);
			return;
		}

		if (control.containsKey(p)) {
			control.get(p).setGliding(p.isGliding());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		control.keySet().removeIf(k -> k.equals(player) || control.get(k).equals(player));
	}

}
