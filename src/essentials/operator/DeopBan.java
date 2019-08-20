package essentials.operator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class DeopBan implements Listener{
	
	public static File OP = new File("plugins/Allgemein", "config.yml");
	public static FileConfiguration OPConf = YamlConfiguration.loadConfiguration(OP);
	private static List<String> OPList = new ArrayList<String>();
	
	static Timer timer = new Timer();
	
    @EventHandler
    public void command(PlayerCommandPreprocessEvent e) {
    	Player player = e.getPlayer();
    	
    	if(player.isOp() && !OPList.contains(player.getName()))
    		player.setOp(false);
    }
    
	public static void opCon() {
		OPConf.addDefault("OP", "j_l_1998,Jannilol12");
			
		OPConf.options().copyDefaults(true);
			
		try {
			OPConf.save(OP);
		} catch (IOException e1) {}
		
		reload();
	}
    
	private static void reload() {
    	String OPs = OPConf.getString("OP");
    	for(String s : OPs.split(","))
    		OPList.add(s);
    }
    
    public static boolean onOpList(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
    	if(!cmd.getName().equalsIgnoreCase("oplist")) return false;
		sender.sendMessage(OPList + "");
    	
    	return true;
    }
}
