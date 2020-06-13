package cbm.modules.sudo;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class SudoModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new SudoListener(), this);
		CommandManager.register("sudo", new RedirectTabExecutor(new SudoCommand(), 0));
		CommandManager.setAlias("sudo-", "sudo");
		CommandManager.setAlias("sudo+", "sudo");
		CommandManager.setAlias("silent", "sudo");
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("sudo");
		return true;
	}

	@Override
	public String getID() {
		return "Sudo";
	}

}
