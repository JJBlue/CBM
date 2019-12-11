package essentials.modules.ban;

import essentials.modulemanager.EModule;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class BanModule extends EModule {

	@Override
	public boolean load() {
		CommandManager.register("ban", new RedirectTabExecutor(new BanCommand(), 0));
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
