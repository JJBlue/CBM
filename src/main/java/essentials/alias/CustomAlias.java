package essentials.alias;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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

import essentials.config.MainConfig;
import essentials.language.LanguageConfig;
import essentials.main.Main;
import essentials.placeholder.PlaceholderFormatter;
import essentials.utilities.BukkitUtilities;

public class CustomAlias {
	static File file;
	static FileConfiguration fileConfiguration;
	
	static final String prefixCommands = "Commands.";
	
	public static void load() {
		file = new File(MainConfig.getDataFolder(), "CustomAlias.yml");
		
		if(!file.exists()) {
			try {
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.append("# extraPermission: This command has a additional Permission. ExtraPermission: CustomAlias.command.<Command>");
				writer.append("\n# cmds: All Commands to be executed");
				writer.append("\n# 	- !delay <Ticks> : Wait x Ticks to continue. 20 Ticks = 1 s");
				writer.append("\n# 	- @c <Command> : Execute the command over the consol");
				writer.append("\n# 	- $1 : Replaced by the arguement on this index. index >= 1");
				writer.append("\n# 	- ... : End of the command. Replaced by all unuesed arguements");
				writer.append("\n# 	Examples:");
				writer.append("\n# 	- '@c say $2 is $3 ...'");
				writer.append("\n# 	- !delay 100");
				writer.append("\n# 	- say 5s later");
				writer.append("\n" + prefixCommands.substring(0, prefixCommands.length() - 1) + ":");
				writer.append("\n  test:");
				writer.append("\n    enable: false");
				writer.append("\n    extraPermission: false");
				writer.append("\n    cmds:");
				writer.append("\n    - '@c say $2 is $3 ...'");
				writer.append("\n    - '!delay 100'");
				writer.append("\n    - 'say 5s later'");
				writer.append("\n    tabComplete:");
				writer.append("\n      1:");
				writer.append("\n      - '@p'");
				writer.append("\n      - 'Meier'");
				writer.append("\n      2:");
				writer.append("\n      - a");
				writer.append("\n      3:");
				writer.append("\n      - b");
				writer.append("\n  test2:");
				writer.append("\n    enable: false");
				writer.append("\n    extraPermission: false");
				writer.append("\n    cmds:");
				writer.append("\n    - 'cbm timer ...'");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
		
		ConfigurationSection section = fileConfiguration.getConfigurationSection("Commands");
		if(section != null) {
			Set<String> keys = section.getKeys(false);
			for(String c : keys) {
				if(fileConfiguration.getBoolean(prefixCommands + c + ".enable"))
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
				boolean useExtraPermission = fileConfiguration.getBoolean(prefixCommands + commandLabel + ".extraPermission");
				
				if(useExtraPermission && !sender.hasPermission("CustomAlias.command." + commandLabel)) return true;
				
				final List<?> commands = new LinkedList<>(fileConfiguration.getList(prefixCommands + commandLabel + ".cmds"));
				CustomAlias.execute(sender, commands, args);
				
				return true;
			}
			
			@Override
			public List<String> tabComplete(CommandSender sender, String commandLabel, String[] args) throws IllegalArgumentException {
				{ //Tab Complete for one Command
					final List<?> commands = fileConfiguration.getList(prefixCommands + commandLabel + ".cmds");
					if(commands.size() == 1) {
						if(commands.get(0) instanceof String) {
							String command = (String) commands.get(0);
							
							if(command.startsWith("@c"))
								command = command.substring(3, command.length());
							
							boolean dots3 = command.endsWith(" ...");
							if(dots3)
								command = command.substring(0, command.length() - 4);
							
							command = PlaceholderFormatter.parseToString(sender, command);
							
							if(command.contains("$" + args.length)) {
								command = command.substring(0, command.indexOf("$" + args.length));
							} else {
								for(int i = args.length; i > 0; i--) {
									if(command.contains("$" + i))
										command = command.replace("$" + i, args[i - 1]);
									else if(dots3)
										command += " " + args[i - 1];
								}
							}
							
							String[] cAndArgs = command.split(" ");
							String label = cAndArgs[0];
							
							if(command.endsWith(" ")) {
								for(int i = 0; i < cAndArgs.length; i++) {
									if(i == cAndArgs.length - 1)
										cAndArgs[i] = "";
									else
										cAndArgs[i] = cAndArgs[i+1];
								}
							} else
								cAndArgs = Arrays.copyOfRange(cAndArgs, 1, cAndArgs.length);
							
							PluginCommand pluginCommand = Main.getPlugin().getCommand(label);
							
							if(pluginCommand != null && pluginCommand.getTabCompleter() != null)
								return pluginCommand.getTabCompleter().onTabComplete(sender, pluginCommand, commandLabel, cAndArgs);
						}
					}
				}
				
				List<String> returnA = new LinkedList<>();
				
				List<?> tabCompletes = fileConfiguration.getList(prefixCommands + commandLabel + ".tabComplete." + args.length);
				if(tabCompletes == null) return null;
				
				for(Object obj : tabCompletes) {
					if(!(obj instanceof String)) continue;
					
					String tab = (String) obj;
					
					if(tab.equalsIgnoreCase("@p"))
						returnA.add(sender.getName());
					else if(tab.contains("%"))
						returnA.add(PlaceholderFormatter.parseToString(sender, tab));
					else
						returnA.add(tab);
				}
				
				returnA.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
				
				returnA.sort((s1, s2) -> {
					return s1.compareTo(s2);
				});
				
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
		while(!commands.isEmpty()) {
			Object obj = commands.remove(0);
			
			if(!(obj instanceof String)) continue;
			
			String command = (String) obj;
			boolean sendOverConsol = false;
			
			if(command.startsWith("!delay")) {
				String[] argsCommand = command.split(" ");
				if(argsCommand.length < 2) continue;
				
				try {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
						CustomAlias.execute(sender, commands, args);
					}, Long.parseLong(argsCommand[1]));
					
					return;
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				
				continue;
			} else if(command.startsWith("@c")) {
				command = command.substring(3, command.length());
				sendOverConsol = true;
			}
			
			boolean dots3 = command.endsWith(" ...");
			if(dots3)
				command = command.substring(0, command.length() - 4);
			
			command = PlaceholderFormatter.parseToString(sender, command);
			
			for(int i = args.length; i > 0; i--) {
				if(command.contains("$" + i))
					command = command.replace("$" + i, args[i - 1]);
				else if(dots3)
					command += " " + args[i - 1];
			}
			
			if(sendOverConsol)
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			else
				Bukkit.dispatchCommand(sender, command);
		}
	}
	
	public static void unload() {
		file = null;
		fileConfiguration = null;
	}
}
