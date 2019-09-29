package essentials.modules.commandspy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.permissions.PermissionHelper;

public class CommandSpyListener implements Listener {
	@EventHandler
	public void spyCommands(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		int val = -1;
		boolean searched = false;

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer == player) continue;
			PlayerConfig config = PlayerManager.getPlayerConfig(onlinePlayer);

			if (config.getBoolean("commandSpyOperator")) {
				onlinePlayer.sendMessage("§oCSpy: §6§o[" + player.getName() + "]: " + event.getMessage());
				continue;
			}

			int commandSpyValue = config.getInt("commandSpy");
			if (commandSpyValue == -1)
				continue;

			if (!searched)
				val = getCommandSpyValue(player);

			if (commandSpyValue >= val && commandSpyValue != 0)
				onlinePlayer.sendMessage("§oCSpy: §6§o[" + player.getName() + "]: " + event.getMessage());
		}
	}

	public static int getCommandSpyValue(Player player) {
		int value = -1;
		String command = PermissionHelper.getPermission("commandspy");

		for (PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
			if (pai.getPermission().startsWith(command)) {
				String per = pai.getPermission();
				per = per.substring(command.length(), per.length());

				try {
					int tmp = Integer.parseInt(per);
					if (tmp > value)
						value = tmp;
				} catch (NumberFormatException e) {}
			}
		}

		return value;
	}

	@EventHandler
	public void spyCommandsServer(ServerCommandEvent event) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			PlayerConfig config = PlayerManager.getPlayerConfig(onlinePlayer);

			if (config.getBoolean("commandSpyOperator"))
				onlinePlayer.sendMessage("§oCSpy: §6§o[Server]: " + event.getCommand());
		}
	}
}
