package essentials.utilitiesvr.sign;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import components.reflections.SimpleReflection;
import essentials.utilities.SignUtilities;
import essentials.utilitiesvr.player.PlayerUtilitiesReflections;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutOpenSignEditor;

public class SignUtilitiesReflections {
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
		
		SignUtilities.openSign(player, sign.getLocation());
	}
	
	public static void openSignWithoutCheck(Player player, Location location) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())); //TODO
		try {
			PlayerUtilitiesReflections.sendPacket(player, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void openSign(Player player) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(); //TODO
		try {
			PlayerUtilitiesReflections.sendPacket(player, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
}
