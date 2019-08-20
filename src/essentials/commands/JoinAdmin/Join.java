package essentials.commands.JoinAdmin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class Join implements Listener{
	public static File J = new File("plugins/Allgemein/SlotsReserve", "config.yml");
	public static FileConfiguration JConf = YamlConfiguration.loadConfiguration(J);
	private static ArrayList<String> l = new ArrayList<String>();
	private static ArrayList<String> tempPlayer = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public static void load(){
		JConf.addDefault("full", 0);
		
		ArrayList<String> t = new ArrayList<String>();
		JConf.addDefault("JoinFull", "§4Der Server ist voll.");
		t.add("UUID eines Players ... UUID from a Player");
		t.add("UUID eines Players ... UUID from a Player");
		JConf.addDefault("JoinPlayers", t);
		JConf.options().copyDefaults(true);
		
		try {
			JConf.save(J);
		} catch (IOException e) {}
		
		l = (ArrayList<String>) JConf.getList("JoinPlayers");
	}
	
	@EventHandler
	private void J(PlayerJoinEvent e){
		Player player = e.getPlayer();
		
		if(player.isOp()) return;
		if(l.size() < JConf.getInt("full")) return;
		if(l.contains(e.getPlayer().getUniqueId().toString())) return;
		if(tempPlayer.contains(e.getPlayer().getName())) return;
		
		System.out.println("Spieler mit der UUID: " + e.getPlayer().getUniqueId() + " versuchte zu joinen!");
		e.getPlayer().kickPlayer(JConf.getString("JoinFull"));
		e.setJoinMessage(null);
		
		tempPlayer.remove(e.getPlayer().getName());
	}
	
	@EventHandler
	private void Slots(ServerListPingEvent e) {
		e.setMaxPlayers(JConf.getInt("full"));
	}
	
	@SuppressWarnings("deprecation")
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {	
		if(!sender.hasPermission("all.join")) return false;
		
		if(args.length == 2){
			if(args[1].equalsIgnoreCase("help")){
				sender.sendMessage("/all join list");
				sender.sendMessage("/all join clear");
				sender.sendMessage("/all join add <player>");
				sender.sendMessage("/all join remove <player>");
				sender.sendMessage("/all join full <Zahl>");
			}else if(args[1].equalsIgnoreCase("helpo")){
				sender.sendMessage("/all join listo");
				sender.sendMessage("/all join addo <player>");
				sender.sendMessage("/all join removeo <player>");
			}else if(args[1].equalsIgnoreCase("list")){
				if(!tempPlayer.isEmpty()) {
					String list = "";
					for(String s : tempPlayer){
						if(list == "")
							list = s;
						else
							list = list + ", " + s;
					}
				} else
					sender.sendMessage("§4Liste ist leer");
			}else if(args[1].equalsIgnoreCase("listo")){
				if(!l.isEmpty()){
					String list = "";
					for(String s : l){
						if(list == "")
							list = s;
						else
							list = list + ", " + s;
					}
					
					sender.sendMessage(list);
				}else{
					sender.sendMessage("§4Liste ist leer");
				}
			}else if(args[1].equalsIgnoreCase("clear")){
				if(!tempPlayer.isEmpty()){
					for(String s : tempPlayer){
						tempPlayer.remove(s);
					}
					
					sender.sendMessage("Alle tempPlayers wurden gel$scht!");
				}
			}
		}else if(args.length == 3){
			if(args[1].equalsIgnoreCase("add")){
				if(!tempPlayer.contains(args[2]))
					tempPlayer.add(args[2]);
				
				sender.sendMessage("Spieler: " + args[2] + " wurde hinzugef$gt.");
			}else if(args[1].equalsIgnoreCase("addo")){
				String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
				
				if(!l.contains(uuid)){
					l.add(uuid);
					
					JConf.set("JoinPlayers", l);
					
					try {
						JConf.save(J);
					} catch (IOException e) {}
				}
				
				sender.sendMessage("Spieler: " + args[2] + " wurde f$r immer hinzugef$gt.");
			}else if(args[1].equalsIgnoreCase("remove")){
				if(tempPlayer.contains(args[2]))
					tempPlayer.remove(args[2]);
				
				sender.sendMessage("Spieler: " + args[2] + " wurde gel$scht.");
			}else if(args[1].equalsIgnoreCase("removeo")){
				String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
				
				if(!l.contains(uuid)){
					l.remove(uuid);
					
					JConf.set("JoinPlayers", l);
					
					try {
						JConf.save(J);
					} catch (IOException e) {}
				}
				
				sender.sendMessage("Spieler: " + args[2] + " wurde f$r immer gel$scht.");
			}else if(args[1].equalsIgnoreCase("full")){
				JConf.set("full", Integer.parseInt(args[2]));
				
				try {
					JConf.save(J);
				} catch (IOException e) {}
				
				sender.sendMessage("Maximale Spieleranzahl wurde auf " + args[2] + " gesetzt.");
			}
		}
		
		return true;
	}
}
