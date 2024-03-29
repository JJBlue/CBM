package cbm.modules.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

public class CommandManager {
	static Map<String, TabExecutor> commands;
	static Map<String, String> alias;
	
	public static void load() {
		commands = new ConcurrentHashMap<>();
		alias = new ConcurrentHashMap<>();
	}
	
	public static boolean register(String command, TabExecutor executor) {
		if(commands.containsKey(command))
			return false;
		commands.put(command, executor);
		return true;
	}
	
	public static TabExecutor unregister(String command) {
		if(commands.containsKey(command)) {
			alias.values().removeIf(s -> s.equalsIgnoreCase(command));
			return commands.remove(command);
		}
		return null;
	}
	
	public static boolean setAlias(String command, String alia) {
		if(commands.containsKey(command)) return false;
		alias.put(command, alia);
		return true;
	}
	
	public static String getCommand(String ac) {
		if(commands.containsKey(ac))
			return ac;
		if(alias.containsKey(ac))
			return alias.get(ac);
		return null;
	}
	
	public static TabExecutor getTabExecutor(CommandExecutor commandexecuter) {
		return getTabExecutor(commandexecuter, null);
	}
	
	public static TabExecutor getTabExecutor(final CommandExecutor commandexecuter, final TabCompleter tabcompleter) {
		return new TabExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if(commandexecuter != null)
					return commandexecuter.onCommand(sender, command, label, args);
				return false;
			}
			
			@Override
			public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
				if(commandexecuter != null)
					return tabcompleter.onTabComplete(sender, command, alias, args);
				return null;
			}
		};
	}
	
	public static boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		String command = getCommand(args[0]);
		if(command == null)
			return false;
		return commands.get(command).onCommand(sender, cmd, label, args);
	}

	public static List<String> tabcomplete(CommandSender sender, Command cmd, String alias, String[] args) {
		String command = getCommand(args[0]);
		if(command == null)
			return null;
		return commands.get(command).onTabComplete(sender, cmd, alias, args);
	}
}
