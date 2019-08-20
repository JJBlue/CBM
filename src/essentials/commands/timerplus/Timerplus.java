package essentials.commands.timerplus;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Timerplus implements Listener{
	
	private static int pupil;
	private static int sek;
	private static CommandSender Sender;
	private static String updown;
	private static int startstop = 0;
	
	static Timer timer = new Timer();
	
	public static boolean onTimerCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("timer")){
			//Player p = (Player)sender;
			
			if(args.length >= 1){
				if(args[0].compareTo("help") == 0){
					sender.sendMessage("/timer <stop/sekunden/§4$mminuten$r> <zahl> <all/me> <up/down>");
				}
				
				if(args.length == 4){
					if(args[0].compareTo("stop") == 0){
						startstop = 0;
						sender.sendMessage("§4Timer wurde gestoppt");
					}else if(args[0].compareTo("sekunden") == 0){
						if(args[2].compareTo("all") == 0){
							pupil = 2;
							
							if(args[3].compareTo("down") == 0){
								sek = Integer.parseInt(args[1]);
								updown = "down";
								sender.sendMessage("§4Coundown started");
								sender.sendMessage("§4" + sek);
								startstop = 1;
							}else if(args[3].compareTo("up") == 0){
								sek = 0;
								updown = "up";
								sender.sendMessage("§4Timer started");
								startstop = 1;
							}
							
						}else if(args[2].compareTo("me") == 0){
							pupil = 1;
							Sender = sender;
							if(args[3].compareTo("down") == 0){
								sek = Integer.parseInt(args[1]);
								updown = "down";
								sender.sendMessage("§4Coundown started");
								sender.sendMessage("§4" + sek);
								startstop = 1;
							}else if(args[3].compareTo("up") == 0){
								sek = 0;
								updown = "up";
								sender.sendMessage("§4Timer started");
								startstop = 1;
							}
						}
					}
				}else{
					sender.sendMessage("/timer <stop/sekunden/§4$mminuten$r> <zahl> <all/me> <up/down>");
				}
			}else{
				sender.sendMessage("/timer <stop/sekunden/§4$mminuten$r> <zahl> <all/me> <up/down>");
			}
		}
		
		if (args.length>1) {
		    return true;
		}else{
			return false;
		}
	}
	
	public static void TimerSekunden(){
		timer.schedule(new TimerTask() {
			@Override
			  public void run() {
				 if(startstop != 0){
					 if(pupil == 2){
						if(updown == "up"){
							for(Player players : Bukkit.getOnlinePlayers()){
								sek += 1;
								players.sendMessage("§4" + sek);
							}
						}else if(updown == "down"){
							if(sek != 0){
								sek -= 1;
								for(Player players : Bukkit.getOnlinePlayers()){
									players.sendMessage("§4" + sek);
								}
							}else{
								startstop = 0;
							}
						}
			
					 }else if(pupil == 1){
				
						if(updown == "up"){
							sek += 1;
							Sender.sendMessage("§4" + sek);
						}else if(updown == "down"){
							if(sek != 0){
								sek -= 1;
								Sender.sendMessage("§4" + sek);
							}else{
								startstop = 0;
							}
						}
					}
				 }
			  }
		}, 1*1000, 1*1000);
	}
}
