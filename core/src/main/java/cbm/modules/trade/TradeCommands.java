package cbm.modules.trade;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class TradeCommands implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 1) return true;
		if (!(sender instanceof Player)) return true;

		Player player = Bukkit.getPlayer(args[0]);
		if (player == null) return true;
		TradeManager.request((Player) sender, player);

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> list = new LinkedList<>();
		for (Player player : Bukkit.getOnlinePlayers())
			list.add(player.getName());
		return list;
	}

}
