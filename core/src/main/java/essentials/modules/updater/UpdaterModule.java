package essentials.modules.updater;

import essentials.main.Main;
import essentials.modulemanager.EModule;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class UpdaterModule extends EModule {

	@Override
	public boolean load() {
		UpdaterServerManager.load();
		CommandManager.register("updater", new RedirectTabExecutor(new UpdaterCommand()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("updater");
		Main.unloadHelper(() -> {
			if (UpdaterConfig.isInstallOnShutdown())
				UpdaterServerManager.install();

			UpdaterServerManager.unload();
		});
		return true;
	}

	@Override
	public String getID() {
		return "Updater";
	}

}
