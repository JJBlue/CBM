package essentials.modules.hide;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.utilities.permissions.PermissionHelper;

public class HideManager implements Listener {
	public final static Set<Player> hide = new HashSet<>();
	
	public static void changeHide(Player player) {
		if (player == null) return;

		boolean isHidden = !hide.contains(player);
		
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			if(!isHidden)
				p2.showPlayer(Main.getPlugin(), player);
			else {
				if (PermissionHelper.hasCommandPermission(player, "hide.all")) { //can be invisible in front of admins
					p2.hidePlayer(Main.getPlugin(), player);
				} else if (!PermissionHelper.hasCommandPermission(p2, "hide.showhidden")) { //p2 is no admin
					p2.hidePlayer(Main.getPlugin(), player);
				}
			}
		}

		if(isHidden)
			LanguageConfig.sendMessage(player, "hide.invisible-Player", player.getName());
		else
			LanguageConfig.sendMessage(player, "hide.visible-Player", player.getName());
	}
	
	@EventHandler
	private void login(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		for (Player ps : hide) {
			if (PermissionHelper.hasCommandPermission(ps, "hide.all")) { //can be invisible in front of admins
				e.getPlayer().hidePlayer(Main.getPlugin(), p);
			} else {
				if (!PermissionHelper.hasCommandPermission(e.getPlayer(), "hide.showhidden")) { //joining player is no admin
					e.getPlayer().hidePlayer(Main.getPlugin(), p);
				}
			}
		}
	}
}
