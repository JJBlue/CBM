package cbm.modules.midiplayer.commands;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import cbm.config.MainConfig;
import cbm.modules.midiplayer.BukkitMidiPlayerManager;

public class MidiPlayerCommands implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0].toLowerCase()) {
			case "play": {
				if(args.length < 2) break;
				
				File file = null;
				
				try {
					File folder = getFolder();
					file = new File(folder, args[1]);
					if(!file.getCanonicalPath().startsWith(folder.getCanonicalPath()))
						return true;
					if(!file.exists()) return true;
				} catch (IOException e) {}
				
				int ID = BukkitMidiPlayerManager.play(file);
				
				if(ID != -1)
					sender.sendMessage("Play File <" + args[1] + "> with ID <" + ID + ">"); // TODO language
				else
					sender.sendMessage("Can't play file"); // TODO language
				break;
			}
			case "stop": {
				if(args.length < 2) break;
				try {
					BukkitMidiPlayerManager.stop(Integer.parseInt(args[1]));
					sender.sendMessage("Stop playing");
				} catch (NumberFormatException e) {}
				break;
			}
			case "ids": {
				StringBuilder builder = new StringBuilder();
				builder.append("Midiplayer ID's: ");
				boolean first = true;
				
				int start = -1;
				int end = -1;
				
				for(int id : BukkitMidiPlayerManager.getIDs()) {
					if(start == -1) {
						start = id;
						end = id;
						continue;
					}
					
					if(id == end + 1) {
						end = id;
						continue;
					}
					
					builder.append((first ? "" : ", ") + start + (end != start ? " - " + end : ""));
					first = false;
					start = -1;
				}
				
				if(start != -1)
					builder.append((first ? "" : ", ") + start + (end != start ? " - " + end : ""));
				
				sender.sendMessage(builder.toString());
				
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("play");
			returnArguments.add("stop");
			returnArguments.add("ids");
		} else if(args.length == 2) {
			for(File file : getFolder().listFiles()) {
				if(!file.isFile()) continue;
				returnArguments.add(file.getName());
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

	protected File getFolder() {
		return new File(MainConfig.getDataFolder(), "midi");
	}
}
