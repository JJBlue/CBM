package essentials.server;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyManager {

	public static void addMoney(Player player, long money) {

	}

	public static void removeMoney(Player player, long money) {

	}

	public static void setMoney(Player player, long money) {

	}

	public static int getMoney(Player player) {
		return 0;
	}

	public static void hasMoney(Player player, long money) {

	}

	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

		switch (args[0].toLowerCase()) {
			case "help":
				sender.sendMessage("/money [add/remove/set] <Player> <Value>");
				sender.sendMessage("/money get <Player>");
				break;

			case "add":

			case "transfer":

			case "set":

			case "remove":

				break;
		}

		return true;
	}
}
