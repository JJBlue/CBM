package cbm.modules.spawn;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class SpawnModule extends EModule {

	@Override
	public boolean load() {
		CommandManager.register("spawn", new RedirectTabExecutor(new SpawnCommands(), 0, true));
		CommandManager.setAlias("setspawn", "spawn");
		CommandManager.setAlias("delspawn", "spawn");
		ModuleManager.addListener(new SpawnListener(), this);
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("spawn");
		return true;
	}

	@Override
	public String getID() {
		return "Spawn";
	}

}
