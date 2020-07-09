package cbm.modules.player.commands;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import cbm.modules.player.BukkitMidiPlayerManager;

public class MidiPlayerCommands implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0].toLowerCase()) {
			case "play": {
				if(args.length < 2) break;
				
				File file = new File(".", args[1]); // TODO warning path (for example: ./..)
				int ID = BukkitMidiPlayerManager.play(file);
				try {
					sender.sendMessage(new File(".").getCanonicalPath());
				} catch (IOException e) {}
				sender.sendMessage("Play File <" + args[1] + "> with ID <" + ID + ">");
				break;
			}
			case "stop": {
				if(args.length < 1) break;
				try {
					BukkitMidiPlayerManager.stop(Integer.parseInt(args[1]));
					sender.sendMessage("Stop playing");
				} catch (NumberFormatException e) {}
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
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

}
