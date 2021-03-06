package cbm.modules.alias;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cbm.config.ConfigHelper;
import cbm.config.MainConfig;
import cbm.language.LanguageConfig;
import cbm.main.Main;
import cbm.utilities.permissions.PermissionHelper;
import cbm.utilities.placeholder.PlaceholderFormatter;
import cbm.utilitiesvr.bukkit.BukkitUtilities;

public class CustomAlias {
	static File file;
	static FileConfiguration fileConfiguration;

	static final String prefixCommands = "Commands.";

	public static void load() {
		file = new File(MainConfig.getDataFolder(), "CustomAlias.yml");

		if (!file.exists())
			ConfigHelper.extractDefaultConfigs("CustomAlias", "CustomAlias.yml");

		fileConfiguration = YamlConfiguration.loadConfiguration(file);

		ConfigurationSection section = fileConfiguration.getConfigurationSection("Commands");
		if (section != null) {
			Set<String> keys = section.getKeys(false);
			for (String c : keys) {
				if (fileConfiguration.getBoolean(prefixCommands + c + ".enable"))
					registerCommand(c);
			}
		}

		fileConfiguration.options().copyDefaults(true);
		try {
			fileConfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void registerCommand(String name) {
		BukkitUtilities.registerCommand("cbm", new Command(name) {
			@Override
			public boolean execute(CommandSender sender, String commandLabel, String[] args) {
				String OptionalCommandBeginn = PermissionHelper.getPluginDefaultCommand() + ":";
				if(!commandLabel.equals(OptionalCommandBeginn) && commandLabel.startsWith(OptionalCommandBeginn))
					commandLabel = commandLabel.substring(OptionalCommandBeginn.length());
				
				boolean useExtraPermission = fileConfiguration.getBoolean(prefixCommands + commandLabel + ".extraPermission");

				if (useExtraPermission && !sender.hasPermission("CustomAlias.command." + commandLabel)) return true;

				List<?> listCommands = fileConfiguration.getList(prefixCommands + commandLabel + ".cmds");
				if(listCommands == null) return true;
				
				final List<?> commands = new LinkedList<>(listCommands);
				CustomAlias.execute(sender, commands, args);

				return true;
			}

			@Override
			public List<String> tabComplete(CommandSender sender, String commandLabel, String[] args) throws IllegalArgumentException {
				{ //Tab Complete for one Command
					String OptionalCommandBeginn = PermissionHelper.getPluginDefaultCommand() + ":";
					if(!commandLabel.equals(OptionalCommandBeginn) && commandLabel.startsWith(OptionalCommandBeginn))
						commandLabel = commandLabel.substring(OptionalCommandBeginn.length());
					
					final List<?> commands = fileConfiguration.getList(prefixCommands + commandLabel + ".cmds");
					if(commands == null) return new ArrayList<>();
					
					if (commands.size() == 1) {
						if (commands.get(0) instanceof String) {
							StringBuilder command = new StringBuilder((String) commands.get(0));

							if (command.toString().startsWith("@c"))
								command = new StringBuilder(command.substring(3, command.length()));

							boolean dots3 = command.toString().endsWith(" ...");
							if (dots3)
								command = new StringBuilder(command.substring(0, command.length() - 4));

							command = new StringBuilder(PlaceholderFormatter.setPlaceholders(sender, command.toString()));

							if (command.toString().contains("$" + args.length)) {
								command = new StringBuilder(command.substring(0, command.indexOf("$" + args.length)));
							} else {
								for (int i = args.length; i > 0; i--) {
									if (command.toString().contains("$" + i))
										command = new StringBuilder(command.toString().replace("$" + i, args[i - 1]));
									else if (dots3)
										command.append(" ").append(args[i - 1]);
								}
							}

							String[] cAndArgs = command.toString().split(" ");
							String label = cAndArgs[0];

							if (command.toString().endsWith(" ")) {
								for (int i = 0; i < cAndArgs.length; i++) {
									if (i == cAndArgs.length - 1)
										cAndArgs[i] = "";
									else
										cAndArgs[i] = cAndArgs[i + 1];
								}
							} else
								cAndArgs = Arrays.copyOfRange(cAndArgs, 1, cAndArgs.length);

							PluginCommand pluginCommand = Main.getPlugin().getCommand(label);

							if (pluginCommand != null && pluginCommand.getTabCompleter() != null) {
								List<String> ret = pluginCommand.getTabCompleter().onTabComplete(sender, pluginCommand, commandLabel, cAndArgs);
								if(ret == null)
									return new ArrayList<>();
								return ret;
							}
						}
					}
				}

				List<String> returnA = new LinkedList<>();

				List<?> tabCompletes = fileConfiguration.getList(prefixCommands + commandLabel + ".tabComplete." + args.length);
				if (tabCompletes == null) return new ArrayList<>();

				for (Object obj : tabCompletes) {
					if (!(obj instanceof String)) continue;

					String tab = (String) obj;

					if (tab.equalsIgnoreCase("@p"))
						returnA.add(sender.getName());
					else if (tab.contains("%"))
						returnA.add(PlaceholderFormatter.setPlaceholders(sender, tab));
					else
						returnA.add(tab);
				}

				returnA.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

				returnA.sort(Comparator.naturalOrder());

				return returnA;
			}

			@Override
			public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
				return tabComplete(sender, alias, args);
			}
		});
	}

	/*
	 * 	@c
	 * 	$[argsNumber] : Index of Arguement, start by 1
	 * 	... : Could only stand of the end of a command. Replaced by all unused Arguements
	 * 	!delay : In Ticks 20ticks = 1s
	 *
	 * 	Examples:
	 * 	@c say $2 in $4 with ...
	 * 	!delay 5
	 */
	public static void execute(CommandSender sender, List<?> commands, String[] args) {
		while (!commands.isEmpty()) {
			Object obj = commands.remove(0);

			if (!(obj instanceof String)) continue;

			StringBuilder command = new StringBuilder((String) obj);
			boolean sendOverConsol = false;

			if (command.toString().startsWith("!delay")) {
				String[] argsCommand = command.toString().split(" ");
				if (argsCommand.length < 2) continue;

				try {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> CustomAlias.execute(sender, commands, args), Long.parseLong(argsCommand[1]));

					return;
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}

				continue;
			} else if (command.toString().startsWith("@c")) {
				command = new StringBuilder(command.substring(3, command.length()));
				sendOverConsol = true;
			}

			boolean dots3 = command.toString().endsWith(" ...");
			if (dots3)
				command = new StringBuilder(command.substring(0, command.length() - 4));

			command = new StringBuilder(PlaceholderFormatter.setPlaceholders(sender, command.toString()));

			for (int i = args.length; i > 0; i--) {
				if (command.toString().contains("$" + i))
					command = new StringBuilder(command.toString().replace("$" + i, args[i - 1]));
				else if (dots3)
					command.append(" ").append(args[i - 1]);
			}

			if (sendOverConsol)
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.toString());
			else
				Bukkit.dispatchCommand(sender, command.toString());
		}
	}

	public static void unload() {
		file = null;
		fileConfiguration = null;
	}
}
