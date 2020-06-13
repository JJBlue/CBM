package cbm.utilitiesvr.player;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

public interface PlayerUtilities_Interface {
	public void updatePlayer(Player p);
	
	public void sendPacket(Player player, Object packet);
	
	public void setArmSwing(Player player, EnumHandUtil hand);
	
	public void setHeldItemSlot(Player player, int number);
	
	public void setGameProfile(Player player, GameProfile gameProfile);
	
	public GameProfile getGameProfile(Player player);
}
