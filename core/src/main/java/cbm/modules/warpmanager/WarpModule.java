package cbm.modules.warpmanager;

import cbm.modulemanager.EModule;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class WarpModule extends EModule {

	@Override
	public boolean load() {
		WarpManager.load();
		CommandManager.register("warp", new RedirectTabExecutor(new warpCommands(), 0, true));
		CommandManager.setAlias("setwarp", "warp");
		CommandManager.setAlias("delwarp", "warp");
		CommandManager.setAlias("editwarp", "warp");
		CommandManager.setAlias("savewarp", "warp");
		return true;
	}

	@Override
	public boolean unload() {
		WarpManager.unload();
		CommandManager.unregister("warp");
		return true;
	}

	@Override
	public String getID() {
		return "Warp";
	}

}
