package essentials.main;

import java.io.File;
import java.time.LocalDateTime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import essentials.config.MainConfig;
import essentials.database.Databases;
import essentials.language.LanguageConfig;
import essentials.modules.ColorListener;
import essentials.modules.Deop;
import essentials.modules.Join;
import essentials.modules.MainListener;
import essentials.modules.ChatVerbesserung.ChatVerbesserung;
import essentials.modules.FlyThroughBlocks.FTB;
import essentials.modules.MapPaint.LoadMapPaint;
import essentials.modules.MapPaint.MPListener;
import essentials.modules.alias.CustomAlias;
import essentials.modules.armorstandeditor.ArmorstandListener;
import essentials.modules.chair.chair;
import essentials.modules.commandonitemstack.CoIListener;
import essentials.modules.commandonobject.CommandListener;
import essentials.modules.commandonobject.CommandOnBlock;
import essentials.modules.commands.MainCommand;
import essentials.modules.commands.bookCommand;
import essentials.modules.commandspy.CommandSpyListener;
import essentials.modules.debugstick.DebugStickListener;
import essentials.modules.display.DisplayListener;
import essentials.modules.move.AFK;
import essentials.modules.move.MoveManager;
import essentials.modules.pluginmanager.DisableEnable;
import essentials.modules.skull.SkullInventory;
import essentials.modules.spawn.SpawnListener;
import essentials.modules.tablist.Tablist;
import essentials.modules.teleport.TeleportListener;
import essentials.modules.timer.TimerConfig;
import essentials.modules.timer.TimerListener;
import essentials.modules.trade.TradeListener;
import essentials.modules.troll.TrollListener;
import essentials.modules.troll.control.ControlListener;
import essentials.modules.updater.UpdaterConfig;
import essentials.modules.updater.UpdaterServerManager;
import essentials.modules.visible.VisibleManager;
import essentials.modules.warpmanager.WarpManager;
import essentials.modules.world.WorldConfig;
import essentials.player.PlayerListener;
import essentials.player.PlayerManager;
import essentials.player.PlayersYMLConfig;
import essentials.utilities.inventory.InventoryListener;

public class Main extends JavaPlugin implements Listener {

	private static JavaPlugin plugin;
	private static LocalDateTime online;

	public void onEnable() {
		System.out.println("[CBM] is starting");
		plugin = this;
		online = LocalDateTime.now();

		File file = new File(getDataFolder().getParentFile(), "Allgemein");
		file.renameTo(getDataFolder());

		System.out.println("[CBM] loading important configs");
		MainConfig.reload();
		LanguageConfig.load();
		Databases.load();

		System.out.println("[CBM] loading Listeners");
		Bukkit.getPluginManager().registerEvents(new FTB(), this);
		Bukkit.getPluginManager().registerEvents(new Deop(), this);
		Bukkit.getPluginManager().registerEvents(new ColorListener(), this);
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
		Bukkit.getPluginManager().registerEvents(new MPListener(), this);
		Bukkit.getPluginManager().registerEvents(new chair(), this);
		Bukkit.getPluginManager().registerEvents(new Join(), this);
		Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
		Bukkit.getPluginManager().registerEvents(new TrollListener(), this);
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
		Bukkit.getPluginManager().registerEvents(new DisplayListener(), this);
		Bukkit.getPluginManager().registerEvents(new CoIListener(), this);
		Bukkit.getPluginManager().registerEvents(new SpawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new MoveManager(), this);
		Bukkit.getPluginManager().registerEvents(new AFK(), this);
		Bukkit.getPluginManager().registerEvents(new VisibleManager(), this);
		Bukkit.getPluginManager().registerEvents(new ControlListener(), this);

		{
			MainCommand mainCommand = new MainCommand();
			this.getCommand("cbm").setExecutor(mainCommand);
			this.getCommand("cbm").setTabCompleter(mainCommand);
		}

		System.out.println("[CBM] loading configs");
		WarpManager.load();
		bookCommand.saveDefaultBook();
		CommandOnBlock.load();
		LoadMapPaint.load();
		DisableEnable.disableEnable.nothing(); //Lade Klasse, damit wenn .jar uberschrieben. Die load/unload Methoden funktionieren

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
		unloadHelper(WarpManager::unload);
		unloadHelper(Tablist::unload);
		unloadHelper(CommandOnBlock::unload);
		unloadHelper(PlayerManager::unload);
		unloadHelper(WorldConfig::unload);
		unloadHelper(PlayersYMLConfig::unload);

		unloadHelper(() -> {
			if (UpdaterConfig.isInstallOnShutdown())
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
		if (sender.hasPermission("cv") && cmd.getName().equalsIgnoreCase("cv"))
			ChatVerbesserung.onCommand(sender, cmd, cmdLabel, args);

		return false;
	}

	public static void reload() {
		CustomAlias.load();
		UpdaterServerManager.load();
		WorldConfig.load();
		PlayersYMLConfig.load();
		LanguageConfig.load();
		Tablist.load();
		LoadMapPaint.load();
		TimerConfig.load();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static LocalDateTime getOnline() {
		return LocalDateTime.from(online);
	}
}
