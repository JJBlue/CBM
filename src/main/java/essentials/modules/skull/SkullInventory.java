package essentials.modules.skull;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullInventory implements Listener, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 1) return true;
		if (!(sender instanceof Player)) return true;

		Player p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "chest":

				ItemStack c = new ItemStack(Material.CHEST, 1);
				ItemMeta cMeta = c.getItemMeta();

				cMeta.setDisplayName("Skull's");
				c.setItemMeta(cMeta);

				p.getInventory().addItem(c);

				break;

			case "give":

				if (args.length < 2) return true;

				ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
				SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
				skullMeta.setOwner(args[1]);
				skull.setItemMeta(skullMeta);
				p.getInventory().addItem(skull);

				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("chest");
			returnArguments.add("give");

		} else {
			switch (args[0]) {
				default:
					for (Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());

					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

	@EventHandler
	private void Iteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (p.getInventory().getItemInMainHand().getType().equals(Material.CHEST)) {
			if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null) {
				if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("Skull's")) {
					e.setCancelled(true);

					Inventory inv;
					inv = p.getPlayer().getServer().createInventory(null, 54, "Skull's");

					ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
					SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

					skullMeta.setOwner("MHF_Alex");
					skull.setItemMeta(skullMeta);
					inv.setItem(0, skull);

					skullMeta.setOwner("MHF_Blaze");
					skull.setItemMeta(skullMeta);
					inv.setItem(1, skull);

					skullMeta.setOwner("MHF_CaveSpider");
					skull.setItemMeta(skullMeta);
					inv.setItem(2, skull);

					skullMeta.setOwner("MHF_Chicken");
					skull.setItemMeta(skullMeta);
					inv.setItem(3, skull);

					skullMeta.setOwner("MHF_Cow");
					skull.setItemMeta(skullMeta);
					inv.setItem(4, skull);

					skullMeta.setOwner("MHF_Creeper");
					skull.setItemMeta(skullMeta);
					inv.setItem(5, skull);

					skullMeta.setOwner("MHF_Enderman");
					skull.setItemMeta(skullMeta);
					inv.setItem(6, skull);

					skullMeta.setOwner("MHF_Ghast");
					skull.setItemMeta(skullMeta);
					inv.setItem(7, skull);

					skullMeta.setOwner("MHF_Golem");
					skull.setItemMeta(skullMeta);
					inv.setItem(8, skull);

					skullMeta.setOwner("MHF_Herobrine");
					skull.setItemMeta(skullMeta);
					inv.setItem(9, skull);

					skullMeta.setOwner("MHF_LavaSlime");
					skull.setItemMeta(skullMeta);
					inv.setItem(10, skull);

					skullMeta.setOwner("MHF_MushroomCow");
					skull.setItemMeta(skullMeta);
					inv.setItem(11, skull);

					skullMeta.setOwner("MHF_Ocelot");
					skull.setItemMeta(skullMeta);
					inv.setItem(12, skull);

					skullMeta.setOwner("MHF_Pig");
					skull.setItemMeta(skullMeta);
					inv.setItem(13, skull);

					skullMeta.setOwner("MHF_PigZombie");
					skull.setItemMeta(skullMeta);
					inv.setItem(14, skull);

					skullMeta.setOwner("MHF_Sheep");
					skull.setItemMeta(skullMeta);
					inv.setItem(15, skull);

					skullMeta.setOwner("MHF_Skeleton");
					skull.setItemMeta(skullMeta);
					inv.setItem(16, skull);

					skullMeta.setOwner("MHF_Slime");
					skull.setItemMeta(skullMeta);
					inv.setItem(17, skull);

					skullMeta.setOwner("MHF_Spider");
					skull.setItemMeta(skullMeta);
					inv.setItem(18, skull);

					skullMeta.setOwner("MHF_Squid");
					skull.setItemMeta(skullMeta);
					inv.setItem(19, skull);

					skullMeta.setOwner("MHF_Steve");
					skull.setItemMeta(skullMeta);
					inv.setItem(20, skull);

					skullMeta.setOwner("MHF_WSkeleton");
					skull.setItemMeta(skullMeta);
					inv.setItem(21, skull);

					skullMeta.setOwner("MHF_Villager");
					skull.setItemMeta(skullMeta);
					inv.setItem(22, skull);

					skullMeta.setOwner("MHF_Zombie");
					skull.setItemMeta(skullMeta);
					inv.setItem(23, skull);


					//Bl$cke
					skullMeta.setOwner("MHF_Cactus");
					skull.setItemMeta(skullMeta);
					inv.setItem(27, skull);

					skullMeta.setOwner("MHF_Cake");
					skull.setItemMeta(skullMeta);
					inv.setItem(28, skull);

					skullMeta.setOwner("MHF_Chest");
					skull.setItemMeta(skullMeta);
					inv.setItem(29, skull);

					skullMeta.setOwner("MHF_CoconutB");
					skull.setItemMeta(skullMeta);
					inv.setItem(30, skull);

					skullMeta.setOwner("MHF_CoconutG");
					skull.setItemMeta(skullMeta);
					inv.setItem(31, skull);

					skullMeta.setOwner("MHF_Melon");
					skull.setItemMeta(skullMeta);
					inv.setItem(32, skull);

					skullMeta.setOwner("MHF_OakLog");
					skull.setItemMeta(skullMeta);
					inv.setItem(33, skull);

					skullMeta.setOwner("MHF_Present1");
					skull.setItemMeta(skullMeta);
					inv.setItem(34, skull);

					skullMeta.setOwner("MHF_Present2");
					skull.setItemMeta(skullMeta);
					inv.setItem(35, skull);

					skullMeta.setOwner("MHF_Pumpkin");
					skull.setItemMeta(skullMeta);
					inv.setItem(36, skull);

					skullMeta.setOwner("MHF_TNT");
					skull.setItemMeta(skullMeta);
					inv.setItem(37, skull);

					skullMeta.setOwner("MHF_TNT2");
					skull.setItemMeta(skullMeta);
					inv.setItem(38, skull);


					//Holzkl$tze
					skullMeta.setOwner("MHF_ArrowUp");
					skull.setItemMeta(skullMeta);
					inv.setItem(45, skull);

					skullMeta.setOwner("MHF_ArrowDown");
					skull.setItemMeta(skullMeta);
					inv.setItem(46, skull);

					skullMeta.setOwner("MHF_ArrowLeft");
					skull.setItemMeta(skullMeta);
					inv.setItem(47, skull);

					skullMeta.setOwner("MHF_ArrowRight");
					skull.setItemMeta(skullMeta);
					inv.setItem(48, skull);

					skullMeta.setOwner("MHF_Exclamation");
					skull.setItemMeta(skullMeta);
					inv.setItem(49, skull);

					skullMeta.setOwner("MHF_Question");
					skull.setItemMeta(skullMeta);
					inv.setItem(50, skull);

					p.openInventory(inv);
				}
			}
		}
	}
}
