package essentials.modules.commandspy;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

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
