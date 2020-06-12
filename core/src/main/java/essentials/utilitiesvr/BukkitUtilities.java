package essentials.utilitiesvr;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import essentials.utilities.minecraft.MinecraftVersions;
import essentials.utilitiesvr.bukkit.BukkitUtilitiesReflection;
import essentials.utilitiesvr.bukkit.BukkitUtilities_v1_14;
import essentials.utilitiesvr.bukkit.BukkitUtilities_v1_15;

public class BukkitUtilities {
	public static List<String> getAvailableCommands(CommandSender sender) {
		List<String> commands = new LinkedList<>();

		for (HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
			if (sender != null && !cmdLabel.canSee(sender)) continue;
			String name = cmdLabel.getName();
			if (name.startsWith("/"))
				name = name.substring(1);
			commands.add(name);
		}

		return commands;
	}

	public static List<String> getAvailableCommands(Plugin plugin, CommandSender sender) {
		List<String> commands = new LinkedList<>();

		for (HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
			if (sender != null && !cmdLabel.canSee(sender)) continue;
			String name = cmdLabel.getName();
			if (name.startsWith("/"))
				name = name.substring(1);
			commands.add(name);
		}

		return commands;
	}

	public static boolean registerCommand(String fallbackPrefix, Command command) {
		return getSimpleCommandMap().register(fallbackPrefix, command);
	}

	public static boolean registerCommand(String label, String fallbackPrefix, Command command) {
		return getSimpleCommandMap().register(label, fallbackPrefix, command);
	}

	public static void registerCommands(String fallbackPrefix, List<Command> commands) {
		getSimpleCommandMap().registerAll(fallbackPrefix, commands);
	}

	public static SimpleCommandMap getSimpleCommandMap() {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return BukkitUtilities_v1_14.getSimpleCommandMap();
			case v1_15_R1:
				return BukkitUtilities_v1_15.getSimpleCommandMap();
		}
		
		return BukkitUtilitiesReflection.getSimpleCommandMap();
	}

	public static void broadcast(String message, String permission) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.isOp() || player.hasPermission(permission))
				player.sendMessage(message);
		}
		Bukkit.getConsoleSender().sendMessage(message);
	}
}
