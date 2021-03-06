package cbm.modules.troll;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import cbm.language.LanguageConfig;
import cbm.modules.troll.control.ControlManager;
import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;

public class TrollCommands implements TabExecutor {

	public final static TrollCommands trollCommands;
	
	static {
		trollCommands = new TrollCommands();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
				
				PlayerConfig config = PlayerManager.getConfig(p);
				try {
					config.setTmp("trollTrappedMaterial", Material.valueOf(args[1].toUpperCase()));
					LanguageConfig.sendMessage(sender, "text.commandExecuted");
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}
				break;

			case "control":
				if (p == null) break;
				if(args.length == 1) {
					ControlManager.remove(p);
					break;
				}
				
				if (args.length < 2) return true;

				Player toControl = Bukkit.getPlayer(args[1]);
				if (toControl == null) return true;
				if (p.equals(toControl)) return true;
				
				ControlManager.add(p, toControl);
				break;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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

}
