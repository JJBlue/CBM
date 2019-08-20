package essentials.ChatVerbesserung;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import essentials.main.Main;

public class ChatVerbesserung implements Listener{
	
	public static File CV = new File("plugins/Allgemein", "ChatCleaner.yml");
	public static FileConfiguration CVConf = YamlConfiguration.loadConfiguration(CV);
	private static List<String> Verboten = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public static void Load(){
		CVConf.addDefault("aktiv", true);
		
		ArrayList<String> l = new ArrayList<String>();
		l.add("Scheisse");
		l.add("Depp");
		l.add("Pfotze");
		l.add("Kacke");
		l.add("Arschloch");
		l.add("Arsch");
		l.add("Vollpfosten");
		l.add("Penner");
		l.add("Schwuchtel");
		l.add("Opfer");
		l.add("Bettnaesser");
		l.add("Bettbrunza");
		l.add("Weichei");
		l.add("Hosenscheisser");
		l.add("Schisshase");
		l.add("Fehlgeburt");
		l.add("Fettsack");
		l.add("Hurhensohn");
		l.add("Idiot");
		l.add("Pisser");
		l.add("Schlappschwanz");
		l.add("Fresse");
		l.add("verpiss");
		l.add("wixer");
		l.add("huren");
		l.add("scheiss");
		
		l.add("juli");
		l.add("j u l i");
		l.add("j u li");
		l.add("ju li");
		l.add("j uli");
		l.add("jul i");
		l.add("j ul i");
		CVConf.addDefault("Verboten", l);
		CVConf.options().copyDefaults(true);
		try {
			CVConf.save(CV);
		} catch (IOException e1) {
			
		}
		
		if(CVConf.getList("Verboten") != null){
			Verboten = (List<String>) CVConf.getList("Verboten");
		}
	}
	
	@EventHandler
	public void ChatV(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		String text = e.getMessage();

		if(p.hasPermission("ChatV.use")){
			if(text.startsWith("-7")) {
				e.setMessage(text.replaceFirst("-", ""));
			} else if(text.startsWith("7")) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
					p.performCommand(text.replaceFirst("7", ""));
				});
				
				p.sendMessage("§4Text wurde in Befehl umgewandelt.");
				p.sendMessage("§4Sollte dies nicht passieren, so geben sie davor '-' ein");
				e.setCancelled(true);
				return;
			}
		}
		
		if(CVConf.getBoolean("aktiv")) {
			String Stern = "*****";
			String textsend = text;
			String oldtext = text;
			
			for(String s : Verboten)
				textsend = textsend.replaceAll("(?i)" + s, Stern);
			
			if(oldtext != textsend) {
				e.setMessage(textsend);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
					p.damage(1.0);
				});
			}
		}
	}
	
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {	
		if(cmd.getName().equalsIgnoreCase("cv")){
			if(args.length == 1){
				if (args[0].equalsIgnoreCase("help")) {
					help(sender);
				} else if (args[0].equalsIgnoreCase("list")){
					for(String s : Verboten)
						sender.sendMessage(s);
				}
			}else if(args.length >= 2){
				if (args[0].compareTo("add") == 0){
					int anzahl = args.length;
					String argsstring = "";
					
					for(int y = 2; y <= anzahl; y++){
						int y2 = y - 1;
						if(y == 2)
							argsstring = args[y2];
						else
							argsstring = argsstring + " " + args[y2];
					}
					
					Verboten.add(argsstring);
					
					CVConf.set("Verboten", Verboten);
					
					sender.sendMessage("Das Wort " + argsstring + " added.");
				
					try {
						CVConf.save(CV);
					} catch (IOException e1) {	}
					
					Load();
				}else if (args[0].compareTo("remove") == 0){
					int anzahl = args.length;
					String argsstring = "";
					
					for(int y = 2; y <= anzahl; y++){
						int y2 = y - 1;
						if(y == 2)
							argsstring = args[y2];
						else
							argsstring = argsstring + " " + args[y2];
					}
					
					if(Verboten.contains(argsstring)) {
						Verboten.remove(argsstring);
						
						CVConf.set("Verboten", Verboten);
						
						sender.sendMessage("The word " + argsstring + " removed.");
					
						try {
							CVConf.save(CV);
						} catch (IOException e1) {}
						Load();
					} else
						sender.sendMessage("The word " + argsstring + " doesn't find.");
				}
			} else
				help(sender);
			
			return true;
		}
		
		return false;
	}
	
	private static void help(CommandSender sender){
		sender.sendMessage("/cv list");
		sender.sendMessage("/cv add <text>");
		sender.sendMessage("/cv remove <text>");
	}
}
