package cbm.modules.kits;

import cbm.modulemanager.EModule;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class KitsModule extends EModule {
	@Override
	public boolean load() {
		KitManager.load();
		CommandManager.register("kits", new RedirectTabExecutor(new KitsCommands()));
		return true;
	}

	@Override
	public boolean unload() {
		KitManager.unload();
		CommandManager.unregister("kits");
		return true;
	}

	@Override
	public String getID() {
		return "Kits";
	}
}
