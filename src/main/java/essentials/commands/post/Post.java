package essentials.commands.post;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Post implements Listener, CommandExecutor, TabCompleter {
	public static File Post = new File("plugins/Allgemein/Post", "Post.yml");
	public static FileConfiguration PostConf = YamlConfiguration.loadConfiguration(Post);
	public final static Post post;
	
	static {
		post = new Post();
	}
	
	public static void Load(){
		try {
			PostConf.load(Post);
		} catch (FileNotFoundException e1) {
		} catch (IOException e1) {
		} catch (InvalidConfigurationException e1) {}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {	
		if(!cmd.getName().equalsIgnoreCase("post")) return false;
		if(args.length < 1) return false;
		if(!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		switch (args[0]) {
			case "help":
				help(sender);
				break;
				
			case "open":
				if(args.length <= 1) {
					Block b = p.getTargetBlock(null, 50);
					Inventory inv = getInventory(b);
					if(inv == null) break;
					
					p.openInventory(inv);
				} else {
					Location l = (Location) PostConf.get(args[1]);
					if(l == null) return false;
					Block b = l.getBlock();
					
					Inventory inv = getInventory(b);
					if(inv == null) return false;
					
					p.openInventory(inv);
				}
				
				break;
			case "add":
				if(args.length < 2) break;
				
				Block b = p.getTargetBlock(null, 50);
				
				if(getInventory(b) != null) {
					Location bl = b.getLocation();
					Location l = new Location(bl.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ());
					
					PostConf.set(args[1], l);
				
					try {
						PostConf.save(Post);
					} catch (IOException e1) {}
				
					p.sendMessage("Truhe erstellt mit dem Namen: " + args[1]);
				} else
					p.sendMessage("Das ist keine Truhe");
				
				break;
		}
		
	    return true;
	}
	
	private static Inventory getInventory(Block b) {
		if(b.getState() instanceof Chest)
			return ((Chest) b.getState()).getInventory();
		else if(b.getState() instanceof ShulkerBox)
			return ((ShulkerBox) b.getState()).getInventory();
		
		return null;
	}
	
	private static void help(CommandSender sender){
		sender.sendMessage("/post help");
		sender.sendMessage("/post add <name>");
		sender.sendMessage("/post open [name]");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args.length < 1) return null;
		
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("help");
			returnArguments.add("open");
			returnArguments.add("add");
			
		} else {
			switch (args[0]) {
				case "open":
				case "add":
					returnArguments.add("<Name>");
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
}
