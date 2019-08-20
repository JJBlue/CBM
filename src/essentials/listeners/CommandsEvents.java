package essentials.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import essentials.main.Main;
import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;

public class CommandsEvents implements Listener{
	public final static List<Player> afk = new LinkedList<>();
	public final static List<Player> hide = new LinkedList<>();
	
	@EventHandler
	private void Chat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		
		if(playerConfig.getBoolean(PlayerConfigKey.tMute))
			e.setCancelled(true);
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!afk.contains(p)) return;
		
		Location to = e.getTo();
		Location from = e.getFrom();
		
		if(from.getX() != to.getX() || from.getZ() != to.getZ()) {
			afk.remove(p);
			Bukkit.broadcastMessage(p.getName() + " ist nicht mehr afk");
		}
	}
	
	@EventHandler
	public void damage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if(!(entity instanceof Player)) return;
		if(!afk.contains((Player) entity)) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	private void Quit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.d)d HH:mm:ss");
		playerConfig.set(PlayerConfigKey.logoutTime, df.format(new Date()));
	}
	
	@EventHandler
	private void login(PlayerJoinEvent e){
		Player p = e.getPlayer();
		
		PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		playerConfig.set(PlayerConfigKey.loginTime, df.format(new Date()));
		
//		if(fileConf.getBoolean("jail")) {
//			//to jail TODO
//		} else if(!p.hasPlayedBefore()) {
//			//Spawn TODO
//		}
	
		for(Player ps : hide)
			p.hidePlayer(Main.getPlugin(), ps);
	}
}
