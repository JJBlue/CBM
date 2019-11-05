package essentials.modules.commands.tabcompleter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.utilities.BukkitUtilities;

public class STabCompleter {
	public static List<String> sortAndRemove(List<String> list, String arg){
		list.removeIf(s -> !s.toLowerCase().startsWith(arg.toLowerCase()));
		list.sort(Comparator.naturalOrder());
		return list;
	}
	
	public static TabCompleter getPlayers() {
		return (sender, cmd, alias, args) -> {
			return sortAndRemove(getPlayersList(), args[args.length - 1]);
		};
	}
	
	public static List<String> getPlayersList() {
		List<String> ra = new LinkedList<>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			ra.add(player.getName());
		}
		
		return ra;
	}
	
	public static TabCompleter getAvailableCommands() {
		return (sender, cmd, alias, args) -> {
			List<String> ra = BukkitUtilities.getAvailableCommands(sender);
			return sortAndRemove(ra, args[args.length - 1]);
		};
	}
}
