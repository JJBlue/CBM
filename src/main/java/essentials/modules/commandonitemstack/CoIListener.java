package essentials.modules.commandonitemstack;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class CoIListener implements Listener {
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		ItemStack itemStack = event.getItem();
		
		if(itemStack == null || itemStack.getType().name().toLowerCase().contains("air")) return;
		
		CoIManager.execute(event.getPlayer(), itemStack, CoIAction.DEFAULT);
		
		switch (event.getAction()) {
			case LEFT_CLICK_AIR:
			case LEFT_CLICK_BLOCK:
				CoIManager.execute(event.getPlayer(), itemStack, CoIAction.LEFT);
				break;
			case RIGHT_CLICK_AIR:
			case RIGHT_CLICK_BLOCK:
				CoIManager.execute(event.getPlayer(), itemStack, CoIAction.RIGHT);
				break;
			default:
				break;
		}
	}
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent event) {
		ItemStack itemStack = null;
		Entity entity = event.getDamager();
		
		if(event.getDamager() instanceof Player)
			itemStack = ((Player) entity).getInventory().getItemInMainHand();
		else if(event.getDamager() instanceof Projectile) {
			ProjectileSource projectileSource = ((Projectile) event.getDamager()).getShooter();
			
			if(!(projectileSource instanceof Entity)) return;
			
			if(projectileSource instanceof Player)
				itemStack = ((Player) projectileSource).getInventory().getItemInMainHand();
			else if(projectileSource instanceof LivingEntity)
				itemStack = ((LivingEntity) entity).getEquipment().getItemInMainHand();
			
		} else if(event.getDamager() instanceof LivingEntity)
			itemStack = ((LivingEntity) entity).getEquipment().getItemInMainHand();
		
		if(itemStack == null || entity == null) return;
		
		CoIManager.execute(entity, itemStack, CoIAction.DEFAULT);
		CoIManager.execute(entity, itemStack, CoIAction.HIT);
	}
	
	@EventHandler
	public void place(BlockPlaceEvent event) {
		ItemStack itemStack = event.getItemInHand();
		if(itemStack == null || itemStack.getType().name().toLowerCase().contains("air")) return;
		
		CoIManager.execute(event.getPlayer(), itemStack, CoIAction.BLOCK_PLACE);
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent event) {
		ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
		if(itemStack == null || itemStack.getType().name().toLowerCase().contains("air")) return;
		
		CoIManager.execute(event.getPlayer(), itemStack, CoIAction.BLOCK_BREAK);
	}
}
