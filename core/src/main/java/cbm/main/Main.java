package cbm.main;

import java.time.LocalDateTime;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import cbm.config.MainConfig;
import cbm.database.Databases;
import cbm.language.LanguageConfig;
import cbm.modulemanager.ModuleManager;
import cbm.modules.ColorListener;
import cbm.modules.JoinListener;
import cbm.modules.MainListener;
import cbm.modules.chair.chair;
import cbm.modules.claim.ClaimConfig;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.MainCommand;
import cbm.modules.commands.commands.BookCommand;
import cbm.modules.move.AFK;
import cbm.modules.move.MoveManager;
import cbm.modules.skull.SkullInventory;
import cbm.modules.teleport.TeleportListener;
import cbm.modules.visible.VisibleManager;
import cbm.modules.world.WorldConfig;
import cbm.player.PlayerListener;
import cbm.player.PlayerManager;
import cbm.player.PlayersYMLConfig;
import cbm.utilities.inventory.InventoryListener;
import cbm.versions.VersionDependency;

public class Main extends JavaPlugin {

	private static JavaPlugin plugin;
	private static LocalDateTime online;

	@Override
	public void onEnable() {
		getLogger().info("is starting");
		plugin = this;
		online = LocalDateTime.now();
		
		VersionDependency.init();

		getLogger().info("loading important configs");
		MainConfig.reload();
		LanguageConfig.load();
		Databases.load();
		
		getLogger().info("load important Commands & Modules");
		CommandManager.load();
		ModuleManager.load();

		getLogger().info("loading Listeners");
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new MoveManager(), this);
		Bukkit.getPluginManager().registerEvents(new ColorListener(), this);
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new chair(), this);
		Bukkit.getPluginManager().registerEvents(new SkullInventory(), this);
		Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
		Bukkit.getPluginManager().registerEvents(new AFK(), this);
		Bukkit.getPluginManager().registerEvents(new VisibleManager(), this);

		{
			MainCommand mainCommand = new MainCommand();
			this.getCommand("cbm").setExecutor(mainCommand);
			this.getCommand("cbm").setTabCompleter(mainCommand);
		}

		getLogger().info("[CBM] loading configs");
		BookCommand.saveDefaultBook();

		for (Player player : Bukkit.getOnlinePlayers())
			PlayerListener.join(player);

//		if (!MainConfig.isFirstTime() && MainConfig.useBStats())
//			bStats.enableBStats();

		reload();
		getLogger().info("[CBM] start complete");
	}

	@Override
	public void onDisable() {
		//Used Runnable, because when one crashed the other could work
		unloadHelper(ModuleManager::unload);
		unloadHelper(PlayerManager::unload);
		unloadHelper(WorldConfig::unload);
		unloadHelper(PlayersYMLConfig::unload);
		unloadHelper(Databases::unload);
		super.onDisable();
	}

	public static void unloadHelper(Runnable runnable) {
		try {
			runnable.run();
		} catch (NoClassDefFoundError e) {
			System.out.print(Main.getPlugin().getName() + ": NoClassDefFoundError by unloading (did you overwrite .jar file)?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reload() {
		LanguageConfig.load();
		WorldConfig.load();
		PlayersYMLConfig.load();
		MainCommand.load();
		
		ClaimConfig.load();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static LocalDateTime getOnline() {
		return LocalDateTime.from(online);
	}
}
