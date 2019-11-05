package essentials.modules.MapPaint;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class MapPaintModule extends EModule {

	@Override
	public boolean load() {
		LoadMapPaint.load();
		ModuleManager.addListener(new MPListener(), this);
		CommandManager.register("paint", new RedirectTabExecutor(new MPCommand()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("paint");
		return true;
	}

	@Override
	public String getID() {
		return "MapPaint";
	}

}
