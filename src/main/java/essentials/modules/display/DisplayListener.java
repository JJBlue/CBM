package essentials.modules.display;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import essentials.language.LanguageConfig;
import essentials.player.PlayersYMLConfig;
import essentials.utilities.MathUtilities;
import essentials.utilities.chat.ChatUtilities;

public class DisplayListener implements Listener {
	private Map<Player, BossBar> damgeBossbar = Collections.synchronizedMap(new HashMap<>());
	private Map<Player, Integer> counts = Collections.synchronizedMap(new HashMap<>());
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void damge(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player)) return;
		if(!(event.getDamager() instanceof LivingEntity)) return;
		
		ConfigurationSection display = PlayersYMLConfig.getConfigurationSection("display");
		if(display == null || !display.getBoolean("showDamageOnEntity")) return;
		
		//Show heal and Damage of Entity
		Player player = (Player) event.getDamager();
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		BossBar bossBar = damgeBossbar.get(player);
		
		if(bossBar == null) {
			bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
			bossBar.addPlayer(player);
			damgeBossbar.put(player, bossBar);
		}
		
		if(bossBar != null) {
			Attributable attributable = (Attributable) entity;
			double max = attributable.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
			double resultHealth = entity.getHealth() - event.getDamage();
			if(resultHealth < 0) resultHealth = 0;
			else if(resultHealth > max) resultHealth = max;
			
			bossBar.setTitle(entity.getName() + ": §e" + MathUtilities.round(resultHealth, 2) + "§f / §e" + max + "§f (§4-" + MathUtilities.round(event.getDamage(), 2) + "§f)");
			bossBar.setProgress((1 / max) * resultHealth);
			
			DisplayBossBarTimer.addBossbar(bossBar, 5, () -> damgeBossbar.remove(player));
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void fly(PlayerMoveEvent event) {
		//Show Eltra Speed
		Player player = event.getPlayer();
		if(!player.isGliding()) return;
		
		ConfigurationSection display = PlayersYMLConfig.getConfigurationSection("display");
		if(display == null || !display.getBoolean("showElytraSpeed")) return;
		
		if(!counts.containsKey(player))
			counts.put(player, 0);
		else {
			int i = counts.get(player) + 1;
			if(i != 10) {
				counts.put(player, i);
				return;
			} else
				counts.put(player, 0);
		}
		
		ChatUtilities.sendHotbarMessage(player, "§e" + LanguageConfig.getString("text.speed") + ": §6" + MathUtilities.round(player.getVelocity().length() * 100 * 3.6, 2) + "§e km/h");
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		BossBar bossBar = damgeBossbar.remove(event.getPlayer());
		DisplayBossBarTimer.map.remove(bossBar);
		counts.remove(event.getPlayer());
	}
}
