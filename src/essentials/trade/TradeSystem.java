package essentials.trade;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.inventory.InventoryFactory;
import essentials.inventory.InventoryItem;
import essentials.inventory.InventoryPage;
import essentials.main.Main;

public class TradeSystem {
	private TradeSystem() {}
	
	private int task = -1;
	private boolean canceld = false;
	private InventoryFactory factory;
	
	private Player p1;
	private Player p2;
	
	private InventoryItem check1;
	private InventoryItem check2;
	
	public static void openTradeInventory(final Player p1, final Player p2) {
		new TradeSystem().openInventory(p1, p2);
	}
	
	public void openInventory(final Player p1, final Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		
		StringBuilder title = new StringBuilder();
		title.append(p1.getName());
		for(int i = p1.getName().length(); i < 19; i++)
			title.append(' ');
		title.append(p2.getName());
		
		factory = new InventoryFactory(Bukkit.createInventory(null, 54, title.toString()));
		InventoryPage page = factory.createFirstPage();
		
		check1 = new InventoryItem(new ItemStack(Material.RED_DYE));
		check2 = new InventoryItem(new ItemStack(Material.RED_DYE));
		check1.setOnClick((event, item) -> {
			if((item == check1 && event.getWhoClicked() == p1) || (item == check2 && event.getWhoClicked() == p2)) {
				ItemStack is = item.getItemStack();
				if(is.getType() != Material.YELLOW_DYE) {
					is.setType(Material.YELLOW_DYE);
					is.setAmount(5);
					factory.refreshPage();
					
					if(check1.getItemStack().getType().equals(Material.YELLOW_DYE) && check2.getItemStack().getType().equals(Material.YELLOW_DYE))
						startTimer();
				}
			}
			
			event.setCancelled(true);
		});
		check2.setOnClick(check1.getOnClick());
		
		page.addInventoryItem(45, check1);
		page.addInventoryItem(53, check2);
		
		InventoryItem barrier = new InventoryItem(new ItemStack(Material.RED_STAINED_GLASS_PANE));
		barrier.setOnClick((event, item) -> event.setCancelled(true)); 
		page.addInventoryItem(4, barrier.clone());
		page.addInventoryItem(13, barrier.clone());
		page.addInventoryItem(22, barrier.clone());
		page.addInventoryItem(31, barrier.clone());
		page.addInventoryItem(40, barrier.clone());
		page.addInventoryItem(49, barrier.clone());
		
		factory.setOnClick((event, item) -> {
			if(canceld) {
				event.setCancelled(true);
				return;
			}
			
			if(!event.isCancelled())
				stopTimer();
			
			switch(event.getClick()) {
				case DOUBLE_CLICK:
					event.setCancelled(true);
					return;
				case NUMBER_KEY:
					event.setCancelled(true);
					return;
				default:
					break;
			}
			
			if(event.getWhoClicked() != p1) {
				if(event.getSlot() % 9 <= 4)
					event.setCancelled(true);
			} else if(event.getWhoClicked() != p2) {
				if(event.getSlot() % 9 >= 4)
					event.setCancelled(true);
			}
		});
		
		factory.setOnClose((event) -> {
			if(canceld) return;
			
			if(event.getPlayer() != p2)
				p2.closeInventory();
			else if(event.getPlayer() != p1)
				p1.closeInventory();
			
			stopTimer();
			putOut(p2, p1);
		});
		
		factory.refreshPage();
		
		if(p1 != null)
			p1.openInventory(factory.getInventory());
		if(p2 != null)
			p2.openInventory(factory.getInventory());
	}
	
	private void startTimer() {
		if(task != -1) return;
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {
			ItemStack is1 = check1.getItemStack();
			ItemStack is2 = check2.getItemStack();
			
			if(is1.getAmount() == 0) {
				canceld = true;
				factory.close();
				
				putOut(p1, p2);
				
				Bukkit.getScheduler().cancelTask(task);
				task = -1;
				return;
			}
			
			is1.setAmount(is1.getAmount() - 1);
			is2.setAmount(is2.getAmount() - 1);
			factory.refreshPage();
		}, 0l, 20l);
	}
	
	private void putOut(Player p1, Player p2) {
		for(int i = 0; i < 54; i++) {
			if(i == 45 || i == 53) continue;
			
			ItemStack is = factory.getInventory().getItem(i);
			if(is == null) continue;
			
			if(i % 9 < 4) {
				Map<Integer, ItemStack> map = p2.getInventory().addItem(is);
				for(ItemStack drop : map.values())
					p2.getWorld().dropItem(p2.getLocation(), drop);
			} else if(i % 9 > 4) {
				Map<Integer, ItemStack> map = p1.getInventory().addItem(is);
				for(ItemStack drop : map.values())
					p1.getWorld().dropItem(p1.getLocation(), drop);
			}
		}
	}
	
	private void stopTimer() {
		ItemStack is1 = check1.getItemStack();
		ItemStack is2 = check2.getItemStack();
		
		if(!is1.getType().equals(Material.RED_DYE) || !is2.getType().equals(Material.RED_DYE)) {
			is1.setType(Material.RED_DYE);
			is1.setAmount(1);
			is2.setType(Material.RED_DYE);
			is2.setAmount(1);
			factory.refreshPage();
		}
		
		if(task == -1) return;
		Bukkit.getScheduler().cancelTask(task);
		task = -1;
	}
}
