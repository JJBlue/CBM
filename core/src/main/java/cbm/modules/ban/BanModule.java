package cbm.modules.ban;

import cbm.modulemanager.EModule;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class BanModule extends EModule {

	@Override
	public boolean load() {
		CommandManager.register("ban", new RedirectTabExecutor(new BanCommand(), 0, true));
		CommandManager.setAlias("unban", "ban");
		CommandManager.setAlias("tempban", "ban");
		CommandManager.setAlias("checkban", "ban");
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("ban");
		return true;
	}

	@Override
	public String getID() {
		return "Ban";
	}

}
