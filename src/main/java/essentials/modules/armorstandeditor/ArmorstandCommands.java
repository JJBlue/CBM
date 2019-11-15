package essentials.modules.armorstandeditor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;

public class ArmorstandCommands implements TabExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, String[] args) {
		if (args.length < 1) return true;
		if (!(sender instanceof Player)) return true;

		Player p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "near":

				ArmorStand armorStand = null;
				double distance = -1;

				for (Entity entity : p.getNearbyEntities(20, 20, 20)) {
					if (entity instanceof ArmorStand) {
						double dis = entity.getLocation().distance(p.getLocation());
						if (dis < distance || distance < 0) {
							distance = dis;
							armorStand = (ArmorStand) entity;
						}
					}
				}

				if (armorStand == null) break;

				new ArmorstandInventory(armorStand).openInventory(p);

				PlayerConfig config = PlayerManager.getConfig(p);
				config.setTmp("armorstandEditor", armorStand);

				break;

			case "use":

				if (args.length < 2) break;

				Entity entity = Bukkit.getEntity(UUID.fromString(args[1]));
				if (!(entity instanceof ArmorStand)) break;
				armorStand = (ArmorStand) entity;

				new ArmorstandInventory(armorStand).openInventory(p);

				config = PlayerManager.getConfig(p);
				config.setTmp("armorstandEditor", armorStand);

				break;

			case "last":

				config = PlayerManager.getConfig(p);
				armorStand = (ArmorStand) config.get("armorstandEditor");
				if (armorStand != null)
					new ArmorstandInventory(armorStand).openInventory(p);

				break;

			case "lookAt":
				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("near");
			returnArguments.add("last");
			returnArguments.add("use");

		} else {
			switch (args[0].toLowerCase()) {
				case "use":
					if (!(sender instanceof Player)) break;

					for (Entity entity : ((Player) sender).getNearbyEntities(20, 20, 20)) {
						if (entity instanceof ArmorStand)
							returnArguments.add(entity.getUniqueId().toString());
					}

				default:
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

}
