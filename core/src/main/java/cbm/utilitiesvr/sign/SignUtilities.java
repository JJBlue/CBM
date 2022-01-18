package cbm.utilitiesvr.sign;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;

public class SignUtilities {
	public final static VersionDependency<SignUtilities_Interface> version_dependency = new VersionDependency<>();
	
	public static void editSign(Player player, Sign sign) {
		SignUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			vd.editSign(player, sign);
		SignUtilitiesReflections.editSign(player, sign);
	}

	public static void openSignWithoutCheck(Player player, Location location) {
		SignUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			vd.openSign(player, location);
		SignUtilitiesReflections.openSign(player, location);
	}
	
	public static void openSign(Player player, Location location) {
		if (!location.getBlock().getType().name().toLowerCase().contains("sign"))
			return; //Ansonsten stuerzt Minecraft beim Spieler ab
		
		SignUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			vd.openSign(player, location);
		SignUtilitiesReflections.openSign(player, location);
	}

	public static void openFakeSign(Player player, Material material, Location location, String[] lines) {
		setFakeSign(player, material, location, lines);
		openSignWithoutCheck(player, location);
	}

	public static void setFakeSign(Player player, Material material, Location location, String[] lines) {
		if (!material.name().toLowerCase().contains("sign")) return;

		player.sendBlockChange(location, material.createBlockData());
		player.sendSignChange(location, lines);
	}

	public static Sign getSign(Block block) {
		return (Sign) block.getState();
	}
}
