package cbm.modules.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import cbm.modules.world.time.timeCommands;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class worldCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Location l = null;
		if (sender instanceof Player)
			l = ((Player) sender).getLocation();
		else if (sender instanceof BlockCommandSender)
			l = ((BlockCommandSender) sender).getBlock().getLocation();

		World world;

		if (args.length == 1) {
			if (l == null) return true;
			world = l.getWorld();
		} else {
			world = Bukkit.getWorld(args[1]);
			if (world == null) return true;
		}

		switch (args[0].toLowerCase()) {
			case "weather":

				weatherCommands.weather.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;

			case "time":

				timeCommands.timeCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("weather");
			returnArguments.add("time");

		} else {
			switch (args[0]) {
				case "weather":

					weatherCommands.weather.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					break;

				case "time":

					timeCommands.timeCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
