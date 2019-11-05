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
				LanguageConfig.sendMessage(sender, "module.enable", module.getID());
				
				break;
			}
			case "disable": {
				
				if(args.length < 2) break;
				
				Module module = ModuleManager.getModule(args[1]);
				ModuleManager.disable(module);
				LanguageConfig.sendMessage(sender, "module.disable", module.getID());
				
				break;
			}
			case "list":
				
				//TODO
				break;
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("enable");
			returnArguments.add("disable");
			returnArguments.add("list");

		} else {
			synchronized (ModuleManager.modules) {
				for (String id : ModuleManager.modules.keySet())
					returnArguments.add(id);
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}
}
