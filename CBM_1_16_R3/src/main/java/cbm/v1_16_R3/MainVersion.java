package cbm.v1_16_R3;

import cbm.modules.sudo.sudoplayer.SudoPlayerManager;
import cbm.utilitiesvr.bukkit.BukkitUtilities;
import cbm.utilitiesvr.chat.ChatUtilities;
import cbm.utilitiesvr.nbt.NBTUtilities;
import cbm.utilitiesvr.player.PlayerUtilities;
import cbm.v1_16_R3.modules.sudo.SudoPlayer_Impl;
import cbm.v1_16_R3.utilitiesvr.bukkit.BukkitUtilities_Impl;
import cbm.v1_16_R3.utilitiesvr.chat.ChatUtilities_Impl;
import cbm.v1_16_R3.utilitiesvr.nbt.NBTUtilities_Impl;
import cbm.v1_16_R3.utilitiesvr.player.PlayerUtilities_Impl;
import cbm.versions.minecraft.MinecraftVersions;
import cbm.versions.minecraft.PackageVersion;

public class MainVersion {
	public static void init() {
		PackageVersion version = MinecraftVersions.getMinecraftVersionExact();
		if(version != PackageVersion.v1_16_R3) return;
		
		NBTUtilities.version_dependency.add(version, new NBTUtilities_Impl());
		PlayerUtilities.version_dependency.add(version, new PlayerUtilities_Impl());
		BukkitUtilities.version_dependency.add(version, new BukkitUtilities_Impl());
		SudoPlayerManager.version_dependency.add(version, new SudoPlayer_Impl());
		ChatUtilities.version_dependency.add(version, new ChatUtilities_Impl());
	}
}
