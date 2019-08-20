package essentials.commands.commandonobject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import essentials.main.CommandAusfuehren;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.chat.ChatUtilities;
import essentials.utilities.chat.ClickAction;
import essentials.utilities.chat.HoverAction;

public class CommandDruckplatten implements CommandExecutor, TabCompleter {
	public final static List<Material> Materials;
	public final static CommandDruckplatten commandDruckplatten;
	
	static {
		Materials = new LinkedList<Material>();
		commandDruckplatten = new CommandDruckplatten();
	}
	
	public static File CDfile(String world) {
		return new File("plugins/Allgemein/Commandsblock", world + ".yml");
	}
	
	public static FileConfiguration CDConf(String world) {
		FileConfiguration CDConf = YamlConfiguration.loadConfiguration(CDfile(world));
		return CDConf;
	}
	
	public static void save(FileConfiguration fileConf, String world) {
		try {
			fileConf.save(CDfile(world));
		} catch (IOException e) {}
	}
	
	public static void load() {
		Materials.clear();
		
		Materials.add(Material.ACACIA_PRESSURE_PLATE);
		Materials.add(Material.BIRCH_PRESSURE_PLATE);
		Materials.add(Material.DARK_OAK_PRESSURE_PLATE);
		Materials.add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
		Materials.add(Material.JUNGLE_PRESSURE_PLATE);
		Materials.add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
		Materials.add(Material.OAK_PRESSURE_PLATE);
		Materials.add(Material.SPRUCE_PRESSURE_PLATE);
		Materials.add(Material.STONE_PRESSURE_PLATE);
	}
	
	//MoveEvent Listener ist im MoveEvents
	@SuppressWarnings({"unchecked" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!sender.hasPermission("all.all")) return false;
		
		Player p = null;
		
		if(sender instanceof Player)
			p = (Player)sender;
		
		if(args.length < 1) return false;
		
		switch(args[0].toLowerCase()) {
			case "help":
				sender.sendMessage("§4@c <command> = Command over the consol,@a = All players,  @p = Player, @w = World");
				sender.sendMessage("/cos add <command>");
				sender.sendMessage("/cos remove <command>");
				sender.sendMessage("/cos clear");
				sender.sendMessage("/cos list");
				
				break;
				
			case "list":
				
				Block targetblock = p.getTargetBlock(null,50);
				
				FileConfiguration fileConf = CDConf(p.getWorld().getName());
				int X = targetblock.getLocation().getBlockX();
				int Y  = targetblock.getLocation().getBlockY();
				int Z  = targetblock.getLocation().getBlockZ();
				String s = X + "-" + Y + "-" + Z;
				
				ArrayList<String> l = new ArrayList<String>();
				
				if(fileConf.getList(s) != null) {
					l = (ArrayList<String>) fileConf.getList(s);
					
					p.sendMessage("Auf dem Item sind folgende Commands:");
					
					for(String s2 : l) {
						ChatUtilities.sendChatMessage(p, "\t/" + s2 + " ",
							ChatUtilities.createExtra(
								ChatUtilities.createClickHoverMessage("§4[-]", HoverAction.SHOW_Text, "Remove Command", ClickAction.RUN_COMMAND, "/cos remove " + s2)
							)
						);
					}
				}
				
				break;
				
			case "clear":
				
				targetblock = p.getTargetBlock(null,50);
				
				if(targetblock != null) {
					fileConf = CDConf(p.getWorld().getName());
					X = targetblock.getLocation().getBlockX();
					Y  = targetblock.getLocation().getBlockY();
					Z  = targetblock.getLocation().getBlockZ();
					s = X + "-" + Y + "-" + Z;
					
					fileConf.set(s, null);
					
					save(fileConf, p.getWorld().getName());
					p.sendMessage("Commands wurden geloescht");
				} else
					sender.sendMessage("Dies ist kein Block");
				
				break;
				
			case "add":
				
				if(args.length < 2) break;
				
				fileConf = CDConf(p.getWorld().getName());
				l = new ArrayList<String>();
				
				targetblock = p.getTargetBlock(null,50);
				
				X = targetblock.getLocation().getBlockX();
				Y = targetblock.getLocation().getBlockY();
				Z = targetblock.getLocation().getBlockZ();
				s = X + "-" + Y + "-" + Z;
				
				int anzahl = args.length;
				
				String argsstring = "";
				for(int y = 2; y <= anzahl; y++){
					int y2 = y - 1;
					if(y == 2)
						argsstring = args[y2];
					else
						argsstring = argsstring + " " + args[y2];
				}
				
				if(fileConf.getList(s) != null)
					l = (ArrayList<String>) fileConf.getList(s);
				
				l.add(argsstring);
				fileConf.set(s, l);
				
				save(fileConf, p.getWorld().getName());
				
				p.sendMessage("Auf dem Item sind nun folgende Commands:");
				
				for(String s2 : l)
					p.sendMessage("/" + s2);
				
				break;
				
			case "remove":
				
				if(args.length < 2) break;
				
				fileConf = CDConf(p.getWorld().getName());
				l = new ArrayList<String>();
				targetblock = p.getTargetBlock(null,50);
				
				X = targetblock.getLocation().getBlockX();
				Y = targetblock.getLocation().getBlockY();
				Z = targetblock.getLocation().getBlockZ();
				s = X + "-" + Y + "-" + Z;
				
				if(fileConf.getList(s) != null){
					l = (ArrayList<String>) fileConf.getList(s);
					argsstring = "";
					
					for(int y = 2; y <= args.length; y++){
						int y2 = y - 1;
						if(y == 2)
							argsstring = args[y2];
						else
							argsstring = argsstring + " " + args[y2];
					}
					
					if(l.contains(argsstring)){
						l.remove(argsstring);
						
						if(!l.isEmpty())
							fileConf.set(s, l);
						else
							fileConf.set(s, null);
							
						save(fileConf, p.getWorld().getName());
						p.sendMessage("§4Command gelöscht und gespeichert.");
					} else
						p.sendMessage("§4Command nicht vorhanden.");
					
					p.sendMessage("Auf dem Item sind nun folgende Commands:");
					
					for(String s2 : l)
						p.sendMessage("/" + s2);
					
				} else
					sender.sendMessage("Diese Block hat keine Commands");
				
				break;
				
			default:
				help(sender);
		}
		
	    return true;
	}
	
