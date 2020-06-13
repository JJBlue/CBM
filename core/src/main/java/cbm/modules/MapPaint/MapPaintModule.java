package cbm.modules.MapPaint;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

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
