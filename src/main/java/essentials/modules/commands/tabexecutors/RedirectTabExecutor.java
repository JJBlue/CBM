package essentials.modules.commands.tabexecutors;

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
	
	public RedirectTabExecutor(CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}
	
	public RedirectTabExecutor(TabExecutor tabExecutor) {
		this.commandExecutor = tabExecutor;
		this.tabCompleter = tabExecutor;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(commandExecutor != null)
			return commandExecutor.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(tabCompleter != null)
			return tabCompleter.onTabComplete(sender, command, alias, Arrays.copyOfRange(args, 1, args.length));
		return null;
	}
	
}
