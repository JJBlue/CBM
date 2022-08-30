package cbm.modules.sudo.sudoplayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface SudoPlayer_IInterface {
	public CommandSender getSudoPlayer(Player player);

	public Player getSudoPlayer(CommandSender usedSudo, Player player);
}
