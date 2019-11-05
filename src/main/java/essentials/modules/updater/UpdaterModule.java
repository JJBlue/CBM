package essentials.modules.updater;

import essentials.modulemanager.EModule;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class UpdaterModule extends EModule {

	@Override
	public boolean load() {
		CommandManager.register("updater", new RedirectTabExecutor(new UpdaterCommand()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("updater");
		return true;
	}

	@Override
	public String getID() {
		return "Updater";
	}

}
