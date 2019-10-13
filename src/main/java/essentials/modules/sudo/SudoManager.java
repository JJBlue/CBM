package essentials.modules.sudo;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import essentials.modules.sudo.sudoplayer.SudoPlayerManager;

public class SudoManager {
	public static void executeInPlugin(CommandSender permissionsSender, Player executer, String plugin, String[] args) {
		PluginCommand pluginCommand = Bukkit.getServer().getPluginCommand(plugin);
		if (pluginCommand != null)
			pluginCommand.execute(SudoPlayerManager.getSudoPlayer(permissionsSender, executer), plugin, args);
	}
	
	public static void execute(CommandSender permissionsSender, Player executer, String command) {
		Bukkit.dispatchCommand(SudoPlayerManager.getSudoPlayer(permissionsSender, executer), command);
	}
}
