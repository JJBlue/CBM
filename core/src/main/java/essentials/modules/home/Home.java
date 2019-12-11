package essentials.modules.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Home { //TODO
	private static void sethome(Player p, String name) {

	}

	private static void delhome(Player p, String name) {

	}

	private static void home(Player p, String name) {

	}

	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("home") && sender.hasPermission("home.use")) {
			if (cmdLabel.equalsIgnoreCase("sethome")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;

					if (args.length == 0)
						sethome(p, null);
					else if (args.length == 1)
						sethome(p, args[0]);
				}
			} else if (cmdLabel.equalsIgnoreCase("delhome")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;

					if (args.length == 0)
						delhome(p, null);
					else if (args.length == 1)
						delhome(p, args[0]);
				}
			} else if (cmdLabel.equalsIgnoreCase("home")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;

					if (args.length == 0)
						home(p, null);
					else if (args.length == 1)
						home(p, args[0]);
				}
			}
		}

		return true;
	}
}
