package essentials.commands.trolling;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TrolCommands implements Listener, CommandExecutor, TabCompleter {

	public static final TrolCommands commands;

	static {
		commands = new TrolCommands();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("trol")) return false;
		if (!sender.hasPermission("trol.all")) return true;

		Player p = null;

		if (sender instanceof Player)
			p = (Player) sender;

		if (args.length < 1) {
			help(sender);
			return true;
		}

		switch (args[0].toLowerCase()) {
			case "click":

				sender.sendMessage("§4Stick §4= Geschlagen Gegner 10 Meter nach open tp");
				sender.sendMessage("§4Diamand Schwert mit DisplayName(Tod) §4= Gegner tot");
				sender.sendMessage("§4Diamand Schwert mit DisplayName(MyTod) §4= Selbst tot");
				sender.sendMessage("§4Diamand Schwert mit DisplayName(Heal) §4= Unsterblich");

				break;

			case "op":

				if (args.length < 2) return true;

				Player p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) return true;
				p2.sendMessage("§7§o[Server]: You were opped by an operator");
				sender.sendMessage("§4 Trollen war erfolgreich");

				break;

			case "deop":

				if (args.length < 2) return true;

				p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) return true;
				p2.sendMessage("§7§o[Server]: You were deopped by an operator");
				sender.sendMessage("§4 Trollen war erfolgreich");

				break;

			case "servertext":

				if (args.length < 2) return true;

				StringBuilder text = new StringBuilder();
				for (int l = 2; l < args.length; l++)
					text.append(" ").append(args[l]);

				if (args[1].compareToIgnoreCase("all") == 0) {
					for (Player players : Bukkit.getOnlinePlayers())
						players.sendMessage("§7§o[Server]:" + text);

				} else {
					p2 = Bukkit.getPlayer(args[1]);
					if (p2 == null) return true;

					p2.sendMessage("§7§o[Server]:" + text);
					sender.sendMessage("§4Trollen war erfolgreich");
				}

				break;

			case "mauer":

				BlockClick.m = p.getInventory().getItemInMainHand().getType();
				sender.sendMessage("Das Item: " + BlockClick.m + " wurde ausgewaehlt");
		}

		return true;
	}

	private static void help(CommandSender sender) {
		sender.sendMessage("-----------------Trol---------------");
		sender.sendMessage("/trol click help");
		sender.sendMessage("/trol op [name]");
		sender.sendMessage("/trol deop [name]");
		sender.sendMessage("/trol servertext <all/name> <text>");
		sender.sendMessage("/trol mauer 'Item in der Hand'");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("click");
			returnArguments.add("op");
			returnArguments.add("deop");
			returnArguments.add("servertext");
			returnArguments.add("mauer");

		} else {
			switch (args[0]) {
				default:
				case "op":
				case "deop":
					for (Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());

					break;
				case "servertext":

					for (Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());
					returnArguments.add("all");

					break;
			}
		}

		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
