package essentials.commands.commandspy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import essentials.permissions.PermissionHelper;
import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class CommandSpyListener implements Listener {
	@EventHandler
	public void spyCommands(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(onlinePlayer == player) continue;
			PlayerConfig config = PlayerManager.getPlayerConfig(onlinePlayer);
			int commandSpyValue = config.getInt("commandSpy");
			
			if(config.getBoolean("commandSpyOperator") || (commandSpyValue != -1 && !PermissionHelper.hasPermission(player, "commandspy." + commandSpyValue))) //TODO commandSpyValue value = 2 but permission has 9?
				onlinePlayer.sendMessage("§oCSpy: §6§o[" + player.getName() + "]: " + event.getMessage());
		}
	}
	
	@EventHandler
	public void spyCommandsServer(ServerCommandEvent event) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			PlayerConfig config = PlayerManager.getPlayerConfig(onlinePlayer);
			
			if(config.getBoolean("commandSpyOperator"))
				onlinePlayer.sendMessage("§oCSpy: §6§o[Server]: " + event.getCommand());
		}
	}
}
