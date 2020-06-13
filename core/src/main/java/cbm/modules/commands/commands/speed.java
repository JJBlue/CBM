package cbm.modules.commands.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabcompleter.STabCompleter;

public class speed {
	public static void register() {
		CommandExecutor executor = (sender, cmd, cmdLabel, args) -> {
			if(args.length < 2) return true;
			
			Player player = null;
			
			if(args.length >= 4) {
				player = Bukkit.getPlayer(args[3]);
			} else if(sender instanceof Player) {
				player = (Player) sender;
			}
			
			if(player == null) return true;
			
			double speed = Double.parseDouble(args[1]);
			
			if(args.length >= 3) {
				switch(args[2].toLowerCase()) {
					case "walk":
						player.setWalkSpeed(flo(speed, sender));
						break;
					case "fly":
						player.setFlySpeed(flo(speed, sender));
						break;
				}
			} else {
				if (player.isFlying())
					player.setFlySpeed(flo(speed, sender));
				else
					player.setWalkSpeed(flo(speed, sender));
			}
			
			return true;
		};
		TabCompleter completer = (sender, cmd, alias, args) -> {
			List<String> list = new LinkedList<>();
			
			if(args.length == 2) {
				list.add("<Speed from 0-10; 2=normal>");
			} else if(args.length == 3) {
				list.add("walk");
				list.add("fly");
			} else {
				list = STabCompleter.getPlayersList();
			}
			return STabCompleter.sortAndRemove(list, args[args.length - 1]);
		};
		CommandManager.register("speed", CommandManager.getTabExecutor(executor, completer));
	}
	
	private static float flo(double i, CommandSender sender) {
		float f = (float) 0.1;

		if (i >= -10 && i <= 10)
			f = (float) (i / 10);
		else
			sender.sendMessage("Es darf nur eine Zahl zwischen -10 bist 10 sein"); //TODO: Change to Language

		return f;
	}
}
