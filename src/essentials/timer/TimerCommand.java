package essentials.timer;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import essentials.language.LanguageConfig;
import essentials.utilities.StringUtilities;

public class TimerCommand implements CommandExecutor, TabCompleter {

	public final static TimerCommand timerCommand;
	
	static {
		timerCommand = new TimerCommand();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 1) return true;
		
		switch (args[0].toLowerCase()) {
			case "bossbar":
				
				// /cbm timer bossbar down 10 BLUE false Stop Serve in ...
				
				try {
					BukkitTimer bukkitTimer = new BukkitTimer(TimerPosition.BOSSBAR);
					
					if(args[1].toLowerCase().equalsIgnoreCase("up"))
						bukkitTimer.setCountUp();
					else
						bukkitTimer.setCountDown();
					
					bukkitTimer.setMaxValue(Integer.parseInt(args[2]));
					bukkitTimer.setColor(BarColor.valueOf(args[3]));
					bukkitTimer.setRepeat(Boolean.valueOf(args[4]));
					bukkitTimer.setTitle(StringUtilities.arrayToStringRange(args, 5, args.length));
					
					bukkitTimer.start();
					
					LanguageConfig.sendMessage(sender, "timer.startTimer", bukkitTimer.getID() + "");
				} catch (IllegalArgumentException e) {}
				
				break;
				
			case "chat":
				
				try {
					BukkitTimer bukkitTimer = new BukkitTimer(TimerPosition.CHAT);
					
					if(args[1].toLowerCase().equalsIgnoreCase("up"))
						bukkitTimer.setCountUp();
					else
						bukkitTimer.setCountDown();
					
					bukkitTimer.setMaxValue(Integer.parseInt(args[2]));
					bukkitTimer.setRepeat(Boolean.valueOf(args[4]));
					bukkitTimer.setTitle(StringUtilities.arrayToStringRange(args, 5, args.length));
					
					bukkitTimer.start();
				} catch (IllegalArgumentException e) {}
				
				break;
				
			case "run":
				
				//TODO config
				break;
				
			case "stop":
				
				try {
					TimerManager.stopTimer(Integer.parseInt(args[1]));
					LanguageConfig.sendMessage(sender, "timer.cancelTimer", args[1]);
				} catch (NumberFormatException e) {}
				
				break;
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("bossbar");
			returnArguments.add("chat");
			returnArguments.add("run");
			returnArguments.add("stop");
			
		} else {
			switch (args[0]) {
				case "bossbar":
					
					if(args.length == 2) {
						returnArguments.add("up");
						returnArguments.add("down");
					} else if(args.length == 3) {
						returnArguments.add(args[2] + "0");
						returnArguments.add(args[2] + "1");
						returnArguments.add(args[2] + "2");
						returnArguments.add(args[2] + "3");
						returnArguments.add(args[2] + "4");
						returnArguments.add(args[2] + "5");
						returnArguments.add(args[2] + "6");
						returnArguments.add(args[2] + "7");
						returnArguments.add(args[2] + "8");
						returnArguments.add(args[2] + "9");
					} else if(args.length == 4) {
						for(BarColor barColor : BarColor.values())
							returnArguments.add(barColor.name());
					} else if(args.length == 5) {
						returnArguments.add("true");
						returnArguments.add("false");
					} else if(args.length == 6)
						returnArguments.add("<Title>");
					
					break;
				case "stop":
					
					returnArguments.add(args[1] + "0");
					returnArguments.add(args[1] + "1");
					returnArguments.add(args[1] + "2");
					returnArguments.add(args[1] + "3");
					returnArguments.add(args[1] + "4");
					returnArguments.add(args[1] + "5");
					returnArguments.add(args[1] + "6");
					returnArguments.add(args[1] + "7");
					returnArguments.add(args[1] + "8");
					returnArguments.add(args[1] + "9");
					
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
