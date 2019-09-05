package essentials.commands.world;

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

import essentials.language.LanguageConfig;

public class weatherCommands implements CommandExecutor, TabCompleter {
	
	public final static weatherCommands weather;
	
	static {
		weather = new weatherCommands();
	}
	
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
			case "sun":
				
				world.setStorm(false);
				world.setThundering(false);
				LanguageConfig.sendMessage(sender, "weather.changeTo-sun", l.getWorld().getName());
				
				break;
				
			case "rain":
				
				world.setStorm(true);
	    		world.setThundering(false);
	    		LanguageConfig.sendMessage(sender, "weather.changeTo-rain", l.getWorld().getName());
				
				break;
				
			case "storm":
				
				world.setStorm(true);
				world.setThundering(true);
				LanguageConfig.sendMessage(sender, "weather.changeTo-thunder", l.getWorld().getName());
				
				break;
				
			case "time":
		}
    	
	    return true;
    }

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("sun");
			returnArguments.add("rain");
			returnArguments.add("thunder");
			
		} else {
			switch (args[0]) {
				default:
					for(World world : Bukkit.getWorlds())
						returnArguments.add(world.getName());
					
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
}
