package essentials.utilitiesvr.sign;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import components.reflections.SimpleReflection;
import essentials.utilities.SignUtilities;
import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;

public class SignUtilitiesReflections {
	public static void editSign(Player player, Sign sign) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		if(sign == null || player == null) return;
		sign.setEditable(true);
		
		Object tileEntity = SimpleReflection.getObject("tileEntity", sign);
		if(tileEntity == null) {
			player.sendMessage("§4SignUtilities Field \"tileEntity\" no longer exist");
			return;
		}
		
		Field field = SimpleReflection.getField("isEditable", tileEntity);
		if(field == null) {
			player.sendMessage("§4SignUtilities Field \"isEditable\" no longer exist");
			return;
		}
		field.set(tileEntity, true);
		
		SignUtilities.openSign(player, sign.getLocation());
	}
	
	public static void openSignWithoutCheck(Player player, Location location) {
		try {
			Object BlockPosition = SimpleReflection.createObject(ReflectionsUtilities.getMCClass("BlockPosition"), location.getBlockX(), location.getBlockY(), location.getBlockZ());
			
			Bukkit.broadcastMessage(net.minecraft.server.v1_14_R1.BlockPosition.class + "");
			Object PacketPlayOutOpenSignEditor = SimpleReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutOpenSignEditor"), BlockPosition);
			PlayerUtilitiesReflections.sendPacket(player, PacketPlayOutOpenSignEditor);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void openSign(Player player) {
		try {
			Object PacketPlayOutOpenSignEditor = SimpleReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutOpenSignEditor"));
			PlayerUtilitiesReflections.sendPacket(player, PacketPlayOutOpenSignEditor);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
