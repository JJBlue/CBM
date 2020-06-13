package cbm.modules.commandspy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;
import cbm.utilities.permissions.PermissionHelper;

public class CommandSpyListener implements Listener {
	@EventHandler
	public void spyCommands(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		int val;

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer == player) continue;
			PlayerConfig config = PlayerManager.getConfig(onlinePlayer);

			if (config.getBoolean("commandSpyOperator")) {
				onlinePlayer.sendMessage("§oCSpy: §6§o[" + player.getName() + "]: " + event.getMessage());
				continue;
			}

			int commandSpyValue = config.getInt("commandSpy");
			if (commandSpyValue <= 0)
				continue;

			val = getCommandSpyValue(player);

			if (commandSpyValue >= val)
				onlinePlayer.sendMessage("§oCSpy: §6§o[" + player.getName() + "]: " + event.getMessage());
		}
	}

	public static int getCommandSpyValue(Player player) {
		int value = -1;
		String command = PermissionHelper.getPermission("commandspy");

		for (PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
			if (pai.getPermission().startsWith(command)) {
				String per = pai.getPermission();
				per = per.substring(command.length());

				try {
					int tmp = Integer.parseInt(per);
					if (tmp > value)
						value = tmp;
				} catch (NumberFormatException ignored) {}
			}
		}

		return value;
	}

	@EventHandler
	public void spyCommandsServer(ServerCommandEvent event) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			PlayerConfig config = PlayerManager.getConfig(onlinePlayer);

			if (config.getBoolean("commandSpyOperator"))
				onlinePlayer.sendMessage("§oCSpy: §6§o[Server]: " + event.getCommand());
		}
	}
}
