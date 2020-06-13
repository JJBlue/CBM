package cbm.modules.commandonitemstack;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class CoIModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new CoIListener(), this);
		CommandManager.register("coi", new RedirectTabExecutor(new CoICommands()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("coi");
		return true;
	}

	@Override
	public String getID() {
		return "CommandOnItemStack";
	}

}
