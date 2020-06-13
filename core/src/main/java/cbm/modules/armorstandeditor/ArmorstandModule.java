package cbm.modules.armorstandeditor;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

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
