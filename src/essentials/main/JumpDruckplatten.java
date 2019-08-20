package essentials.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpDruckplatten implements Listener{
	private static List<String> worlds = new ArrayList<String>();
	private static List<Player> players = new ArrayList<Player>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		
		if(p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.SLIME_BLOCK){
			if(p.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock().getType() == Material.SLIME_BLOCK){
				if(worlds.contains(p.getWorld().getName())){
					Vector v = p.getLocation().getDirection().multiply(10D).setY(1D);
					p.setVelocity(v);
					p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 3);
					p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 3, 2);
					
					if(!players.contains(p))
						players.add(p);
				}
			}
		}
	}
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent e){
		Entity e2 = e.getEntity();
		
		if(e2 instanceof Player){
			Player p = (Player)e2;
			if(e.getCause().equals(DamageCause.FALL)){
				if(players.contains(p)){
					e.setCancelled(true);
					try{
						players.remove(p);
					}catch (Exception e5){}
				}
	        }
		}
	}
}
