package essentials.commands.commands;

import essentials.utilities.SignUtilities;
import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.permissions.PermissionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SignCommands implements CommandExecutor, TabCompleter {

	public final static SignCommands signCommands;

	static {
		signCommands = new SignCommands();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, String[] args) {
		/*
		 * 	/cbm sign edit
		 * 	/cbm sign fake [~ ~ ~ | <name> ] [<Material>] ["<line1>"] ["<line2>"] ["<line3>"] ["<line4>"]
		 * 	/cbm sign show [<name>] ["<line1>"] ["<line2>"] ["<line3>"] ["<line4>"]
		 */

		if (args.length < 1) return true;

		Player p = null;
		if (sender instanceof Player)
			p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "edit":

				if (!PermissionHelper.hasCommandPermission(sender, "sign.edit")) return true;
				if (p == null) break;

				Block block = p.getTargetBlock(null, 50);
				try {
					SignUtilities.editSign(p, (Sign) block.getState());
				} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException | InvocationTargetException e1) {
					e1.printStackTrace();
				}

				break;

			case "fake": {
				if (!PermissionHelper.hasCommandPermission(sender, "sign.fake")) return true;
				if (args.length < 3) break;

				Player showPlayer = Bukkit.getPlayer(args[1]);
				if (showPlayer == null) break;

				Material material;
				try {
					material = Material.valueOf(args[2].toUpperCase());
				} catch (Exception e) {
					material = Material.OAK_SIGN;
				}

				StringBuilder text = new StringBuilder();
				for (int i = 3; i < args.length; i++) {
					if (i != 3) text.append(' ');
					text.append(args[i]);
				}

				String[] lines = parser(text.toString());
				SignUtilities.setFakeSign(showPlayer, material, showPlayer.getLocation(), lines);
			}

			break;

			case "show":

				if (!PermissionHelper.hasCommandPermission(sender, "sign.show")) return true;
				if (args.length < 3) break;

				Player showPlayer = Bukkit.getPlayer(args[1]);
				if (showPlayer == null) break;
				Location location = showPlayer.getLocation();
				location.setY(0);

				Material material;
				try {
					material = Material.valueOf(args[2].toUpperCase());
				} catch (Exception e) {
					material = Material.OAK_SIGN;
				}

				StringBuilder text = new StringBuilder();
				for (int i = 3; i < args.length; i++) {
					if (i != 3) text.append(' ');
					text.append(args[i]);
				}

				String[] lines = parser(text.toString());
				SignUtilities.openFakeSign(showPlayer, material, location, lines);

				break;
		}

		return true;
	}

	private String[] parser(String text) {
		String[] array = new String[4];
		int count = 0;

		StringBuilder builder = null;
		boolean anf = false;
		boolean back = false;

		for (char c : text.toCharArray()) {
			if (builder == null) builder = new StringBuilder();
			if (count == 4) break;

			switch (c) {
				case '\"':
					if (back) {
						back = false;
						builder.append(c);
					} else
						anf = !anf;

					if (!anf) {
						array[count++] = ChatUtilities.convertToColor(builder.toString());
						builder = null;
					}

					break;

				case '\\':
					if (back) {
						builder.append(c);
						back = false;
					} else
						back = true;

					break;

				default:
					builder.append(c);
			}
		}
		return array;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, String[] args) {
		if (args.length < 1) return null;

		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("edit");
			returnArguments.add("fake");
			returnArguments.add("show");

		} else {
			switch (args[0]) {
				case "fake":
				case "show":

					switch (args.length) {
						case 2:
							for (Player player : Bukkit.getOnlinePlayers())
								returnArguments.add(player.getName());

							break;
						case 3:
							for (Material material : Material.values())
								if (material.name().toLowerCase().contains("sign"))
									returnArguments.add(material.name());
							break;
						default:
							returnArguments.add("\"<Line 1>\" \"<Line 2>\" \"<Line 3>\" \"<Line 4>\"");
					}

					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

}
