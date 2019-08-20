package essentials.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class mail {
	private static String suffix = "[Mail]";
	
	public static void load(Player p){
		File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		
		fileConf.addDefault("toogle", 1);
		fileConf.addDefault("show", 1);
		fileConf.options().copyDefaults(true);
		try {
			fileConf.save(file);
		} catch (IOException e) {
		}
		
		if(fileConf.getInt("show") == 1 && fileConf.getInt("toggle") == 1){
			p.sendMessage(read(p));
		}
	}
	
	private static String read(Player p){
		File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
		FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
		
		int i = 0;
		if(fileConf.getList("mail") != null){
			for(@SuppressWarnings("unused") Object s : fileConf.getList("mail")){
				i++;
			}
		}
		
		return suffix + " Du hast " + i + " Nachrichten";
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("mail")){
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("help")){
					help(sender);
				}else if(args[0].equalsIgnoreCase("read")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						
						p.sendMessage(read(p));
					}else{
						sender.sendMessage("Nur fuer Spieler!");
					}
				}else if(args[0].equalsIgnoreCase("clearall")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						
						File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
						FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
						
						ArrayList<String> l = new ArrayList<String>();
						
						fileConf.set("mail", l);
						
						try {
							fileConf.save(file);
							sender.sendMessage(suffix + " Alle Nachrichten wurden gel$scht");
						} catch (IOException e) {
							sender.sendMessage(suffix + " §4Error");
						}
					}else{
						sender.sendMessage("Nur fuer Spieler!");
					}
				}else if(args[0].equalsIgnoreCase("toggle")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						
						File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
						FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
						
						int t = 0;
						
						if(fileConf.getInt("toggle") == 1){
							fileConf.set("toggle", 0);
						}else{
							fileConf.set("toggle", 1);
							t = 1;
						}
						
						try {
							fileConf.save(file);
							if(t == 1){
								sender.sendMessage(suffix + " Du erh$hlst nun Nachrichten");
							}else{
								sender.sendMessage(suffix + " Du erh$hlst keine Nachrichten mehr");
							}
						} catch (IOException e) {
							sender.sendMessage(suffix + " §4Error");
						}
					}else{
						sender.sendMessage("Nur fuer Spieler!");
					}
				}else if(args[0].equalsIgnoreCase("show")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						
						File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
						FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
						
						int s = 0;
						
						if(fileConf.getInt("show") == 1){
							fileConf.set("show", 0);
						}else{
							fileConf.set("show", 1);
							s = 1;
						}
						
						try {
							fileConf.save(file);
							if(s == 1){
								sender.sendMessage(suffix + " Die Nachrichten werden beim joinen angezeigt");
							}else{
								sender.sendMessage(suffix + " Die Nachrichten werden nicht beim joinen angezeigt");
							}
						} catch (IOException e) {
							sender.sendMessage(suffix + " §4Error");
						}
					}else{
						sender.sendMessage("Nur fuer Spieler!");
					}
				}
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("read")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						
						File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
						FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
						
						ArrayList<String> l = new ArrayList<String>();
						int i = 0;
						
						if(fileConf.getList("mail") != null){
							for(String s : (ArrayList<String>) fileConf.getList("mail")){
								i++;
								l.add(s);
							}
						}
						
						if(i == 0){
							sender.sendMessage(suffix + " §4Du hast keine Nachrichten");
						}else if(Integer.parseInt(args[1]) <= 0){
							sender.sendMessage(suffix + " §4Diese Nummer ist zu klein");
						}else if(Integer.parseInt(args[1]) <= i){
							sender.sendMessage(l.get(Integer.parseInt(args[1]) - 1));
						}else{
							sender.sendMessage(suffix + " §4Diese Nummer ist zu gro$");
						}
					}else{
						sender.sendMessage("Nur fuer Spieler!");
					}
				}else if(args[0].equalsIgnoreCase("remove")){
					if(sender instanceof Player){
						Player p = (Player) sender;
						
						File file = new File("plugins/Allgemein/Mails", p.getUniqueId().toString() + ".yml");
						FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
						
						int i = 0;
						ArrayList<String> l = new ArrayList<String>();
						if(fileConf.getList("mail") != null){
							for(String s : (ArrayList<String>) fileConf.getList("mail")){
								l.add(s);
								i++;
							}
						}
						
						if(Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1]) <= i){
							l.remove(l.get(Integer.parseInt(args[1]) - 1));
							
							fileConf.set("mail", l);
							
							try {
								fileConf.save(file);
								sender.sendMessage(suffix + " Nachricht wurde gel$scht");
							} catch (IOException e) {
								sender.sendMessage(suffix + " §4Error");
							}
						}else{
							sender.sendMessage("Diese Zahl ist ung$ldig");
						}
					}else{
						sender.sendMessage("Nur fuer Spieler!");
					}
				}
			}else if(args.length >= 3){
				if(args[0].equalsIgnoreCase("send")){
					OfflinePlayer p2 = Bukkit.getOfflinePlayer(args[1]);
					
					File file = new File("plugins/Allgemein/Mails", p2.getUniqueId().toString() + ".yml");
					FileConfiguration fileConf = YamlConfiguration.loadConfiguration(file);
					
					if(fileConf.getInt("toggle") == 1){
						int i = args.length - 2;
						String name = sender.getName();
						String mail = "[" + name + "]";
						
						for(int y = 1; y <= i; y++){
							if(mail == null){
								mail = args[y];
							}else{
								mail = mail + " " + args[y];
							}
						}
						
						ArrayList<String> l = new ArrayList<String>();
						if(fileConf.getList("mail") != null){
							for(String s : (ArrayList<String>) fileConf.getList("mail")){
								l.add(s);
							}
						}
						
						l.add(mail);
						
						fileConf.set("mail", l);
						
						try {
							fileConf.save(file);
							sender.sendMessage(suffix + " Nachricht wurde versendet");
						} catch (IOException e) {
							sender.sendMessage(suffix + " §4Error");
						}
					}else{
						sender.sendMessage(suffix + " §4Der Spieler hat Nachrichten ausgeschaltet");
					}
				}
			}else{
				help(sender);
			}
		}
		
		if (args.length>1) {
		    return true;
		}else{
			return false;
		}
	}
	
	private static void help(CommandSender sender){
		sender.sendMessage("/mail send <Player> <Nachricht>");
		sender.sendMessage("/mail read");
		sender.sendMessage("/mail read <Nachrichtszahl>");
		sender.sendMessage("/mail remove <Nachrichtszahl>");
		sender.sendMessage("/mail clearall");
		sender.sendMessage("/mail toggle");
		sender.sendMessage("/mail show");
	}
}
