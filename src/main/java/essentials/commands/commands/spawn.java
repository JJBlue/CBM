package essentials.commands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

//TODO old
public class spawn {
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 1) return false;

		switch (args[0].toLowerCase()) {

		}

		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (cmdLabel.equalsIgnoreCase("spawn") && sender.hasPermission("spawn.use")) {
				sender.sendMessage("/spawn");
				sender.sendMessage("/spawn <Player>");
			} else if (cmdLabel.equalsIgnoreCase("setspawn") && sender.hasPermission("setspawn.use")) {

			} else if (cmdLabel.equalsIgnoreCase("delspawn") && sender.hasPermission("delspawn.use")) {

			} else if (cmdLabel.equalsIgnoreCase("spawnc") && sender.hasPermission("spawnC.use")) {
				sender.sendMessage("/spawnC [default, first] <Name>");
			}
		}

		return true;
	}
}
