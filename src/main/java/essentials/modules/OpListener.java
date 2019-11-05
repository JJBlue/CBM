package essentials.modules;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import essentials.config.MainConfig;
import essentials.config.MainConfigEnum;
import essentials.utilities.player.PlayerUtilities;

public class OpListener implements Listener, TabExecutor {
	@EventHandler
	public void command(PlayerCommandPreprocessEvent e) {
		if(!MainConfig.getConfiguration().getBoolean(MainConfigEnum.enableOperators.value)) return;
		
		Player player = e.getPlayer();
		
		if (player.isOp() && !MainConfig.getOperators().contains(player.getUniqueId().toString()))
			player.setOp(false);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> list = MainConfig.getOperators();
		StringBuilder builder = new StringBuilder();
		builder.append("###### Operators ######\n");
		boolean start = true;

		for (String uuid : list) {
			if (!start)
				builder.append(", ");
			else
				start = false;

			OfflinePlayer player = PlayerUtilities.getOfflinePlayerFromUUID(uuid);
			if (player != null)
				builder.append(player.getName());
			else
				builder.append("§4").append(uuid);
		}

		sender.sendMessage(builder.toString());

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}
}
