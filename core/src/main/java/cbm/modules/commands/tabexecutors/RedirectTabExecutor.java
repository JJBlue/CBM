package cbm.modules.commands.tabexecutors;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import cbm.utilities.permissions.PermissionHelper;

public class RedirectTabExecutor implements TabExecutor {

	CommandExecutor commandExecutor;
	TabCompleter tabCompleter;
	int removeArgs = 1;
	boolean usePermission = true;
	
	public RedirectTabExecutor(CommandExecutor commandExecutor, TabCompleter tabCompleter) {
		this.commandExecutor = commandExecutor;
		this.tabCompleter = tabCompleter;
	}
	
	public RedirectTabExecutor(TabExecutor tabExecutor) {
		this.commandExecutor = tabExecutor;
		this.tabCompleter = tabExecutor;
	}
	
	public RedirectTabExecutor(TabExecutor tabExecutor, int removeArgs, boolean usePermission) {
		this.removeArgs = removeArgs;
		this.commandExecutor = tabExecutor;
		this.tabCompleter = tabExecutor;
		this.usePermission = usePermission;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return onCommand(sender, command, label, 0, args.length, args);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, int from, int to, String[] args) {
		if(commandExecutor instanceof RedirectTabExecutor rte)
			return rte.onCommand(sender, command, label, from + removeArgs, to, args);
		
		if(commandExecutor != null) {
			if(usePermission && !PermissionHelper.hasCommandPermission(sender, 0, from + 1 + removeArgs, args))
				return false;
			
			return commandExecutor.onCommand(sender, command, label, removeArgs != 0 ? Arrays.copyOfRange(args, from + removeArgs, args.length) : args);
		}
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return onTabComplete(sender, command, alias, 0, args.length, args);
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, int from, int to, String[] args) {
		if(tabCompleter instanceof RedirectTabExecutor rte)
			return rte.onTabComplete(sender, command, alias, from + removeArgs, to, args);
		
		if(tabCompleter != null) {
			if(usePermission && to - from > 1 + removeArgs) {
				if(!PermissionHelper.hasCommandPermission(sender, 0, from + 1 + removeArgs, args))
					return null;
			}
			
			var list = tabCompleter.onTabComplete(sender, command, alias, removeArgs != 0 ? Arrays.copyOfRange(args, from + removeArgs, to) : args);
			if(list == null) return null;
			
			if(usePermission && to - from <= 1 + removeArgs)
				list.removeIf(s -> !sender.hasPermission(PermissionHelper.getPermissionCommand(0, from + removeArgs, args) + "." + s));
				
			return list;
		}
		
		return null;
	}
	
	public int getRemoveArgs() {
		return removeArgs;
	}
	
	public void setRemoveArgs(int removeArgs) {
		this.removeArgs = removeArgs;
	}
	
	public boolean isUsePermission() {
		return usePermission;
	}
	
	public void setUsePermission(boolean usePermission) {
		this.usePermission = usePermission;
	}
}
