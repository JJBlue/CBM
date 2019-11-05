package essentials.modules.spawn;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class SpawnModule extends EModule {

	@Override
	public boolean load() {
		CommandManager.register("spawn", new RedirectTabExecutor(new SpawnCommands(), 0));
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
