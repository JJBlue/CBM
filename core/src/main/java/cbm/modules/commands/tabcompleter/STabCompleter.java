package cbm.modules.commands.tabcompleter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;

import cbm.utilitiesvr.bukkit.BukkitUtilities;

public class STabCompleter {
	public static List<String> sortAndRemove(List<String> list, String arg){
		list.removeIf(s -> !s.toLowerCase().startsWith(arg.toLowerCase()));
		list.sort(Comparator.naturalOrder());
		return list;
	}
	
	public static TabCompleter getPlayers() {
		return (sender, cmd, alias, args) -> sortAndRemove(getPlayersList(), args[args.length - 1]);
	}
	
	public static List<String> getPlayersList() {
		return Bukkit.getOnlinePlayers().stream()
			.map(player -> player.getName())
			.collect(Collectors.toList());
	}
	
	public static TabCompleter getAvailableCommands() {
		return (sender, cmd, alias, args) -> {
			List<String> ra = BukkitUtilities.getAvailableCommands(sender);
			return sortAndRemove(ra, args[args.length - 1]);
		};
	}

	public static List<String> getWorlds() {
		return Bukkit.getWorlds().stream()
			.map(world -> world.getName())
			.collect(Collectors.toList());
	}
}
