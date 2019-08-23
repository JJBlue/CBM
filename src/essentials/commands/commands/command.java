package essentials.commands.commands;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//TODO old
public class command {
	@SuppressWarnings("deprecation")
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ptime")){
			if(args.length == 3){
				Player p1 = Bukkit.getPlayer(args[0]);
				if(p1 == null) return true;
				double i = Double.parseDouble(args[2]);
				double y = p1.getPlayerTime();
						
				if(args[1].equalsIgnoreCase("add"))
					p1.setPlayerTime((long) (i + y), true);
				else if(args[1].equalsIgnoreCase("remove"))
					p1.setPlayerTime((long) (y - 1), true);
				else if(args[1].equalsIgnoreCase("set"))
					p1.setPlayerTime((long) i, true);
			}
		}else if(cmd.getName().equalsIgnoreCase("seen")){
			OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(args[0]);
//			FileConfiguration fileConf = essentials.player.player.playerConf(oPlayer.getPlayer());
			
			if(oPlayer.isOnline()){
//				String s = fileConf.getString("lastlogin");
//				sender.sendMessage("Player ist Zeit " + s + " online." );
			} else {
//				String s = fileConf.getString("lastlogout");
//				sender.sendMessage("Player ist Zeit " + s + " offline." );
			}
		}
		
		return true;
	}
	
	
}
