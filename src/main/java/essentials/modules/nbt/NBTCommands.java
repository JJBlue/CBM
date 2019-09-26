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

import essentials.language.LanguageConfig;
import essentials.utilities.NBTUtilities;
import essentials.utilities.StringUtilities;
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
			case "add": { //add [byte|short|int|long|double|float|intarray|longarray|doublearray|floatarray|String] <key> <value>
				if(args.length < 4 || !(sender instanceof Player)) break;
				
				Player player = (Player) sender;
				ItemStack itemstack = player.getInventory().getItemInMainHand();
				
				NBTTag tag = NBTUtilities.getNBTTag(itemstack);
				String key = args[2];
				
				try {
					Object value = null;
					
					switch(args[1].toLowerCase()) {
						case "byte":
							value = Byte.parseByte(args[3]);
							break;
						case "short":
							value = Short.parseShort(args[3]);
							break;
						case "int":
							value = Integer.parseInt(args[3]);
							break;
						case "double":
							value = Double.parseDouble(args[3]);
							break;
						case "float":
							value = Float.parseFloat(args[3]);
							break;
						case "long":
							value = Long.parseLong(args[3]);
							break;
						case "intarray":
							
							int[] ia = new int[args.length - 3];
							
							for(int i = 3; i < args.length; i++)
								ia[i - 3] = Integer.parseInt(args[i]);
							
							value = ia;
							
							break;
							
						case "longarray":
							
							long[] la = new long[args.length - 3];
							
							for(int i = 3; i < args.length; i++)
								la[i - 3] = Long.parseLong(args[i]);
							
							value = la;
							
							break;
							
						case "doublearray":
							
							double[] da = new double[args.length - 3];
							
							for(int i = 3; i < args.length; i++)
								da[i - 3] = Double.parseDouble(args[i]);
							
							value = da;
							
							break;
							
						case "floatarray":
							
							float[] fa = new float[args.length - 3];
							
							for(int i = 3; i < args.length; i++)
								fa[i - 3] = Float.parseFloat(args[i]);
							
							value = fa;
							
							break;
							
						case "String":
							
							value = StringUtilities.arrayToStringRange(args, 3, args.length);
							
							break;
					}
					
					if(value != null) {
						tag.set(key, NBTUtilities.createNBTBase(value));
						NBTUtilities.setNBTTagCompound(itemstack, tag.getNBTTagCompound());
					}
					
					LanguageConfig.sendMessage(sender, "nbt.add", args[1], args[2], StringUtilities.arrayToStringRange(args, 3, args.length));
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}
			}
			
			case "remove": //remove <Key>
				
				if(args.length < 2 || !(sender instanceof Player)) break;
				
				Player player = (Player) sender;
				ItemStack itemstack = player.getInventory().getItemInMainHand();
				
				NBTTag tag = NBTUtilities.getNBTTag(itemstack);
				tag.remove(args[1]);
				NBTUtilities.setNBTTagCompound(itemstack, tag.getNBTTagCompound());
				LanguageConfig.sendMessage(sender, "nbt.remove", args[1]);
				
				break;
		}
		
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("info");
			returnArguments.add("add");
			returnArguments.add("remove");

		} else {
			switch (args[0]) {
				case "add":
					
					if(args.length == 2) {
						returnArguments.add("byte");
						returnArguments.add("short");
						returnArguments.add("int");
						returnArguments.add("long");
						returnArguments.add("double");
						returnArguments.add("float");
						returnArguments.add("intarray");
						returnArguments.add("longarray");
						returnArguments.add("doublearray");
						returnArguments.add("floatarray");
						returnArguments.add("bytStringe");
					} else if(args.length == 3)
						returnArguments.add("<Key>");
					else if(args.length == 4)
						returnArguments.add("<Value>");
					
					break;
					
				case "remove":
					if(!(sender instanceof Player)) break;
					
					Player player = (Player) sender;
					ItemStack itemstack = player.getInventory().getItemInMainHand();
					NBTTag tag = NBTUtilities.getNBTTag(itemstack);
					
					for(String s : tag.getKeys())
						returnArguments.add(s);
					
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
