package cbm.modules.sudo;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import cbm.modules.sudo.sudoplayer.SudoPlayerInterface;
import cbm.modules.sudo.sudoplayer.SudoPlayerManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SudoManager {
	
	protected static final List<Player> tmpOperators = Collections.synchronizedList(new LinkedList<>());
	
	public static void setTmpOperators() {
		synchronized (tmpOperators) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(!player.isOp()) {
					tmpOperators.add(player);
					player.setOp(true);
				}
			}
		}
	}
	
	public static void removeTmpOperators() {
		synchronized (tmpOperators) {
			for(Player player : tmpOperators) {
				if(player.isOp())
					player.setOp(false);
			}
			
			tmpOperators.clear();
		}
	}
	
	public static void removePlayer(Player player) {
		if(tmpOperators.isEmpty() || !tmpOperators.contains(player)) return;
		
		player.setOp(false);
		tmpOperators.remove(player);
	}
	
	public static void executeInPlugin(CommandSender permissionsSender, Player executer, String plugin, String[] args) {
		PluginCommand pluginCommand = Bukkit.getServer().getPluginCommand(plugin);
		if (pluginCommand != null) {
			Player player = SudoPlayerManager.getSudoPlayer(permissionsSender, executer);
			if (player != null)
				pluginCommand.execute(player, plugin, args);
		}
	}
	
	public static void execute(CommandSender permissionsSender, Player executer, String command) {
		Player player = SudoPlayerManager.getSudoPlayer(permissionsSender, executer);
		if (player != null)
			Bukkit.dispatchCommand(player, command);
	}
	
	public static void executeSilent(CommandSender sender, String command) {
		CommandSender commandSender = SudoPlayerManager.getSudoPlayer(sender);
		((SudoPlayerInterface) commandSender).setSilentOutputMessage(true);
		Bukkit.dispatchCommand(commandSender, command);
	}
}
