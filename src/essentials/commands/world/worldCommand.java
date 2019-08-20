package essentials.commands.world;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class worldCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Location l = null;
		if(sender instanceof Player)
			l = ((Player) sender).getLocation();
		else if(sender instanceof BlockCommandSender)
			l = ((BlockCommandSender) sender).getBlock().getLocation();
		
		World world;
		
		if(args.length == 1) {
			if(l == null) return true;
			world = l.getWorld();
		} else {
			world = Bukkit.getWorld(args[1]);
			if(world == null) return true;
		}
		
		switch(args[0].toLowerCase()) {
			case "weather":
				
				weatherCommands.weather.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;
				
			case "time":
				
				timeCommands.timeCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;
		}
    	
	    return true;
    }

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("weather");
			returnArguments.add("time");
			
		} else {
			switch (args[0]) {
				case "weather":
					
					weatherCommands.weather.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					break;
					
				case "time":
					
					timeCommands.timeCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
}
