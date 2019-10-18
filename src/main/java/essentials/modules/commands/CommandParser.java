package essentials.modules.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandParser {
	
	/*
	 * CommandSender = Entity, BlockCommandSender, ConsoleCommandSender
	 */
	public static boolean parse(CommandSender sender, Command command, String commandLabel, String[] args, boolean exitOnFailure) {
		boolean failure = false;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].startsWith("@p")) {
				if(sender instanceof Player) {
					args[i] = ((Player) sender).getName(); //TODO UUID ?
					continue;
				} else if(sender instanceof BlockCommandSender) {
					CommandBlock commandBlock = (CommandBlock) sender;
					
					Player nearest = null;
					double distance = -1;
					
					for(Player player : commandBlock.getWorld().getPlayers()) {
						double d = player.getLocation().distance(commandBlock.getLocation());
						
						if(distance < 0 || d < distance) {
							distance = d;
							nearest = player;
						}
					}
					
					if(nearest != null) {
						args[i] = nearest.getName(); //TODO UUID ?
						continue;
					}
				}
				
				failure = true;
			} else if(args[i].startsWith("@a")) {
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					args[i] = player.getName();
					command.execute(sender, commandLabel, Arrays.copyOf(args, args.length));
				}
				
			} else if(args[i].startsWith("@r")) {
				Collection<? extends Player> players = Bukkit.getOnlinePlayers();
				if(players.isEmpty()) continue;
				
				int random = new Random().nextInt(players.size());
				int count = 0;
				Player selectPlayer = null;
				
				for(Player player : players) {
					if(count == random) {
						selectPlayer = player;
						break;
					}
					
					count++;
				}
				
				if(selectPlayer != null) {
					args[i] = selectPlayer.getName(); //TODO UUID ?
					continue;
				}
				
				failure = true;
				
			} else if(args[i].startsWith("@rw")) { //random player in world
				World world = null;
				
				if(sender instanceof Entity)
					world = ((Entity) sender).getWorld();
				else if(sender instanceof BlockCommandSender)
					world = ((BlockCommandSender) sender).getBlock().getWorld();
				
				if(world != null) {
					List<Entity> entities = world.getEntities();
					if(entities.isEmpty()) continue;
					
					Entity entity = entities.get(new Random().nextInt(entities.size()));
					
					if(entity instanceof Player)
						args[i] = entity.getName(); //TODO UUID ?
					else
						args[i] = entity.getUniqueId().toString();
					
					continue;
				}
				
				failure = true;
			}
			
			if(exitOnFailure && failure)
				return !failure;
		}
		
		return !failure;
	}
}
