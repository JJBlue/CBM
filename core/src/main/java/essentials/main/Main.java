package essentials.main;

import java.time.LocalDateTime;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import essentials.config.MainConfig;
import essentials.database.Databases;
import essentials.language.LanguageConfig;
import essentials.modulemanager.ModuleManager;
import essentials.modules.ColorListener;
import essentials.modules.JoinListener;
import essentials.modules.MainListener;
import essentials.modules.chair.chair;
import essentials.modules.claim.ClaimConfig;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.MainCommand;
import essentials.modules.commands.commands.BookCommand;
import essentials.modules.move.AFK;
import essentials.modules.move.MoveManager;
import essentials.modules.skull.SkullInventory;
import essentials.modules.teleport.TeleportListener;
import essentials.modules.visible.VisibleManager;
import essentials.modules.world.WorldConfig;
import essentials.player.PlayerListener;
import essentials.player.PlayerManager;
import essentials.player.PlayersYMLConfig;
import essentials.utilities.inventory.InventoryListener;

public class Main extends JavaPlugin {

	private static JavaPlugin plugin;
	private static LocalDateTime online;

	public void onEnable() {
		System.out.println("[CBM] is starting");
		plugin = this;
		online = LocalDateTime.now();

		System.out.println("[CBM] loading important configs");
		MainConfig.reload();
		LanguageConfig.load();
		Databases.load();
		
		System.out.println("[CBM] load important Commands & Modules");
		CommandManager.load();
		ModuleManager.load();

		System.out.println("[CBM] loading Listeners");
		
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

		System.out.println("[CBM] loading configs");
		BookCommand.saveDefaultBook();

		for (Player player : Bukkit.getOnlinePlayers())
			PlayerListener.join(player);

		if (!MainConfig.isFirstTime() && MainConfig.useBStats())
			bStats.enableBStats();

		reload();
		System.out.println("[CBM] start complete");
	}

	@Override
	public void onDisable() {
		//Used Runnable, because when one crashed the other could work
		unloadHelper(PlayerManager::unload);
		unloadHelper(WorldConfig::unload);
		unloadHelper(PlayersYMLConfig::unload);
		unloadHelper(Databases::unload);
		unloadHelper(ModuleManager::unload);
		super.onDisable();
	}

	public static void unloadHelper(Runnable runnable) {
		try {
			runnable.run();
		} catch (NoClassDefFoundError e) {
			System.out.println(Main.getPlugin().getName() + ": NoClassDefFoundError by unloading (did you overwrite .jar file)?");
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