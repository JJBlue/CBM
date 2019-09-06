package essentials.listeners;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class noHunger implements Listener{
	private static String config = "plugins/Allgemein";
	
	private static File Backupwarp = new File(config, "noHunger.yml");
	private static FileConfiguration BackupwarpConf = YamlConfiguration.loadConfiguration(Backupwarp);
	
	private static List<String> worlds = new ArrayList<String>();
	
	private static boolean enable;
	
	//TODO -> configFile to world.database
	public static void onLoad(){
		try {
			BackupwarpConf.load(Backupwarp);
		} catch (FileNotFoundException e1) {
		} catch (IOException e1) {
		} catch (InvalidConfigurationException e1) {}
		
		BackupwarpConf.addDefault("worlds", "Jump");
		BackupwarpConf.addDefault("enable", true);
		
		BackupwarpConf.options().copyDefaults(true);
		try {
			BackupwarpConf.save(Backupwarp);
		} catch (IOException e1) {
			
		}
		
		enable = BackupwarpConf.getBoolean("enable");
		
		String CVS = BackupwarpConf.getString("worlds");
		for(String s : CVS.split(","))
			worlds.add(s);
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e){
		if(enable == true && e.getEntity() instanceof Player){
			Player p =  (Player) e.getEntity();
		
			if(worlds.contains(p.getWorld().getName()))
				e.setCancelled(true);
		}
	}
}
