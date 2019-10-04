package essentials.modules.container;

import essentials.utilities.container.ContainerUtilities;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ContainerCommands implements CommandExecutor, TabCompleter {

	public static final ContainerCommands containerCommands;
	
	static {
		containerCommands = new ContainerCommands();
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		
		if(args.length < 1) return true;
		
		Player p = null;
		if(sender instanceof Player)
			p = (Player) sender;
		
		switch (args[0].toLowerCase()) {
			case "open":
				
				if(p == null) break;
				
				Block b = p.getTargetBlock(null, 200);
				Inventory inv = ContainerUtilities.getInventory(b);
				if (inv == null) break;

				p.openInventory(inv);
				
				break;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
		if (args.length < 1) return null;

		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("open");

		} else {
			switch (args[0]) {
				case "open":
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
