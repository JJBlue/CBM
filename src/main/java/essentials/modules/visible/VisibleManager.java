package essentials.modules.visible;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.utilities.permissions.PermissionHelper;

public class VisibleManager implements Listener {
	public final static Map<Player, HideState> hide = Collections.synchronizedMap(new HashMap<>());
	
	public static void changeVisible(Player player) {
		if (player == null) return;

		if(hide.containsKey(player))
			setVisible(player, HideState.VISIBLE);
		else
			setVisible(player, HideState.INVISIBLE);
	}
	
	public static void setVisible(Player player, HideState hideState) {
		boolean isHidden = false;
		
		switch (hideState) {
			case INVISIBLE:
			case INVISIBLE_FOR_ALL:
				hide.put(player, hideState);
				isHidden = true;
				break;
			case VISIBLE:
				hide.remove(player);
				isHidden = false;
				break;
		}
		
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			if(!isHidden)
				p2.showPlayer(Main.getPlugin(), player);
			else if(hideState == HideState.INVISIBLE_FOR_ALL)
				p2.hidePlayer(Main.getPlugin(), player);
			else if (!PermissionHelper.hasCommandPermission(p2, "hide.showhidden")) //p2 is no admin
				p2.hidePlayer(Main.getPlugin(), player);
		}

		if(isHidden)
			LanguageConfig.sendMessage(player, "hide.invisible-Player", player.getName());
		else
			LanguageConfig.sendMessage(player, "hide.visible-Player", player.getName());
	}
	
	@EventHandler
	private void login(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		synchronized (hide) {
			hide.forEach((player, state) -> {
				switch (state) {
					case INVISIBLE:
						if (!PermissionHelper.hasCommandPermission(p, "hide.showhidden")) //joining player is no admin
							p.hidePlayer(Main.getPlugin(), player);
						break;
					case INVISIBLE_FOR_ALL:
						p.hidePlayer(Main.getPlugin(), player);
						break;
					case VISIBLE:
						break;
				}
			});
		}
	}
}
