package essentials.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import essentials.ChatVerbesserung.ChatVerbesserung;
import essentials.commands.JoinAdmin.Join;
import essentials.commands.commandonobject.CommandDruckplatten;
import essentials.commands.commandonobject.CommandListener;
import essentials.commands.commands.MainCommand;
import essentials.commands.commands.SignBook;
import essentials.commands.post.Post;
import essentials.commands.skull.Skullitem;
import essentials.commands.teleport.TeleportListener;
import essentials.commands.timerplus.Timerplus;
import essentials.commands.trolling.BlockClick;
import essentials.commands.trolling.TrolCommands;
import essentials.inventory.InventoryListener;
import essentials.listeners.ColorListener;
import essentials.listeners.CommandsEvents;
import essentials.listeners.noHunger;
import essentials.listeners.FlyThrowBlocks.FTB;
import essentials.listeners.MapPaint.PlayerMapListener;
import essentials.listeners.chair.chair;
import essentials.operator.DeopBan;
import essentials.player.PlayerListener;
import essentials.trade.TradeListener;

public class Main extends JavaPlugin implements Listener{

	private static JavaPlugin plugin;
	
	public void onEnable() {
		plugin = this;
		System.out.println("[All] wurde gestartet");
		
		Bukkit.getPluginManager().registerEvents(new FTB(), this);
		Bukkit.getPluginManager().registerEvents(new DeopBan(), this);
		Bukkit.getPluginManager().registerEvents(new ColorListener(), this);
		Bukkit.getPluginManager().registerEvents(new CommandsEvents(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMapListener(), this);
		Bukkit.getPluginManager().registerEvents(new chair(), this);
		Bukkit.getPluginManager().registerEvents(new Join(), this);
		Bukkit.getPluginManager().registerEvents(new noHunger(), this);
		Bukkit.getPluginManager().registerEvents(new Post(), this);
		Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
		Bukkit.getPluginManager().registerEvents(new TrolCommands(), this);
		Bukkit.getPluginManager().registerEvents(new BlockClick(), this);
		Bukkit.getPluginManager().registerEvents(new ChatVerbesserung(), this);
		Bukkit.getPluginManager().registerEvents(new Skullitem(), this);
		Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new TradeListener(), this);
		
		{
			MainCommand mainCommand = new MainCommand();
			this.getCommand("all").setExecutor(mainCommand);
			this.getCommand("all").setTabCompleter(mainCommand);
		}
		
		{
			this.getCommand("cos").setExecutor(CommandDruckplatten.commandDruckplatten);
			this.getCommand("cos").setTabCompleter(CommandDruckplatten.commandDruckplatten);
		}
		
		{
			this.getCommand("post").setExecutor(Post.post);
			this.getCommand("post").setTabCompleter(Post.post);
		}
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		Timerplus.TimerSekunden();
		Loading();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(sender.hasPermission("all.all")) {
			if (cmd.getName().equalsIgnoreCase("trol"))
				essentials.commands.trolling.TrolCommands.commands.onCommand(sender, cmd, cmdLabel, args);
			else if (cmd.getName().equalsIgnoreCase("timer"))
				Timerplus.onTimerCommand(sender, cmd, cmdLabel, args);
			else if(cmd.getName().equalsIgnoreCase("cv"))
				ChatVerbesserung.onCommand(sender, cmd, cmdLabel, args);
			else if(cmd.getName().equalsIgnoreCase("oplist"))
				DeopBan.onOpList(sender, cmd, cmdLabel, args);
	    	else if(cmd.getName().equalsIgnoreCase("sb"))
				SignBook.onSignBook(sender, cmd, cmdLabel, args);
		}
		
		return false;
	}
	
	public static void Loading() {
		CommandDruckplatten.load();
		ChatVerbesserung.Load();
		DeopBan.opCon();
		SignBook.Load();
//		MoveEvents.Load();
		Post.Load();
//		Navigator.onLoad();
		noHunger.onLoad();
		Join.load();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}