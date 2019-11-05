package essentials.modules.troll;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;
import essentials.modules.troll.control.ControlListener;

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
