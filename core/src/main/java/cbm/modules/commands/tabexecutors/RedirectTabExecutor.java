package cbm.modules.commands.tabexecutors;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

public class RedirectTabExecutor implements TabExecutor {

	CommandExecutor commandExecutor;
	TabCompleter tabCompleter;
	int removeArgs = 1;
	
	public RedirectTabExecutor(CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}
	
	public RedirectTabExecutor(TabExecutor tabExecutor) {
		this.commandExecutor = tabExecutor;
		this.tabCompleter = tabExecutor;
	}
	
	public RedirectTabExecutor(TabExecutor tabExecutor, int removeArgs) {
		this.removeArgs = removeArgs;
		this.commandExecutor = tabExecutor;
		this.tabCompleter = tabExecutor;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(commandExecutor != null)
			return commandExecutor.onCommand(sender, command, label, removeArgs != 0 ? Arrays.copyOfRange(args, removeArgs, args.length) : args);
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(tabCompleter != null)
			return tabCompleter.onTabComplete(sender, command, alias, removeArgs != 0 ? Arrays.copyOfRange(args, removeArgs, args.length) : args);
		return null;
	}
	
}
