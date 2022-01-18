package cbm.utilitiesvr.sign;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public interface SignUtilities_Interface {
	void editSign(Player player, Sign sign);
	
	default void openSign(Player player, Location location) {
		Sign sign = (Sign) location.getBlock().getState();
		openSign(player, sign);
	}
	
	void openSign(Player player, Sign sign);
}
