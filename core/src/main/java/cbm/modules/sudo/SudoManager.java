package cbm.modules.sudo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import cbm.modules.sudo.sudoplayer.SudoPlayerInterface;
import cbm.modules.sudo.sudoplayer.SudoPlayerManager;

public class SudoManager {
	
	protected static final List<Player> tmpOperators = Collections.synchronizedList(new LinkedList<>());
	
	public static void setTmpOperators() {
		Bukkit.getOnlinePlayers().stream()
			.filter(player -> !player.isOp())
			.forEach(player -> {
				tmpOperators.add(player);
				player.setOp(true);
			});
	}
	
	public static void removeTmpOperators() {
		tmpOperators.removeIf(player -> {
			if(player.isOp())
				player.setOp(false);
			return true;
		});
	}
	
	public static void removePlayer(Player player) {
		if(tmpOperators.isEmpty() || !tmpOperators.contains(player)) return;
		
		player.setOp(false);
		tmpOperators.remove(player);
	}
	
	public static void executeInPlugin(CommandSender permissionsSender, Player executer, String plugin, String[] args) {
		PluginCommand pluginCommand = Bukkit.getServer().getPluginCommand(plugin);
		if (pluginCommand == null) return;
		
		Player player = SudoPlayerManager.getSudoPlayer(permissionsSender, executer);
		if (player == null) return;
		
		pluginCommand.execute(player, plugin, args);
	}
	
	public static void execute(CommandSender permissionsSender, Player executer, String command) {
		Player player = SudoPlayerManager.getSudoPlayer(permissionsSender, executer);
		if (player == null) return;
		
		Bukkit.dispatchCommand(player, command);
	}
	
	public static void executeSilent(Player player, String command) {
		CommandSender commandSender = SudoPlayerManager.getSudoPlayer(player);
		((SudoPlayerInterface) commandSender).setSilentOutputMessage(true);
		Bukkit.dispatchCommand(commandSender, command);
	}
}
