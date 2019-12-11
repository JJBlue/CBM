package essentials.modules.ban;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.modules.commands.tabcompleter.STabCompleter;
import essentials.utilities.StringUtilities;
import essentials.utilities.TimeUtilities;
import essentials.utilities.permissions.PermissionHelper;
import essentials.utilities.player.PlayerUtilities;

public class BanCommand implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "ban": { // tempban <Player> <Reason>
				
				if(args.length < 2) break;
				
				String reason = args.length < 3 ? null : StringUtilities.arrayToStringRange(args, 2, args.length);
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), reason);
				
				
				LanguageConfig.sendMessageWithPermission(PermissionHelper.getPermission("ban.see"), "ban.ban", args[1]);
				if(reason != null)
					LanguageConfig.sendMessageWithPermission(PermissionHelper.getPermission("ban.see"), "ban.banReason", args[1], reason);
				
				break;
			}	
			case "tempban": { // tempban <Player> <Time> <Reason>
				
				if(args.length < 3) break;
				
				String reason = args.length < 3 ? null : args.length < 4 ? null : StringUtilities.arrayToStringRange(args, 3, args.length);
				LocalDateTime time = TimeUtilities.parseAddTime(args[2]);
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), reason, time);
				
				if(reason == null)
					LanguageConfig.sendMessageWithPermission(PermissionHelper.getPermission("ban.see"), "ban.tempban", args[1], TimeUtilities.timeToString(LocalDateTime.now(), time));
				else
					LanguageConfig.sendMessageWithPermission(PermissionHelper.getPermission("ban.see"), "ban.tempbanReason", args[1], TimeUtilities.timeToString(LocalDateTime.now(), time), reason);
				
				break;
			}
			case "unban": {
				
				if(args.length < 2) break;
				BanManager.unbanPlayer(PlayerUtilities.getUUID(args[1]));
				LanguageConfig.sendMessageWithPermission(PermissionHelper.getPermission("ban.see"), "ban.unban", args[1]);
				
				break;
			}
			case "checkban": {
				
				if(args.length < 2) break;
				
				Player player = Bukkit.getPlayer(args[1]);
				if(player == null) break;
				
				if(BanManager.isPlayerBanned(player.getUniqueId())) {
					LanguageConfig.sendMessage(sender, "ban.notbanned", player.getName());
				} else {
					LocalDateTime time = BanManager.getBanUntil(player.getUniqueId());
					LanguageConfig.sendMessage(sender, "ban.pisbanned", player.getName(), TimeUtilities.timeToString(time), TimeUtilities.timeToString(LocalDateTime.now(), time));
				}
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("unban");
			returnArguments.add("ban");
			returnArguments.add("tempban");
			returnArguments.add("checkban");

		} else {
			
			switch(args[0].toLowerCase()) {
				case "ban":
					
					if(args.length == 2) {
						for(Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else
						returnArguments.add("<Reason>");
					
					break;
					
				case "tempban":
					
					if(args.length == 2) {
						returnArguments.addAll(STabCompleter.getPlayersList());
					} else if(args.length == 3) {
						returnArguments.add("0y0h0d0w0h0m0s");
						returnArguments.add("1m");
					} else {
						returnArguments.add("<Reason>");
					}
					
					break;
					
				case "unban":
					returnArguments.add("<Player>");
					break;
				case "checkban":
					returnArguments.addAll(STabCompleter.getPlayersList());
					break;
			}
			
			for (Player player : Bukkit.getOnlinePlayers())
				returnArguments.add(player.getName());
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

}
