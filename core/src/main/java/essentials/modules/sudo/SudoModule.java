package essentials.modules.sudo;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

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
