package essentials.utilitiesvr.player;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PlayerConnection;

public class PlayerUtilities_v1_14 {
	public static void sendPacket(Player player, Packet<?> packet) {
		getPlayerConnection(player).sendPacket(packet);
	}
	
	public static EntityPlayer getEntityPlayerFromPlayerConnection(Player player) {
		return getPlayerConnection(player).player;
	}
	
	public static PlayerConnection getPlayerConnection(Player player) {
		return getEntityPlayer(player).playerConnection;
	}
	
	public static EntityPlayer getEntityPlayer(Player player) {
		return ((CraftPlayer) player).getHandle();
	}
}
