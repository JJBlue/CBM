package cbm.v1_16_R2.utilitiesvr.sign;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.PacketPlayOutOpenSignEditor;

public class SignUtilities_Impl {
	public static void openSignWithoutCheck(Player player, Location location) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static void openSign(Player player) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor();
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
