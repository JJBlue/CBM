package cbm.utilitiesvr.sign;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import cbm.utilities.SignUtilities;
import cbm.utilitiesvr.player.PlayerUtilitiesReflections;
import cbm.versions.minecraft.ReflectionsUtilities;
import components.reflection.ConstructorReflection;
import components.reflection.MethodReflection;
import components.reflection.ObjectReflection;

public class SignUtilitiesReflections {
	public static void editSign(Player player, Sign sign) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException, InvocationTargetException {
		if (sign == null || player == null) return;
		sign.setEditable(true);

		Object tileEntity = ObjectReflection.getObject("tileEntity", sign);
		if (tileEntity == null)
			player.sendMessage("§4SignUtilities Field \"tileEntity\" no longer exist");
		else {
			Field field = ObjectReflection.getField("isEditable", tileEntity);
			if (field == null)
				player.sendMessage("§4SignUtilities Field \"isEditable\" no longer exist");
			else
				field.set(tileEntity, true);

			Field entityHuman = ObjectReflection.getField("j", tileEntity);
			if (entityHuman == null)
				player.sendMessage("§4SignUtilities Field \"j\" no longer exist");
			else
				MethodReflection.callMethod(tileEntity, "a", PlayerUtilitiesReflections.getEntityPlayer(player)); //Oder Field j
		}

		SignUtilities.openSign(player, sign.getLocation());
	}

	public static void openSignWithoutCheck(Player player, Location location) {
		try {
			Object BlockPosition = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("BlockPosition"), location.getBlockX(), location.getBlockY(), location.getBlockZ());
			Object PacketPlayOutOpenSignEditor = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutOpenSignEditor"), BlockPosition);
			PlayerUtilitiesReflections.sendPacket(player, PacketPlayOutOpenSignEditor);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void openSign(Player player) {
		try {
			Object PacketPlayOutOpenSignEditor = ConstructorReflection.createObject(ReflectionsUtilities.getMCClass("PacketPlayOutOpenSignEditor"));
			PlayerUtilitiesReflections.sendPacket(player, PacketPlayOutOpenSignEditor);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException | InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
