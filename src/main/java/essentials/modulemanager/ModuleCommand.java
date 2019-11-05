package essentials.modulemanager;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import essentials.language.LanguageConfig;

public class ModuleCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length < 0) return true;
		
		switch (args[0].toLowerCase()) {
			case "enable": {
				
				if(args.length < 2) break;
				
				Module module = ModuleManager.getModule(args[1]);
				ModuleManager.enable(module);
				ModuleConfig.setAutoload(module.getID(), true);
				ModuleConfig.save();
				LanguageConfig.sendMessage(sender, "module.enable", module.getID());
				
				break;
			}
			case "disable": {
				
				if(args.length < 2) break;
				
				Module module = ModuleManager.getModule(args[1]);
				ModuleManager.disable(module);
				ModuleConfig.setAutoload(module.getID(), false);
				ModuleConfig.save();
				LanguageConfig.sendMessage(sender, "module.disable", module.getID());
				
				break;
			}
			case "list": {
				
				StringBuilder enabled = new StringBuilder();
				StringBuilder disabled = new StringBuilder();
				List<Module> modules;
				
				synchronized (ModuleManager.modules) {
					modules = new LinkedList<>(ModuleManager.modules.values());
				}
				
				for(Module module : modules) {
					if(module.isLoaded()) {
						if(enabled.length() > 0) {
							enabled.append(", ");
						}
						
						enabled.append(module.getID());
					} else {
						if(disabled.length() > 0) {
							disabled.append(", ");
						}
						
						disabled.append(module.getID());
					}
				}
				
				LanguageConfig.sendMessage(sender, "module.list", enabled.toString(), disabled.toString());
				
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("enable");
			returnArguments.add("disable");

		} else {
			
			switch (args[0].toLowerCase()) {
				case "enable":
					synchronized (ModuleManager.modules) {
						for (Module module : ModuleManager.modules.values()) {
							if(!module.isLoaded())
								returnArguments.add(module.getID());
						}
					}
					break;
				case "disable":
					synchronized (ModuleManager.modules) {
						for (Module module : ModuleManager.modules.values()) {
							if(module.isLoaded())
								returnArguments.add(module.getID());
						}
					}
					break;
			}
			
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
