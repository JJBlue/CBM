package essentials.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import essentials.ChatVerbesserung.ChatVerbesserung;
import essentials.commands.armorstand.ArmorstandListener;
import essentials.commands.commandonobject.CommandListener;
import essentials.commands.commandonobject.CommandOnBlock;
import essentials.commands.commands.MainCommand;
import essentials.commands.commands.bookCommand;
import essentials.commands.commandspy.CommandSpyListener;
import essentials.commands.post.Post;
import essentials.commands.skull.Skullitem;
import essentials.commands.teleport.TeleportListener;
import essentials.commands.timerplus.Timerplus;
import essentials.commands.trade.TradeListener;
import essentials.commands.trolling.BlockClick;
import essentials.commands.trolling.TrolCommands;
import essentials.config.MainConfig;
import essentials.database.Databases;
import essentials.inventory.InventoryListener;
import essentials.listeners.ColorListener;
import essentials.listeners.CommandsEvents;
import essentials.listeners.noHunger;
import essentials.listeners.FlyThrowBlocks.FTB;
import essentials.listeners.MapPaint.LoadMapPaint;
import essentials.listeners.MapPaint.MPListener;
import essentials.listeners.chair.chair;
import essentials.listeners.debugstick.DebugStickListener;
import essentials.player.PlayerListener;
import essentials.player.PlayerManager;
import essentials.pluginmanager.DisableEnable;
import essentials.tablist.Tablist;

public class Main extends JavaPlugin implements Listener{

	private static JavaPlugin plugin;
	
	public void onEnable() {
		plugin = this;
		System.out.println("[All] wurde gestartet");
		
		MainConfig.reload();
		Databases.load();
		
		Bukkit.getPluginManager().registerEvents(new FTB(), this);
		Bukkit.getPluginManager().registerEvents(new Deop(), this);
		Bukkit.getPluginManager().registerEvents(new ColorListener(), this);
		Bukkit.getPluginManager().registerEvents(new CommandsEvents(), this);
		Bukkit.getPluginManager().registerEvents(new MPListener(), this);
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
		Bukkit.getPluginManager().registerEvents(new ArmorstandListener(), this);
		Bukkit.getPluginManager().registerEvents(new CommandSpyListener(), this);
		Bukkit.getPluginManager().registerEvents(new DebugStickListener(), this);
		
		{
			MainCommand mainCommand = new MainCommand();
			this.getCommand("all").setExecutor(mainCommand);
			this.getCommand("all").setTabCompleter(mainCommand);
		}
		
		{ //TODO move under all command
			this.getCommand("post").setExecutor(Post.post);
			this.getCommand("post").setTabCompleter(Post.post);
		}
		
		{ //TODO move under all command
			this.getCommand("trol").setExecutor(TrolCommands.commands);
			this.getCommand("trol").setTabCompleter(TrolCommands.commands);
		}
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		CommandOnBlock.load();
		LoadMapPaint.load();
		DisableEnable.disableEnable.nothing(); //Lade Klasse, damit wenn .jar uberschrieben. Die load/unload Methoden funktionieren
		Timerplus.TimerSekunden();
		reload();
	}
	
	@Override
	public void onDisable() {
		//Used Runnable, because when one crashed the other could work
		unloadHelper(() -> { Tablist.unload(); });
		unloadHelper(() -> { CommandOnBlock.unload(); });
		unloadHelper(() -> { PlayerManager.unload(); });
		
		unloadHelper(() -> { Databases.unload(); });
		
		super.onDisable();
	}
	
	private void unloadHelper(Runnable runnable) {
		try {
			runnable.run();
		} catch (NoClassDefFoundError e) {
			System.out.println(Main.getPlugin().getName() + ": NoClassDefFoundError by unloading (did you overwrite .jar file)?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(sender.hasPermission("all.all")) {
			if (cmd.getName().equalsIgnoreCase("timer"))
				Timerplus.onTimerCommand(sender, cmd, cmdLabel, args);
			else if(cmd.getName().equalsIgnoreCase("cv"))
				ChatVerbesserung.onCommand(sender, cmd, cmdLabel, args);
		}
		
		return false;
	}
	
	public static void reload() {
		Tablist.load();
		LoadMapPaint.load();
		ChatVerbesserung.Load();
		bookCommand.saveDefaultBook();
		Post.Load();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}
}
