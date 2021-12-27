package cbm.v1_18_R1.utilitiesvr.block;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import cbm.v1_18_R1.utilitiesvr.player.PlayerUtilities_Impl;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;

public class BlockUtilities_Impl {
	public static void blockBreak(Entity entity, int x, int y, int z) {
		blockBreak(entity, x, y, z, 0);
	}
	
	public static void blockBreak(Entity entity, int x, int y, int z, int damage) {
		if(entity == null || damage < 0 || damage > 9) return;
		
		//<> position damage(0 - 9)
		PacketPlayOutBlockBreakAnimation animation = new PacketPlayOutBlockBreakAnimation(entity.getEntityId(), new BlockPosition(x, y, z), damage);
		for(Entity nearEntity : entity.getNearbyEntities(100, 100, 100)) {
			if(!(nearEntity instanceof Player)) continue;
			
			PlayerUtilities_Impl.sendPacket((Player) nearEntity, animation);
		}
	}
}
