package essentials.modules.nbt;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.utilities.NBTUtilities;
import essentials.utilitiesvr.nbt.NBTTag;

public class NBTCommands implements CommandExecutor, TabCompleter {

	public final static NBTCommands nbtCommands;
	
	static {
		nbtCommands = new NBTCommands();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length < 1) return true;
		
		switch(args[0].toLowerCase()) {
			case "info": {
				
				if(!(sender instanceof Player)) return true;
				
				Player player = (Player) sender;
				NBTManager.listNBT(sender, player.getInventory().getItemInMainHand());
				
				break;
			}
			case "add": { //TODO
				if(args.length < 2) break;
				
				if(!(sender instanceof Player)) break;
				
				Player player = (Player) sender;
				ItemStack itemstack = player.getInventory().getItemInMainHand();
				
				NBTTag tag = NBTUtilities.getNBTTag(itemstack);
				String key = args[2];
				
				switch(args[1].toLowerCase()) {
					case "int":
						
						try {
							tag.set(key, NBTUtilities.createNBTBase(Integer.parseInt(args[3])));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
						
						NBTUtilities.setNBTTagCompound(itemstack, tag.getNBTTagCompound());
						//TODO ausgabe
						
						break;
				}
			}
		}
		
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("info");

		} else {
			switch (args[0]) {
			
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
