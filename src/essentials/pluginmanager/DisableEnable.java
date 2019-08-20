package essentials.pluginmanager;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import components.reflections.SimpleReflection;

public class DisableEnable implements CommandExecutor, TabCompleter{
	public final static DisableEnable disableEnable;
	
	static {
		disableEnable = new DisableEnable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 2 || !sender.hasPermission("all.pluginmanager")) return true;
		
		String pluginname = args[1];
		final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginname);
		
		System.out.println(args[0].toLowerCase());
		
		switch(args[0].toLowerCase()) {
			case "enable":
				
				Bukkit.getServer().getPluginManager().enablePlugin(plugin);
				sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde aktiviert!");
				
				break;
				
			case "disable":
				
				Bukkit.getServer().getPluginManager().disablePlugin(plugin);
				sender.sendMessage("§4[Disable/Enable] §rDas Plugin " + plugin + " wurde deaktiviert!");
				
				break;
				
			case "reload":
				
				try {
					unloadPlugin(plugin);
					
					PluginManager manager = Bukkit.getServer().getPluginManager();
					manager.loadPlugin(new File(plugin.getDataFolder() + ".jar"));
			    	manager.enablePlugin(Bukkit.getPluginManager().getPlugin(plugin.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
		}
		
		return true;
	}
	
//	final static long ss = System.currentTimeMillis();
	private boolean unloadPlugin(Plugin plugin) throws Exception {
//		Bukkit.broadcastMessage(ss + "");
		if(plugin == null) return false;
		
    	PluginManager manager = Bukkit.getServer().getPluginManager();
    	SimplePluginManager spmanager = (SimplePluginManager) manager;
    	if(spmanager == null) return false;
    	
    	manager.disablePlugin(plugin);
    	
    	List<?> plugins = (List<?>) SimpleReflection.getObject("plugins", spmanager);
    	
    	if (plugins != null)
        	plugins.remove((Object) plugin);
    	
    	Map<?,?> lookupNames = (Map<?,?>) SimpleReflection.getObject("lookupNames", spmanager);
    	
    	if (lookupNames != null) {
        	lookupNames.remove(plugin.getName());
        	
        	List<Object> deleteList = new LinkedList<>();
        	
        	lookupNames.forEach((a, b) -> {
        		if(b == plugin)
        			deleteList.add(a);
        	});
        	for(Object obj : deleteList)
        		lookupNames.remove(obj);
    	}
    	
    	Map<?,?> fileAssociations = (Map<?,?>) SimpleReflection.getObject("fileAssociations", spmanager);
    	
    	if (fileAssociations != null) {
    		fileAssociations.forEach((a, b) -> {
				try {
					((Map<?, ?>) SimpleReflection.getObject("classes", b)).clear();
					
    	        	List<?> loaders = (List<?>) SimpleReflection.getObject("loaders", b);
    	        	List<Object> deleteList = new LinkedList<>();
    	        	
    	        	for(Object a1 : loaders) {
    	        		if(a1 == plugin.getClass().getClassLoader()) {
    	        			deleteList.add(a1);
							((URLClassLoader) a1).close();
    	        			
    	        			Bukkit.broadcastMessage("found");
    	        		}
    	        	}
    	        	
    	        	for(Object obj : deleteList)
    	        		loaders.remove(obj);
    	        	
				} catch (IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
    		});
    	}
    	
    	SimpleCommandMap commandMap = (SimpleCommandMap) SimpleReflection.getObject("commandMap", spmanager);
    	Map<?, ?> knownCommands = (Map<?, ?>) SimpleReflection.getObject("knownCommands", commandMap);
    	
    	plugin.getDescription().getCommands().forEach((a, b) -> {
    		knownCommands.remove(a);
    	});
    	
    	return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("enable");
			returnArguments.add("disable");
			returnArguments.add("reload");
			
		} else {
			switch (args[0]) {
				default:
					for(Plugin plugin: Bukkit.getPluginManager().getPlugins())
						returnArguments.add(plugin.getName());
					
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
}
