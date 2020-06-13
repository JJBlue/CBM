package cbm.modules.FlyThroughBlocks;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import cbm.language.LanguageConfig;
import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;

public class FTBModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new FTB(), this);
		
		CommandExecutor executor = (sender, cmd, label, args) -> {
			if (args.length <= 1) {
				if(!(sender instanceof Player)) return true;
				
				Player p = (Player) sender;
				
				if (FTB.toogle(p))
					LanguageConfig.sendMessage(sender, "wallghost.add-Player", p.getName());
				else
					LanguageConfig.sendMessage(sender, "wallghost.remove-Player", p.getName());
			} else {
				Player p2 = Bukkit.getPlayer(args[1]);
				if (p2 == null) return true;

				if (FTB.toogle(p2))
					LanguageConfig.sendMessage(sender, "wallghost.add-Player", args[1]);
				else
					LanguageConfig.sendMessage(sender, "wallghost.remove-Player", args[1]);
			}
			
			return true;
		};
		CommandManager.register("wallghost", CommandManager.getTabExecutor(executor));
		
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("wallghost");
		return true;
	}

	@Override
	public String getID() {
		return "FlyTroughBlocks";
	}

}
