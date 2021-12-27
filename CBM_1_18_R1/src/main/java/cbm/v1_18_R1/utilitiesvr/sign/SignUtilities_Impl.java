package cbm.v1_18_R1.utilitiesvr.sign;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import cbm.v1_18_R1.utilitiesvr.player.PlayerUtilities_Impl;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;

public class SignUtilities_Impl {
	public static void openSignWithoutCheck(Player player, Location location) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		PlayerUtilities_Impl.getPlayerConnection(player).a(packet);
	}
}
