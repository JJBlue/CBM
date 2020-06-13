package cbm.modules.pluginmanager;

import java.io.File;
import java.net.URLClassLoader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;

import cbm.utilitiesvr.bukkit.BukkitUtilities;
import components.reflection.MethodReflection;
import components.reflection.ObjectReflection;

public class DisableEnable implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 2) return true;

		String pluginname = args[1];
		final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginname);

		switch (args[0].toLowerCase()) {
			case "enable":

				if (plugin == null) break;
				Bukkit.getServer().getPluginManager().enablePlugin(plugin);
				sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde aktiviert!");

				break;

			case "disable":

				if (plugin == null) break;
				Bukkit.getServer().getPluginManager().disablePlugin(plugin);
				sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde deaktiviert!");

				break;

			case "load":

				try {
					PluginManager manager = Bukkit.getServer().getPluginManager();
					File file = new File("./plugins" + args[1] + ".jar");
					if (!file.exists()) break;

					manager.loadPlugin(file);
					manager.enablePlugin(Bukkit.getPluginManager().getPlugin(args[1]));
					sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + args[1] + " wurde geladen!");
				} catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e2) {
					e2.printStackTrace();
					sender.sendMessage("§4Hier ist etwas schiefgelaufen. Siehe Console");
				}

				break;

			case "unload":

				if (plugin == null) break;

				try {
					unloadPlugin(plugin);
					sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde entfernt!");
				} catch (Exception e1) {
					e1.printStackTrace();
					sender.sendMessage("§4Hier ist etwas schiefgelaufen. Siehe Console");
				}
				break;

			case "reload":

				if (plugin == null) break;

				try {
					unloadPlugin(plugin);
					sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde entfernt!");

					PluginManager manager = Bukkit.getServer().getPluginManager();
					manager.loadPlugin(new File(plugin.getDataFolder() + ".jar"));
					manager.enablePlugin(Bukkit.getPluginManager().getPlugin(plugin.getName()));
					sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde geladen!");
				} catch (Exception e) {
					e.printStackTrace();
					sender.sendMessage("§4Hier ist etwas schiefgelaufen. Siehe Console");
				}

				break;
		}

		return true;
	}

	private boolean unloadPlugin(Plugin plugin) throws Exception {
		if (plugin == null) return false;

		System.out.print("Try disable Plugin " + plugin.getName());
		PluginManager manager = Bukkit.getServer().getPluginManager();
		SimplePluginManager spmanager = (SimplePluginManager) manager;
		if (spmanager == null) return false;
		
		System.out.print("Remove commands of Plugin " + plugin.getName());
		
		SimpleCommandMap commandMap = BukkitUtilities.getSimpleCommandMap();
		Map<?, ?> knownCommands = (Map<?, ?>) ObjectReflection.getObject("knownCommands", commandMap);

		plugin.getDescription().getCommands().forEach((a, b) -> knownCommands.remove(a));

		manager.disablePlugin(plugin);

		@SuppressWarnings("unchecked")
		List<Plugin> plugins = (List<Plugin>) ObjectReflection.getObject("plugins", spmanager);

		if (plugins != null) {
			plugins.remove(plugin);
		}

		System.out.print("Removing lookup Name of Plugin " + plugin.getName());
		@SuppressWarnings("unchecked")
		Map<String, Plugin> lookupNames = (Map<String, Plugin>) ObjectReflection.getObject("lookupNames", spmanager);

		if (lookupNames != null) {
			lookupNames.values().removeIf(p -> p == plugin);
		}

		ClassLoader classLoader = (ClassLoader) MethodReflection.callMethod(plugin, "getClassLoader"); //Warning sometimes fail when dependency was unloaded
		if(classLoader instanceof URLClassLoader) {
			((URLClassLoader) classLoader).close();
		}

		System.gc();
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("enable");
			returnArguments.add("disable");
			returnArguments.add("reload");
			returnArguments.add("load");
			returnArguments.add("unload");

		} else {
			switch (args[0]) {
				default:
					for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
						returnArguments.add(plugin.getName());

					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
