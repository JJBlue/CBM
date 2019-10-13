package essentials.modules.ban;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.language.LanguageConfig;
import essentials.utilities.StringUtilities;
import essentials.utilities.TimeUtilities;
import essentials.utilities.player.PlayerUtilities;

public class BanCommand implements CommandExecutor, TabCompleter {
	
	public final static BanCommand commands;
	
	static {
		commands = new BanCommand();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "ban": { // tempban <Player> <Reason>
				
				if(args.length < 2) break;
				
				String reason = args.length < 3 ? null : StringUtilities.arrayToStringRange(args, 2, args.length);
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), reason);
				
				
				LanguageConfig.sendMessage(sender, "ban.ban", args[1]);
				if(reason != null)
					LanguageConfig.sendMessage(sender, "ban.banReason", args[1], reason);
				
				break;
			}	
			case "tempban": { // tempban <Player> <Time> <Reason>
				
				if(args.length < 3) break;
				
				String reason = args.length < 3 ? null : args.length < 4 ? null : StringUtilities.arrayToStringRange(args, 3, args.length);
				LocalDateTime time = TimeUtilities.parseAddTime(args[2]);
				BanManager.banPlayer(PlayerUtilities.getUUID(args[1]), reason, time);
				
				if(reason == null)
					LanguageConfig.sendMessage(sender, "ban.tempban", args[1], TimeUtilities.timeToString(LocalDateTime.now(), time));
				else
					LanguageConfig.sendMessage(sender, "ban.tempbanReason", args[1], TimeUtilities.timeToString(LocalDateTime.now(), time), reason);
				
				break;
			}
			case "unban": {
				
				if(args.length < 2) break;
				BanManager.unbanPlayer(PlayerUtilities.getUUID(args[1]));
				LanguageConfig.sendMessage(sender, "ban.unban", args[1]);
				
				break;
			}
			case "checkban": {
				//TODO
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
						for(Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else if(args.length == 3) {
						returnArguments.add("0y0h0d0w0h0m0s");
						returnArguments.add("1m");
					} else
						returnArguments.add("<Reason>");
					
					break;
					
				case "unban":
					returnArguments.add("<Player>");
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
