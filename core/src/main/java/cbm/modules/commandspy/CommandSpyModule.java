package cbm.modules.commandspy;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class CommandSpyModule extends EModule {
	
	@Override
	public boolean load() {
		ModuleManager.addListener(new CommandSpyListener(), this);
		CommandManager.register("commandspy", new RedirectTabExecutor(new CommandSpyCommand()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("commandspy");
		return true;
	}
	
	@Override
	public String getID() {
		return "CommandSpy";
	}

}
