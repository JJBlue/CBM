package cbm.v1_16_R2.utilitiesvr.sign;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import cbm.utilitiesvr.player.PlayerUtilitiesReflections;
import cbm.utilitiesvr.sign.SignUtilities_Interface;
import components.reflection.MethodReflection;
import components.reflection.ObjectReflection;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenSignEditor;

public class SignUtilities_Impl implements SignUtilities_Interface {
	@Override
	public void openSign(Player player, Location location) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	@Override
	public void openSign(Player player, Sign sign) {
		openSign(player, sign.getLocation());
	}
	
	@Override
	public void editSign(Player player, Sign sign) {
		if (sign == null || player == null) return;
		sign.setEditable(true);

		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		openSign(player, sign);
	}
}
