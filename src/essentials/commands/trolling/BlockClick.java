package essentials.commands.trolling;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BlockClick implements Listener {
	@EventHandler
	public static void onDamageEntity(EntityDamageByEntityEvent e){
		Entity damager = e.getDamager();
		Entity entity = e.getEntity();
		
		if(!(damager instanceof Player)) return;
		if(!(entity instanceof LivingEntity)) return;
		
		Ausfuehren((Player) damager, (LivingEntity) entity);
	}
	
	@EventHandler
	public static void onBlockClick2(PlayerInteractEntityEvent e){
		Player player = e.getPlayer();
		if(!(e.getRightClicked() instanceof LivingEntity)) return;
		Ausfuehren(player, (LivingEntity) e.getRightClicked());
	}
	
	public static Material m = Material.GLASS;
	
	private static void Ausfuehren(Player ausfuehrer, LivingEntity onEntity){
		if(ausfuehrer == null || onEntity == null) return;
		
		ItemStack isAusfuehrer = ausfuehrer.getInventory().getItemInMainHand();
		
		//nur mit Permission und Gamemode 1
		if(ausfuehrer.hasPermission("trol.item")) {
			if(ausfuehrer.getGameMode() == GameMode.CREATIVE){
				if(isAusfuehrer.getType().equals(Material.STICK))
					onEntity.getLocation().setY(onEntity.getLocation().getY() + 10);
				
				if(isAusfuehrer.getType().equals(Material.GLASS) || isAusfuehrer.getType().equals(Material.GLASS_PANE))
					gefangen(onEntity.getLocation());
			}
			
			if(isAusfuehrer.getType().equals(Material.DIAMOND_SWORD)){
				if(onEntity instanceof Player) {
					ItemStack is = ((Player) onEntity).getInventory().getItemInMainHand();
					if(is.getType().equals(Material.DIAMOND_SWORD) && is.getItemMeta().getDisplayName().equals("Heal"))
						return;
				}
				
				if(isAusfuehrer.getItemMeta().getDisplayName().equals("Tod"))
					onEntity.damage(1000.0);
			}
		}
		
		//Jeder
		if(isAusfuehrer.getType().equals(Material.DIAMOND_SWORD) && isAusfuehrer.getItemMeta().getDisplayName().equals("MyTod")){
			ausfuehrer.setGameMode(GameMode.SURVIVAL);
			ausfuehrer.damage(1000.0);
		}
	}
	
	private static void gefangen(Location l){
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		World world = l.getWorld();
		
		Location b = new Location(world, x, y - 1, z);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x, y + 2, z);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x - 1, y, z);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x - 1, y + 1, z);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x + 1, y, z);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x + 1, y + 1, z);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x, y, z - 1);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x, y + 1, z - 1);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x, y, z + 1);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		b = new Location(world, x, y + 1, z + 1);
		if(b.getBlock().getType() == Material.AIR)
			b.getBlock().setType(m);
		
		if(m != Material.GLASS)
			m = Material.GLASS;
	}
}
