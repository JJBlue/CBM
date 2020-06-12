package essentials.modules.commands.commands;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import essentials.utilitiesvr.player.PlayerUtilities;

public class InventorySee implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (!(sender instanceof Player)) return true;
		if (args.length < 1) return true;

		Player p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "workbench":
				p.openWorkbench(p.getLocation(), true);
				break;

			case "enderchest":

				if (args.length <= 1)
					p.openInventory(p.getEnderChest());
				else {
					Player p1 = (Player) PlayerUtilities.getOfflinePlayer(args[1]);
					if (p1 == null) return true;
					p.openInventory(p1.getEnderChest());
				}

				break;

			case "entchanting":
				p.openEnchanting(p.getLocation(), true);
				break;

			case "inventory":

				if (args.length < 2) return true;

				Player p1 = (Player) PlayerUtilities.getOfflinePlayer(args[1]);
				if (p1 == null) return true;
				p.openInventory(p1.getInventory());

				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("workbench");
			returnArguments.add("enderchest");
			returnArguments.add("entchanting");
			returnArguments.add("inventory");

		} else {
			for (Player player : Bukkit.getOnlinePlayers())
				returnArguments.add(player.getName());
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
