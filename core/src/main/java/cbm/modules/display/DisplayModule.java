package cbm.modules.display;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;

public class DisplayModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new DisplayListener(), this);
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public String getID() {
		return "Display";
	}

}
