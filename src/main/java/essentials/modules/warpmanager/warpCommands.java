package essentials.modules.warpmanager;

import essentials.language.LanguageConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class warpCommands implements CommandExecutor, TabCompleter {
	public final static warpCommands commands;

	static {
		commands = new warpCommands();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = null;
		if (sender instanceof Player)
			p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "warp":

				if (args.length == 1)
					WarpManager.openInventory(p);
				else if (args.length == 2)
					WarpManager.teleport(p, args[1]);
				else if (args.length == 3)
					WarpManager.teleport(Bukkit.getPlayer(args[2]), args[1]);

				break;

			case "editwarp":

				if (args.length < 2) break;
				WarpEditor.openEditor(p, WarpManager.getWarp(args[1]));

				break;

			case "setwarp":

				// setwarp <Name> (<autoLore> <hasPermission> <showWithoutPermission> <pos>)
				if (args.length < 2 || p == null) break;

				Warp warp = new Warp(args[1]);
				warp.setLocation(p.getLocation());

				if (args.length >= 6) {
					try {
						warp.autoLore = Boolean.parseBoolean(args[2]);
						warp.hasPermission = Boolean.parseBoolean(args[3]);
						warp.showWithoutPermission = Boolean.parseBoolean(args[4]);
						warp.pos = Integer.parseInt(args[5]);
						LanguageConfig.sendMessage(sender, "warp.setWarp2", args[1], args[2], args[3], args[4], args[5]);
					} catch (NumberFormatException e) {
						LanguageConfig.sendMessage(sender, "error.NumberFormatException");
					}
				} else if (args.length == 2) {
					WarpManager.addWarp(warp);
					LanguageConfig.sendMessage(sender, "warp.setWarp", args[1]);
				}

				break;

			case "delwarp":

				if (args.length < 2 || p == null) break;
				WarpManager.deleteWarp(args[1]);
				LanguageConfig.sendMessage(sender, "warp.delWarp", args[1]);

				break;

			case "savewarp":
				WarpManager.save();
				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("warp");
			returnArguments.add("delwarp");
			returnArguments.add("setwarp");
			returnArguments.add("editwarp");
			returnArguments.add("savewarp");
		} else {
			switch (args[0]) {
				case "warp":
				case "delwarp":
					if (args.length == 2)
						returnArguments.add("<Name>");
					break;

				case "setwarp":
					
					switch (args.length) {
						case 2:
							returnArguments.add("<Name>");
							break;
						case 3:
						case 4:
						case 5:
							returnArguments.add("True");
							returnArguments.add("False");
							break;
						default:
							returnArguments.add(args[args.length - 1] + "0");
							returnArguments.add(args[args.length - 1] + "1");
							returnArguments.add(args[args.length - 1] + "2");
							returnArguments.add(args[args.length - 1] + "3");
							returnArguments.add(args[args.length - 1] + "4");
							returnArguments.add(args[args.length - 1] + "5");
							returnArguments.add(args[args.length - 1] + "6");
							returnArguments.add(args[args.length - 1] + "7");
							returnArguments.add(args[args.length - 1] + "8");
							returnArguments.add(args[args.length - 1] + "9");
					}
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
