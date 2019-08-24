package essentials.utilities;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import components.reflections.SimpleReflection;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutOpenSignEditor;

public class SignUtilities {
	public static void editSign(Player player, Sign sign) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		if(sign == null || player == null) return;
		sign.setEditable(true);
		
		Object tileEntity = SimpleReflection.getObject("tileEntity", sign);
		if(tileEntity == null) {
			System.out.println("§4SignUtilities Field \"tileEntity\" no longer exist");
			return;
		}
		
		Field field = SimpleReflection.getField("isEditable", tileEntity);
		if(field == null) {
			System.out.println("§4SignUtilities Field \"isEditable\" no longer exist");
			return;
		}
		field.set(tileEntity, true);
		
		openSign(player, sign.getLocation());
	}
	
	public static void openSign(Player player, Location location) {
		if(!location.getBlock().getType().name().toLowerCase().contains("sign")) return; //Ansonsten stuerzt Minecraft beim Spieler ab
		openSignWithoutCheck(player, location);
	}
	
	public static void openSignWithoutCheck(Player player, Location location) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static void openFakeSign(Player player, Material material, Location location, String[] lines) {
		if(!material.name().toLowerCase().contains("sign")) return;
	    
	    player.sendBlockChange(location, material.createBlockData());
	    player.sendSignChange(location, lines);

	    openSignWithoutCheck(player, location);
	}
	
	public static void openSign(Player player) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor();
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public static Sign getSign(Block block) {
		return (Sign) block.getState();
	}
}
