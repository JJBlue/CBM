package cbm.utilitiesvr.bukkit;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;

public class BukkitUtilities {
	public final static VersionDependency<BukkitUtilities_Interface> version_dependency = new VersionDependency<>();
	
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
		BukkitUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.getSimpleCommandMap();
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
