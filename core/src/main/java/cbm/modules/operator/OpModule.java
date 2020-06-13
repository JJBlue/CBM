package cbm.modules.operator;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

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
