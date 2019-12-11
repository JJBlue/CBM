package essentials.modules.armorstandeditor;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class ArmorstandModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new ArmorstandListener(), this);
		CommandManager.register("armorstand", new RedirectTabExecutor(new ArmorstandCommands()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("armorstand");
		return true;
	}

	@Override
	public String getID() {
		return "ArmorstandEditor";
	}

}
