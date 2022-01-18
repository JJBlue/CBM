package cbm.v1_18_R1.utilitiesvr.sign;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftSign;
import org.bukkit.entity.Player;

import cbm.utilitiesvr.sign.SignUtilities_Interface;
import cbm.v1_18_R1.utilitiesvr.player.PlayerUtilities_Impl;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;

public class SignUtilities_Impl implements SignUtilities_Interface {
	@Override
	public void openSign(Player player, Location location) {
		if(location.getBlock().getState() instanceof Sign sign) {
			openSign(player, sign);
			return;
		}
		
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		PlayerUtilities_Impl.getPlayerConnection(player).a(packet);
	}
	
	@Override
	public void openSign(Player player, Sign sign) {
		CraftSign.openSign(sign, player);
	}

	@Override
	public void editSign(Player player, Sign sign) {
		if (sign == null || player == null) return;
		
		CraftSign.openSign(sign, player);
//		CraftSign craft_sign = (CraftSign) sign;
//		craft_sign.setEditable(true);
//		
//		Object tileEntity = ObjectReflection.getObject("tileEntity", sign);
//		if (tileEntity == null)
//			player.sendMessage("§4SignUtilities Field \"tileEntity\" no longer exist");
//		else {
//			Field field = ObjectReflection.getField("isEditable", tileEntity);
//			if (field == null)
//				player.sendMessage("§4SignUtilities Field \"isEditable\" no longer exist");
//			else
//				field.set(tileEntity, true);
//
//			Field entityHuman = ObjectReflection.getField("j", tileEntity);
//			if (entityHuman == null)
//				player.sendMessage("§4SignUtilities Field \"j\" no longer exist");
//			else
//				MethodReflection.callMethod(tileEntity, "a", PlayerUtilitiesReflections.getEntityPlayer(player)); //Oder Field j
//		}
//
//		SignUtilities.openSign(player, sign.getLocation());
	}
}
