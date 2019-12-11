package essentials.utilities.block;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import essentials.utilitiesvr.player.PlayerUtilities_v1_14;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;

public class BlockUtilities_v1_14 {
	public static void blockBreak(Entity entity, int x, int y, int z) {
		blockBreak(entity, x, y, z, 0);
	}
	
	public static void blockBreak(Entity entity, int x, int y, int z, int damage) {
		if(entity == null || damage < 0 || damage > 9) return;
		
		//<> position damage(0 - 9)
		PacketPlayOutBlockBreakAnimation animation = new PacketPlayOutBlockBreakAnimation(entity.getEntityId(), new BlockPosition(x, y, z), damage);
		for(Entity nearEntity : entity.getNearbyEntities(100, 100, 100)) {
			if(!(nearEntity instanceof Player)) continue;
			
			PlayerUtilities_v1_14.sendPacket((Player) nearEntity, animation);
		}
	}
}
