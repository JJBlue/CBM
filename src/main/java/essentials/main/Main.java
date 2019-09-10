package essentials.main;

import java.io.File;
import java.time.LocalDateTime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import essentials.ChatVerbesserung.ChatVerbesserung;
import essentials.alias.CustomAlias;
import essentials.commands.armorstand.ArmorstandListener;
import essentials.commands.commandonobject.CommandListener;
import essentials.commands.commandonobject.CommandOnBlock;
import essentials.commands.commands.MainCommand;
import essentials.commands.commands.bookCommand;
import essentials.commands.commandspy.CommandSpyListener;
import essentials.commands.post.Post;
import essentials.commands.skull.Skullitem;
import essentials.commands.teleport.TeleportListener;
import essentials.commands.trade.TradeListener;
import essentials.commands.trolling.BlockClick;
import essentials.commands.trolling.TrolCommands;
import essentials.config.MainConfig;
import essentials.database.Databases;
import essentials.inventory.InventoryListener;
import essentials.language.LanguageConfig;
import essentials.listeners.ColorListener;
import essentials.listeners.MainListener;
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
import essentials.timer.TimerConfig;
import essentials.timer.TimerListener;
import essentials.updater.UpdaterConfig;
import essentials.updater.UpdaterServerManager;
import essentials.warpmanager.WarpManager;

public class Main extends JavaPlugin implements Listener{

	private static JavaPlugin plugin;
	private static LocalDateTime online;
	
	public void onEnable() {
		plugin = this;
		online = LocalDateTime.now();
		System.out.println("[CBM] wurde gestartet");
		
		File file = new File(getDataFolder().getParentFile(), "Allgemein");
		file.renameTo(getDataFolder());
		
		MainConfig.reload();
		LanguageConfig.load();
		Databases.load();
		
		Bukkit.getPluginManager().registerEvents(new FTB(), this);
		Bukkit.getPluginManager().registerEvents(new Deop(), this);
		Bukkit.getPluginManager().registerEvents(new ColorListener(), this);
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
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
		Bukkit.getPluginManager().registerEvents(new TimerListener(), this);
		
		{
			MainCommand mainCommand = new MainCommand();
			this.getCommand("cbm").setExecutor(mainCommand);
			this.getCommand("cbm").setTabCompleter(mainCommand);
		}
		
		{ //TODO move under cbm command
			this.getCommand("post").setExecutor(Post.post);
			this.getCommand("post").setTabCompleter(Post.post);
		}
		
		{ //TODO move under cbm command
			this.getCommand("trol").setExecutor(TrolCommands.commands);
			this.getCommand("trol").setTabCompleter(TrolCommands.commands);
		}
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		WarpManager.load();
		bookCommand.saveDefaultBook();
		CommandOnBlock.load();
		LoadMapPaint.load();
		DisableEnable.disableEnable.nothing(); //Lade Klasse, damit wenn .jar uberschrieben. Die load/unload Methoden funktionieren
		
		for(Player player : Bukkit.getOnlinePlayers())
			PlayerListener.join(player);
		
		if(!MainConfig.isFirstTime() && MainConfig.useBStats())
			bStats.enableBStats();
		
		reload();
	}
	
	@Override
	public void onDisable() {
		//Used Runnable, because when one crashed the other could work
		unloadHelper(() -> WarpManager.unload());
		unloadHelper(() -> Tablist.unload());
		unloadHelper(() -> CommandOnBlock.unload());
		unloadHelper(() -> PlayerManager.unload());
		
		unloadHelper(() -> {
			if(UpdaterConfig.isInstallOnShutdown())
				UpdaterServerManager.install();
			
			UpdaterServerManager.unload();
		});
		
		unloadHelper(() -> Databases.unload());
		
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
		if(sender.hasPermission("cv") && cmd.getName().equalsIgnoreCase("cv"))
			ChatVerbesserung.onCommand(sender, cmd, cmdLabel, args);
		
		return false;
	}
	
	public static void reload() {
		UpdaterServerManager.load();
		CustomAlias.load();
		LanguageConfig.load();
		Tablist.load();
		LoadMapPaint.load();
		ChatVerbesserung.Load();
		Post.Load();
		TimerConfig.load();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static LocalDateTime getOnline() {
		return LocalDateTime.from(online);
	}
}
