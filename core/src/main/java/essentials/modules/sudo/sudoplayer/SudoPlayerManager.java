package essentials.modules.sudo.sudoplayer;

import essentials.modules.sudo.sudoplayer.versions.SudoPlayer_v1_14;
import essentials.modules.sudo.sudoplayer.versions.SudoPlayer_v1_15;
import essentials.utilities.minecraft.ReflectionsUtilities;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoPlayerManager {
	public static CommandSender getSudoPlayer(CommandSender commandSender) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return SudoPlayer_v1_14.createSudoPlayer(commandSender);
			case "v1_15_R1":
				return SudoPlayer_v1_15.createSudoPlayer(commandSender);
		}
		
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				
			case "v1_15_R1":
				
		}
		
		return SudoPlayerProxy.create(commandSender);
	}

	public static Player getSudoPlayer(CommandSender usedSudo, Player player) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return SudoPlayer_v1_14.createSudoPlayer(usedSudo, player);
			case "v1_15_R1":
				return SudoPlayer_v1_15.createSudoPlayer(usedSudo, player);
		}
		
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
