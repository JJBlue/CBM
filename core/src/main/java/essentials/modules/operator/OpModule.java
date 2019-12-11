package essentials.modules.operator;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class OpModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new OpListener(), this);
		CommandManager.register("op", new RedirectTabExecutor(new OpListener()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("op");
		return true;
	}

	@Override
	public String getID() {
		return "Operator";
	}

}
