package essentials.modules.sudo.sudoplayer;

import essentials.modules.sudo.sudoplayer.versions.SudoPlayer_v1_14;
import essentials.utilitiesvr.ReflectionsUtilities;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoPlayerManager {
	public static CommandSender getSudoPlayer(CommandSender commandSender) {
		if (ReflectionsUtilities.getPackageVersionName().equalsIgnoreCase("v1_14_R1"))
			return SudoPlayer_v1_14.createSudoPlayer(commandSender);
		return SudoPlayerProxy.create(commandSender);
	}

	public static Player getSudoPlayer(CommandSender usedSudo, Player player) {
		if (ReflectionsUtilities.getPackageVersionName().equalsIgnoreCase("v1_14_R1"))
			return SudoPlayer_v1_14.createSudoPlayer(usedSudo, player);

		try {
			/*
			 * This is not a really Player it didn't work for cast to CraftPlayer, it only uses all the interfaces from CraftPlayer and Superclasses
			 */
			return SudoPlayerProxy.create(usedSudo, player);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
