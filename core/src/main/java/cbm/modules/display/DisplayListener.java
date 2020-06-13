package cbm.modules.display;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import cbm.language.LanguageConfig;
import cbm.utilities.MathUtilities;
import cbm.utilities.chat.ChatUtilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DisplayListener implements Listener {
	private Map<Player, BossBar> damgeBossbar = Collections.synchronizedMap(new HashMap<>());
	private Map<Player, Integer> counts = Collections.synchronizedMap(new HashMap<>());
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void damage(EntityDamageByEntityEvent event) {
		if(!(event.getEntity() instanceof LivingEntity)) return;
		
		ConfigurationSection display = DisplayManager.getConfigurationSection();
		if(display == null || !display.getBoolean("showDamageOnEntity")) return;
		
		//Show heal and Damage of Entity
		Player player = getPlayer(event.getDamager());
		if(player == null) return;
		
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		BossBar bossBar = damgeBossbar.get(player);
		
		if(bossBar == null) {
			bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
			bossBar.addPlayer(player);
			damgeBossbar.put(player, bossBar);
		}

		double max = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		double resultHealth = entity.getHealth() - event.getDamage();
		if(resultHealth < 0) resultHealth = 0;
		else if(resultHealth > max) resultHealth = max;

		bossBar.setTitle(entity.getName() + ": §e" + MathUtilities.round(resultHealth, 2) + "§f / §e" + max + "§f (§4-" + MathUtilities.round(event.getDamage(), 2) + "§f)");
		bossBar.setProgress((1 / max) * resultHealth);

		DisplayBossBarTimer.addBossbar(bossBar, 5, () -> damgeBossbar.remove(player));
	}
	
	private static Player getPlayer(Entity entity) {
		if(entity instanceof Player)
			return (Player) entity;
		else if(entity instanceof Projectile) {
			ProjectileSource source = ((Projectile) entity).getShooter();
			
			if(source instanceof Player)
				return (Player) source;
		}
		
		return null;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void fly(PlayerMoveEvent event) {
		//Show Eltra Speed
		Player player = event.getPlayer();
		if(!player.isGliding()) return;
		
		ConfigurationSection display = DisplayManager.getConfigurationSection();
		if(display == null || !display.getBoolean("showElytraSpeed")) return;
		
		if(!counts.containsKey(player))
			counts.put(player, 0);
		else {
			int i = counts.get(player) + 1;
			if(i != 10) {
				counts.put(player, i);
				return;
			}
			
			counts.put(player, 0);
		}
		
		ChatUtilities.sendHotbarMessage(player, "§e" + LanguageConfig.getString("text.speed") + ": §6" + MathUtilities.round(player.getVelocity().length() * 20 * 3.6, 2) + "§e km/h");
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event) {
		BossBar bossBar = damgeBossbar.remove(event.getPlayer());
		DisplayBossBarTimer.map.remove(bossBar);
		counts.remove(event.getPlayer());
	}
}
