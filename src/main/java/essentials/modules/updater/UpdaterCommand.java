package essentials.modules.updater;

import essentials.language.LanguageConfig;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.StringUtilities;
import essentials.utilities.permissions.PermissionHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class UpdaterCommand implements CommandExecutor, TabCompleter {

	public final static UpdaterCommand updaterCommands;
	
	static {
		updaterCommands = new UpdaterCommand();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length < 1) return true;
		
		switch(args[0].toLowerCase()) {
			case "check":
				
				List<String> updates = UpdaterServerManager.checkForUpdate();
				if(updates == null || updates.isEmpty())
					LanguageConfig.sendMessage(sender, "updater.no-new-versions");
				else
					LanguageConfig.sendMessage(sender, "updater.new-versions", StringUtilities.listToListingString(updates));
				
				break;
		
			case "update":
				
				if(args.length == 1) {
					LanguageConfig.sendMessage(sender, "updater.install-need-confirm");
					return true;
				}
				
				BukkitUtilities.broadcast(LanguageConfig.getString("updater.install-confirm"), PermissionHelper.getPermission("updater.seeBroadcast"));
				UpdaterServerManager.updateInstall();
				
				break;
				
			case "download":
				
				BukkitUtilities.broadcast(LanguageConfig.getString("updater.download"), PermissionHelper.getPermission("updater.seeBroadcast"));
				UpdaterServerManager.update();
				
				break;
				
			case "install":
				
				if(args.length == 1) {
					LanguageConfig.sendMessage(sender, "updater.install-need-confirm");
					return true;
				}
				
				BukkitUtilities.broadcast(LanguageConfig.getString("updater.install-confirm"), PermissionHelper.getPermission("updater.seeBroadcast"));
				UpdaterServerManager.install();
				
				break;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("check");
			returnArguments.add("update");
			returnArguments.add("download");
			returnArguments.add("install");
			
		}
		
		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		
		returnArguments.sort(Comparator.naturalOrder());
		
		return returnArguments;
	}

}
