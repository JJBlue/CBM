package essentials.commands.commandonobject.newNotUsed;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import essentials.commands.commandonobject.CommandAusfuehren;

public class CommandOnBlock {
	private CommandOnBlock() {}
	
	public static Map<Location, CommandOnBlockInformation> buffer = Collections.synchronizedMap(new HashMap<>());
	
	public static void unload() {
		synchronized (buffer) {
			for(CommandOnBlockInformation commandOnBlockInformation : buffer.values())
				commandOnBlockInformation.save();
		}
	}
	
	public static void onDruckplatte(Player p, Block targetblock){	
		for(String s2 : getCommands(targetblock.getLocation()))
			CommandAusfuehren.Command(p, s2);
	}
	
	public static CommandOnBlockInformation getCommandOnBlock(Location location) {
		if(buffer.containsKey(location))
			return buffer.get(location);
		
		//TODO
		
		return null;
	}
	
	public static List<String> getCommands(Location location) {
		CommandOnBlockInformation commandOnBlock = getCommandOnBlock(location);
		return commandOnBlock.getCommands();
	}
	
	public static void clear(Location location) {
		//TODO
	}
	
	public static void addCommand(Location location, String command) {
		CommandOnBlockInformation commandOnBlock = getCommandOnBlock(location);
		commandOnBlock.addCommand(command);
	}
	
	public static void removeCommand(Location location, String command) {
		CommandOnBlockInformation commandOnBlock = getCommandOnBlock(location);
		commandOnBlock.removeCommand(command);
	}
}
