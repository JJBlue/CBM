package cbm.v1_14_R1.modules.sudo;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cbm.modules.sudo.sudoplayer.SudoPlayer_IInterface;

public class SudoPlayer_Impl implements SudoPlayer_IInterface {
	@Override
	public CommandSender getSudoPlayer(Player player) {
		return new SudoPlayer_v(player);
	}

	@Override
	public Player getSudoPlayer(CommandSender usedSudo, Player player) {
		return new SudoPlayer_v(usedSudo, player);
	}
}
