package cbm.modules.sudo.sudoplayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;

public class SudoPlayerManager {
	public final static VersionDependency<SudoPlayer_IInterface> version_dependency = new VersionDependency<>();
	
	public static CommandSender getSudoPlayer(CommandSender commandSender) {
		SudoPlayer_IInterface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.getSudoPlayer(commandSender);
		return SudoPlayerProxy.create(commandSender);
	}

	public static Player getSudoPlayer(CommandSender usedSudo, Player player) {
		SudoPlayer_IInterface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.getSudoPlayer(usedSudo, player);
		return SudoPlayerProxy.create(usedSudo, player);
	}
}
