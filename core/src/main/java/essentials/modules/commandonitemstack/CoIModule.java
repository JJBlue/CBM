package essentials.modules.commandonitemstack;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

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
