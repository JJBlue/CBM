package cbm.modules.troll;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;
import cbm.modules.troll.control.ControlListener;

public class TrollModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new TrollListener(), this);
		ModuleManager.addListener(new ControlListener(), this);
		CommandManager.register("troll", new RedirectTabExecutor(new TrollCommands()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("troll");
		return true;
	}

	@Override
	public String getID() {
		return "Troll";
	}

}
