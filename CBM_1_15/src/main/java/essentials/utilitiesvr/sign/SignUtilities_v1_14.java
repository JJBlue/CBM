package essentials.utilitiesvr.sign;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutOpenSignEditor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SignUtilities_v1_14 {
	public static void openSignWithoutCheck(Player player, Location location) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static void openSign(Player player) {
		PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor();
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
