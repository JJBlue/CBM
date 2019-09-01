package essentials.utilities;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import essentials.utilitiesvr.sign.SignUtilitiesReflections;

public class SignUtilities {
	public static void editSign(Player player, Sign sign) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException, InvocationTargetException {
		SignUtilitiesReflections.editSign(player, sign);
	}
	
	public static void openSign(Player player, Location location) {
		if(!location.getBlock().getType().name().toLowerCase().contains("sign")) return; //Ansonsten stuerzt Minecraft beim Spieler ab
		openSignWithoutCheck(player, location);
	}
	
	public static void openSignWithoutCheck(Player player, Location location) {
		SignUtilitiesReflections.openSignWithoutCheck(player, location);
	}
	
	public static void openFakeSign(Player player, Material material, Location location, String[] lines) {
		setFakeSign(player, material, location, lines);
	    openSignWithoutCheck(player, location);
	}
	
	public static void setFakeSign(Player player, Material material, Location location, String[] lines) {
		if(!material.name().toLowerCase().contains("sign")) return;
	    
	    player.sendBlockChange(location, material.createBlockData());
	    player.sendSignChange(location, lines);
	}
	
	public static void openSign(Player player) {
		SignUtilitiesReflections.openSign(player);
	}
	
	public static Sign getSign(Block block) {
		return (Sign) block.getState();
	}
}
