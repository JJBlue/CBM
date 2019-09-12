package essentials.main;

import essentials.commands.commands.MainCommand;
import essentials.commands.commands.bookCommand;
import essentials.commands.post.Post;
import essentials.commands.trolling.BlockClick;
import essentials.commands.trolling.TrolCommands;
import essentials.config.MainConfig;
import essentials.database.Databases;
import essentials.language.LanguageConfig;
import essentials.modules.ChatVerbesserung.ChatVerbesserung;
import essentials.modules.ColorListener;
import essentials.modules.Deop;
import essentials.modules.FlyThrowBlocks.FTB;
import essentials.modules.Join;
import essentials.modules.MainListener;
import essentials.modules.MapPaint.LoadMapPaint;
import essentials.modules.MapPaint.MPListener;
import essentials.modules.alias.CustomAlias;
import essentials.modules.armorstandeditor.ArmorstandListener;
import essentials.modules.chair.chair;
import essentials.modules.commandonobject.CommandListener;
import essentials.modules.commandonobject.CommandOnBlock;
import essentials.modules.commandspy.CommandSpyListener;
import essentials.modules.debugstick.DebugStickListener;
import essentials.modules.pluginmanager.DisableEnable;
import essentials.modules.skull.SkullInventory;
import essentials.modules.tablist.Tablist;
import essentials.modules.teleport.TeleportListener;
import essentials.modules.timer.TimerConfig;
import essentials.modules.timer.TimerListener;
import essentials.modules.trade.TradeListener;
import essentials.modules.updater.UpdaterConfig;
import essentials.modules.updater.UpdaterServerManager;
import essentials.modules.warpmanager.WarpManager;
import essentials.modules.world.WorldConfig;
import essentials.player.PlayerListener;
import essentials.player.PlayerManager;
import essentials.utilities.inventory.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.LocalDateTime;

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
		Bukkit.getPluginManager().registerEvents(new Post(), this);
		Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
		Bukkit.getPluginManager().registerEvents(new TrolCommands(), this);
		Bukkit.getPluginManager().registerEvents(new BlockClick(), this);
		Bukkit.getPluginManager().registerEvents(new ChatVerbesserung(), this);
		Bukkit.getPluginManager().registerEvents(new SkullInventory(), this);
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
		unloadHelper(WarpManager::unload);
		unloadHelper(Tablist::unload);
		unloadHelper(CommandOnBlock::unload);
		unloadHelper(PlayerManager::unload);
		unloadHelper(WorldConfig::unload);
		
		unloadHelper(() -> {
			if(UpdaterConfig.isInstallOnShutdown())
				UpdaterServerManager.install();
			
			UpdaterServerManager.unload();
		});
		
		unloadHelper(Databases::unload);
		
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
		CustomAlias.load();
		UpdaterServerManager.load();
		WorldConfig.load();
		LanguageConfig.load();
		Tablist.load();
		LoadMapPaint.load();
//		ChatVerbesserung.Load();
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
