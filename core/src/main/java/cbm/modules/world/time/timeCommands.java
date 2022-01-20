package cbm.modules.world.time;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class timeCommands implements CommandExecutor, TabCompleter {

	public final static timeCommands timeCommands;

	static {
		timeCommands = new timeCommands();
	}

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
			case "day":

				world.setTime(600);

				break;

			case "night":

				world.setTime(12400);

				break;

			case "add":

				if (args.length < 2) break;
				try {
					world.setTime(world.getTime() + Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {}

				break;

			case "remove":

				try {
					world.setTime(world.getTime() - Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {}

				break;

			case "set":

				try {
					world.setTime(Integer.parseInt(args[1]));
				} catch (NumberFormatException e) {}

				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("day");
			returnArguments.add("night");
			returnArguments.add("add");
			returnArguments.add("remove");
			returnArguments.add("set");

		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