	public static void help(CommandSender sender) {
		sender.sendMessage("§4@c <command> = Command over the consol,@a = All players,  @p = Player, @w = World");
		sender.sendMessage("/cos add <command>");
		sender.sendMessage("/cos remove <command>");
		sender.sendMessage("/cos clear");
		sender.sendMessage("/cos list");
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(!cmdLabel.equalsIgnoreCase("cos")) return null;
		
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("list");
			returnArguments.add("clear");
			returnArguments.add("add");
			returnArguments.add("remove");
		} else {
			switch(args[0].toLowerCase()) {
				case "add":
				case "remove":
					switch (args.length) {
						case 2:
							
							returnArguments = BukkitUtilities.getAvailableCommands(sender);
							returnArguments.add("@c");
							
							break;
							
						case 3:
							
							if(args[1].toLowerCase().equalsIgnoreCase("@c"))
								return BukkitUtilities.getAvailableCommands(sender);
	
						default:
							
							boolean sendServer = args[1].toLowerCase().equalsIgnoreCase("@c");
							
							PluginCommand command = Bukkit.getPluginCommand(sendServer ? args[2] : args[1]);
							if(command == null) break;
							TabCompleter tabCompleter = command.getTabCompleter();
							if(tabCompleter == null) break;
							return tabCompleter.onTabComplete(sender, command, sendServer ? args[2] : args[1], Arrays.copyOfRange(args, sendServer ? 3 : 2, args.length));
					}
					
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void onDruckplatte(Player p, Block targetblock){	
		FileConfiguration fileConf = CDConf(p.getWorld().getName());
		ArrayList<String> l = new ArrayList<String>();
		
		int X = targetblock.getLocation().getBlockX();
		int Y  = targetblock.getLocation().getBlockY();
		int Z  = targetblock.getLocation().getBlockZ();
		String s = X + "-" + Y + "-" + Z;
		
		if(fileConf.getList(s) != null){
			l = (ArrayList<String>) fileConf.getList(s);
			for(String s2 : l){
				String s3 = s2;
				s3 = s3.replaceAll("@p", p.getName());
				s3 = s3.replaceAll("@w", p.getWorld().getName());
				
				if(s3.contains("@a")) {
					String oldstring = s3;
				
					for(Player players : Bukkit.getOnlinePlayers()){
						s3 = oldstring;
						String s4 = s3.replace("@a", players.getName());
						CommandAusfuehren.commandstart(s4, p);
					}
				} else
					CommandAusfuehren.commandstart(s3, p);
			}
		}
	}
}
