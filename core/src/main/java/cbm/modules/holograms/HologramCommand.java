package cbm.modules.holograms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import cbm.language.LanguageConfig;
import cbm.utilities.StringUtilities;
import cbm.utilities.chat.ChatUtilities;

public class HologramCommand implements TabExecutor {

	private final double radius = 20;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		if(args.length < 1) return true;
		
		Player player = (Player) sender;
		
		/*
		 * create <Text>
		 * createid <ID> <Text>
		 * add <ID> <Text> 			-> Add text
		 * remove <ID>				-> remove text
		 * removeline <ID> <Line>
		 * addline <ID> <Line>
		 * 
		 * delete <ID>
		 * move <ID>
		 * getids <Radius>
		 * setid <FromID> <ToID>
		 */
		switch (args[0].toLowerCase()) {
			case "create": {
				if(args.length < 2) break;
				
				Hologram hologram = new Hologram(player.getLocation().clone().add(0, 2, 0));
				hologram.addText(ChatUtilities.convertToColor(StringUtilities.arrayToStringRange(args, 1, args.length)));
				break;
			}
			case "createid": {
				if(args.length < 3) break;
				
				Hologram hologram = new Hologram(player.getLocation().clone().add(0, 2, 0), args[1]);
				hologram.addText(ChatUtilities.convertToColor(StringUtilities.arrayToStringRange(args, 2, args.length)));
				break;
			}
			case "add": {
				if(args.length < 3) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				hologram.addText(ChatUtilities.convertToColor(StringUtilities.arrayToStringRange(args, 2, args.length)));
				break;
			}
			case "addline": {
				if(args.length < 4) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				try {
					hologram.addText(Integer.parseInt(args[2]), ChatUtilities.convertToColor(StringUtilities.arrayToStringRange(args, 3, args.length)));
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				break;
			}
			case "setline": {
				if(args.length < 4) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				try {
					hologram.setText(Integer.parseInt(args[2]), ChatUtilities.convertToColor(StringUtilities.arrayToStringRange(args, 3, args.length)));
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				break;
			}
			case "remove": {
				if(args.length < 2) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				hologram.removeText();
				break;
			}
			case "removeline": {
				if(args.length < 3) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				try {
					hologram.removeLine(Integer.parseInt(args[2]));
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				break;
			}
			case "delete": {
				if(args.length < 2) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				hologram.clear();
				break;
			}
			case "move": {
				if(args.length < 2) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				hologram.moveHologram(player.getLocation().clone().add(0, 2, 0));
				break;
			}
			case "setid" : {
				if(args.length < 3) break;
				
				Hologram hologram = HologramManager.getHologram(player.getLocation(), radius, args[1]);
				if(hologram == null) break;
				hologram.setID(args[2]);
				break;
			}
			case "getids": {
				
				player.sendMessage("§6Hologram ID's in a Radius of " + radius + ":");
				player.sendMessage("§6" + StringUtilities.listToListingString(HologramManager.getIDs(player.getLocation(), radius)));
				
				break;
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(!(sender instanceof Player)) return null;
		
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("create");
			returnArguments.add("createid");
			returnArguments.add("add");
			returnArguments.add("addline");
			returnArguments.add("setline");
			returnArguments.add("remove");
			returnArguments.add("removeline");
			returnArguments.add("getids");
			returnArguments.add("move");
			returnArguments.add("setid");
			returnArguments.add("delete");

		} else {
			switch (args[0].toLowerCase()) {
				case "create": {
					returnArguments.add("<Text>");
					break;
				}
				case "createid": {
					if(args.length == 2)
						returnArguments.add("<ID>");
					else
						returnArguments.add("<Text>");
					
					break;
				}
				case "add": {
					
					if(args.length == 2) {
						for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
							returnArguments.add(id);
					} else
						returnArguments.add("<Text>");
					
					break;
				}
				case "addline": {
					
					if(args.length == 2) {
						for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
							returnArguments.add(id);
					} else if(args.length == 3)
						returnArguments.add("<Line>");
					else
						returnArguments.add("<Text>");
					
					break;
				}
				case "setline": {
					
					if(args.length == 2) {
						for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
							returnArguments.add(id);
					} else if(args.length == 3)
						returnArguments.add("<Line>");
					else if(args.length == 4) {
						Hologram hologram = HologramManager.getHologram(((Player) sender).getLocation(), radius, args[1]);
						if(hologram != null) {
							try {
								HologramLine line = hologram.getLines().get(Integer.parseInt(args[2]));
								if(line != null)
									returnArguments.add(line.getText());
							}catch (NumberFormatException e) {}
						}
						returnArguments.add("<Text>");
					}
					
					break;
				}
				case "remove": {
					
					if(args.length == 2) {
						for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
							returnArguments.add(id);
					}
					
					break;
				}
				case "removeline": {
					
					if(args.length == 2) {
						for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
							returnArguments.add(id);
					} else if(args.length == 3)
						returnArguments.add("<Line>");
					
					break;
				}
				case "delete": {
					
					for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
						returnArguments.add(id);
					
					break;
				}
				case "move": {
					
					for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
						returnArguments.add(id);
					
					break;
				}
				case "setid" : {
					
					if(args.length == 2) {
						for(String id : HologramManager.getIDs(((Player) sender).getLocation(), radius))
							returnArguments.add(id);
					}
					
					break;
				}
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

}
