package essentials.modules.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import essentials.utilities.permissions.PermissionHelper;

public class CommandManager {
	private static Map<String, TabExecutor> commands;
	private static Map<String, String> alias;
	
	public static boolean register(String command, TabExecutor executor) {
		if(commands.containsKey(command))
			return false;
		commands.put(command, executor);
		return true;
	}
	
	public static TabExecutor unregister(String command) {
		if(command.contains(command)) {
			alias.values().removeIf(s -> s.equalsIgnoreCase(command));
			return commands.remove(command);
		}
		return null;
	}
	
	public static boolean checkPermissions(CommandSender sender, String[] args) {
		return sender.hasPermission(PermissionHelper.getPermissionCommand(args[0].toLowerCase()));
	}
	
	public static boolean setAlias(String command, String alia) {
		if(!commands.containsKey(command) || alias.containsKey(alia)) return false;
		alias.put(alia, command);
		return true;
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
}
