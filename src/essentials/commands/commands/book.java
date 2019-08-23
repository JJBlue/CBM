package essentials.commands.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

//TODO old
public class book implements Listener{
	public static File SB = new File("plugins/Allgemein/book", "books.yml");
	public static FileConfiguration SBConf = YamlConfiguration.loadConfiguration(SB);
	
	public static boolean onSignBook(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = null;
		
		if(sender instanceof Player){
			p = (Player) sender;
		}
		
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("help")){
				sender.sendMessage("/sb author <name>");
				sender.sendMessage("/sb title <name>");
				sender.sendMessage("/sb give <name>");
				sender.sendMessage("/sb reload");
			}else if(args[0].equalsIgnoreCase("reload")){
				try {
					SBConf.load(SB);
				} catch (FileNotFoundException e1) {
				} catch (IOException e1) {
				} catch (InvalidConfigurationException e1) {
				}
			}
		}else if(args.length == 2){
			
			if(args[0].equalsIgnoreCase("author")){
				if(p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK){
					BookMeta meta = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
				
					meta.setAuthor(args[1]);
				
					p.getInventory().getItemInMainHand().setItemMeta(meta);
				}
			}else if(args[0].equalsIgnoreCase("title")){
				if(p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK){
					BookMeta meta = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
				
					meta.setTitle(args[1]);
				
					p.getInventory().getItemInMainHand().setItemMeta(meta);
				}
			}else if(args[0].equalsIgnoreCase("give")){
					ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
					
					BookMeta meta = (BookMeta) book.getItemMeta();
					
					List<String> pages = new ArrayList<String>();
					
					String SBs = SBConf.getString(args[1]);
					for(String s : SBs.split("/n")){
						pages.add(s);
					}
					
					meta.setAuthor("Regeln");
			        meta.setTitle("Regeln");
			        meta.setDisplayName("Regeln");
					meta.setPages(pages);
					book.setItemMeta(meta);
					
					p.getInventory().addItem(book);
			}
		}
		
		if (args.length>1) {
		    return false;
		}else{
			return true;
		}
	}
	
	public static void Load(){
		try {
			SBConf.load(SB);
		} catch (FileNotFoundException e1) {
		} catch (IOException e1) {
		} catch (InvalidConfigurationException e1) {
		}
		
		SBConf.addDefault("Regel", "w");
		SBConf.options().copyDefaults(false);
		try {
			SBConf.save(SB);
		} catch (IOException e1) {
			
		}
	}
}
