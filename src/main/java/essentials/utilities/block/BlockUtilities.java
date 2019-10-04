package essentials.utilities.block;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import essentials.utilities.player.PlayerUtilities;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.EnumHand;
import net.minecraft.server.v1_14_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_14_R1.PacketPlayOutHeldItemSlot;

public class BlockUtilities {
	//TODO test
	
	public static void blockBreak(Entity entity, int x, int y, int z) {
		blockBreak(entity, x, y, z, 0);
	}
	
	public static void blockBreak(Entity entity, int x, int y, int z, int damage) {
		if(entity == null || damage < 0 || damage > 9) return;
		
		//<> position damage(0 - 9)
		PacketPlayOutBlockBreakAnimation animation = new PacketPlayOutBlockBreakAnimation(entity.getEntityId(), new BlockPosition(x, y, z), damage);
		for(Entity nearEntity : entity.getNearbyEntities(100, 100, 100)) {
			if(!(nearEntity instanceof Player)) continue;
			
			PlayerUtilities.sendPacket((Player) nearEntity, animation);
		}
	}
	
	public static void armAnimation(Player player, EnumHand hand) {
		((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInArmAnimation(hand));
	}
	
	public static void helditemSlot(Player player, int number) {
		PacketPlayOutHeldItemSlot animation = new PacketPlayOutHeldItemSlot(number);
		PlayerUtilities.sendPacket(player, animation);
	}
	
	
//	public static void blockPlace(Entity entity, int x, int y, int z) {
//		//<> position damage(0 - 9)
//		PacketPlayOutBlockAction animation = new Packet
//		for(Entity nearEntity : entity.getNearbyEntities(100, 100, 100)) {
//			if(!(nearEntity instanceof Player)) continue;
//			
//			PlayerUtilities.sendPacket((Player) nearEntity, animation);	
//		}
//	}
}
