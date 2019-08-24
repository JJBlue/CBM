package essentials.main;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import essentials.config.MainConfig;
import essentials.utilities.PlayerUtilities;

public class DeopBan implements Listener{
    @EventHandler
    public void command(PlayerCommandPreprocessEvent e) {
    	Player player = e.getPlayer();
    	
    	if(player.isOp() && !MainConfig.getOperators().contains(player.getUniqueId().toString()))
    		player.setOp(false);
    }
    
    public static boolean onOpList(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
    	if(!cmd.getName().equalsIgnoreCase("oplist")) return false;
    	
    	List<String> list = MainConfig.getOperators();
    	StringBuilder builder = new StringBuilder();
    	builder.append("###### Operators ######\n");
    	boolean start = true;
    	
    	for(String uuid : list) {
    		if(!start)
    			builder.append(", ");
    		else
    			start = false;
    		
    		OfflinePlayer player = PlayerUtilities.getOfflinePlayerFromUUID(uuid);
    		if(player != null)
    			builder.append(player.getName());
    		else
    			builder.append("§4" + uuid);
    	}
    	
    	sender.sendMessage(builder.toString());
    	
    	return true;
    }
}
