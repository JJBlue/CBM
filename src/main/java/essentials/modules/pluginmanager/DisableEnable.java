package essentials.modules.pluginmanager;

import components.reflections.SimpleReflection;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.*;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DisableEnable implements CommandExecutor, TabCompleter {
	public final static DisableEnable disableEnable;

	static {
		disableEnable = new DisableEnable();
	}

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

		PluginManager manager = Bukkit.getServer().getPluginManager();
		SimplePluginManager spmanager = (SimplePluginManager) manager;
		if (spmanager == null) return false;

		manager.disablePlugin(plugin);

		List<?> plugins = (List<?>) SimpleReflection.getObject("plugins", spmanager);

		if (plugins != null)
			plugins.remove((Object) plugin);

		Map<?, ?> lookupNames = (Map<?, ?>) SimpleReflection.getObject("lookupNames", spmanager);

		if (lookupNames != null) {
			lookupNames.remove(plugin.getName());

			List<Object> deleteList = new LinkedList<>();

			lookupNames.forEach((a, b) -> {
				if (b == plugin)
					deleteList.add(a);
			});
			for (Object obj : deleteList)
				lookupNames.remove(obj);
		}

		Map<?, ?> fileAssociations = (Map<?, ?>) SimpleReflection.getObject("fileAssociations", spmanager);

		if (fileAssociations != null) {
			fileAssociations.forEach((a, b) -> {
				try {
					((Map<?, ?>) SimpleReflection.getObject("classes", b)).clear();

					List<?> loaders = (List<?>) SimpleReflection.getObject("loaders", b);
					List<Object> deleteList = new LinkedList<>();

					for (Object a1 : loaders) {
						if (a1 == plugin.getClass().getClassLoader()) {
							deleteList.add(a1);
							((URLClassLoader) a1).close();
						}
					}

					for (Object obj : deleteList)
						loaders.remove(obj);

				} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		}

		SimpleCommandMap commandMap = (SimpleCommandMap) SimpleReflection.getObject("commandMap", spmanager);
		Map<?, ?> knownCommands = (Map<?, ?>) SimpleReflection.getObject("knownCommands", commandMap);

		plugin.getDescription().getCommands().forEach((a, b) -> knownCommands.remove(a));

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

	public void nothing() {}
}
